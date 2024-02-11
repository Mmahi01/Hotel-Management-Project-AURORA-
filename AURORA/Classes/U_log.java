package Classes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Interface.UserLogin;

public class U_log extends JFrame implements UserLogin {

    private String signUpFirstName;

    public U_log(String signUpFirstName) {
        this.signUpFirstName = signUpFirstName;
        initializeUI();
    }

    private void initializeUI() {
        setSize(1080, 720);
        setResizable(false);
        setTitle("LOG IN");

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(52, 152, 219));
        leftPanel.setBounds(0, 0, (getWidth() * 2 / 5) + 70, getHeight());
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setBounds(50, 220, 170, 100);
        leftPanel.add(userTypeLabel);

        String[] userTypes = {"Admin", "Employee"};
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setBounds(160, 260, 190, 20);
        leftPanel.add(userTypeComboBox);

        JLabel textFieldLabel = new JLabel("Username:");
        textFieldLabel.setForeground(Color.WHITE);
        textFieldLabel.setBounds(50, 250, 170, 100);
        leftPanel.add(textFieldLabel);

        JTextField textField = new JTextField();
        textField.setBounds(50, 310, 300, 25);
        leftPanel.add(textField);

        JLabel passwordFieldLabel = new JLabel("Password:");
        passwordFieldLabel.setForeground(Color.WHITE);
        passwordFieldLabel.setBounds(50, 317, 170, 100);
        leftPanel.add(passwordFieldLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 380, 300, 25);
        leftPanel.add(passwordField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 450, 100, 30);
        customizeButton(submitButton);
        leftPanel.add(submitButton);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(50, 450, 100, 30);
        customizeButton(signupButton);
        leftPanel.add(signupButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(20, 20, 100, 30); // Adjust the position and size as needed
        customizeButton(updateButton);
        leftPanel.add(updateButton);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\moynu\\Downloads\\AURORA.png");

        // Get the original image size
        int originalWidth = imageIcon.getIconWidth();
        int originalHeight = imageIcon.getIconHeight();

        // Calculate the scale factor for both width and height
        double widthScale = (double) getWidth() * 3 / 5 / originalWidth;
        double heightScale = (double) getHeight() / originalHeight;

        // Use the minimum scale factor to maintain aspect ratio
        double scale = Math.min(widthScale, heightScale);

        // Scale the image
        Image scaledImage = imageIcon.getImage().getScaledInstance((int) (originalWidth * scale), (int) (originalHeight * scale), Image.SCALE_SMOOTH);

        // Set the scaled image to the label
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setBounds(leftPanel.getWidth(), 0, getWidth() * 3 / 5, getHeight());
        add(imageLabel);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);
        setLocationRelativeTo(null);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new U_sign();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userType = (String) userTypeComboBox.getSelectedItem();
                String username = textField.getText();
                String password = new String(passwordField.getPassword());

                // Using the interface method for login handling
                handleLogin(userType, username, password);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Update_1 frame
				new Update_1().setVisible(true);
            }
        });
    }

    public void handleLogin(String userType, String username, String password) {
        if ("Admin".equals(userType) && "admin".equals(username) && "12345".equals(password)) {
            dispose();
            SwingUtilities.invokeLater(() -> new S_Frame());
        } else if ("Employee".equals(userType) && checkEmployeeLogin(username, password)) {
            JOptionPane.showMessageDialog(U_log.this, "Welcome Employee!", "Employee Login", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new U_Frame());
        } else {
            JOptionPane.showMessageDialog(U_log.this, "Invalid username/password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkEmployeeLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee_sign_info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && username.equals(data[0]) && password.equals(data[3])) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }
}
