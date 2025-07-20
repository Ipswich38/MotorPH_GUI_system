package com.example.MotorPH.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EmployeeDetails extends JFrame {

    // Simple variables
    private String employeeNumber;
    private String[] employeeData;

    // Employee info labels
    private JLabel empNumberLabel;
    private JLabel nameLabel;
    private JLabel birthdayLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel sssLabel;
    private JLabel philhealthLabel;
    private JLabel tinLabel;
    private JLabel pagibigLabel;
    private JLabel statusLabel;
    private JLabel positionLabel;
    private JLabel supervisorLabel;
    private JLabel basicSalaryLabel;
    private JLabel riceSubsidyLabel;
    private JLabel phoneAllowanceLabel;
    private JLabel clothingAllowanceLabel;
    private JLabel grossSemiMonthlyLabel;
    private JLabel hourlyRateLabel;

    // Salary computation controls
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;
    private JButton computeButton;
    private JButton closeButton;

    // Salary details area
    private JTextArea salaryDetailsArea;

    public EmployeeDetails(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        loadEmployeeData();
        setupWindow();
        createComponents();
        arrangeComponents();
        addButtonActions();
    }

    // Load employee data
    private void loadEmployeeData() {
        try {
            employeeData = EmployeeDatabase.getEmployeeByNumber(employeeNumber);
            if (employeeData == null) {
                JOptionPane.showMessageDialog(null, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Set up the window
    private void setupWindow() {
        if (employeeData != null && employeeData.length > 2) {
            setTitle("Employee Details - " + employeeData[2] + " " + employeeData[1]);
        } else {
            setTitle("Employee Details");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500); // Reduced height
        setLocationRelativeTo(null);
        setResizable(true); // Allow resizing
    }

    // Create all components
    private void createComponents() {
        if (employeeData == null || employeeData.length < 19) {
            return;
        }

        // Create labels with employee data
        empNumberLabel = new JLabel("Employee #: " + employeeData[0]);
        nameLabel = new JLabel("Name: " + employeeData[2] + " " + employeeData[1]);
        birthdayLabel = new JLabel("Birthday: " + employeeData[3]);
        addressLabel = new JLabel("Address: " + employeeData[4]);
        phoneLabel = new JLabel("Phone: " + employeeData[5]);
        sssLabel = new JLabel("SSS #: " + employeeData[6]);
        philhealthLabel = new JLabel("PhilHealth #: " + employeeData[7]);
        tinLabel = new JLabel("TIN #: " + employeeData[8]);
        pagibigLabel = new JLabel("Pag-IBIG #: " + employeeData[9]);
        statusLabel = new JLabel("Status: " + employeeData[10]);
        positionLabel = new JLabel("Position: " + employeeData[11]);
        supervisorLabel = new JLabel("Supervisor: " + employeeData[12]);
        basicSalaryLabel = new JLabel("Basic Salary: ₱" + employeeData[13]);
        riceSubsidyLabel = new JLabel("Rice Subsidy: ₱" + employeeData[14]);
        phoneAllowanceLabel = new JLabel("Phone Allowance: ₱" + employeeData[15]);
        clothingAllowanceLabel = new JLabel("Clothing Allowance: ₱" + employeeData[16]);
        grossSemiMonthlyLabel = new JLabel("Gross Semi-monthly: ₱" + employeeData[17]);
        hourlyRateLabel = new JLabel("Hourly Rate: ₱" + employeeData[18]);

        // Create month and year selection
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthBox = new JComboBox<>(months);
        monthBox.setSelectedIndex(0); // January

        String[] years = {"2024", "2025", "2026"};
        yearBox = new JComboBox<>(years);
        yearBox.setSelectedIndex(1); // 2025

        // Create buttons
        computeButton = new JButton("Compute Salary");
        closeButton = new JButton("Close");

        // Make buttons look nice
        computeButton.setBackground(Color.BLUE);
        computeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.GRAY);

        // Create salary details area
        salaryDetailsArea = new JTextArea(10, 40);
        salaryDetailsArea.setEditable(false);
        salaryDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        salaryDetailsArea.setText("Select month and year, then click 'Compute Salary' to view salary details.");
    }

    // Arrange all components with scroll support
    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(580, 700)); // Set preferred size for scrolling

        int y = 20;

        // Title
        JLabel titleLabel = new JLabel("Employee Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(200, y, 200, 25);
        contentPanel.add(titleLabel);
        y += 40;

        // Employee details
        empNumberLabel.setBounds(50, y, 300, 25);
        contentPanel.add(empNumberLabel);
        y += 25;

        nameLabel.setBounds(50, y, 300, 25);
        contentPanel.add(nameLabel);
        y += 25;

        birthdayLabel.setBounds(50, y, 300, 25);
        contentPanel.add(birthdayLabel);
        y += 25;

        addressLabel.setBounds(50, y, 500, 25);
        contentPanel.add(addressLabel);
        y += 25;

        phoneLabel.setBounds(50, y, 300, 25);
        contentPanel.add(phoneLabel);
        y += 25;

        sssLabel.setBounds(50, y, 300, 25);
        contentPanel.add(sssLabel);
        y += 25;

        philhealthLabel.setBounds(50, y, 300, 25);
        contentPanel.add(philhealthLabel);
        y += 25;

        tinLabel.setBounds(50, y, 300, 25);
        contentPanel.add(tinLabel);
        y += 25;

        pagibigLabel.setBounds(50, y, 300, 25);
        contentPanel.add(pagibigLabel);
        y += 25;

        statusLabel.setBounds(50, y, 300, 25);
        contentPanel.add(statusLabel);
        y += 25;

        positionLabel.setBounds(50, y, 300, 25);
        contentPanel.add(positionLabel);
        y += 25;

        supervisorLabel.setBounds(50, y, 300, 25);
        contentPanel.add(supervisorLabel);
        y += 30;

        // Salary information section
        JLabel salaryTitle = new JLabel("Salary Information");
        salaryTitle.setFont(new Font("Arial", Font.BOLD, 14));
        salaryTitle.setBounds(50, y, 200, 25);
        contentPanel.add(salaryTitle);
        y += 30;

        basicSalaryLabel.setBounds(50, y, 250, 25);
        contentPanel.add(basicSalaryLabel);
        y += 25;

        riceSubsidyLabel.setBounds(50, y, 250, 25);
        contentPanel.add(riceSubsidyLabel);
        y += 25;

        phoneAllowanceLabel.setBounds(50, y, 250, 25);
        contentPanel.add(phoneAllowanceLabel);
        y += 25;

        clothingAllowanceLabel.setBounds(50, y, 250, 25);
        contentPanel.add(clothingAllowanceLabel);
        y += 25;

        grossSemiMonthlyLabel.setBounds(50, y, 250, 25);
        contentPanel.add(grossSemiMonthlyLabel);
        y += 25;

        hourlyRateLabel.setBounds(50, y, 250, 25);
        contentPanel.add(hourlyRateLabel);
        y += 40;

        // Salary computation section
        JLabel computeTitle = new JLabel("Compute Monthly Salary");
        computeTitle.setFont(new Font("Arial", Font.BOLD, 14));
        computeTitle.setBounds(50, y, 200, 25);
        contentPanel.add(computeTitle);
        y += 30;

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, y, 80, 25);
        contentPanel.add(monthLabel);
        monthBox.setBounds(130, y, 80, 25);
        contentPanel.add(monthBox);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(220, y, 50, 25);
        contentPanel.add(yearLabel);
        yearBox.setBounds(270, y, 80, 25);
        contentPanel.add(yearBox);

        computeButton.setBounds(360, y, 120, 25);
        contentPanel.add(computeButton);
        y += 40;

        // Salary details area
        JLabel detailsTitle = new JLabel("Salary Computation Details");
        detailsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        detailsTitle.setBounds(50, y, 200, 25);
        contentPanel.add(detailsTitle);
        y += 30;

        JScrollPane scrollPane = new JScrollPane(salaryDetailsArea);
        scrollPane.setBounds(50, y, 500, 150); // Smaller height
        contentPanel.add(scrollPane);
        y += 170;

        // Close button
        closeButton.setBounds(250, y, 100, 30);
        contentPanel.add(closeButton);

        // Add content panel to a scroll pane
        JScrollPane mainScrollPane = new JScrollPane(contentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(mainScrollPane, BorderLayout.CENTER);
    }

    // Add button actions
    private void addButtonActions() {
        computeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Compute salary for selected month
    private void computeSalary() {
        try {
            String selectedMonth = (String) monthBox.getSelectedItem();
            String selectedYear = (String) yearBox.getSelectedItem();

            if (selectedMonth == null || selectedYear == null) {
                JOptionPane.showMessageDialog(this, "Please select month and year!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show loading message
            salaryDetailsArea.setText("Computing salary for " + getMonthName(selectedMonth) + " " + selectedYear + "...");

            // Calculate salary
            Map<String, Object> salaryDetails = EmployeeDatabase.calculateSalary(employeeNumber, selectedMonth, selectedYear);

            if (salaryDetails.isEmpty()) {
                salaryDetailsArea.setText("Error: Could not compute salary. Please check employee data.");
                return;
            }

            // Format and display salary details
            StringBuilder details = new StringBuilder();
            details.append("=== SALARY COMPUTATION FOR ").append(getMonthName(selectedMonth)).append(" ").append(selectedYear).append(" ===\n\n");

            details.append("EMPLOYEE: ").append(employeeData[2]).append(" ").append(employeeData[1]).append("\n");
            details.append("EMPLOYEE #: ").append(employeeNumber).append("\n");
            details.append("POSITION: ").append(employeeData[11]).append("\n\n");

            details.append("EARNINGS:\n");
            details.append("Basic Salary: ₱").append(String.format("%.2f", (Double) salaryDetails.get("basicSalary"))).append("\n");
            details.append("Regular Hours: ").append(String.format("%.1f", (Double) salaryDetails.get("regularHours"))).append(" hrs\n");
            details.append("Overtime Hours: ").append(String.format("%.1f", (Double) salaryDetails.get("overtimeHours"))).append(" hrs\n");
            details.append("Regular Pay: ₱").append(String.format("%.2f", (Double) salaryDetails.get("regularPay"))).append("\n");
            details.append("Overtime Pay: ₱").append(String.format("%.2f", (Double) salaryDetails.get("overtimePay"))).append("\n");
            details.append("Rice Subsidy: ₱").append(String.format("%.2f", (Double) salaryDetails.get("riceSubsidy"))).append("\n");
            details.append("Phone Allowance: ₱").append(String.format("%.2f", (Double) salaryDetails.get("phoneAllowance"))).append("\n");
            details.append("Clothing Allowance: ₱").append(String.format("%.2f", (Double) salaryDetails.get("clothingAllowance"))).append("\n");
            details.append("GROSS PAY: ₱").append(String.format("%.2f", (Double) salaryDetails.get("grossPay"))).append("\n\n");

            details.append("DEDUCTIONS:\n");
            details.append("Withholding Tax (10%): ₱").append(String.format("%.2f", (Double) salaryDetails.get("withholdingTax"))).append("\n");
            details.append("SSS Contribution (4.5%): ₱").append(String.format("%.2f", (Double) salaryDetails.get("sssContribution"))).append("\n");
            details.append("PhilHealth (1.75%): ₱").append(String.format("%.2f", (Double) salaryDetails.get("philhealthContribution"))).append("\n");
            details.append("Pag-IBIG (2%): ₱").append(String.format("%.2f", (Double) salaryDetails.get("pagibigContribution"))).append("\n");
            details.append("TOTAL DEDUCTIONS: ₱").append(String.format("%.2f", (Double) salaryDetails.get("totalDeductions"))).append("\n\n");

            details.append("NET PAY: ₱").append(String.format("%.2f", (Double) salaryDetails.get("netPay"))).append("\n");
            details.append("\nTotal Hours Worked: ").append(String.format("%.1f", (Double) salaryDetails.get("totalHoursWorked"))).append(" hours");

            salaryDetailsArea.setText(details.toString());

        } catch (Exception e) {
            salaryDetailsArea.setText("Error computing salary: " + e.getMessage());
        }
    }

    // Get month name
    private String getMonthName(String monthNumber) {
        String[] monthNames = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        try {
            int month = Integer.parseInt(monthNumber);
            if (month >= 1 && month <= 12) {
                return monthNames[month];
            }
        } catch (Exception e) {
            // ignore
        }
        return "Month " + monthNumber;
    }
}