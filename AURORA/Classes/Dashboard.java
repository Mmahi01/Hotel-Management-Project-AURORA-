package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Dashboard extends JFrame {
    private JButton backButton;
    private JLabel totalRoomsLabel;
    private JLabel reservedRoomsLabel;
    private JLabel freeRoomsLabel;
    private JLabel maxEmployeeIDLabel;
    private Rooms roomsInstance;
    private Employee employeeInstance;

    // Declare all panels as class-level variables
    private JPanel panel1, panel2, panel3, panel4;

    public Dashboard() {
        initialize();
        roomsInstance = new Rooms();
        employeeInstance = new Employee();
        hideFrames(); // Call the method to hide frames
        updateLabels(); // Call the method to update labels
    }

    private void initialize() {
        setTitle("Dashboard");
        setSize(800, 720);
		Color customColor = new Color(46, 204, 113);
        getContentPane().setBackground(customColor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
		setResizable(false);
		

        // Add panels with small size
        int panelWidth = 250;
        int panelHeight = 250;

        panel1 = createPanel(110, 50, panelWidth, panelHeight);
		panel1.setBackground(new Color(50, 190, 200));
        add(panel1);

        panel2 = createPanel(430, 50, panelWidth, panelHeight);
		panel2.setBackground(new Color(255, 150, 50));
        add(panel2);

        panel3 = createPanel(110, 400, panelWidth, panelHeight);
		panel3.setBackground(new Color(80, 120, 200));
        add(panel3);

        panel4 = createPanel(430, 400, panelWidth, panelHeight);
		panel4.setBackground(new Color(220, 70, 70));
        add(panel4);

        backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(10, 10, 100, 30);
        add(backButton);

        setVisible(true);

        backButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                new S_Frame();
            }
        });
    }

    private JPanel createPanel(int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
		// Add a line border to the panel
		Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
		panel.setBorder(roundedBorder);
        // Add any additional customization or components to the panel if needed.
        return panel;
    }

    private void hideFrames() {
        // Assuming Rooms class extends JFrame
        roomsInstance.setVisible(false);
    }

    private void updateLabels() {
        hideFrames1();

        // Display information
        int totalRoomCount = roomsInstance.getTotalRowCount();
        int reservedRoomCount = roomsInstance.getReservedRoomCount();
        int freeRoomCount = roomsInstance.getFreeRoomCount();
        int totalEmployeeCount = employeeInstance.getTotalEmployeeCount();

        // Update labels with the information
        totalRoomsLabel = new JLabel("Total Rooms: " + (totalRoomCount));
        totalRoomsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalRoomsLabel.setBounds(400, 150, 200, 30);
        panel1.add(totalRoomsLabel);

        reservedRoomsLabel = new JLabel("Reserved Rooms: " + (reservedRoomCount));
        reservedRoomsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        reservedRoomsLabel.setBounds(400, 180, 200, 30);
        panel2.add(reservedRoomsLabel);

        freeRoomsLabel = new JLabel("Free Rooms: " + freeRoomCount);
        freeRoomsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        freeRoomsLabel.setBounds(400, 210, 200, 30);
        panel3.add(freeRoomsLabel);

        maxEmployeeIDLabel = new JLabel("Total Employees: " + totalEmployeeCount);
        maxEmployeeIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        maxEmployeeIDLabel.setBounds(400, 100, 200, 30);
        panel4.add(maxEmployeeIDLabel);

        revalidate();
        repaint();
    }

    private void hideFrames1() {
        // Assuming Employee class extends JFrame
        employeeInstance.setVisible(false);
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46, 204, 113));

        // Added import statements for Border and LineBorder
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }
}
