package com.example.MotorPH.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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

    // Payslip components
    private JPanel payslipPanel;
    private JLabel empInfoLabel;
    private JLabel payPeriodLabel;
    private JTable earningsTable;
    private JTable deductionsTable;
    private JLabel grossPayLabel;
    private JLabel totalDeductionsLabel;
    private JLabel netPayLabel;
    private JTextArea summaryArea;

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
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createComponents() {
        // Month selection
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthBox = new JComboBox<>(months);
        monthBox.setSelectedIndex(5); // June

        // Year selection
        String[] years = {"2023", "2024", "2025", "2026"};
        yearBox = new JComboBox<>(years);
        yearBox.setSelectedIndex(1); // 2024

        // FIXED BUTTONS - Now clearly visible with BLACK text!
        computeButton = new JButton("Compute Salary");
        closeButton = new JButton("Close");

        // LIGHT BLUE button with BLACK text - ALWAYS VISIBLE!
        computeButton.setBackground(new Color(173, 216, 230)); // Light blue
        computeButton.setForeground(Color.BLACK);
        computeButton.setOpaque(true);
        computeButton.setBorderPainted(true);
        computeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        computeButton.setFont(new Font("Arial", Font.BOLD, 12));

        // LIGHT GRAY button with BLACK text - ALWAYS VISIBLE!
        closeButton.setBackground(new Color(211, 211, 211)); // Light gray
        closeButton.setForeground(Color.BLACK);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(true);
        closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Create payslip components
        createPayslipComponents();
    }

    private void createPayslipComponents() {
        // Main payslip panel
        payslipPanel = new JPanel();
        payslipPanel.setLayout(new BoxLayout(payslipPanel, BoxLayout.Y_AXIS));
        payslipPanel.setBackground(Color.WHITE);
        payslipPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header
        JLabel headerLabel = new JLabel("MOTORPH PAYROLL - EMPLOYEE PAY STATEMENT");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabel.setForeground(Color.BLUE);

        // Employee info
        empInfoLabel = new JLabel("Employee: " + employeeName + " (ID: " + employeeNumber + ")");
        empInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        empInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pay period
        payPeriodLabel = new JLabel("Pay Period: Select month and year above");
        payPeriodLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        payPeriodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Earnings table
        String[] earningsColumns = {"Description", "Hours/Rate", "Amount"};
        DefaultTableModel earningsModel = new DefaultTableModel(earningsColumns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        earningsTable = new JTable(earningsModel);
        earningsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        earningsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        earningsTable.getTableHeader().setBackground(new Color(173, 216, 230)); // Light blue
        earningsTable.setRowHeight(25);

        // Set column widths for earnings table
        earningsTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        earningsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        earningsTable.getColumnModel().getColumn(2).setPreferredWidth(120);

        // Deductions table
        String[] deductionsColumns = {"Description", "Rate", "Amount"};
        DefaultTableModel deductionsModel = new DefaultTableModel(deductionsColumns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        deductionsTable = new JTable(deductionsModel);
        deductionsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        deductionsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        deductionsTable.getTableHeader().setBackground(new Color(255, 182, 193)); // Light pink
        deductionsTable.setRowHeight(25);

        // Set column widths for deductions table
        deductionsTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        deductionsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        deductionsTable.getColumnModel().getColumn(2).setPreferredWidth(120);

        // Total labels
        grossPayLabel = new JLabel("GROSS PAY: ₱0.00");
        grossPayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        grossPayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        grossPayLabel.setForeground(new Color(0, 128, 0)); // Green

        totalDeductionsLabel = new JLabel("TOTAL DEDUCTIONS: ₱0.00");
        totalDeductionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalDeductionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalDeductionsLabel.setForeground(Color.RED);

        netPayLabel = new JLabel("NET PAY: ₱0.00");
        netPayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        netPayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        netPayLabel.setForeground(Color.BLUE);

        // Summary area
        summaryArea = new JTextArea(4, 40);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 11));
        summaryArea.setBackground(new Color(248, 248, 255)); // Ghost white
        summaryArea.setBorder(BorderFactory.createTitledBorder("Attendance Summary"));
        summaryArea.setText("Click 'Compute Salary' to see attendance summary.");

        // Initially hide payslip
        payslipPanel.setVisible(false);
    }

    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Top panel with controls
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(240, 248, 255)); // Alice blue background

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel empInfoTopLabel = new JLabel("Employee: " + employeeName + " (ID: " + employeeNumber + ")");
        empInfoTopLabel.setFont(new Font("Arial", Font.BOLD, 14));
        empInfoTopLabel.setForeground(Color.DARK_GRAY);

        topPanel.add(empInfoTopLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(monthLabel);
        topPanel.add(monthBox);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(yearLabel);
        topPanel.add(yearBox);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(computeButton);

        // Center panel for payslip
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Payslip"));

        // Build payslip panel
        buildPayslipPanel();

        JScrollPane scrollPane = new JScrollPane(payslipPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with better styling
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(240, 248, 255)); // Alice blue background
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void buildPayslipPanel() {
        payslipPanel.removeAll();

        // Add header
        JLabel headerLabel = new JLabel("MOTORPH PAYROLL - EMPLOYEE PAY STATEMENT");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabel.setForeground(Color.BLUE);
        payslipPanel.add(headerLabel);
        payslipPanel.add(Box.createVerticalStrut(10));

        // Add employee info
        payslipPanel.add(empInfoLabel);
        payslipPanel.add(payPeriodLabel);
        payslipPanel.add(Box.createVerticalStrut(20));

        // Add earnings section
        JLabel earningsTitle = new JLabel("EARNINGS");
        earningsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        earningsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        earningsTitle.setForeground(new Color(0, 128, 0));
        payslipPanel.add(earningsTitle);
        payslipPanel.add(Box.createVerticalStrut(5));

        JScrollPane earningsScroll = new JScrollPane(earningsTable);
        earningsScroll.setPreferredSize(new Dimension(500, 180));
        earningsScroll.setMaximumSize(new Dimension(600, 180));
        payslipPanel.add(earningsScroll);
        payslipPanel.add(Box.createVerticalStrut(10));

        // Add gross pay
        payslipPanel.add(grossPayLabel);
        payslipPanel.add(Box.createVerticalStrut(20));

        // Add deductions section
        JLabel deductionsTitle = new JLabel("DEDUCTIONS");
        deductionsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        deductionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        deductionsTitle.setForeground(Color.RED);
        payslipPanel.add(deductionsTitle);
        payslipPanel.add(Box.createVerticalStrut(5));

        JScrollPane deductionsScroll = new JScrollPane(deductionsTable);
        deductionsScroll.setPreferredSize(new Dimension(500, 140));
        deductionsScroll.setMaximumSize(new Dimension(600, 140));
        payslipPanel.add(deductionsScroll);
        payslipPanel.add(Box.createVerticalStrut(10));

        // Add total deductions
        payslipPanel.add(totalDeductionsLabel);
        payslipPanel.add(Box.createVerticalStrut(20));

        // Add net pay with special formatting
        JPanel netPayPanel = new JPanel();
        netPayPanel.setBackground(new Color(220, 220, 220));
        netPayPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        netPayPanel.add(netPayLabel);
        payslipPanel.add(netPayPanel);
        payslipPanel.add(Box.createVerticalStrut(20));

        // Add summary
        payslipPanel.add(summaryArea);
        payslipPanel.add(Box.createVerticalStrut(10));

        // Add footer
        JLabel footerLabel = new JLabel("This is a computer-generated payslip. Generated on: " + getCurrentDate());
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setForeground(Color.GRAY);
        payslipPanel.add(footerLabel);
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

            // Update button appearance while computing
            computeButton.setEnabled(false);
            computeButton.setText("Computing...");
            computeButton.setBackground(Color.ORANGE);
            computeButton.setForeground(Color.BLACK); // BLACK TEXT

            // Get employee data
            String[] employeeData = EmployeeDatabase.getEmployeeByNumber(employeeNumber);
            if (employeeData == null || employeeData.length < 19) {
                JOptionPane.showMessageDialog(this, "Employee data not found!", "Error", JOptionPane.ERROR_MESSAGE);
                resetComputeButton();
                return;
            }

            // Calculate salary
            Map<String, Object> salaryDetails = EmployeeDatabase.calculateSalary(employeeNumber, selectedMonth, selectedYear);

            if (salaryDetails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Could not compute salary. Please check employee data and attendance records.", "Error", JOptionPane.ERROR_MESSAGE);
                resetComputeButton();
                return;
            }

            // Update payslip with data
            updatePayslipWithData(selectedMonth, selectedYear, employeeData, salaryDetails);

            // Show payslip
            payslipPanel.setVisible(true);
            revalidate();
            repaint();

            resetComputeButton();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error computing salary: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            resetComputeButton();
            e.printStackTrace();
        }
    }

    private void resetComputeButton() {
        computeButton.setEnabled(true);
        computeButton.setText("Compute Salary");
        computeButton.setBackground(new Color(173, 216, 230)); // Light blue
        computeButton.setForeground(Color.BLACK); // BLACK TEXT
        computeButton.repaint(); // Force repaint
    }

    private void updatePayslipWithData(String month, String year, String[] employeeData, Map<String, Object> salaryDetails) {
        // Update pay period
        payPeriodLabel.setText("Pay Period: " + getMonthName(month) + " " + year + " | Pay Date: " + getCurrentDate());

        // Clear and populate earnings table
        DefaultTableModel earningsModel = (DefaultTableModel) earningsTable.getModel();
        earningsModel.setRowCount(0);

        earningsModel.addRow(new Object[]{"Basic Salary", "-", formatMoney((Double) salaryDetails.get("basicSalary"))});
        earningsModel.addRow(new Object[]{"Regular Hours", String.format("%.1f hrs", (Double) salaryDetails.get("regularHours")), formatMoney((Double) salaryDetails.get("regularPay"))});
        earningsModel.addRow(new Object[]{"Overtime Hours (1.25x)", String.format("%.1f hrs", (Double) salaryDetails.get("overtimeHours")), formatMoney((Double) salaryDetails.get("overtimePay"))});
        earningsModel.addRow(new Object[]{"Rice Subsidy", "-", formatMoney((Double) salaryDetails.get("riceSubsidy"))});
        earningsModel.addRow(new Object[]{"Phone Allowance", "-", formatMoney((Double) salaryDetails.get("phoneAllowance"))});
        earningsModel.addRow(new Object[]{"Clothing Allowance", "-", formatMoney((Double) salaryDetails.get("clothingAllowance"))});

        // Clear and populate deductions table
        DefaultTableModel deductionsModel = (DefaultTableModel) deductionsTable.getModel();
        deductionsModel.setRowCount(0);

        deductionsModel.addRow(new Object[]{"Withholding Tax", "10%", formatMoney((Double) salaryDetails.get("withholdingTax"))});
        deductionsModel.addRow(new Object[]{"SSS Contribution", "4.5%", formatMoney((Double) salaryDetails.get("sssContribution"))});
        deductionsModel.addRow(new Object[]{"PhilHealth", "1.75%", formatMoney((Double) salaryDetails.get("philhealthContribution"))});
        deductionsModel.addRow(new Object[]{"Pag-IBIG", "2%", formatMoney((Double) salaryDetails.get("pagibigContribution"))});

        // Update totals
        grossPayLabel.setText("GROSS PAY: " + formatMoney((Double) salaryDetails.get("grossPay")));
        totalDeductionsLabel.setText("TOTAL DEDUCTIONS: " + formatMoney((Double) salaryDetails.get("totalDeductions")));
        netPayLabel.setText("NET PAY: " + formatMoney((Double) salaryDetails.get("netPay")));

        // Update summary
        StringBuilder summary = new StringBuilder();
        summary.append("• Total Hours Worked: ").append(String.format("%.1f hours\n", (Double) salaryDetails.get("totalHoursWorked")));
        summary.append("• Hourly Rate: ").append(formatMoney((Double) salaryDetails.get("hourlyRate"))).append("\n");

        double totalHours = (Double) salaryDetails.get("totalHoursWorked");
        if (totalHours < 160) {
            summary.append("• Status: Undertime (").append(String.format("%.1f", 160 - totalHours)).append(" hours short)\n");
        } else if (totalHours > 160) {
            summary.append("• Status: Overtime included\n");
        } else {
            summary.append("• Status: Full-time hours completed\n");
        }

        summary.append("• Position: ").append(employeeData[11]);

        summaryArea.setText(summary.toString());

        // Apply alternating row colors to tables
        applyTableStyling(earningsTable);
        applyTableStyling(deductionsTable);

        // Rebuild the panel to reflect changes
        buildPayslipPanel();
    }

    private void applyTableStyling(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 245, 245)); // Light gray
                    }
                }

                // Right-align money amounts (last column)
                if (column == table.getColumnCount() - 1) {
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.RIGHT);
                } else {
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });
    }

    private String formatMoney(double amount) {
        return String.format("₱%,.2f", amount);
    }

    private String getCurrentDate() {
        return new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
    }

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