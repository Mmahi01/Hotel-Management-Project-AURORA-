package Classes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import Interface.SignUpUI;

public class U_sign extends JFrame implements SignUpUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passField;
    private String[] textFieldValues = new String[4]; // Added to store text field values

    public U_sign() {
        initializeUI();
    }

    @Override
    public void initializeUI() {
        setSize(1080, 720);
		setResizable(false);
		setTitle("SIGN UP");

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(52, 152, 219));
        leftPanel.setBounds(0, 0, getWidth() * 2 / 5, getHeight());
        leftPanel.setLayout(null);
        add(leftPanel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 200, 150, 30);
        customizeButton(loginButton);
        leftPanel.add(loginButton);

        JLabel label1 = new JLabel("First Name :");
        label1.setForeground(Color.WHITE);
        label1.setBounds(50, 245, 170, 100);
        leftPanel.add(label1);

        JLabel label2 = new JLabel("Last Name :");
        label2.setForeground(Color.WHITE);
        label2.setBounds(50, 300, 170, 100);
        leftPanel.add(label2);

        JLabel label3 = new JLabel("Email :");
        label3.setForeground(Color.WHITE);
        label3.setBounds(50, 360, 170, 100);
        leftPanel.add(label3);

        JLabel label4 = new JLabel("Password :");
        label4.setForeground(Color.WHITE);
        label4.setBounds(50, 420, 170, 100);
        leftPanel.add(label4);

        textField1 = createTextField();
        textField1.setBounds(50, 310, 300, 25);
        leftPanel.add(textField1);

        textField2 = createTextField();
        textField2.setBounds(50, 370, 300, 25);
        leftPanel.add(textField2);

        textField3 = createTextField();
        textField3.setBounds(50, 430, 300, 25);
        leftPanel.add(textField3);

        passField = new JPasswordField();
        passField.setBounds(50, 490, 300, 25);
        leftPanel.add(passField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(50, 550, 150, 30);
        customizeButton(submitButton);
        leftPanel.add(submitButton);

        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
                // U_log class constructor that takes String parameter
                U_log uLog = new U_log("default_user");
                uLog.setVisible(true);
            }
        });

        submitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textFieldValues[0] = getFirstName();
                textFieldValues[1] = getLastName();
                textFieldValues[2] = getEmail();
                textFieldValues[3] = getPassword();

            // Check if the password is less than four characters
			// Check if any text field is empty
        if (isAnyTextFieldEmpty()) {
            showMessage("All fields must be filled.");
            return; // Exit the method if any field is empty
        }

        // Check if the password is less than four characters
        if (textFieldValues[3].length() < 4) {
            showMessage("Password must be at least four characters.");
            return; // Exit the method if the password is too short
        }

        JOptionPane.showMessageDialog(U_sign.this, "Successfully Signed In!");
		JOptionPane.showMessageDialog(U_sign.this, "Now Go for Log In !");
        System.out.println("Stored values: " + Arrays.toString(textFieldValues));

        // Call the method to write signup data to a text file
        writeSignupDataToFile(textFieldValues);
            }
        });

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
	
	// Add a method to check if any text field is empty
	private boolean isAnyTextFieldEmpty() {
    return getFirstName().isEmpty() || getLastName().isEmpty() || getEmail().isEmpty() || getPassword().isEmpty();
	}

    public String getFirstName() {
        return textField1.getText();
    }


    public String getLastName() {
        return textField2.getText();
    }


    public String getEmail() {
        return textField3.getText();
    }


    public String getPassword() {
        return new String(passField.getPassword());
    }


    public void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        passField.setText("");
    }


    public void signUp() {
    }
	
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }


    public JTextField createTextField() {
        return new JTextField();
    }

    private void writeSignupDataToFile(String[] data) {
        try (BufferedWriter infoWriter = new BufferedWriter(new FileWriter("employee_sign_info.txt", true))) {
            // Add all signup data to the "employee_sign_info.txt" file
            infoWriter.write(String.join(",", data));
            infoWriter.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
