package com.example.MotorPH.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainDashboard extends JFrame {

    // Simple variables
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JButton viewButton;
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
        setTitle("MotorPH Payroll System - Employee Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Create all the parts we need
    private void createComponents() {
        // Create table
        String[] columnNames = {"Employee #", "Last Name", "First Name", "SSS #", "PhilHealth #", "TIN #", "Pag-IBIG #"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Can't edit table
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowHeight(25);

        // Create buttons
        viewButton = new JButton("View Employee");
        newButton = new JButton("New Employee");
        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");
        refreshButton = new JButton("Refresh");
        logoutButton = new JButton("Logout");
        searchButton = new JButton("Search");

        // Create search box
        searchBox = new JTextField(20);

        // Make buttons look nice
        viewButton.setBackground(Color.BLUE);
        viewButton.setForeground(Color.WHITE);
        newButton.setBackground(Color.GREEN);
        newButton.setForeground(Color.WHITE);
        updateButton.setBackground(Color.ORANGE);
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        refreshButton.setBackground(Color.CYAN);
        logoutButton.setBackground(Color.GRAY);
        searchButton.setBackground(Color.BLUE);
        searchButton.setForeground(Color.WHITE);

        // Disable buttons that need selection
        viewButton.setEnabled(false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Status label
        statusLabel = new JLabel("Ready | Total Employees: 0");
    }

    // Put everything in the right place
    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Top panel - title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("MotorPH Employee Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by Name:"));
        searchPanel.add(searchBox);
        searchPanel.add(searchButton);

        // Button panel - arranged in rows for better layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        buttonPanel.add(viewButton);
        buttonPanel.add(newButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);

        // Controls panel (search + buttons)
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.add(searchPanel, BorderLayout.NORTH);
        controlsPanel.add(buttonPanel, BorderLayout.CENTER);
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table panel
        JScrollPane tableScroll = new JScrollPane(employeeTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Employee Records"));

        // Bottom panel - status
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        statusLabel.setForeground(Color.WHITE);
        bottomPanel.add(statusLabel);

        // Put everything together
        add(topPanel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.SOUTH);
        add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    // Make buttons do things when clicked
    private void addButtonActions() {
        // Table selection - SIMPLIFIED: Use old-style listener
        employeeTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = employeeTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        selectedEmployeeNumber = (String) employeeTable.getValueAt(selectedRow, 0);
                        viewButton.setEnabled(true);
                        updateButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    } else {
                        selectedEmployeeNumber = null;
                        viewButton.setEnabled(false);
                        updateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
                }
            }
        });

        // Double-click to view employee
        employeeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && selectedEmployeeNumber != null) {
                    viewSelectedEmployee();
                }
            }
        });

        // Button actions
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSelectedEmployee();
            }
        });

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
        statusLabel.setText("Ready | Total Employees: " + totalEmployees + " | Selected: " +
                (selectedEmployeeNumber != null ? selectedEmployeeNumber : "None"));
    }

    // View selected employee
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
                String[] employeeData = EmployeeDatabase.getEmployeeByNumber(selectedEmployeeNumber);
                if (employeeData == null) {
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Open update form
                new UpdateEmployeeForm(this, selectedEmployeeNumber, employeeData).setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening update form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Delete selected employee
    private void deleteSelectedEmployee() {
        if (selectedEmployeeNumber != null) {
            try {
                String[] employeeData = EmployeeDatabase.getEmployeeByNumber(selectedEmployeeNumber);
                if (employeeData == null) {
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String employeeName = employeeData[EmployeeDatabase.FIRST_NAME] + " " + employeeData[EmployeeDatabase.LAST_NAME];

                // Create detailed confirmation dialog
                String message = "Are you sure you want to delete this employee?\n\n" +
                        "Employee #: " + selectedEmployeeNumber + "\n" +
                        "Name: " + employeeName + "\n" +
                        "Position: " + employeeData[EmployeeDatabase.POSITION] + "\n\n" +
                        "WARNING: This action cannot be undone!\n" +
                        "All employee records will be permanently removed.";

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
                        boolean deleted = EmployeeDatabase.deleteEmployee(selectedEmployeeNumber);

                        if (deleted) {
                            JOptionPane.showMessageDialog(this,
                                    "Employee " + employeeName + " has been successfully deleted!",
                                    "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                            refreshData();
                            selectedEmployeeNumber = null;
                            viewButton.setEnabled(false);
                            updateButton.setEnabled(false);
                            deleteButton.setEnabled(false);
                            updateStatusLabel();
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
        }
    }

    // Refresh data
    private void refreshData() {
        try {
            EmployeeDatabase.refreshData();
            loadEmployeeData();

            // Clear selection
            employeeTable.clearSelection();
            selectedEmployeeNumber = null;
            viewButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);

            // Clear search
            searchBox.setText("");

            updateStatusLabel();

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

            List searchResults = EmployeeDatabase.searchEmployeesByName(searchTerm);

            // Clear table
            tableModel.setRowCount(0);

            // Add search results
            for (int i = 0; i < searchResults.size(); i++) {
                String[] employee = (String[]) searchResults.get(i);
                if (employee.length >= 19) {
                    Object[] row = {
                            employee[EmployeeDatabase.EMP_NUMBER],
                            employee[EmployeeDatabase.LAST_NAME],
                            employee[EmployeeDatabase.FIRST_NAME],
                            employee[EmployeeDatabase.SSS_NUMBER],
                            employee[EmployeeDatabase.PHILHEALTH_NUMBER],
                            employee[EmployeeDatabase.TIN_NUMBER],
                            employee[EmployeeDatabase.PAGIBIG_NUMBER]
                    };
                    tableModel.addRow(row);
                }
            }

            statusLabel.setText("Search Results: " + searchResults.size() + " employees found for '" + searchTerm + "'");

            if (searchResults.size() == 0) {
                JOptionPane.showMessageDialog(this, "No employees found matching: '" + searchTerm + "'", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during search: " + e.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Logout
    private void logout() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?\n\nAny unsaved changes will be lost.",
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
}