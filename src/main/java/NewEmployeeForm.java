package com.example.MotorPH.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewEmployeeForm extends JDialog {

    // Simple variables
    private MainDashboard parentDashboard;

    // Form fields - all simple text boxes
    private JTextField employeeNumberBox;
    private JTextField lastNameBox;
    private JTextField firstNameBox;
    private JTextField birthdayBox;
    private JTextArea addressBox;
    private JTextField phoneNumberBox;
    private JTextField sssNumberBox;
    private JTextField philhealthNumberBox;
    private JTextField tinNumberBox;
    private JTextField pagibigNumberBox;
    private JComboBox statusBox;
    private JTextField positionBox;
    private JTextField supervisorBox;
    private JTextField basicSalaryBox;
    private JTextField riceSubsidyBox;
    private JTextField phoneAllowanceBox;
    private JTextField clothingAllowanceBox;
    private JTextField grossSemiMonthlyBox;
    private JTextField hourlyRateBox;

    // Buttons
    private JButton saveButton;
    private JButton cancelButton;
    private JButton calculateButton;

    public NewEmployeeForm(MainDashboard parent) {
        super(parent, "Add New Employee", true);
        this.parentDashboard = parent;

        setupWindow();
        createComponents();
        arrangeComponents();
        addButtonActions();
        generateEmployeeNumber();
        addInputValidation();
    }

    // Set up the window
    private void setupWindow() {
        setSize(500, 750);
        setLocationRelativeTo(parentDashboard);
        setResizable(false);
    }

    // Create all the parts we need
    private void createComponents() {
        // Create all text boxes
        employeeNumberBox = new JTextField(15);
        employeeNumberBox.setEnabled(false); // Auto-generated

        lastNameBox = new JTextField(15);
        firstNameBox = new JTextField(15);
        birthdayBox = new JTextField(15);
        addressBox = new JTextArea(3, 15);
        phoneNumberBox = new JTextField(15);
        sssNumberBox = new JTextField(15);
        philhealthNumberBox = new JTextField(15);
        tinNumberBox = new JTextField(15);
        pagibigNumberBox = new JTextField(15);

        // Status dropdown
        String[] statusOptions = {"Active", "Inactive", "On Leave", "Probationary", "Regular"};
        statusBox = new JComboBox(statusOptions);

        positionBox = new JTextField(15);
        supervisorBox = new JTextField(15);
        basicSalaryBox = new JTextField(15);
        riceSubsidyBox = new JTextField(15);
        phoneAllowanceBox = new JTextField(15);
        clothingAllowanceBox = new JTextField(15);
        grossSemiMonthlyBox = new JTextField(15);
        grossSemiMonthlyBox.setEnabled(false); // Auto-calculated
        hourlyRateBox = new JTextField(15);
        hourlyRateBox.setEnabled(false); // Auto-calculated

        // Create buttons
        saveButton = new JButton("Save Employee");
        cancelButton = new JButton("Cancel");
        calculateButton = new JButton("Calculate Rates");

        // Make buttons look nice
        saveButton.setBackground(Color.GREEN);
        saveButton.setForeground(Color.WHITE);
        calculateButton.setBackground(Color.BLUE);
        calculateButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.GRAY);

        // Set default values
        riceSubsidyBox.setText("1500.00");
        phoneAllowanceBox.setText("1000.00");
        clothingAllowanceBox.setText("1000.00");

        // Add placeholder text for birthday
        birthdayBox.setToolTipText("Format: YYYY-MM-DD (e.g., 1990-01-15)");
    }

    // Put everything in the right place
    private void arrangeComponents() {
        setLayout(null); // Simple positioning

        int y = 20; // Starting position

        // Title
        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(150, y, 200, 25);
        add(titleLabel);
        y = y + 40;

        // Employee Number
        JLabel empNumLabel = new JLabel("Employee #:");
        empNumLabel.setBounds(50, y, 120, 25);
        add(empNumLabel);
        employeeNumberBox.setBounds(170, y, 150, 25);
        add(employeeNumberBox);
        y = y + 30;

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name: *");
        lastNameLabel.setBounds(50, y, 120, 25);
        lastNameLabel.setForeground(Color.RED);
        add(lastNameLabel);
        lastNameBox.setBounds(170, y, 150, 25);
        add(lastNameBox);
        y = y + 30;

        // First Name
        JLabel firstNameLabel = new JLabel("First Name: *");
        firstNameLabel.setBounds(50, y, 120, 25);
        firstNameLabel.setForeground(Color.RED);
        add(firstNameLabel);
        firstNameBox.setBounds(170, y, 150, 25);
        add(firstNameBox);
        y = y + 30;

        // Birthday
        JLabel birthdayLabel = new JLabel("Birthday (YYYY-MM-DD):");
        birthdayLabel.setBounds(50, y, 120, 25);
        add(birthdayLabel);
        birthdayBox.setBounds(170, y, 150, 25);
        add(birthdayBox);
        y = y + 30;

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, y, 120, 25);
        add(addressLabel);
        JScrollPane addressScroll = new JScrollPane(addressBox);
        addressScroll.setBounds(170, y, 150, 60);
        add(addressScroll);
        y = y + 70;

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, y, 120, 25);
        add(phoneLabel);
        phoneNumberBox.setBounds(170, y, 150, 25);
        add(phoneNumberBox);
        y = y + 30;

        // SSS Number
        JLabel sssLabel = new JLabel("SSS Number:");
        sssLabel.setBounds(50, y, 120, 25);
        add(sssLabel);
        sssNumberBox.setBounds(170, y, 150, 25);
        add(sssNumberBox);
        y = y + 30;

        // PhilHealth Number
        JLabel philhealthLabel = new JLabel("PhilHealth Number:");
        philhealthLabel.setBounds(50, y, 120, 25);
        add(philhealthLabel);
        philhealthNumberBox.setBounds(170, y, 150, 25);
        add(philhealthNumberBox);
        y = y + 30;

        // TIN Number
        JLabel tinLabel = new JLabel("TIN Number:");
        tinLabel.setBounds(50, y, 120, 25);
        add(tinLabel);
        tinNumberBox.setBounds(170, y, 150, 25);
        add(tinNumberBox);
        y = y + 30;

        // Pag-IBIG Number
        JLabel pagibigLabel = new JLabel("Pag-IBIG Number:");
        pagibigLabel.setBounds(50, y, 120, 25);
        add(pagibigLabel);
        pagibigNumberBox.setBounds(170, y, 150, 25);
        add(pagibigNumberBox);
        y = y + 30;

        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, y, 120, 25);
        add(statusLabel);
        statusBox.setBounds(170, y, 150, 25);
        add(statusBox);
        y = y + 30;

        // Position
        JLabel positionLabel = new JLabel("Position: *");
        positionLabel.setBounds(50, y, 120, 25);
        positionLabel.setForeground(Color.RED);
        add(positionLabel);
        positionBox.setBounds(170, y, 150, 25);
        add(positionBox);
        y = y + 30;

        // Supervisor
        JLabel supervisorLabel = new JLabel("Supervisor:");
        supervisorLabel.setBounds(50, y, 120, 25);
        add(supervisorLabel);
        supervisorBox.setBounds(170, y, 150, 25);
        add(supervisorBox);
        y = y + 30;

        // Basic Salary
        JLabel basicSalaryLabel = new JLabel("Basic Salary: *");
        basicSalaryLabel.setBounds(50, y, 120, 25);
        basicSalaryLabel.setForeground(Color.RED);
        add(basicSalaryLabel);
        basicSalaryBox.setBounds(170, y, 150, 25);
        add(basicSalaryBox);
        y = y + 30;

        // Rice Subsidy
        JLabel riceSubsidyLabel = new JLabel("Rice Subsidy:");
        riceSubsidyLabel.setBounds(50, y, 120, 25);
        add(riceSubsidyLabel);
        riceSubsidyBox.setBounds(170, y, 150, 25);
        add(riceSubsidyBox);
        y = y + 30;

        // Phone Allowance
        JLabel phoneAllowanceLabel = new JLabel("Phone Allowance:");
        phoneAllowanceLabel.setBounds(50, y, 120, 25);
        add(phoneAllowanceLabel);
        phoneAllowanceBox.setBounds(170, y, 150, 25);
        add(phoneAllowanceBox);
        y = y + 30;

        // Clothing Allowance
        JLabel clothingAllowanceLabel = new JLabel("Clothing Allowance:");
        clothingAllowanceLabel.setBounds(50, y, 120, 25);
        add(clothingAllowanceLabel);
        clothingAllowanceBox.setBounds(170, y, 150, 25);
        add(clothingAllowanceBox);
        y = y + 30;

        // Gross Semi-monthly Rate
        JLabel grossSemiMonthlyLabel = new JLabel("Gross Semi-monthly:");
        grossSemiMonthlyLabel.setBounds(50, y, 120, 25);
        add(grossSemiMonthlyLabel);
        grossSemiMonthlyBox.setBounds(170, y, 150, 25);
        add(grossSemiMonthlyBox);
        y = y + 30;

        // Hourly Rate
        JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
        hourlyRateLabel.setBounds(50, y, 120, 25);
        add(hourlyRateLabel);
        hourlyRateBox.setBounds(170, y, 150, 25);
        add(hourlyRateBox);
        y = y + 40;

        // Required fields notice
        JLabel requiredLabel = new JLabel("* Required fields");
        requiredLabel.setBounds(50, y, 150, 20);
        requiredLabel.setForeground(Color.RED);
        requiredLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        add(requiredLabel);
        y = y + 30;

        // Buttons
        calculateButton.setBounds(50, y, 110, 30);
        add(calculateButton);
        saveButton.setBounds(170, y, 110, 30);
        add(saveButton);
        cancelButton.setBounds(290, y, 80, 30);
        add(cancelButton);
    }

    // Add input validation
    private void addInputValidation() {
        // Restrict basic salary to numbers and decimal point only
        basicSalaryBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        // Restrict allowances to numbers and decimal point only
        KeyAdapter numberOnlyAdapter = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        };

        riceSubsidyBox.addKeyListener(numberOnlyAdapter);
        phoneAllowanceBox.addKeyListener(numberOnlyAdapter);
        clothingAllowanceBox.addKeyListener(numberOnlyAdapter);

        // Restrict phone number to numbers, +, and spaces only
        phoneNumberBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '+' && c != ' ' && c != '-' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        // Restrict SSS, PhilHealth, TIN, Pag-IBIG to numbers and dashes only
        KeyAdapter idNumberAdapter = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '-' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        };

        sssNumberBox.addKeyListener(idNumberAdapter);
        philhealthNumberBox.addKeyListener(idNumberAdapter);
        tinNumberBox.addKeyListener(idNumberAdapter);
        pagibigNumberBox.addKeyListener(idNumberAdapter);

        // Restrict birthday to numbers and dashes only
        birthdayBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '-' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    // Make buttons do things when clicked
    private void addButtonActions() {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateRates();
            }
        });
    }

    // Get next employee number with exception handling
    private void generateEmployeeNumber() {
        try {
            String nextEmpNumber = EmployeeDatabase.generateNextEmployeeNumber();
            employeeNumberBox.setText(nextEmpNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating employee number. Using default.", "Warning", JOptionPane.WARNING_MESSAGE);
            employeeNumberBox.setText("1004");
        }
    }

    // Calculate salary rates with enhanced validation
    private void calculateRates() {
        try {
            String basicSalaryText = basicSalaryBox.getText().replace(",", "").trim();

            if (basicSalaryText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter basic salary first!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                basicSalaryBox.requestFocus();
                return;
            }

            // Validate basic salary input
            double basicSalary;
            try {
                basicSalary = Double.parseDouble(basicSalaryText);
                if (basicSalary <= 0) {
                    throw new NumberFormatException("Salary must be positive");
                }
                if (basicSalary > 1000000) {
                    throw new NumberFormatException("Salary seems too high");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid basic salary amount!\nAmount must be a positive number less than 1,000,000.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                basicSalaryBox.requestFocus();
                return;
            }

            double riceSubsidy = 0;
            double phoneAllowance = 0;
            double clothingAllowance = 0;

            // Get allowances with validation
            try {
                String riceText = riceSubsidyBox.getText().replace(",", "").trim();
                if (!riceText.isEmpty()) {
                    riceSubsidy = Double.parseDouble(riceText);
                    if (riceSubsidy < 0) {
                        throw new NumberFormatException("Rice subsidy cannot be negative");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid rice subsidy amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                riceSubsidyBox.requestFocus();
                return;
            }

            try {
                String phoneText = phoneAllowanceBox.getText().replace(",", "").trim();
                if (!phoneText.isEmpty()) {
                    phoneAllowance = Double.parseDouble(phoneText);
                    if (phoneAllowance < 0) {
                        throw new NumberFormatException("Phone allowance cannot be negative");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone allowance amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                phoneAllowanceBox.requestFocus();
                return;
            }

            try {
                String clothingText = clothingAllowanceBox.getText().replace(",", "").trim();
                if (!clothingText.isEmpty()) {
                    clothingAllowance = Double.parseDouble(clothingText);
                    if (clothingAllowance < 0) {
                        throw new NumberFormatException("Clothing allowance cannot be negative");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid clothing allowance amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                clothingAllowanceBox.requestFocus();
                return;
            }

            // Calculate gross semi-monthly rate
            double grossSemiMonthlyRate = (basicSalary / 2) + (riceSubsidy / 2) + (phoneAllowance / 2) + (clothingAllowance / 2);

            // Calculate hourly rate (21 working days per month, 8 hours per day)
            double hourlyRate = basicSalary / (21 * 8);

            // Show results
            grossSemiMonthlyBox.setText(String.format("%.2f", grossSemiMonthlyRate));
            hourlyRateBox.setText(String.format("%.2f", hourlyRate));

            JOptionPane.showMessageDialog(this, "Rates calculated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating rates: " + e.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save the new employee with comprehensive validation
    private void saveEmployee() {
        try {
            // Check required fields with enhanced validation
            if (!validateRequiredFields()) {
                return;
            }

            // Validate optional fields
            if (!validateOptionalFields()) {
                return;
            }

            // Auto-calculate rates if not done
            if (grossSemiMonthlyBox.getText().trim().isEmpty() || hourlyRateBox.getText().trim().isEmpty()) {
                calculateRates();
                // Check if calculation was successful
                if (grossSemiMonthlyBox.getText().trim().isEmpty() || hourlyRateBox.getText().trim().isEmpty()) {
                    return; // Calculation failed, don't proceed
                }
            }

            // Check for duplicate employee number
            String empNumber = employeeNumberBox.getText().trim();
            if (EmployeeDatabase.getEmployeeByNumber(empNumber) != null) {
                JOptionPane.showMessageDialog(this, "Employee number already exists! Generating new number...", "Duplicate Employee Number", JOptionPane.WARNING_MESSAGE);
                generateEmployeeNumber();
                return;
            }

            // Create employee data array
            String[] employeeData = new String[19];
            employeeData[0] = employeeNumberBox.getText().trim();
            employeeData[1] = lastNameBox.getText().trim();
            employeeData[2] = firstNameBox.getText().trim();
            employeeData[3] = birthdayBox.getText().trim();
            employeeData[4] = addressBox.getText().trim();
            employeeData[5] = phoneNumberBox.getText().trim();
            employeeData[6] = sssNumberBox.getText().trim();
            employeeData[7] = philhealthNumberBox.getText().trim();
            employeeData[8] = tinNumberBox.getText().trim();
            employeeData[9] = pagibigNumberBox.getText().trim();
            employeeData[10] = (String) statusBox.getSelectedItem();
            employeeData[11] = positionBox.getText().trim();
            employeeData[12] = supervisorBox.getText().trim();
            employeeData[13] = basicSalaryBox.getText().replace(",", "").trim();
            employeeData[14] = riceSubsidyBox.getText().replace(",", "").trim();
            employeeData[15] = phoneAllowanceBox.getText().replace(",", "").trim();
            employeeData[16] = clothingAllowanceBox.getText().replace(",", "").trim();
            employeeData[17] = grossSemiMonthlyBox.getText().replace(",", "").trim();
            employeeData[18] = hourlyRateBox.getText().replace(",", "").trim();

            // Add employee to database
            boolean success = EmployeeDatabase.addEmployee(employeeData);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Employee added successfully!\n\nEmployee #: " + employeeNumberBox.getText() +
                                "\nName: " + firstNameBox.getText() + " " + lastNameBox.getText() +
                                "\nPosition: " + positionBox.getText(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Refresh parent dashboard
                if (parentDashboard != null) {
                    parentDashboard.refreshDashboard();
                }

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Error adding employee. Employee number might already exist or data is invalid.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving employee: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Enhanced validation for required fields
    private boolean validateRequiredFields() {
        // Check last name
        if (lastNameBox.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name is required!", "Required Field Missing", JOptionPane.ERROR_MESSAGE);
            lastNameBox.requestFocus();
            return false;
        }

        // Check first name
        if (firstNameBox.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name is required!", "Required Field Missing", JOptionPane.ERROR_MESSAGE);
            firstNameBox.requestFocus();
            return false;
        }

        // Check position
        if (positionBox.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Position is required!", "Required Field Missing", JOptionPane.ERROR_MESSAGE);
            positionBox.requestFocus();
            return false;
        }

        // Check basic salary
        if (basicSalaryBox.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Basic Salary is required!", "Required Field Missing", JOptionPane.ERROR_MESSAGE);
            basicSalaryBox.requestFocus();
            return false;
        }

        // Validate basic salary format and value
        try {
            double salary = Double.parseDouble(basicSalaryBox.getText().replace(",", "").trim());
            if (salary <= 0) {
                JOptionPane.showMessageDialog(this, "Basic Salary must be greater than 0!", "Invalid Salary", JOptionPane.ERROR_MESSAGE);
                basicSalaryBox.requestFocus();
                return false;
            }
            if (salary > 1000000) {
                JOptionPane.showMessageDialog(this, "Basic Salary seems too high! Please verify the amount.", "Salary Validation", JOptionPane.WARNING_MESSAGE);
                basicSalaryBox.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Basic Salary amount!", "Invalid Number Format", JOptionPane.ERROR_MESSAGE);
            basicSalaryBox.requestFocus();
            return false;
        }

        return true;
    }

    // Validate optional fields
    private boolean validateOptionalFields() {
        // Validate birthday format if provided
        String birthday = birthdayBox.getText().trim();
        if (!birthday.isEmpty()) {
            if (birthday.length() != 10 || !birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Birthday must be in YYYY-MM-DD format!\nExample: 1990-01-15", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
                birthdayBox.requestFocus();
                return false;
            }

            // Additional date validation
            try {
                String[] parts = birthday.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                if (year < 1900 || year > 2010) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid birth year (1900-2010)!", "Invalid Birth Year", JOptionPane.ERROR_MESSAGE);
                    birthdayBox.requestFocus();
                    return false;
                }
                if (month < 1 || month > 12) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid month (01-12)!", "Invalid Month", JOptionPane.ERROR_MESSAGE);
                    birthdayBox.requestFocus();
                    return false;
                }
                if (day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid day (01-31)!", "Invalid Day", JOptionPane.ERROR_MESSAGE);
                    birthdayBox.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid birthday format! Use YYYY-MM-DD", "Date Validation Error", JOptionPane.ERROR_MESSAGE);
                birthdayBox.requestFocus();
                return false;
            }
        }

        // Validate phone number format if provided
        String phone = phoneNumberBox.getText().trim();
        if (!phone.isEmpty()) {
            // Remove spaces and dashes for validation
            String cleanPhone = phone.replace(" ", "").replace("-", "");
            if (cleanPhone.length() < 10 || cleanPhone.length() > 15) {
                JOptionPane.showMessageDialog(this, "Phone number should be 10-15 digits long!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                phoneNumberBox.requestFocus();
                return false;
            }
        }

        // Validate allowance fields
        if (!validateAllowanceField(riceSubsidyBox, "Rice Subsidy")) return false;
        if (!validateAllowanceField(phoneAllowanceBox, "Phone Allowance")) return false;
        if (!validateAllowanceField(clothingAllowanceBox, "Clothing Allowance")) return false;

        return true;
    }

    // Helper method to validate allowance fields
    private boolean validateAllowanceField(JTextField field, String fieldName) {
        String value = field.getText().replace(",", "").trim();
        if (!value.isEmpty()) {
            try {
                double amount = Double.parseDouble(value);
                if (amount < 0) {
                    JOptionPane.showMessageDialog(this, fieldName + " cannot be negative!", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                    field.requestFocus();
                    return false;
                }
                if (amount > 50000) {
                    int result = JOptionPane.showConfirmDialog(this,
                            fieldName + " amount seems very high (" + amount + ").\nDo you want to continue?",
                            "High Amount Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (result != JOptionPane.YES_OPTION) {
                        field.requestFocus();
                        return false;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid " + fieldName + " amount!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                field.requestFocus();
                return false;
            }
        }
        return true;
    }
}