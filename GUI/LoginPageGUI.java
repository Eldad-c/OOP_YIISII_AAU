package GUI;
//import Models.*; // Import the Employee class
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginPageGUI extends JPanel {
    private JLabel signInLabel, usernameLabel, passwordLabel, roleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private LoginListener loginListener;

    public interface LoginListener {
        void onLoginSuccess(String username, String password, String role);
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public LoginPageGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        signInLabel = new JLabel("Sign in to PMS");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(signInLabel, gbc);

        gbc.gridwidth = 1;

        // Username
        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Role
        roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(roleLabel, gbc);

        String[] roles = {"Select Role", "Employee", "Manager", "Executive"};
        roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 30));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Action listener
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || role.equals("Select Role")) {
                JOptionPane.showMessageDialog(LoginPageGUI.this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (loginListener != null) {
                    loginListener.onLoginSuccess(username, password, role);
                }
            }
        });

    }

}
