package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.example.authentication.IAuthentication;
import com.example.database.DBAuthentication;

public class LoginGUI {

    private static String futureUsername;
    private IAuthentication authenticationService;
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI(IAuthentication authService) {
        this.authenticationService = authService;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(null);
        centerFrame(frame);

        JPanel formPanel = createFormPanel();
        frame.add(formPanel);
        frame.setVisible(true);
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.LIGHT_GRAY); 
        formPanel.setBounds(0, 0, 600, 300);

        // Set up the labels and text fields
        JLabel headingLabel = new JLabel("Welcome to Flinder", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setBounds(100, 20, 400, 30);
        formPanel.add(headingLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(50, 70, 100, 25);
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 400, 25);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(50, 110, 100, 25);
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 400, 25);
        formPanel.add(passwordField);

        // Set up the buttons
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(0, 0, 255));
        loginButton.setBounds(150, 160, 100, 30);
        formPanel.add(loginButton);
        
        JButton signUpButton = new JButton("Sign Up");
        styleButton(signUpButton, new Color(255, 165, 0));
        signUpButton.setBounds(300, 160, 100, 30);
        formPanel.add(signUpButton);

        // Add action listeners
        loginButton.addActionListener(this::loginAction);
        signUpButton.addActionListener(this::signupAction);

        return formPanel;
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (authenticationService.check(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login successful");
                futureUsername = username;
                frame.dispose();
                // Next GUI screen goes here

                new MainGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed. Please check your credentials.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void signupAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (authenticationService.signup(username, password)) {
                JOptionPane.showMessageDialog(frame, "Account created successfully.");
                usernameField.setText("");  // Clear the username field
                passwordField.setText(""); // Clear the password field
            } else {
                JOptionPane.showMessageDialog(frame, "Account creation failed. Username might already exist.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public static String getFutureUsername() {
       return futureUsername;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(new DBAuthentication());
        });
    }
}