package Classes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Employee extends JFrame {
    private JPanel leftPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textField1, textField2, textField3, searchTextField;
    private JComboBox<String> comboBox1, comboBox11;

    private int maxSerialNumber = 1; // Track the maximum serial number used

    public Employee() {
        // Set up the frame
        setTitle("Employee");
        setSize(1080, 720);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(46, 204, 113));
        getContentPane().add(leftPanel);

        // Labels and Text Fields
        JLabel label1 = new JLabel("First Name :");
		label1.setForeground(Color.WHITE);
        label1.setBounds(40, 220, 80, 25);
        leftPanel.add(label1);

        textField1 = new JTextField();
        textField1.setBounds(150, 220, 200, 25);
        leftPanel.add(textField1);

        JLabel label2 = new JLabel("Last Name :");
		label2.setForeground(Color.WHITE);
        label2.setBounds(40, 255, 80, 25);
        leftPanel.add(label2);

        textField2 = new JTextField();
        textField2.setBounds(150, 255, 200, 25);
        leftPanel.add(textField2);

        JLabel label3 = new JLabel("Gender :");
		label3.setForeground(Color.WHITE);
        label3.setBounds(40, 290, 80, 25);
        leftPanel.add(label3);

        String[] options1 = {"male", "Female"};
        comboBox1 = new JComboBox<>(options1);
        comboBox1.setBounds(150, 290, 200, 25);
        leftPanel.add(comboBox1);

        JLabel label4 = new JLabel("Email :");
		label4.setForeground(Color.WHITE);
        label4.setBounds(40, 325, 80, 25);
        leftPanel.add(label4);

        textField3 = new JTextField();
        textField3.setBounds(150, 325, 200, 65);
        leftPanel.add(textField3);

        JLabel label5 = new JLabel("Employee Type :");
		label5.setForeground(Color.WHITE);
        label5.setBounds(40, 400, 100, 25);
        leftPanel.add(label5);

        String[] options11 = {"Front Desk Agent", "General Manager"};
        comboBox11 = new JComboBox<>(options11);
        comboBox11.setBounds(150, 400, 200, 25);
        leftPanel.add(comboBox11);

        JButton addButton = new JButton("Add");
        customizeButton(addButton);
        addButton.setBounds(60, 440, 150, 25);
        leftPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        customizeButton(editButton);
        editButton.setBounds(240, 440, 150, 25);
        leftPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        customizeButton(deleteButton);
        deleteButton.setBounds(240, 475, 150, 25);
        leftPanel.add(deleteButton);

        searchTextField = new JTextField();
        searchTextField.setBounds(200, 40, 500, 25);
        leftPanel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        customizeButton(searchButton);
        searchButton.setBounds(730, 40, 80, 25);
        leftPanel.add(searchButton);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(432, 100, 2, getHeight() - 100);
        leftPanel.add(separator);

        String[] columnNames = {"Employee ID", "First Name", "Last Name", "Gender", "Email", "Employee Type"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 150, 600, 200);
        leftPanel.add(tableScrollPane);

        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(60, 475, 150, 25);
        leftPanel.add(backButton);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                leftPanel.setBounds(0, 0, getWidth(), getHeight());
                separator.setBounds(432, 100, 2, getHeight() - 100);
                tableScrollPane.setBounds(450, 150, getWidth() * 3 / 5 - 50, getHeight() - 200);
            }
        });

        readDataFromFileAndPopulateTable();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int newSerialNumber = maxSerialNumber++;

                String column2Value = textField1.getText();
                String column3Value = textField2.getText();
                String column4Value = comboBox1.getSelectedItem().toString();
                String column5Value = textField3.getText();
                String column6Value = comboBox11.getSelectedItem().toString();

                Object[] rowData = {newSerialNumber, column2Value, column3Value, column4Value, column5Value, column6Value};
                tableModel.addRow(rowData);

                saveDataToFile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int modelRowIndex = table.convertRowIndexToModel(selectedRows[i]);
                    tableModel.removeRow(modelRowIndex);
                }

                updateMaxSerialNumber();

                saveDataToFile();
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
                new S_Frame();
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
                    String column6Value = tableModel.getValueAt(selectedRow, 5).toString();

                    textField1.setText(column2Value);
                    textField2.setText(column3Value);
                    comboBox1.setSelectedItem(column4Value);
                    textField3.setText(column5Value);
                    comboBox11.setSelectedItem(column6Value);

                    JDialog editDialog = new JDialog(Employee.this, "Edit Employee", true);
                    editDialog.setLayout(new GridLayout(6, 2, 10, 10));
                    editDialog.setSize(300, 220);

                    editDialog.add(new JLabel("First Name:"));
                    JTextField editTextField1 = new JTextField(column2Value);
                    editDialog.add(editTextField1);

                    editDialog.add(new JLabel("Last Name:"));
                    JTextField editTextField2 = new JTextField(column3Value);
                    editDialog.add(editTextField2);

                    editDialog.add(new JLabel("Gender:"));
                    JComboBox<String> editComboBox1 = new JComboBox<>(new String[]{"male", "female"});
                    editComboBox1.setSelectedItem(column4Value);
                    editDialog.add(editComboBox1);

                    editDialog.add(new JLabel("Email:"));
                    JTextField editTextField3 = new JTextField(column5Value);
                    editDialog.add(editTextField3);

                    editDialog.add(new JLabel("Employee Type:"));
                    JComboBox<String> editComboBox11 = new JComboBox<>(new String[]{"Front Desk Agent", "General Manager"});
                    editComboBox11.setSelectedItem(column6Value);
                    editDialog.add(editComboBox11);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String modifiedColumn2Value = editTextField1.getText();
                            String modifiedColumn3Value = editTextField2.getText();
                            String modifiedColumn4Value = editComboBox1.getSelectedItem().toString();
                            String modifiedColumn5Value = editTextField3.getText();
                            String modifiedColumn6Value = editComboBox11.getSelectedItem().toString();

                            tableModel.setValueAt(modifiedColumn2Value, selectedRow, 1);
                            tableModel.setValueAt(modifiedColumn3Value, selectedRow, 2);
                            tableModel.setValueAt(modifiedColumn4Value, selectedRow, 3);
                            tableModel.setValueAt(modifiedColumn5Value, selectedRow, 4);
                            tableModel.setValueAt(modifiedColumn6Value, selectedRow, 5);

                            editDialog.dispose();

                            saveDataToFile();
                        }
                    });

                    editDialog.add(saveButton);
                    editDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Employee.this, "Please select a row to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46, 204, 113));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }

    private void readDataFromFileAndPopulateTable() {
        clearTable();

        String filePath = "employee.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");

                if (rowData.length >= 6) {
                    try {
                        int serialNumber = Integer.parseInt(rowData[0]);
                        Object[] tableData = {serialNumber, rowData[1], rowData[2], rowData[3], rowData[4], rowData[5]};
                        tableModel.addRow(tableData);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing serial number: " + rowData[0]);
                    }
                }
            }

            updateMaxSerialNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTable() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void updateMaxSerialNumber() {
        maxSerialNumber = 1;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int serialNumber = (int) tableModel.getValueAt(i, 0);
            if (serialNumber >= maxSerialNumber) {
                maxSerialNumber = serialNumber + 1;
            }
        }
    }

    public int getMaxEmployeeID() {
        return maxSerialNumber;
    }

    public int getTotalEmployeeCount() {
        return tableModel.getRowCount();
    }

    public void saveDataToFile() {
        String filePath = "employee.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object cellValue = tableModel.getValueAt(i, j);
                    writer.print(cellValue != null ? cellValue.toString() : "");
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
