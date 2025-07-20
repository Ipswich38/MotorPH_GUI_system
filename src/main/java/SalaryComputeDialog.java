package com.example.MotorPH.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SalaryComputeDialog extends JDialog {

    private String employeeNumber;
    private String employeeName;
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;
    private JButton computeButton;
    private JButton closeButton;
    private JTextArea resultArea;

    public SalaryComputeDialog(String empNumber, String empName) {
        super((Frame) null, "Salary Computation - " + empName, true);
        this.employeeNumber = empNumber;
        this.employeeName = empName;

        setupDialog();
        createComponents();
        arrangeComponents();
        addButtonActions();
    }

    private void setupDialog() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createComponents() {
        // Month selection
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthBox = new JComboBox<>(months);
        monthBox.setSelectedIndex(0); // January

        // Year selection
        String[] years = {"2023", "2024", "2025", "2026"};
        yearBox = new JComboBox<>(years);
        yearBox.setSelectedIndex(1); // 2024

        // Buttons
        computeButton = new JButton("Compute Salary");
        closeButton = new JButton("Close");

        computeButton.setBackground(Color.BLUE);
        computeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.GRAY);
        closeButton.setForeground(Color.WHITE);

        // Result area
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setText("Select month and year, then click 'Compute Salary' to calculate.");
    }

    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Top panel with employee info and controls
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Employee info
        JLabel empLabel = new JLabel("Employee: " + employeeName + " (ID: " + employeeNumber + ")");
        empLabel.setFont(new Font("Arial", Font.BOLD, 14));
        empLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.add(new JLabel("Month:"));
        selectionPanel.add(monthBox);
        selectionPanel.add(new JLabel("Year:"));
        selectionPanel.add(yearBox);
        selectionPanel.add(computeButton);

        topPanel.add(empLabel);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(selectionPanel);

        // Result panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Salary Computation Results"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

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

    private void computeSalary() {
        try {
            String selectedMonth = (String) monthBox.getSelectedItem();
            String selectedYear = (String) yearBox.getSelectedItem();

            if (selectedMonth == null || selectedYear == null) {
                JOptionPane.showMessageDialog(this, "Please select month and year!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show computing message
            resultArea.setText("Computing salary for " + getMonthName(selectedMonth) + " " + selectedYear + "...\nPlease wait...");
            computeButton.setEnabled(false);

            // Get employee data first
            String[] employeeData = EmployeeDatabase.getEmployeeByNumber(employeeNumber);
            if (employeeData == null || employeeData.length < 19) {
                resultArea.setText("Error: Employee data not found or incomplete.");
                computeButton.setEnabled(true);
                return;
            }

            // Calculate salary
            Map<String, Object> salaryDetails = EmployeeDatabase.calculateSalary(employeeNumber, selectedMonth, selectedYear);

            if (salaryDetails.isEmpty()) {
                resultArea.setText("Error: Could not compute salary. Please check employee data and attendance records.");
                computeButton.setEnabled(true);
                return;
            }

            // Format and display results
            StringBuilder result = new StringBuilder();
            result.append("=".repeat(50)).append("\n");
            result.append("SALARY COMPUTATION SUMMARY\n");
            result.append("=".repeat(50)).append("\n\n");

            result.append("EMPLOYEE: ").append(employeeName).append("\n");
            result.append("EMPLOYEE ID: ").append(employeeNumber).append("\n");
            result.append("PERIOD: ").append(getMonthName(selectedMonth)).append(" ").append(selectedYear).append("\n");
            result.append("POSITION: ").append(employeeData[11]).append("\n\n");

            result.append("-".repeat(50)).append("\n");
            result.append("EARNINGS BREAKDOWN\n");
            result.append("-".repeat(50)).append("\n");
            result.append(String.format("%-25s: ₱%,.2f\n", "Basic Salary", (Double) salaryDetails.get("basicSalary")));
            result.append(String.format("%-25s: %.1f hours\n", "Regular Hours Worked", (Double) salaryDetails.get("regularHours")));
            result.append(String.format("%-25s: %.1f hours\n", "Overtime Hours", (Double) salaryDetails.get("overtimeHours")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Regular Pay", (Double) salaryDetails.get("regularPay")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Overtime Pay", (Double) salaryDetails.get("overtimePay")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Rice Subsidy", (Double) salaryDetails.get("riceSubsidy")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Phone Allowance", (Double) salaryDetails.get("phoneAllowance")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Clothing Allowance", (Double) salaryDetails.get("clothingAllowance")));
            result.append("-".repeat(50)).append("\n");
            result.append(String.format("%-25s: ₱%,.2f\n", "GROSS PAY", (Double) salaryDetails.get("grossPay")));

            result.append("\n").append("-".repeat(50)).append("\n");
            result.append("DEDUCTIONS BREAKDOWN\n");
            result.append("-".repeat(50)).append("\n");
            result.append(String.format("%-25s: ₱%,.2f\n", "Withholding Tax (10%)", (Double) salaryDetails.get("withholdingTax")));
            result.append(String.format("%-25s: ₱%,.2f\n", "SSS Contribution (4.5%)", (Double) salaryDetails.get("sssContribution")));
            result.append(String.format("%-25s: ₱%,.2f\n", "PhilHealth (1.75%)", (Double) salaryDetails.get("philhealthContribution")));
            result.append(String.format("%-25s: ₱%,.2f\n", "Pag-IBIG (2%)", (Double) salaryDetails.get("pagibigContribution")));
            result.append("-".repeat(50)).append("\n");
            result.append(String.format("%-25s: ₱%,.2f\n", "TOTAL DEDUCTIONS", (Double) salaryDetails.get("totalDeductions")));

            result.append("\n").append("=".repeat(50)).append("\n");
            result.append(String.format("%-25s: ₱%,.2f\n", "NET PAY", (Double) salaryDetails.get("netPay")));
            result.append("=".repeat(50)).append("\n\n");

            result.append("ATTENDANCE SUMMARY:\n");
            result.append(String.format("Total Hours Worked: %.1f hours\n", (Double) salaryDetails.get("totalHoursWorked")));

            double totalHours = (Double) salaryDetails.get("totalHoursWorked");
            if (totalHours < 160) {
                result.append("⚠️  NOTE: Less than full-time hours (160 hours/month)\n");
            } else if (totalHours > 160) {
                result.append("✓ Overtime hours included in calculation\n");
            }

            resultArea.setText(result.toString());
            computeButton.setEnabled(true);

        } catch (Exception e) {
            resultArea.setText("Error computing salary: " + e.getMessage() +
                    "\n\nPlease check:\n" +
                    "- Employee data exists\n" +
                    "- Attendance records are available\n" +
                    "- Data format is correct");
            computeButton.setEnabled(true);
            e.printStackTrace();
        }
    }

    // Get month name from number
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