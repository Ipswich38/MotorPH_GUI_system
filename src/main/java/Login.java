package com.example.MotorPH.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    // Simple variables
    private JTextField usernameBox;
    private JPasswordField passwordBox;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel messageLabel;
    private int tries = 0;
    private final int MAX_TRIES = 3;

    public Login() {
        setupWindow();
        createComponents();
        arrangeComponents();
        addButtonActions();
        checkFiles();
    }

    // Set up the main window
    private void setupWindow() {
        setTitle("MotorPH Payroll System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400); // Made taller to fit all elements
        setLocationRelativeTo(null);
        setResizable(false);

        // Set application icon if available
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        } catch (Exception e) {
            // Ignore if icon not found
        }
    }

    // Create all the parts we need
    private void createComponents() {
        usernameBox = new JTextField(20);
        passwordBox = new JPasswordField(20);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        messageLabel = new JLabel(" ");

        // Make buttons look nice and visible
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);

        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);

        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add tooltips
        usernameBox.setToolTipText("Enter your username");
        passwordBox.setToolTipText("Enter your password");
        loginButton.setToolTipText("Click to login or press Enter");
        cancelButton.setToolTipText("Exit the application");
    }

    // Put everything in the right place
    private void arrangeComponents() {
        setLayout(null); // Simple positioning

        // Title
        JLabel titleLabel = new JLabel("MotorPH Payroll System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBounds(90, 30, 280, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Employee Management System");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setBounds(90, 55, 280, 20);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(subtitleLabel);

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createTitledBorder("Please Login"));
        loginPanel.setBounds(50, 90, 350, 120);
        loginPanel.setLayout(null);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 30, 80, 25);
        loginPanel.add(userLabel);

        usernameBox.setBounds(100, 30, 200, 25);
        loginPanel.add(usernameBox);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        loginPanel.add(passLabel);

        passwordBox.setBounds(100, 60, 200, 25);
        loginPanel.add(passwordBox);

        add(loginPanel);

        // Message
        messageLabel.setBounds(50, 220, 350, 25);
        add(messageLabel);

        // Buttons
        loginButton.setBounds(150, 250, 80, 35);
        add(loginButton);

        cancelButton.setBounds(240, 250, 80, 35);
        add(cancelButton);

        // Info panel - moved down and made more visible
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Test Accounts"));
        infoPanel.setBounds(50, 300, 350, 50); // Moved down and made taller
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setBackground(Color.LIGHT_GRAY); // Added background color

        JLabel infoLabel = new JLabel("<html><center>admin/admin123, hr/hr123, manager/manager123</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(Color.DARK_GRAY);
        infoPanel.add(infoLabel);

        add(infoPanel);
    }

    // Make buttons do things when clicked
    private void addButtonActions() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });

        // Press Enter to login
        passwordBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        usernameBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordBox.requestFocus();
            }
        });
    }

    // Enhanced login process with better exception handling
    private void performLogin() {
        try {
            String username = usernameBox.getText().trim();
            String password = new String(passwordBox.getPassword());

            // Input validation with specific error messages
            if (username.isEmpty()) {
                showMessage("Please enter your username!", Color.RED);
                usernameBox.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                showMessage("Please enter your password!", Color.RED);
                passwordBox.requestFocus();
                return;
            }

            // Check attempt limit
            if (tries >= MAX_TRIES) {
                showMessage("Maximum login attempts exceeded. Application will close in 3 seconds...", Color.RED);
                disableLoginControls();

                Timer timer = new Timer(3000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return;
            }

            // Show loading message
            showMessage("Verifying credentials...", Color.BLUE);
            loginButton.setEnabled(false);

            // Validate login credentials
            boolean loginSuccessful = false;
            String userRole = "";

            try {
                // First try to validate from CSV file
                loginSuccessful = EmployeeCSVReader.validateLogin(username, password);

                if (loginSuccessful) {
                    // Get user role from login file (if available)
                    userRole = getUserRole(username);
                }

            } catch (Exception fileException) {
                System.out.println("CSV login validation failed: " + fileException.getMessage());

                // Fallback to hardcoded credentials
                if ((username.equals("admin") && password.equals("admin123")) ||
                        (username.equals("hr") && password.equals("hr123")) ||
                        (username.equals("manager") && password.equals("manager123"))) {
                    loginSuccessful = true;
                    userRole = username.equals("admin") ? "Administrator" :
                            username.equals("hr") ? "HR Manager" : "Manager";
                }
            }

            if (loginSuccessful) {
                handleSuccessfulLogin(username, userRole);
            } else {
                handleFailedLogin();
            }

        } catch (Exception e) {
            handleLoginException(e);
        }
    }

    // Handle successful login
    private void handleSuccessfulLogin(String username, String userRole) {
        showMessage("Login successful! Welcome " + username + "!", Color.GREEN);

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose(); // Close login window

                    // Show welcome message
                    String welcomeMessage = "Welcome to MotorPH Payroll System!\n\n" +
                            "User: " + username + "\n" +
                            "Role: " + userRole + "\n" +
                            "Login Time: " + new java.util.Date();

                    JOptionPane.showMessageDialog(null, welcomeMessage,
                            "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                    // Open main dashboard
                    new MainDashboard().setVisible(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error opening dashboard: " + ex.getMessage() +
                                    "\n\nPlease restart the application.",
                            "Application Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Handle failed login
    private void handleFailedLogin() {
        tries++;
        int remainingTries = MAX_TRIES - tries;

        String message;
        if (remainingTries > 0) {
            message = "Invalid username or password! " + remainingTries + " attempt(s) remaining.";
        } else {
            message = "Invalid credentials! Maximum attempts reached.";
        }

        showMessage(message, Color.RED);
        passwordBox.setText(""); // Clear password
        passwordBox.requestFocus();
        loginButton.setEnabled(true);

        if (remainingTries <= 0) {
            disableLoginControls();
        }
    }

    // Handle login exceptions
    private void handleLoginException(Exception e) {
        System.err.println("Login error: " + e.getMessage());
        e.printStackTrace();

        showMessage("Login error occurred. Please try again.", Color.RED);
        loginButton.setEnabled(true);

        // Log the error for debugging
        JOptionPane.showMessageDialog(this,
                "An unexpected error occurred during login.\n" +
                        "Error: " + e.getMessage() + "\n\n" +
                        "Please contact system administrator if this persists.",
                "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    // Get user role from login file
    private String getUserRole(String username) {
        try {
            java.util.List<String[]> logins = EmployeeCSVReader.readLoginCredentials();
            for (String[] login : logins) {
                if (login.length >= 3 && login[0].equals(username)) {
                    return login[2]; // Role is in the third column
                }
            }
        } catch (Exception e) {
            System.out.println("Could not get user role: " + e.getMessage());
        }
        return "User"; // Default role
    }

    // Disable login controls
    private void disableLoginControls() {
        usernameBox.setEnabled(false);
        passwordBox.setEnabled(false);
        loginButton.setEnabled(false);
    }

    // Show a message to the user
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    // Overloaded method for backward compatibility
    private void showMessage(String message) {
        showMessage(message, Color.RED);
    }

    // Exit application with confirmation
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the MotorPH Payroll System?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Check if files are ready with enhanced error handling
    private void checkFiles() {
        try {
            // Print file locations for debugging
            EmployeeCSVReader.printFileLocations();

            if (EmployeeCSVReader.filesExist()) {
                showMessage("System ready! Please enter your credentials.", Color.BLUE);
                usernameBox.requestFocus();
            } else {
                showMessage("Setting up system files... Please wait...", Color.ORANGE);

                // Create files and show progress
                Timer timer = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            EmployeeCSVReader.createFilesIfNeeded();
                            if (EmployeeCSVReader.filesExist()) {
                                showMessage("System ready! Please enter your credentials.", Color.BLUE);
                                usernameBox.requestFocus();
                            } else {
                                showMessage("Warning: Some files missing, but login still works!", Color.ORANGE);
                            }
                        } catch (Exception ex) {
                            showMessage("File setup warning - login may still work!", Color.ORANGE);
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

        } catch (Exception e) {
            System.err.println("File check error: " + e.getMessage());
            showMessage("File setup warning - but login should still work!", Color.ORANGE);
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        // Print startup information
        System.out.println("=== STARTING MOTORPH PAYROLL SYSTEM ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Operating System: " + System.getProperty("os.name"));
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        System.out.println("User: " + System.getProperty("user.name"));
        System.out.println("No external libraries needed!");
        System.out.println("=======================================");

        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel: " + e.getMessage());
        }

        // Start the GUI with proper exception handling
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.out.println("Creating login window...");
                    Login loginWindow = new Login();
                    loginWindow.setVisible(true);
                    System.out.println("Login window created successfully!");

                } catch (Exception e) {
                    System.err.println("FATAL ERROR: " + e.getMessage());
                    e.printStackTrace();

                    // Show error dialog
                    String errorMessage = "Failed to start MotorPH Payroll System:\n\n" +
                            "Error: " + e.getMessage() + "\n\n" +
                            "Please check:\n" +
                            "1. Java is properly installed\n" +
                            "2. You have write permissions in current folder\n" +
                            "3. No other instance is running\n\n" +
                            "Contact IT support if problem persists.";

                    JOptionPane.showMessageDialog(null, errorMessage,
                            "Startup Error", JOptionPane.ERROR_MESSAGE);

                    System.exit(1);
                }
            }
        });

        System.out.println("Main method finished - GUI should be starting...");
    }
}