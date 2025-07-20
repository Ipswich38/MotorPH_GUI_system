package com.example.MotorPH.GUI;

import java.io.*;
import java.util.*;

public class EmployeeCSVReader {

    // Updated file paths to use your actual files
    public static String DATA_FOLDER = "src/main/resources";
    public static String EMPLOYEE_FILE = "src/main/resources/Copy of MotorPH_Employee Data - Employee Details.csv";
    public static String ATTENDANCE_FILE = "src/main/resources/Copy of MotorPH_Employee Data - Attendance Record.csv";
    public static String LOGIN_FILE = "src/main/resources/login.csv";

    // Make sure data folder exists
    static {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Created data folder");
        }
        createLoginFileIfNeeded();
    }

    // Create login file if it doesn't exist (only login file since employee files already exist)
    public static void createFilesIfNeeded() {
        createLoginFileIfNeeded();
    }

    // Create login file with sample data
    public static void createLoginFileIfNeeded() {
        File file = new File(LOGIN_FILE);
        if (!file.exists()) {
            try {
                PrintWriter writer = new PrintWriter(file);

                // Write header
                writer.println("username,password,role");

                // Write login data
                writer.println("admin,admin123,Administrator");
                writer.println("hr,hr123,HR Manager");
                writer.println("manager,manager123,Manager");

                writer.close();
                System.out.println("Created login file with sample data");

            } catch (Exception e) {
                System.out.println("Error creating login file: " + e.getMessage());
            }
        }
    }

    // Read all employees from your actual file
    public static List<String[]> readEmployeeDetails() {
        List<String[]> employees = new ArrayList<String[]>();

        try {
            File file = new File(EMPLOYEE_FILE);
            if (!file.exists()) {
                System.out.println("Employee file not found: " + EMPLOYEE_FILE);
                return employees;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }

                // Handle CSV parsing with commas inside quotes
                String[] parts = parseCSVLine(line);
                if (parts.length >= 19) {
                    employees.add(parts);
                }
            }

            reader.close();
            System.out.println("Read " + employees.size() + " employees from actual file");

        } catch (Exception e) {
            System.out.println("Error reading employees: " + e.getMessage());
            e.printStackTrace();
        }

        return employees;
    }

    // Parse CSV line handling commas inside quotes
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<String>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString().trim());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }

        // Add the last field
        result.add(field.toString().trim());

        return result.toArray(new String[0]);
    }

    // Read all attendance from your actual file
    public static List<String[]> readEmployeeAttendance() {
        List<String[]> attendance = new ArrayList<String[]>();

        try {
            File file = new File(ATTENDANCE_FILE);
            if (!file.exists()) {
                System.out.println("Attendance file not found: " + ATTENDANCE_FILE);
                return attendance;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] parts = parseCSVLine(line);
                if (parts.length >= 6) {
                    attendance.add(parts);
                }
            }

            reader.close();
            System.out.println("Read " + attendance.size() + " attendance records from actual file");

        } catch (Exception e) {
            System.out.println("Error reading attendance: " + e.getMessage());
            e.printStackTrace();
        }

        return attendance;
    }

    // Read login data from file
    public static List<String[]> readLoginCredentials() {
        List<String[]> logins = new ArrayList<String[]>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] parts = line.split(",");
                logins.add(parts);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading login file: " + e.getMessage());
        }

        return logins;
    }

    // Write all employees to file (backup the original first)
    public static void writeEmployeeDetails(List<String[]> employees) {
        try {
            // Create backup of original file
            File originalFile = new File(EMPLOYEE_FILE);
            File backupFile = new File(EMPLOYEE_FILE + ".backup." + System.currentTimeMillis());

            if (originalFile.exists()) {
                copyFile(originalFile, backupFile);
                System.out.println("Created backup: " + backupFile.getName());
            }

            PrintWriter writer = new PrintWriter(originalFile);

            // Write header (based on your actual file structure)
            writer.println("Employee #,Last Name,First Name,Birthday,Address,Phone Number,SSS #,Philhealth #,TIN #,Pag-ibig #,Status,Position,Immediate Supervisor,Basic Salary,Rice Subsidy,Phone Allowance,Clothing Allowance,Gross Semi-monthly Rate,Hourly Rate");

            // Write all employees
            for (int i = 0; i < employees.size(); i++) {
                String[] emp = employees.get(i);
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < emp.length; j++) {
                    // Handle fields that might contain commas
                    String field = emp[j];
                    if (field.contains(",") || field.contains("\"")) {
                        field = "\"" + field.replace("\"", "\"\"") + "\"";
                    }
                    line.append(field);
                    if (j < emp.length - 1) {
                        line.append(",");
                    }
                }
                writer.println(line.toString());
            }

            writer.close();
            System.out.println("Saved " + employees.size() + " employees");

        } catch (Exception e) {
            System.out.println("Error saving employees: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to copy files
    private static void copyFile(File source, File dest) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        fis.close();
        fos.close();
    }

    // Add a new employee
    public static void appendEmployee(String[] employeeData) {
        List<String[]> employees = readEmployeeDetails();
        employees.add(employeeData);
        writeEmployeeDetails(employees);
        System.out.println("Added new employee: " + employeeData[2] + " " + employeeData[1]);
    }

    // Find employee by number
    public static String[] getEmployeeByNumber(String employeeNumber) {
        List<String[]> employees = readEmployeeDetails();

        for (int i = 0; i < employees.size(); i++) {
            String[] emp = employees.get(i);
            if (emp.length > 0 && emp[0].equals(employeeNumber)) {
                return emp;
            }
        }

        return null; // Not found
    }

    // Get attendance for one employee
    public static List<String[]> getAttendanceByEmployeeNumber(String employeeNumber) {
        List<String[]> allAttendance = readEmployeeAttendance();
        List<String[]> employeeAttendance = new ArrayList<String[]>();

        for (int i = 0; i < allAttendance.size(); i++) {
            String[] record = allAttendance.get(i);
            if (record.length > 0 && record[0].equals(employeeNumber)) {
                employeeAttendance.add(record);
            }
        }

        return employeeAttendance;
    }

    // Get attendance for one employee and month
    public static List<String[]> getAttendanceByEmployeeAndMonth(String employeeNumber, String month, String year) {
        List<String[]> employeeAttendance = getAttendanceByEmployeeNumber(employeeNumber);
        List<String[]> monthlyAttendance = new ArrayList<String[]>();

        String targetMonth = year + "-" + (month.length() == 1 ? "0" + month : month);

        for (int i = 0; i < employeeAttendance.size(); i++) {
            String[] record = employeeAttendance.get(i);
            if (record.length > 3 && record[3].startsWith(targetMonth)) {
                monthlyAttendance.add(record);
            }
        }

        return monthlyAttendance;
    }

    // Check if username and password are correct
    public static boolean validateLogin(String username, String password) {
        List<String[]> logins = readLoginCredentials();

        for (int i = 0; i < logins.size(); i++) {
            String[] login = logins.get(i);
            if (login.length >= 2 && login[0].equals(username) && login[1].equals(password)) {
                return true;
            }
        }

        return false;
    }

    // Count total employees
    public static int getTotalEmployeeCount() {
        return readEmployeeDetails().size();
    }

    // Get next employee number based on existing data
    public static String generateNextEmployeeNumber() {
        List<String[]> employees = readEmployeeDetails();
        int maxNumber = 0;

        for (int i = 0; i < employees.size(); i++) {
            String[] emp = employees.get(i);
            if (emp.length > 0) {
                try {
                    int empNum = Integer.parseInt(emp[0]);
                    if (empNum > maxNumber) {
                        maxNumber = empNum;
                    }
                } catch (Exception e) {
                    // Skip bad numbers
                }
            }
        }

        return String.valueOf(maxNumber + 1);
    }

    // Update an employee
    public static boolean updateEmployee(String employeeNumber, String[] updatedData) {
        List<String[]> employees = readEmployeeDetails();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).length > 0 && employees.get(i)[0].equals(employeeNumber)) {
                employees.set(i, updatedData);
                writeEmployeeDetails(employees);
                return true;
            }
        }

        return false;
    }

    // Delete an employee
    public static boolean deleteEmployee(String employeeNumber) {
        List<String[]> employees = readEmployeeDetails();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).length > 0 && employees.get(i)[0].equals(employeeNumber)) {
                employees.remove(i);
                writeEmployeeDetails(employees);
                return true;
            }
        }

        return false;
    }

    // Check if files exist
    public static boolean filesExist() {
        File empFile = new File(EMPLOYEE_FILE);
        File attFile = new File(ATTENDANCE_FILE);
        File loginFile = new File(LOGIN_FILE);
        return empFile.exists() && attFile.exists() && loginFile.exists();
    }

    // Show where files are located
    public static void printFileLocations() {
        System.out.println("=== FILE LOCATIONS ===");
        System.out.println("Data folder: " + new File(DATA_FOLDER).getAbsolutePath());
        System.out.println("Employee file: " + new File(EMPLOYEE_FILE).getAbsolutePath());
        System.out.println("Attendance file: " + new File(ATTENDANCE_FILE).getAbsolutePath());
        System.out.println("Login file: " + new File(LOGIN_FILE).getAbsolutePath());

        System.out.println("Employee file exists: " + new File(EMPLOYEE_FILE).exists());
        System.out.println("Attendance file exists: " + new File(ATTENDANCE_FILE).exists());
        System.out.println("Login file exists: " + new File(LOGIN_FILE).exists());
        System.out.println("=====================");
    }

    // Get all employees with their basic info for quick display
    public static List<String[]> getAllEmployeesBasicInfo() {
        List<String[]> employees = readEmployeeDetails();
        List<String[]> basicInfo = new ArrayList<String[]>();

        for (String[] emp : employees) {
            if (emp.length >= 19) {
                String[] basic = new String[6];
                basic[0] = emp[0]; // Employee #
                basic[1] = emp[1] + ", " + emp[2]; // Full Name
                basic[2] = emp[11]; // Position
                basic[3] = emp[10]; // Status
                basic[4] = emp[13]; // Basic Salary
                basic[5] = emp[12]; // Supervisor
                basicInfo.add(basic);
            }
        }

        return basicInfo;
    }
}