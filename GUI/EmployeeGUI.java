package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeGUI extends JPanel {
    private JTextArea taskArea;
    private JButton updateTaskBtn, backBtn;
    private BackListener backListener;

    public interface BackListener {
        void onBack();
    }

    public void setBackListener(BackListener listener) {
        this.backListener = listener;
    }

    public EmployeeGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Assigned Tasks Label
        JLabel taskLabel = new JLabel("Assigned Tasks:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        add(taskLabel, gbc);

        // Minimize space between label and textarea
        gbc.insets = new Insets(5, 15, 15, 15);

        // Task Area inside ScrollPane
        taskArea = new JTextArea(8, 50);
        taskArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        taskArea.setEditable(true);
        JScrollPane taskScrollPane = new JScrollPane(taskArea);
        gbc.gridy = 1;
        add(taskScrollPane, gbc);

        // Restore default spacing for buttons
        gbc.insets = new Insets(15, 15, 15, 15);

        // Update Task Button
        updateTaskBtn = new JButton("Update Task Status");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateTaskBtn, gbc);

        // Back Button
        backBtn = new JButton("Back");
        gbc.gridx = 1;
        add(backBtn, gbc);

        // Event Listener
        updateTaskBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update Task Status clicked");
                // Placeholder: Add logic here
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backListener != null) {
                    backListener.onBack();
                }
            }
        });
    }
}
