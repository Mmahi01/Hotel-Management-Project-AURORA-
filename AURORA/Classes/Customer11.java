package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class Customer11 extends JFrame {

    private JPanel leftPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textField1, textField2, textField3, textField4, searchTextField;
    private JComboBox<String> comboBox1;

    private int serialNumber = 1; // Initial serial number
    private int maxSerialNumber = 1; // Track the maximum serial number used

    public Customer11() {
        setTitle("Customer");
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

        JLabel label2 = new JLabel("Phone num :");
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

        JLabel label4 = new JLabel("Address :");
		label4.setForeground(Color.WHITE);
        label4.setBounds(40, 325, 80, 25);
        leftPanel.add(label4);

        // Use text fields instead of the combo box
        textField3 = new JTextField();
        textField3.setBounds(150, 325, 200, 65);
        leftPanel.add(textField3);

        JLabel label5 = new JLabel("Date of Birth :");
		label5.setForeground(Color.WHITE);
        label5.setBounds(40, 400, 100, 25);
        leftPanel.add(label5);

        // Use text fields instead of the combo box
        textField4 = new JTextField();
        textField4.setBounds(150, 400, 200, 25);
        leftPanel.add(textField4);

        // Buttons
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

        // Search components
        searchTextField = new JTextField();
        searchTextField.setBounds(200, 40, 500, 25);
        leftPanel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        customizeButton(searchButton);
        searchButton.setBounds(730, 40, 80, 25);
        leftPanel.add(searchButton);

        JButton refreshButton = new JButton("Refresh");
        customizeButton(refreshButton);
        refreshButton.setBounds(840, 40, 80, 25);
        leftPanel.add(refreshButton);

        // Add a separator at x=432
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(432, 100, 2, getHeight() - 100);
        leftPanel.add(separator);

        // Add a table with 6 columns and infinite rows
        String[] columnNames = {"Customer ID", "Name", "Phone num", "Gender", "Address", "Date of Birth"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 150, 600, 200);
        leftPanel.add(tableScrollPane);

        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(60, 475, 150, 25);
        leftPanel.add(backButton);

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

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Use the next available serial number
                int newSerialNumber = maxSerialNumber++;

                // Get data from text fields and combo boxes
                String column2Value = textField1.getText();
                String column3Value = textField2.getText();
                String column4Value = comboBox1.getSelectedItem().toString();
                String column5Value = textField3.getText();
                String column6Value = textField4.getText();

                // Add data to the table with the new serial number
                Object[] rowData = {newSerialNumber, column2Value, column3Value, column4Value, column5Value, column6Value};
                tableModel.addRow(rowData);
				saveDataToFile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete selected rows from the table
                int[] selectedRows = table.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    // Convert the selected row index to the model coordinate system
                    int modelRowIndex = table.convertRowIndexToModel(selectedRows[i]);
                    // Remove the row from the table model
                    tableModel.removeRow(modelRowIndex);
                }

                // Update maxSerialNumber by finding the maximum serial number in the remaining data
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

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                    boolean allColumnsEmpty = true;

                    for (int j = 1; j < tableModel.getColumnCount(); j++) {
                        Object value = tableModel.getValueAt(i, j);

                        if (value != null && !value.toString().trim().isEmpty()) {
                            allColumnsEmpty = false;
                            break;
                        }
                    }

                    if (allColumnsEmpty) {
                        tableModel.removeRow(i);
                    }
                }

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                int columnIndexToSort = 0; // Column index of "Serial No" column
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);

                sorter.setComparator(columnIndexToSort, new Comparator<Integer>() {
                    public int compare(Integer num1, Integer num2) {
                        return Integer.compare(num1, num2);
                    }
                });

                sorter.sort();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
				dispose();
                // Assuming S_Frame is your main class, you can change it accordingly
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
                    String column6Value = tableModel.getValueAt(selectedRow, 5).toString();

                    textField1.setText(column2Value);
                    textField2.setText(column3Value);
                    comboBox1.setSelectedItem(column4Value);
                    textField3.setText(column5Value);
                    textField4.setText(column6Value);

                    JDialog editDialog = new JDialog(Customer11.this, "Edit Row", true);
                    editDialog.setLayout(new GridLayout(6, 2, 10, 10));
                    editDialog.setSize(300, 220);

                    editDialog.add(new JLabel("Name:"));
                    JTextField editTextField1 = new JTextField(column2Value);
                    editDialog.add(editTextField1);

                    editDialog.add(new JLabel("Phone num:"));
                    JTextField editTextField2 = new JTextField(column3Value);
                    editDialog.add(editTextField2);

                    editDialog.add(new JLabel("Gender:"));
                    JComboBox<String> editComboBox1 = new JComboBox<>(new String[]{"male", "female"});
                    editComboBox1.setSelectedItem(column4Value);
                    editDialog.add(editComboBox1);

                    editDialog.add(new JLabel("Address:"));
                    JTextField editTextField3 = new JTextField(column5Value);
                    editDialog.add(editTextField3);

                    editDialog.add(new JLabel("Date of Birth:"));
                    JTextField editTextField4 = new JTextField(column6Value);
                    editDialog.add(editTextField4);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String modifiedColumn2Value = editTextField1.getText();
                            String modifiedColumn3Value = editTextField2.getText();
                            String modifiedColumn4Value = editComboBox1.getSelectedItem().toString();
                            String modifiedColumn5Value = editTextField3.getText();
                            String modifiedColumn6Value = editTextField4.getText();

                            tableModel.setValueAt(modifiedColumn2Value, selectedRow, 1);
                            tableModel.setValueAt(modifiedColumn3Value, selectedRow, 2);
                            tableModel.setValueAt(modifiedColumn4Value, selectedRow, 3);
                            tableModel.setValueAt(modifiedColumn5Value, selectedRow, 4);
                            tableModel.setValueAt(modifiedColumn6Value, selectedRow, 5);

                            saveDataToFile();
							editDialog.dispose();
                        }
                    });

                    editDialog.add(saveButton);
                    editDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Customer11.this, "Please select a row to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
		loadDataFromFile();
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 188, 181));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }

    private void updateMaxSerialNumber() {
    maxSerialNumber = 1; // Reset to the default value

    for (int i = 0; i < tableModel.getRowCount(); i++) {
        Object serialNumberObj = tableModel.getValueAt(i, 0);
        if (serialNumberObj instanceof Integer) {
            int serialNumber = (int) serialNumberObj;
            if (serialNumber >= maxSerialNumber) {
                maxSerialNumber = serialNumber + 1;
            }
        } else if (serialNumberObj instanceof String) {
            try {
                int serialNumber = Integer.parseInt((String) serialNumberObj);
                if (serialNumber >= maxSerialNumber) {
                    maxSerialNumber = serialNumber + 1;
                }
            } catch (NumberFormatException e) {
                // Handle the case where parsing fails
                e.printStackTrace();
            }
        }
    }
}

	private void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("customer.txt"))) {
            // Write the data to the file in a formatted way
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.print(tableModel.getValueAt(i, j));
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

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                tableModel.addRow(values);
				// Update maxSerialNumber based on the loaded data
				int serialNumber = Integer.parseInt(values[0]);
				if (serialNumber >= maxSerialNumber) {
					maxSerialNumber = serialNumber + 1;
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
