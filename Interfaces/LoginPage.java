package GUI;
import k.java.models.Employee; // Import the Employee class
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//sample commit

public class LoginPage {
    private JLabel signInLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JComboBox<String> roleComboBox;
    
    public LoginPage() {
        JFrame frame = new JFrame("PMS - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        signInLabel = new JLabel("Sign in to PMS");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(signInLabel, gbc);

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        usernameLabel = new JLabel("Username");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(usernamePanel, gbc);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(passwordPanel, gbc);

        // Roles
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel roleLabel = new JLabel("Role");
        String[] roles = {"Select Role", "Employee", "Manager", "Executive"};
        roleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleLabel);
        rolePanel.add(roleComboBox);
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(rolePanel, gbc);

        // Buttons
      loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                // Simple validation
                if (username.isEmpty() || password.isEmpty() || role.equals("Select Role")) {
                    JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Logged in as: " + username + " (" + role + ")");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        frame.add(loginButton, gbc);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Registration logic (placeholder)
                JOptionPane.showMessageDialog(frame, "Registration functionality not implemented.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        frame.add(registerButton, gbc);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
    }

    private String getSelectedRole() {
        String selectedRole = (String) roleComboBox.getSelectedItem();
        if (selectedRole != null && !selectedRole.equals("Select Role")) {
            return selectedRole;
        }
        return null;
    }
}
