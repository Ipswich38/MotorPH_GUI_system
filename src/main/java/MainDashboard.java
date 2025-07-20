package com.example.MotorPH.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class MainDashboard extends JFrame {

    // Simple variables
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JButton newButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private JTextField searchBox;
    private JButton searchButton;
    private JLabel statusLabel;
    private String selectedEmployeeNumber = null;

    public MainDashboard() {
        setupWindow();
        createComponents();
        arrangeComponents();
        addButtonActions();
        loadEmployeeData();
    }

    // Set up the main window
    private void setupWindow() {
        setTitle("MotorPH Payroll System - Employee Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Create all the parts we need
    private void createComponents() {
        // Create table with buttons for each employee
        String[] columnNames = {"Employee #", "Full Name", "Position", "Status", "Basic Salary", "Supervisor", "View Details", "Compute Salary"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // Only buttons are editable
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowHeight(35); // Taller rows for buttons

        // Set button columns with FIXED COLORS
        employeeTable.getColumn("View Details").setCellRenderer(new ButtonRenderer());
        employeeTable.getColumn("View Details").setCellEditor(new ButtonEditor(new JCheckBox(), "VIEW"));

        employeeTable.getColumn("Compute Salary").setCellRenderer(new ButtonRenderer());
        employeeTable.getColumn("Compute Salary").setCellEditor(new ButtonEditor(new JCheckBox(), "COMPUTE"));

        // Set column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Employee #
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Full Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Status
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Basic Salary
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Supervisor
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(100); // View Details
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(120); // Compute Salary

        // Create control buttons - IMPROVED STYLING
        newButton = new JButton("Add New Employee");
        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");
        refreshButton = new JButton("Refresh Data");
        logoutButton = new JButton("Logout");
        searchButton = new JButton("Search");

        // Create search box
        searchBox = new JTextField(20);

        // Make buttons look nice and more visible with BLACK TEXT
        styleButton(newButton, new Color(144, 238, 144), Color.BLACK); // Light Green with BLACK text
        styleButton(updateButton, new Color(255, 165, 0), Color.BLACK); // Orange with BLACK text
        styleButton(deleteButton, new Color(255, 182, 193), Color.BLACK); // Light Pink with BLACK text
        styleButton(refreshButton, new Color(173, 216, 230), Color.BLACK); // Light Blue with BLACK text
        styleButton(logoutButton, new Color(211, 211, 211), Color.BLACK); // Light Gray with BLACK text
        styleButton(searchButton, new Color(221, 160, 221), Color.BLACK); // Plum with BLACK text

        // Initially disable buttons that require selection
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Status label
        statusLabel = new JLabel("Ready | Total Employees: 0");
    }

    // Helper method to style buttons consistently - FIXED TEXT VISIBILITY
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setOpaque(true);
        button.setBorderPainted(true); // Keep border for better definition
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Nice 3D border
        button.setContentAreaFilled(true); // Ensure background is filled
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(140, 35));
        button.setFocusPainted(false);
    }

    // Put everything in the right place
    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Top panel - title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("MotorPH Employee Management Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 248, 255)); // Alice blue
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Employees"));

        JLabel searchLabel = new JLabel("Search by Name:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchPanel.add(searchLabel);
        searchPanel.add(searchBox);
        searchPanel.add(searchButton);

        // Table panel - REDUCED HEIGHT to accommodate buttons below
        JPanel tablePanel = new JPanel(new BorderLayout());

        JScrollPane tableScroll = new JScrollPane(employeeTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Employee Records"));
        tableScroll.setPreferredSize(new Dimension(1000, 350)); // REDUCED HEIGHT

        tablePanel.add(tableScroll, BorderLayout.CENTER);

        // CRUD BUTTONS PANEL - BELOW THE TABLE
        JPanel crudButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        crudButtonsPanel.setBackground(new Color(248, 248, 255)); // Ghost white
        crudButtonsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Employee Management Actions",
                0, 0,
                new Font("Arial", Font.BOLD, 12)
        ));

        // Add buttons to CRUD panel
        crudButtonsPanel.add(newButton);
        crudButtonsPanel.add(updateButton);
        crudButtonsPanel.add(deleteButton);
        crudButtonsPanel.add(refreshButton);
        crudButtonsPanel.add(logoutButton);

        // Combine table and CRUD buttons
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);
        mainContentPanel.add(crudButtonsPanel, BorderLayout.SOUTH);

        // Bottom panel - status
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        bottomPanel.add(statusLabel);

        // Put everything together
        add(topPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.PAGE_START);
        add(mainContentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    // Make buttons do things when clicked
    private void addButtonActions() {
        // Table selection listener
        employeeTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = employeeTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        selectedEmployeeNumber = (String) employeeTable.getValueAt(selectedRow, 0);
                        updateButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                        updateStatusLabel();
                    } else {
                        selectedEmployeeNumber = null;
                        updateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                        updateStatusLabel();
                    }
                }
            }
        });

        // Double-click to view employee details
        employeeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && selectedEmployeeNumber != null) {
                    viewSelectedEmployee();
                }
            }
        });

        // Button actions
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewEmployeeForm();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSelectedEmployee();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEmployee();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchEmployees();
            }
        });

        // Search when pressing Enter
        searchBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchEmployees();
            }
        });
    }

    // Load employee data into table
    private void loadEmployeeData() {
        try {
            List<String[]> employees = EmployeeCSVReader.getAllEmployeesBasicInfo();

            // Clear table
            tableModel.setRowCount(0);

            // Add data to table
            for (String[] emp : employees) {
                Object[] row = new Object[8];
                row[0] = emp[0]; // Employee #
                row[1] = emp[1]; // Full Name
                row[2] = emp[2]; // Position
                row[3] = emp[3]; // Status
                row[4] = "₱" + emp[4]; // Basic Salary
                row[5] = emp[5]; // Supervisor
                row[6] = "View Details"; // Button text
                row[7] = "Compute Salary"; // Button text
                tableModel.addRow(row);
            }

            updateStatusLabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Update status label
    private void updateStatusLabel() {
        int totalEmployees = EmployeeDatabase.getTotalEmployees();
        statusLabel.setText("Ready | Total Employees: " + totalEmployees +
                (selectedEmployeeNumber != null ? " | Selected: Employee #" + selectedEmployeeNumber : ""));
    }

    // View selected employee details
    private void viewSelectedEmployee() {
        if (selectedEmployeeNumber != null) {
            try {
                new EmployeeDetails(selectedEmployeeNumber).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening employee details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open new employee form
    private void openNewEmployeeForm() {
        try {
            new NewEmployeeForm(this).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening new employee form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update selected employee
    private void updateSelectedEmployee() {
        if (selectedEmployeeNumber != null) {
            try {
                // Get current employee data
                String[] employeeData = EmployeeCSVReader.getEmployeeByNumber(selectedEmployeeNumber);
                if (employeeData == null) {
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Open update form
                new UpdateEmployeeForm(this, selectedEmployeeNumber, employeeData).setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening update form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to update!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Delete selected employee
    private void deleteSelectedEmployee() {
        if (selectedEmployeeNumber != null) {
            try {
                String[] employeeData = EmployeeCSVReader.getEmployeeByNumber(selectedEmployeeNumber);
                if (employeeData == null) {
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String employeeName = employeeData[2] + " " + employeeData[1]; // First + Last name

                // Create detailed confirmation dialog
                String message = "Are you sure you want to delete this employee?\n\n" +
                        "Employee #: " + selectedEmployeeNumber + "\n" +
                        "Name: " + employeeName + "\n" +
                        "Position: " + (employeeData.length > 11 ? employeeData[11] : "N/A") + "\n\n" +
                        "WARNING: This action cannot be undone!\n" +
                        "All employee records will be permanently removed from the CSV file.";

                int result = JOptionPane.showConfirmDialog(this,
                        message,
                        "Confirm Employee Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    // Additional confirmation for security
                    String confirmation = JOptionPane.showInputDialog(this,
                            "To confirm deletion, please type the employee number (" + selectedEmployeeNumber + "):",
                            "Final Confirmation",
                            JOptionPane.WARNING_MESSAGE);

                    if (confirmation != null && confirmation.equals(selectedEmployeeNumber)) {
                        boolean deleted = EmployeeCSVReader.deleteEmployee(selectedEmployeeNumber);

                        if (deleted) {
                            JOptionPane.showMessageDialog(this,
                                    "Employee " + employeeName + " has been successfully deleted from the system!",
                                    "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);

                            // Clear selection and refresh
                            selectedEmployeeNumber = null;
                            updateButton.setEnabled(false);
                            deleteButton.setEnabled(false);
                            refreshData();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error deleting employee! Please try again.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (confirmation != null) {
                        JOptionPane.showMessageDialog(this, "Employee number did not match. Deletion cancelled.", "Confirmation Failed", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error during delete: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Refresh data
    private void refreshData() {
        try {
            loadEmployeeData();
            searchBox.setText("");

            // Clear selection
            employeeTable.clearSelection();
            selectedEmployeeNumber = null;
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);

            updateStatusLabel();
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!\nLoaded " + EmployeeCSVReader.getTotalEmployeeCount() + " employees from CSV files.",
                    "Refresh Complete", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error refreshing data: " + e.getMessage(), "Refresh Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Search employees
    private void searchEmployees() {
        try {
            String searchTerm = searchBox.getText().trim();

            if (searchTerm.isEmpty()) {
                loadEmployeeData(); // Show all employees
                return;
            }

            List<String[]> allEmployees = EmployeeCSVReader.readEmployeeDetails();
            List<String[]> searchResults = new ArrayList<String[]>();

            // Search in first name, last name, and employee number
            for (String[] employee : allEmployees) {
                if (employee.length >= 3) {
                    String empNumber = employee[0].toLowerCase();
                    String firstName = employee[2].toLowerCase();
                    String lastName = employee[1].toLowerCase();
                    String fullName = firstName + " " + lastName;
                    String search = searchTerm.toLowerCase();

                    if (empNumber.contains(search) || firstName.contains(search) ||
                            lastName.contains(search) || fullName.contains(search)) {
                        searchResults.add(employee);
                    }
                }
            }

            // Clear table
            tableModel.setRowCount(0);

            // Add search results
            for (String[] employee : searchResults) {
                if (employee.length >= 19) {
                    Object[] row = new Object[8];
                    row[0] = employee[0]; // Employee #
                    row[1] = employee[2] + ", " + employee[1]; // Full Name
                    row[2] = employee[11]; // Position
                    row[3] = employee[10]; // Status
                    row[4] = "₱" + employee[13]; // Basic Salary
                    row[5] = employee[12]; // Supervisor
                    row[6] = "View Details"; // Button text
                    row[7] = "Compute Salary"; // Button text
                    tableModel.addRow(row);
                }
            }

            statusLabel.setText("Search Results: " + searchResults.size() + " employees found for '" + searchTerm + "'");

            if (searchResults.size() == 0) {
                JOptionPane.showMessageDialog(this, "No employees found matching: '" + searchTerm + "'", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }

            // Clear selection
            selectedEmployeeNumber = null;
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during search: " + e.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Logout
    private void logout() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new Login().setVisible(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error returning to login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }
            });
        }
    }

    // Method to refresh dashboard after employee operations
    public void refreshDashboard() {
        refreshData();
    }

    // Handle button clicks from table
    private void handleTableButtonClick(int row, String action) {
        String employeeNumber = (String) tableModel.getValueAt(row, 0);
        String employeeName = (String) tableModel.getValueAt(row, 1);

        if ("VIEW".equals(action)) {
            try {
                new EmployeeDetails(employeeNumber).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening employee details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("COMPUTE".equals(action)) {
            try {
                new SalaryComputeDialog(employeeNumber, employeeName).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening salary computation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // FIXED Button renderer for table - FIXED VISIBILITY ISSUE
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorderPainted(true);
            setFocusPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            if (column == 6) {
                setText("View Details");
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createRaisedBevelBorder());
            } else if (column == 7) {
                setText("Compute Salary");
                setBackground(new Color(34, 139, 34)); // Forest Green - MUCH MORE VISIBLE
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createRaisedBevelBorder());
            }

            return this;
        }
    }

    // FIXED Button editor for table - FIXED VISIBILITY ISSUE
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private String action;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.setBorderPainted(true);
            button.setFocusPainted(false);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.currentRow = row;

            if (column == 6) {
                label = "View Details";
                button.setText(label);
                button.setBackground(Color.BLUE);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            } else if (column == 7) {
                label = "Compute Salary";
                button.setText(label);
                button.setBackground(new Color(34, 139, 34)); // Forest Green - MUCH MORE VISIBLE
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }

            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                handleTableButtonClick(currentRow, action);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}