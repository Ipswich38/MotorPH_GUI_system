package com.example.MotorPH.GUI;

import java.util.*;

public class EmployeeDatabase {

    // Simple numbers to remember which column is which
    public static final int EMP_NUMBER = 0;
    public static final int LAST_NAME = 1;
    public static final int FIRST_NAME = 2;
    public static final int BIRTHDAY = 3;
    public static final int ADDRESS = 4;
    public static final int PHONE_NUMBER = 5;
    public static final int SSS_NUMBER = 6;
    public static final int PHILHEALTH_NUMBER = 7;
    public static final int TIN_NUMBER = 8;
    public static final int PAGIBIG_NUMBER = 9;
    public static final int STATUS = 10;
    public static final int POSITION = 11;
    public static final int IMMEDIATE_SUPERVISOR = 12;
    public static final int BASIC_SALARY = 13;
    public static final int RICE_SUBSIDY = 14;
    public static final int PHONE_ALLOWANCE = 15;
    public static final int CLOTHING_ALLOWANCE = 16;
    public static final int GROSS_SEMI_MONTHLY_RATE = 17;
    public static final int HOURLY_RATE = 18;

    // Attendance column numbers
    public static final int ATT_EMP_NUMBER = 0;
    public static final int ATT_LAST_NAME = 1;
    public static final int ATT_FIRST_NAME = 2;
    public static final int ATT_DATE = 3;
    public static final int ATT_LOG_IN = 4;
    public static final int ATT_LOG_OUT = 5;

    // Get all employees - delegates to CSV reader
    public static List<String[]> getAllEmployees() {
        return EmployeeCSVReader.readEmployeeDetails();
    }

    // Get one employee by their number - delegates to CSV reader
    public static String[] getEmployeeByNumber(String employeeNumber) {
        return EmployeeCSVReader.getEmployeeByNumber(employeeNumber);
    }

    // Search employees by name
    public static List<String[]> searchEmployeesByName(String name) {
        List<String[]> allEmployees = getAllEmployees();
        List<String[]> results = new ArrayList<String[]>();
        String searchName = name.toLowerCase();

        for (int i = 0; i < allEmployees.size(); i++) {
            String[] employee = allEmployees.get(i);
            if (employee.length >= 3) {
                String firstName = employee[FIRST_NAME].toLowerCase();
                String lastName = employee[LAST_NAME].toLowerCase();
                String fullName = firstName + " " + lastName;

                if (firstName.contains(searchName) || lastName.contains(searchName) || fullName.contains(searchName)) {
                    results.add(employee);
                }
            }
        }

        return results;
    }

    // Get employees by their job position
    public static List<String[]> getEmployeesByPosition(String position) {
        List<String[]> allEmployees = getAllEmployees();
        List<String[]> results = new ArrayList<String[]>();
        String searchPos = position.toLowerCase();

        for (int i = 0; i < allEmployees.size(); i++) {
            String[] employee = allEmployees.get(i);
            if (employee.length > POSITION) {
                String empPosition = employee[POSITION].toLowerCase();
                if (empPosition.contains(searchPos)) {
                    results.add(employee);
                }
            }
        }

        return results;
    }

    // Add a new employee - delegates to CSV reader
    public static boolean addEmployee(String[] employeeData) {
        if (employeeData == null || employeeData.length < 19) {
            System.out.println("Employee data is not complete - need 19 fields");
            return false;
        }

        // Check if employee number already exists
        if (getEmployeeByNumber(employeeData[EMP_NUMBER]) != null) {
            System.out.println("Employee number already exists: " + employeeData[EMP_NUMBER]);
            return false;
        }

        EmployeeCSVReader.appendEmployee(employeeData);
        return true;
    }

    // Update an existing employee - delegates to CSV reader
    public static boolean updateEmployee(String employeeNumber, String[] updatedData) {
        return EmployeeCSVReader.updateEmployee(employeeNumber, updatedData);
    }

    // Delete an employee - delegates to CSV reader
    public static boolean deleteEmployee(String employeeNumber) {
        return EmployeeCSVReader.deleteEmployee(employeeNumber);
    }

    // Count total employees - delegates to CSV reader
    public static int getTotalEmployees() {
        return EmployeeCSVReader.getTotalEmployeeCount();
    }

    // Get next employee number - delegates to CSV reader
    public static String generateNextEmployeeNumber() {
        return EmployeeCSVReader.generateNextEmployeeNumber();
    }

    // Get data for the employee table (for backward compatibility)
    public static Object[][] getAllEmployeesForTable() {
        List<String[]> employees = getAllEmployees();
        Object[][] data = new Object[employees.size()][7];

        for (int i = 0; i < employees.size(); i++) {
            String[] emp = employees.get(i);
            if (emp.length >= 19) {
                data[i][0] = emp[EMP_NUMBER];
                data[i][1] = emp[LAST_NAME];
                data[i][2] = emp[FIRST_NAME];
                data[i][3] = emp[SSS_NUMBER];
                data[i][4] = emp[PHILHEALTH_NUMBER];
                data[i][5] = emp[TIN_NUMBER];
                data[i][6] = emp[PAGIBIG_NUMBER];
            }
        }

        return data;
    }

    // Get basic employee info for dashboard - delegates to CSV reader
    public static List<String[]> getAllEmployeesBasicInfo() {
        return EmployeeCSVReader.getAllEmployeesBasicInfo();
    }

    // Get attendance for an employee - delegates to CSV reader
    public static List<String[]> getEmployeeAttendance(String employeeNumber) {
        return EmployeeCSVReader.getAttendanceByEmployeeNumber(employeeNumber);
    }

    // Get attendance for an employee for a specific month - delegates to CSV reader
    public static List<String[]> getEmployeeAttendanceForMonth(String employeeNumber, String month, String year) {
        return EmployeeCSVReader.getAttendanceByEmployeeAndMonth(employeeNumber, month, year);
    }

    // Calculate total hours worked in a month
    public static double getTotalHoursWorkedForMonth(String employeeNumber, String month, String year) {
        List<String[]> attendance = getEmployeeAttendanceForMonth(employeeNumber, month, year);
        double totalHours = 0.0;

        for (int i = 0; i < attendance.size(); i++) {
            String[] record = attendance.get(i);
            if (record.length >= 6) {
                try {
                    String logIn = record[ATT_LOG_IN];
                    String logOut = record[ATT_LOG_OUT];

                    if (!logIn.isEmpty() && !logOut.isEmpty()) {
                        double hours = calculateHoursBetween(logIn, logOut);
                        totalHours = totalHours + hours;
                    }
                } catch (Exception e) {
                    System.out.println("Error calculating hours for record: " + e.getMessage());
                }
            }
        }

        return totalHours;
    }

    // Calculate hours between two times
    private static double calculateHoursBetween(String timeIn, String timeOut) {
        try {
            String[] inParts = timeIn.split(":");
            String[] outParts = timeOut.split(":");

            int inHour = Integer.parseInt(inParts[0]);
            int inMinute = Integer.parseInt(inParts[1]);
            int outHour = Integer.parseInt(outParts[0]);
            int outMinute = Integer.parseInt(outParts[1]);

            int totalInMinutes = (inHour * 60) + inMinute;
            int totalOutMinutes = (outHour * 60) + outMinute;

            // Handle next day
            if (totalOutMinutes < totalInMinutes) {
                totalOutMinutes = totalOutMinutes + (24 * 60);
            }

            int diffMinutes = totalOutMinutes - totalInMinutes;
            return diffMinutes / 60.0;

        } catch (Exception e) {
            System.out.println("Error parsing time: " + timeIn + " - " + timeOut);
            return 0.0;
        }
    }

    // Calculate salary for an employee
    public static Map<String, Object> calculateSalary(String employeeNumber, String month, String year) {
        Map<String, Object> salaryDetails = new HashMap<String, Object>();
        String[] employee = getEmployeeByNumber(employeeNumber);

        if (employee == null || employee.length < 19) {
            return salaryDetails;
        }

        try {
            // Get salary info
            double basicSalary = Double.parseDouble(employee[BASIC_SALARY].replace(",", ""));
            double riceSubsidy = Double.parseDouble(employee[RICE_SUBSIDY].replace(",", ""));
            double phoneAllowance = Double.parseDouble(employee[PHONE_ALLOWANCE].replace(",", ""));
            double clothingAllowance = Double.parseDouble(employee[CLOTHING_ALLOWANCE].replace(",", ""));
            double hourlyRate = Double.parseDouble(employee[HOURLY_RATE]);

            // Calculate hours worked
            double totalHoursWorked = getTotalHoursWorkedForMonth(employeeNumber, month, year);
            double regularHours = Math.min(totalHoursWorked, 160); // 160 regular hours per month
            double overtimeHours = Math.max(0, totalHoursWorked - 160);

            // Calculate pay
            double regularPay = regularHours * hourlyRate;
            double overtimePay = overtimeHours * hourlyRate * 1.25; // 25% overtime
            double grossPay = regularPay + overtimePay + riceSubsidy + phoneAllowance + clothingAllowance;

            // Calculate deductions (simple)
            double withholdingTax = grossPay * 0.1; // 10% tax
            double sssContribution = basicSalary * 0.045; // 4.5% SSS
            double philhealthContribution = basicSalary * 0.0175; // 1.75% PhilHealth
            double pagibigContribution = basicSalary * 0.02; // 2% Pag-IBIG

            double totalDeductions = withholdingTax + sssContribution + philhealthContribution + pagibigContribution;
            double netPay = grossPay - totalDeductions;

            // Save all details
            salaryDetails.put("basicSalary", basicSalary);
            salaryDetails.put("riceSubsidy", riceSubsidy);
            salaryDetails.put("phoneAllowance", phoneAllowance);
            salaryDetails.put("clothingAllowance", clothingAllowance);
            salaryDetails.put("hourlyRate", hourlyRate);
            salaryDetails.put("totalHoursWorked", totalHoursWorked);
            salaryDetails.put("regularHours", regularHours);
            salaryDetails.put("overtimeHours", overtimeHours);
            salaryDetails.put("regularPay", regularPay);
            salaryDetails.put("overtimePay", overtimePay);
            salaryDetails.put("grossPay", grossPay);
            salaryDetails.put("withholdingTax", withholdingTax);
            salaryDetails.put("sssContribution", sssContribution);
            salaryDetails.put("philhealthContribution", philhealthContribution);
            salaryDetails.put("pagibigContribution", pagibigContribution);
            salaryDetails.put("totalDeductions", totalDeductions);
            salaryDetails.put("netPay", netPay);

        } catch (Exception e) {
            System.out.println("Error calculating salary for employee: " + employeeNumber + " - " + e.getMessage());
        }

        return salaryDetails;
    }

    // Get some statistics
    public static Map<String, Object> getEmployeeStatistics() {
        List<String[]> employees = getAllEmployees();
        Map<String, Object> stats = new HashMap<String, Object>();

        stats.put("totalEmployees", employees.size());

        // Count by position
        Map<String, Integer> positionCount = new HashMap<String, Integer>();

        for (int i = 0; i < employees.size(); i++) {
            String[] emp = employees.get(i);
            if (emp.length > POSITION) {
                String position = emp[POSITION];
                if (positionCount.containsKey(position)) {
                    positionCount.put(position, positionCount.get(position) + 1);
                } else {
                    positionCount.put(position, 1);
                }
            }
        }

        stats.put("positionCounts", positionCount);
        return stats;
    }

    // Check if employee data is good
    public static boolean isValidEmployeeData(String[] employeeData) {
        if (employeeData == null || employeeData.length < 19) {
            return false;
        }

        // Check required fields are not empty
        if (employeeData[EMP_NUMBER] == null || employeeData[EMP_NUMBER].trim().isEmpty()) {
            return false;
        }
        if (employeeData[LAST_NAME] == null || employeeData[LAST_NAME].trim().isEmpty()) {
            return false;
        }
        if (employeeData[FIRST_NAME] == null || employeeData[FIRST_NAME].trim().isEmpty()) {
            return false;
        }
        if (employeeData[POSITION] == null || employeeData[POSITION].trim().isEmpty()) {
            return false;
        }
        if (employeeData[BASIC_SALARY] == null || employeeData[BASIC_SALARY].trim().isEmpty()) {
            return false;
        }

        // Check salary is a number
        try {
            Double.parseDouble(employeeData[BASIC_SALARY].replace(",", ""));
            Double.parseDouble(employeeData[HOURLY_RATE]);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // Create a new employee array
    public static String[] createNewEmployeeArray(String empNumber, String lastName, String firstName,
                                                  String birthday, String address, String phoneNumber,
                                                  String sssNumber, String philhealthNumber, String tinNumber,
                                                  String pagibigNumber, String status, String position,
                                                  String supervisor, String basicSalary, String riceSubsidy,
                                                  String phoneAllowance, String clothingAllowance,
                                                  String grossSemiMonthlyRate, String hourlyRate) {

        String[] newEmployee = new String[19];
        newEmployee[0] = empNumber;
        newEmployee[1] = lastName;
        newEmployee[2] = firstName;
        newEmployee[3] = birthday;
        newEmployee[4] = address;
        newEmployee[5] = phoneNumber;
        newEmployee[6] = sssNumber;
        newEmployee[7] = philhealthNumber;
        newEmployee[8] = tinNumber;
        newEmployee[9] = pagibigNumber;
        newEmployee[10] = status;
        newEmployee[11] = position;
        newEmployee[12] = supervisor;
        newEmployee[13] = basicSalary;
        newEmployee[14] = riceSubsidy;
        newEmployee[15] = phoneAllowance;
        newEmployee[16] = clothingAllowance;
        newEmployee[17] = grossSemiMonthlyRate;
        newEmployee[18] = hourlyRate;

        return newEmployee;
    }

    // Refresh data (delegates to CSV reader)
    public static void refreshData() {
        System.out.println("Refreshing employee data from CSV files...");
    }
}