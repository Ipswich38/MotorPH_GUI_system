package com.example.MotorPH.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateEmployeeForm extends JDialog {

    // Simple variables
    private MainDashboard parentDashboard;
    private String employeeNumber;
    private String[] originalEmployeeData;

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
    private JButton updateButton;
    private JButton cancelButton;
    private JButton calculateButton;
    private JButton resetButton;

    public UpdateEmployeeForm(MainDashboard parent, String empNumber, String[] employeeData) {
        super(parent, "Update Employee - " + empNumber, true);
        this.parentDashboard = parent;
        this.employeeNumber = empNumber;
        this.originalEmployeeData = employeeData;

        setupWindow();
        createComponents();
        arrangeComponents();
        addButtonActions();
        populateFields();
        addInputValidation();
    }

    // Set up the window
    private void setupWindow() {
        setSize(500, 780);
        setLocationRelativeTo(parentDashboard);
        setResizable(false);
    }

    // Create all the parts we need
    private void createComponents() {
        // Create all text boxes
        employeeNumberBox = new JTextField(15);
        employeeNumberBox.setEnabled(false); // Cannot change employee number

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
        updateButton = new JButton("Update Employee");
        cancelButton = new JButton("Cancel");
        calculateButton = new JButton("Recalculate Rates");
        resetButton = new JButton("Reset to Original");

        // Make buttons look nice
        updateButton.setBackground(Color.ORANGE);
        updateButton.setForeground(Color.WHITE);
        calculateButton.setBackground(Color.BLUE);
        calculateButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.CYAN);
        cancelButton.setBackground(Color.GRAY);

        // Add tooltips
        birthdayBox.setToolTipText("Format: YYYY-MM-DD (e.g., 1990-01-15)");
        basicSalaryBox.setToolTipText("Enter monthly basic salary");
        grossSemiMonthlyBox.setToolTipText("Auto-calculated when you click 'Recalculate Rates'");
        hourlyRateBox.setToolTipText("Auto-calculated when you click 'Recalculate Rates'");
    }

    // Put everything in the right place
    private void arrangeComponents() {
        setLayout(null); // Simple positioning

        int y = 20; // Starting position

        // Title
        JLabel titleLabel = new JLabel("Update Employee Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(120, y, 250, 25);
        add(titleLabel);
        y = y + 40;

        // Employee Number
        JLabel empNumLabel = new JLabel("Employee #:");
        empNumLabel.setBounds(50, y, 120, 25);
        add(empNumLabel);
        employeeNumberBox.setBounds(170, y, 150, 25);
        employeeNumberBox.setBackground(Color.LIGHT_GRAY);
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
        grossSemiMonthlyBox.setBackground(Color.LIGHT_GRAY);
        add(grossSemiMonthlyBox);
        y = y + 30;

        // Hourly Rate
        JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
        hourlyRateLabel.setBounds(50, y, 120, 25);
        add(hourlyRateLabel);
        hourlyRateBox.setBounds(170, y, 150, 25);
        hourlyRateBox.setBackground(Color.LIGHT_GRAY);
        add(hourlyRateBox);
        y = y + 40;

        // Required fields notice
        JLabel requiredLabel = new JLabel("* Required fields");
        requiredLabel.setBounds(50, y, 150, 20);
        requiredLabel.setForeground(Color.RED);
        requiredLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        add(requiredLabel);
        y = y + 30;

        // Buttons in two rows
        calculateButton.setBounds(50, y, 120, 30);
        add(calculateButton);
        resetButton.setBounds(180, y, 130, 30);
        add(resetButton);
        y = y + 40;

        updateButton.setBounds(50, y, 120, 30);
        add(updateButton);
        cancelButton.setBounds(180, y, 130, 30);
        add(cancelButton);
    }

    // Populate fields with existing employee data
    private void populateFields() {
        if (originalEmployeeData != null && originalEmployeeData.length >= 19) {
            employeeNumberBox.setText(originalEmployeeData[0]);
            lastNameBox.setText(originalEmployeeData[1]);
            firstNameBox.setText(originalEmployeeData[2]);
            birthdayBox.setText(originalEmployeeData[3]);
            addressBox.setText(originalEmployeeData[4]);
            phoneNumberBox.setText(originalEmployeeData[5]);
            sssNumberBox.setText(originalEmployeeData[6]);
            philhealthNumberBox.setText(originalEmployeeData[7]);
            tinNumberBox.setText(originalEmployeeData[8]);
            pagibigNumberBox.setText(originalEmployeeData[9]);
            statusBox.setSelectedItem(originalEmployeeData[10]);
            positionBox.setText(originalEmployeeData[11]);
            supervisorBox.setText(originalEmployeeData[12]);
            basicSalaryBox.setText(originalEmployeeData[13]);
            riceSubsidyBox.setText(originalEmployeeData[14]);
            phoneAllowanceBox.setText(originalEmployeeData[15]);
            clothingAllowanceBox.setText(originalEmployeeData[16]);
            grossSemiMonthlyBox.setText(originalEmployeeData[17]);
            hourlyRateBox.setText(originalEmployeeData[18]);
        }
    }

    // Add input validation (same as NewEmployeeForm)
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
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
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

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetToOriginal();
            }
        });
    }

    // Calculate salary rates (same logic as NewEmployeeForm)
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
                    if (riceSubsidy < 0) throw new NumberFormatException("Rice subsidy cannot be negative");
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
                    if (phoneAllowance < 0) throw new NumberFormatException("Phone allowance cannot be negative");
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
                    if (clothingAllowance < 0) throw new NumberFormatException("Clothing allowance cannot be negative");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid clothing allowance amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                clothingAllowanceBox.requestFocus();
                return;
            }

            // Calculate rates
            double grossSemiMonthlyRate = (basicSalary / 2) + (riceSubsidy / 2) + (phoneAllowance / 2) + (clothingAllowance / 2);
            double hourlyRate = basicSalary / (21 * 8);

            // Show results
            grossSemiMonthlyBox.setText(String.format("%.2f", grossSemiMonthlyRate));
            hourlyRateBox.setText(String.format("%.2f", hourlyRate));

            JOptionPane.showMessageDialog(this, "Rates recalculated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating rates: " + e.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reset all fields to original values
    private void resetToOriginal() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset all fields to original values?\nAll your changes will be lost.",
                "Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            populateFields();
            JOptionPane.showMessageDialog(this, "All fields have been reset to original values.", "Reset Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Update the employee with comprehensive validation
    private void updateEmployee() {
        try {
            // Check required fields
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
                if (grossSemiMonthlyBox.getText().trim().isEmpty() || hourlyRateBox.getText().trim().isEmpty()) {
                    return;
                }
            }

            // Check if any changes were made
            if (!hasChanges()) {
                JOptionPane.showMessageDialog(this, "No changes were made to the employee record.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Confirm update
            String employeeName = firstNameBox.getText().trim() + " " + lastNameBox.getText().trim();
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to update the record for:\n" +
                            "Employee #: " + employeeNumber + "\n" +
                            "Name: " + employeeName + "\n" +
                            "Position: " + positionBox.getText().trim(),
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            // Create updated employee data array
            String[] updatedData = new String[19];
            updatedData[0] = employeeNumberBox.getText().trim();
            updatedData[1] = lastNameBox.getText().trim();
            updatedData[2] = firstNameBox.getText().trim();
            updatedData[3] = birthdayBox.getText().trim();
            updatedData[4] = addressBox.getText().trim();
            updatedData[5] = phoneNumberBox.getText().trim();
            updatedData[6] = sssNumberBox.getText().trim();
            updatedData[7] = philhealthNumberBox.getText().trim();
            updatedData[8] = tinNumberBox.getText().trim();
            updatedData[9] = pagibigNumberBox.getText().trim();
            updatedData[10] = (String) statusBox.getSelectedItem();
            updatedData[11] = positionBox.getText().trim();
            updatedData[12] = supervisorBox.getText().trim();
            updatedData[13] = basicSalaryBox.getText().replace(",", "").trim();
            updatedData[14] = riceSubsidyBox.getText().replace(",", "").trim();
            updatedData[15] = phoneAllowanceBox.getText().replace(",", "").trim();
            updatedData[16] = clothingAllowanceBox.getText().replace(",", "").trim();
            updatedData[17] = grossSemiMonthlyBox.getText().replace(",", "").trim();
            updatedData[18] = hourlyRateBox.getText().replace(",", "").trim();

            // Update employee in database
            boolean success = EmployeeDatabase.updateEmployee(employeeNumber, updatedData);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Employee record updated successfully!\n\nEmployee #: " + employeeNumber +
                                "\nName: " + employeeName +
                                "\nPosition: " + positionBox.getText().trim(),
                        "Update Successful", JOptionPane.INFORMATION_MESSAGE);

                // Refresh parent dashboard
                if (parentDashboard != null) {
                    parentDashboard.refreshDashboard();
                }

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Error updating employee record. Please try again.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Check if any changes were made
    private boolean hasChanges() {
        if (originalEmployeeData == null || originalEmployeeData.length < 19) {
            return true; // Assume changes if we can't compare
        }

        String[] currentData = {
                employeeNumberBox.getText().trim(),
                lastNameBox.getText().trim(),
                firstNameBox.getText().trim(),
                birthdayBox.getText().trim(),
                addressBox.getText().trim(),
                phoneNumberBox.getText().trim(),
                sssNumberBox.getText().trim(),
                philhealthNumberBox.getText().trim(),
                tinNumberBox.getText().trim(),
                pagibigNumberBox.getText().trim(),
                (String) statusBox.getSelectedItem(),
                positionBox.getText().trim(),
                supervisorBox.getText().trim(),
                basicSalaryBox.getText().replace(",", "").trim(),
                riceSubsidyBox.getText().replace(",", "").trim(),
                phoneAllowanceBox.getText().replace(",", "").trim(),
                clothingAllowanceBox.getText().replace(",", "").trim(),
                grossSemiMonthlyBox.getText().replace(",", "").trim(),
                hourlyRateBox.getText().replace(",", "").trim()
        };

        for (int i = 0; i < currentData.length && i < originalEmployeeData.length; i++) {
            if (!currentData[i].equals(originalEmployeeData[i])) {
                return true;
            }
        }

        return false;
    }

    // Enhanced validation for required fields (same as NewEmployeeForm)
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

    // Validate optional fields (same as NewEmployeeForm)
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