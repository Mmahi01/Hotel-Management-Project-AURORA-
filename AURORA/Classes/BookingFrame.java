package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BookingFrame extends JFrame {

    private JPanel bookingPanel;
    private JTable bookingTable;
    private DefaultTableModel bookingTableModel;
    private JTextField roomIDField, customerIDField, bookingDateField, bookingDurationField;

    public BookingFrame() {
        setTitle("Booking Frame");
        setSize(1080, 720);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        bookingPanel = new JPanel();
        bookingPanel.setLayout(null);
        bookingPanel.setBackground(new Color(64, 188, 181));
        getContentPane().add(bookingPanel);

        // Labels and Text Fields
        JLabel roomIDLabel = new JLabel("Room ID:");
		roomIDLabel.setForeground(Color.WHITE);
        roomIDLabel.setBounds(40, 50, 80, 25);
        bookingPanel.add(roomIDLabel);

        roomIDField = new JTextField();
        roomIDField.setBounds(150, 50, 200, 25);
        bookingPanel.add(roomIDField);

        JLabel customerIDLabel = new JLabel("Customer ID:");
		customerIDLabel.setForeground(Color.WHITE);
        customerIDLabel.setBounds(40, 90, 100, 25);
        bookingPanel.add(customerIDLabel);

        customerIDField = new JTextField();
        customerIDField.setBounds(150, 90, 200, 25);
        bookingPanel.add(customerIDField);

        JLabel bookingDateLabel = new JLabel("Booking Date:");
		bookingDateLabel.setForeground(Color.WHITE);
        bookingDateLabel.setBounds(40, 130, 120, 25);
        bookingPanel.add(bookingDateLabel);

        bookingDateField = new JTextField();
        bookingDateField.setBounds(150, 130, 200, 25);
        bookingPanel.add(bookingDateField);

        JLabel bookingDurationLabel = new JLabel("Booking Duration:");
		bookingDurationLabel.setForeground(Color.WHITE);
        bookingDurationLabel.setBounds(40, 170, 140, 25);
        bookingPanel.add(bookingDurationLabel);

        bookingDurationField = new JTextField();
        bookingDurationField.setBounds(150, 170, 200, 25);
        bookingPanel.add(bookingDurationField);

        // Buttons
        JButton bookButton = new JButton("Book");
        customizeButton(bookButton);
        bookButton.setBounds(150, 210, 150, 25);
        bookingPanel.add(bookButton);

        JButton editButton = new JButton("Edit");
        customizeButton(editButton);
        editButton.setBounds(150, 250, 150, 25);
        bookingPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        customizeButton(deleteButton);
        deleteButton.setBounds(150, 290, 150, 25);
        bookingPanel.add(deleteButton);

        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(150, 330, 150, 25);
        bookingPanel.add(backButton);

        // Add a table with 4 columns and infinite rows
        String[] columnNames = {"Room ID", "Customer ID", "Booking Date", "Booking Duration"};
        bookingTableModel = new DefaultTableModel(null, columnNames);
        bookingTable = new JTable(bookingTableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        tableScrollPane.setBounds(400, 50, 350, 300);
        bookingPanel.add(tableScrollPane);

        // Add a component listener to track changes in the frame's size
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Adjust the size of the booking panel to match the frame's size
                bookingPanel.setBounds(0, 0, getWidth(), getHeight());
                // Adjust the size and position of the table scroll pane
                tableScrollPane.setBounds(400, 50, getWidth() * 3 / 5 - 50, getHeight() - 100);
            }
        });

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String roomID = roomIDField.getText();
                String customerID = customerIDField.getText();
                String bookingDate = bookingDateField.getText();
                String bookingDuration = bookingDurationField.getText();

                // Add data to the booking table
                Object[] rowData = {roomID, customerID, bookingDate, bookingDuration};
                bookingTableModel.addRow(rowData);

                // Clear text fields after booking
                clearFields();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookingTable.getSelectedRow();
                if (selectedRow != -1) {
                    String roomID = bookingTableModel.getValueAt(selectedRow, 0).toString();
                    String customerID = bookingTableModel.getValueAt(selectedRow, 1).toString();
                    String bookingDate = bookingTableModel.getValueAt(selectedRow, 2).toString();
                    String bookingDuration = bookingTableModel.getValueAt(selectedRow, 3).toString();

                    roomIDField.setText(roomID);
                    customerIDField.setText(customerID);
                    bookingDateField.setText(bookingDate);
                    bookingDurationField.setText(bookingDuration);

                    // Remove the selected row
                    bookingTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(BookingFrame.this, "Please select a row to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookingTable.getSelectedRow();
                if (selectedRow != -1) {
                    bookingTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(BookingFrame.this, "Please select a row to delete.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new U_Frame();
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        roomIDField.setText("");
        customerIDField.setText("");
        bookingDateField.setText("");
        bookingDurationField.setText("");
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 188, 181));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }
}
