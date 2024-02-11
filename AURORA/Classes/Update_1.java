package Classes;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Update_1 extends JFrame {

    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField;
    private String matchedEmail; // Variable to store the matched email
    private JButton submitButton; // Declare the submit button
    private JButton updateButton; // Declare the update button
    private JLabel label3; // Declare label3 here

    public Update_1() {
        // Set the layout to null
        setLayout(null);

        // Create JLabels for the text fields
        JLabel label1 = new JLabel("First Name :");
        label1.setBounds(125, 50, 170, 100);

        JLabel label2 = new JLabel("Last Name :");
        label2.setBounds(125, 105, 170, 100);

        // Create four JTextFields
        textField1 = new JTextField();
        textField1.setBounds(125, 115, 300, 25);

        textField2 = new JTextField();
        textField2.setBounds(125, 175, 300, 25);

        // Initialize label3 here
        label3 = new JLabel("Password :");
        label3.setBounds(125, 170, 170, 100);

        // Create a JPasswordField
        passwordField = new JPasswordField();
        passwordField.setBounds(125, 235, 300, 25);
        passwordField.setVisible(false); // Initially set to invisible

        // Create a submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 300, 100, 30);
		customizeButton(submitButton);

        // Create an update button
        updateButton = new JButton("Update");
        updateButton.setBounds(300, 300, 100, 30);
		customizeButton(updateButton);

        // Add components to the frame
        add(label1);
        add(textField1);
        add(label2);
        add(textField2);
        add(label3);
        add(passwordField);
        add(submitButton);
        add(updateButton); // Add the "Update" button
		
		label3.setVisible(false);
		updateButton.setVisible(false);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(64, 188, 181));

        // Set the size and default close operation
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Access Update");

        // Make the frame non-resizable
        setResizable(false);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);


        // Add an ActionListener to the submitButton
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the first name and last name match with any array in "employee.txt"
                String email = checkEmployeeMatch(textField1.getText(), textField2.getText());
                if (email != null) {
                    passwordField.setVisible(true); // Show the password field
					label3.setVisible(true);
					updateButton.setVisible(true);
                    matchedEmail = email; // Save the matched email
                } else {
                    JOptionPane.showMessageDialog(Update_1.this, "Invalid first name or last name", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add an ActionListener to the updateButton
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there is a matched email
                if (matchedEmail != null) {
                    // Write information to "employee_sign_info.txt"
                    writeEmployeeInfo("employee_sign_info.txt", textField1.getText(), textField2.getText(), matchedEmail, new String(passwordField.getPassword()));
                    JOptionPane.showMessageDialog(Update_1.this, "Your password has been updated");
					dispose();
                } else {
                    JOptionPane.showMessageDialog(Update_1.this, "No matching email found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private String checkEmployeeMatch(String firstName, String lastName) {
        // Read the "employee.txt" file and check if the first name and last name match with any array
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into an array using a delimiter (adjust based on your file structure)
                String[] employeeData = line.split(",");
                if (employeeData.length >= 3 && employeeData[1].equals(firstName) && employeeData[2].equals(lastName)) {
                    return employeeData[4]; // Return the email when a match is found
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null; // No match found
    }

    private void writeEmployeeInfo(String fileName, String firstName, String lastName, String email, String password) {
        // Write information to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Append the new information to the file
            writer.write(firstName + "," + lastName + "," + email + "," + password);
            writer.newLine(); // Add a new line for the next entry
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	public void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }

    public static void main(String[] args) {
        // Create an instance of Update_1
        Update_1 frame = new Update_1();
        // Set the visibility of the frame
        frame.setVisible(true);
    }
}
