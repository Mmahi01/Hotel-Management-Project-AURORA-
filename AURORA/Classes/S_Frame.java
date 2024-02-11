package Classes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class S_Frame extends JFrame {

    JButton jb1, jb2, jb3, jb4, logoutButton;
    JLabel welcomeLabel;
    JPanel contentPanel;  // Use a panel to hold the content

    public S_Frame() {
        // Set the title of the frame
        setTitle("Admin");

        // Set the size of the frame to 1080x720 pixels
        setSize(1080, 720);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Make the frame unresizable
        setResizable(false);

        // Create a green panel
        JPanel gPanel = new JPanel();
        gPanel.setBackground(new Color(46, 204, 113));
        gPanel.setLayout(null); // Important: Set layout to null to use absolute positioning

        jb1 = new JButton("Rooms");
        jb1.setBounds(100, 240, 190, 40);
        customizeButton(jb1);

        jb2 = new JButton("Customers");
        jb2.setBounds(100, 300, 190, 40);
        customizeButton(jb2);

        jb3 = new JButton("Employee");
        jb3.setBounds(100, 360, 190, 40);
        customizeButton(jb3);

        jb4 = new JButton("Dashboard");
        jb4.setBounds(100, 420, 190, 40);
        customizeButton(jb4);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 480, 190, 40);
        customizeButton(logoutButton);

        gPanel.add(jb1);
        gPanel.add(jb2);
        gPanel.add(jb3);
        gPanel.add(jb4);
        gPanel.add(logoutButton);

        // Create a panel to hold the content
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.add(gPanel);

        // Add the content panel to the frame
        add(contentPanel);

        // Create a label for the welcome message
        welcomeLabel = new JLabel("Welcome to The Admin Panel");
        welcomeLabel.setBounds(getWidth() / 2, getHeight() / 2 - 25, 500, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setForeground(Color.BLACK);

        // Add the label to the content panel
        contentPanel.add(welcomeLabel);

        // Add a component listener to track changes in the frame's size
        addComponentListener(new ComponentAdapter() {
    
            public void componentResized(ComponentEvent e) {
                // Adjust the size and position of the green panel when the frame is resized
                gPanel.setBounds(0, 0, getWidth() * 2 / 5, getHeight());
                // Adjust the size and position of the welcome label to center it
                int labelWidth = getWidth() * 3 / 5;
                int labelHeight = 50;
                int labelX = getWidth() / 2 - labelWidth / 2;
                int labelY = getHeight() / 2 - labelHeight / 2;
                welcomeLabel.setBounds(labelX + 390, labelY, labelWidth, labelHeight);
            }
        });

        // Add ActionListener to jb1
        jb1.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (S_Frame)
                dispose();

                // Create a new instance of Rooms
                new Rooms();
            }
        });

        // Add ActionListener to jb2
        jb2.addActionListener(new ActionListener() {
      
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (S_Frame)
                dispose();

                // Create a new instance of Customer
                new Customer();
            }
        });

        // Add ActionListener to jb3
        jb3.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (S_Frame)
                dispose();

                // Create a new instance of Employee
                new Employee();
            }
        });

        // Add ActionListener to jb4
        jb4.addActionListener(new ActionListener() {
   
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Create a new instance of Dashboard
                new Dashboard();
            }
        });

        // Add ActionListener to Logout button
        logoutButton.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (S_Frame)
                dispose();

                U_log uLog = new U_log("default_user");
				uLog.setVisible(true);
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46, 204, 113));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create a rounded border
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }
}
