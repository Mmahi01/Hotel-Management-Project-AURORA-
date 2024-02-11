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


public class Rooms11 extends JFrame {

    private JPanel leftPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textField1, textField2, searchTextField;
    private JComboBox<String> comboBox1, comboBox2;

    private int maxSerialNumber = 1; // Track the maximum serial number used

    public Rooms11() {
        setTitle("Rooms");
        setSize(1080, 720);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(64, 188, 181));
        getContentPane().add(leftPanel);

        // Labels and Text Fields
        JLabel label1 = new JLabel("Name :");
		label1.setForeground(Color.WHITE);
        label1.setBounds(40, 220, 80, 25);
        leftPanel.add(label1);

        textField1 = new JTextField();
        textField1.setBounds(150, 220, 200, 25);
        leftPanel.add(textField1);

        JLabel label2 = new JLabel("Charge :");
        label2.setForeground(Color.WHITE);
		label2.setBounds(40, 255, 80, 25);
        leftPanel.add(label2);

        textField2 = new JTextField();
        textField2.setBounds(150, 255, 200, 25);
        leftPanel.add(textField2);

        JLabel label3 = new JLabel("Categories :");
        label3.setForeground(Color.WHITE);
		label3.setBounds(40, 290, 80, 25);
        leftPanel.add(label3);

        String[] options1 = {"VIP", "Double Bed", "Single Bed"};
        comboBox1 = new JComboBox<>(options1);
        comboBox1.setBounds(150, 290, 200, 25);
        leftPanel.add(comboBox1);

        JLabel label4 = new JLabel("Status :");
		label4.setForeground(Color.WHITE);
        label4.setBounds(40, 325, 80, 25);
        leftPanel.add(label4);

        String[] options2 = {"Reserved", "Free"};
        comboBox2 = new JComboBox<>(options2);
        comboBox2.setBounds(150, 325, 200, 25);
        leftPanel.add(comboBox2);

        // Buttons

        JButton editButton = new JButton("Edit");
        customizeButton(editButton);
        editButton.setBounds(240, 405, 150, 25);
        leftPanel.add(editButton);


        // Search components
        searchTextField = new JTextField();
        searchTextField.setBounds(200, 40, 500, 25);
        leftPanel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        customizeButton(searchButton);
        searchButton.setBounds(730, 40, 80, 25);
        leftPanel.add(searchButton);

        // Add a separator at x=432
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(432, 100, 2, getHeight() - 100);
        leftPanel.add(separator);

        // Add a table with 5 columns and infinite rows
        String[] columnNames = {"Serial No", "Name", "Categories", "Charge", "Status"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 150, 600, 200);
        leftPanel.add(tableScrollPane);

        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(60, 405, 150, 25);
        leftPanel.add(backButton);

        // Load data from file when initializing the Rooms frame
        loadDataFromFile();

        // Find the maximum serial number from the loaded data
        findMaxSerialNumber();

        // Add a component listener to track changes in the frame's size
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Adjust the size of the left panel to match the frame's size
                leftPanel.setBounds(0, 0, getWidth(), getHeight());
                // Adjust the separator's height to match the frame's height
                separator.setBounds(432, 100, 2, getHeight() - 100);
                // Adjust the size and position of the table scroll pane
                tableScrollPane.setBounds(450, 150, getWidth() * 3 / 5 - 50, getHeight() - 200);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchTextField.getText().toLowerCase();
                table.clearSelection();

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 1; j < table.getColumnCount(); j++) {
                        String cellValue = table.getValueAt(i, j).toString().toLowerCase();

                        if (cellValue.contains(searchTerm)) {
                            table.addRowSelectionInterval(i, i);
                            break;
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new U_Frame();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String column2Value = tableModel.getValueAt(selectedRow, 1).toString();
                    String column3Value = tableModel.getValueAt(selectedRow, 2).toString();
                    String column4Value = tableModel.getValueAt(selectedRow, 3).toString();
                    String column5Value = tableModel.getValueAt(selectedRow, 4).toString();

                    JDialog editDialog = new JDialog(Rooms11.this, "Edit Row", true);
                    editDialog.setLayout(new GridLayout(5, 2, 10, 10));
                    editDialog.setSize(300, 200);

                    editDialog.add(new JLabel("Name:"));
                    JTextField editTextField1 = new JTextField(column2Value);
                    editDialog.add(editTextField1);

                    editDialog.add(new JLabel("Categories:"));
                    JComboBox<String> editComboBox1 = new JComboBox<>(new String[]{"VIP", "Double Bed", "Single Bed"});
                    editComboBox1.setSelectedItem(column3Value);
                    editDialog.add(editComboBox1);

                    editDialog.add(new JLabel("Charge:"));
                    JTextField editTextField2 = new JTextField(column4Value);
                    editDialog.add(editTextField2);

                    editDialog.add(new JLabel("Status:"));
                    JComboBox<String> editComboBox2 = new JComboBox<>(new String[]{"Reserved", "Free"});
                    editComboBox2.setSelectedItem(column5Value);
                    editDialog.add(editComboBox2);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String modifiedColumn2Value = editTextField1.getText();
                            String modifiedColumn3Value = editComboBox1.getSelectedItem().toString();
                            String modifiedColumn4Value = editTextField2.getText();
                            String modifiedColumn5Value = editComboBox2.getSelectedItem().toString();

                            tableModel.setValueAt(modifiedColumn2Value, selectedRow, 1);
                            tableModel.setValueAt(modifiedColumn3Value, selectedRow, 2);
                            tableModel.setValueAt(modifiedColumn4Value, selectedRow, 3);
                            tableModel.setValueAt(modifiedColumn5Value, selectedRow, 4);

                            editDialog.dispose();
                            saveDataToFile();  // Save data to file after editing a row
                        }
                    });

                    editDialog.add(saveButton);
                    editDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Rooms11.this, "Please select a row to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
	
	public int getTotalRowCount() {
    return tableModel.getRowCount();
	}
	
	public int getReservedRoomCount() {
        int reservedCount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = tableModel.getValueAt(i, 4).toString();
            if (status.equalsIgnoreCase("Reserved")) {
                reservedCount++;
            }
        }
        return reservedCount;
    }
	
	public int getFreeRoomCount() {
        int freeCount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = tableModel.getValueAt(i, 4).toString();
            if (status.equalsIgnoreCase("Free")) {
                freeCount++;
            }
        }
        return freeCount;
    }

    private void loadDataFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("Rooms.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            tableModel.addRow(data);
        }

        // Find the maximum serial number from the loaded data
        maxSerialNumber = (findMaxSerialNumber()+1);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Rooms.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    line.append(tableModel.getValueAt(i, j)).append(",");
                }
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int findMaxSerialNumber() {
        int maxSerial = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 0);

            if (value instanceof Integer) {
                int serialNumber = (Integer) value;
                maxSerial = Math.max(maxSerial, serialNumber);
            } else if (value instanceof String) {
                int serialNumber = Integer.parseInt((String) value);
                maxSerial = Math.max(maxSerial, serialNumber);
            }
        }
        return maxSerial;
    }


    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 188, 181));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }
}
