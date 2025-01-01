package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;

public class PayrollSystem extends JFrame implements ActionListener {
    JLabel lblEmpId, lblSalary, lblIncrement, lblMonth;
    JTextField txtEmpId, txtSalary, txtIncrement;
    JComboBox<String> cmbMonth;
    JButton btnUpdate, btnBack;

    PayrollSystem() {
        setTitle("Payroll Management");

        // Employee ID
        lblEmpId = new JLabel("Employee ID:");
        lblEmpId.setBounds(50, 30, 150, 30);
        lblEmpId.setFont(new Font("serif", Font.BOLD, 20));
        add(lblEmpId);

        txtEmpId = new JTextField();
        txtEmpId.setBounds(200, 30, 150, 30);
        add(txtEmpId);

        // Base Salary
        lblSalary = new JLabel("Base Salary:");
        lblSalary.setBounds(50, 80, 150, 30);
        lblSalary.setFont(new Font("serif", Font.BOLD, 20));
        add(lblSalary);

        txtSalary = new JTextField();
        txtSalary.setBounds(200, 80, 150, 30);
        add(txtSalary);

        // Increment/Decrement
        lblIncrement = new JLabel("Increment/Decrement:");
        lblIncrement.setBounds(50, 130, 200, 30);
        lblIncrement.setFont(new Font("serif", Font.BOLD, 20));
        add(lblIncrement);

        txtIncrement = new JTextField();
        txtIncrement.setBounds(250, 130, 100, 30);
        add(txtIncrement);

        // Month Selection
        lblMonth = new JLabel("Month:");
        lblMonth.setBounds(50, 180, 150, 30);
        lblMonth.setFont(new Font("serif", Font.BOLD, 20));
        add(lblMonth);

        cmbMonth = new JComboBox<>(getMonths());
        cmbMonth.setBounds(200, 180, 150, 30);
        add(cmbMonth);

        // Buttons
        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(50, 230, 150, 30);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnBack = new JButton("BACK");
        btnBack.setBounds(220, 230, 150, 30);
        btnBack.addActionListener(this);
        add(btnBack);

        // Frame settings
        setLayout(null);
        setSize(450, 350);
        setLocation(450, 200);
        setVisible(true);
        getContentPane().setBackground(new Color(95, 203, 250));
    }

    // Action event handling
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            String empId = txtEmpId.getText().trim();
            String salary = txtSalary.getText().trim();
            String increment = txtIncrement.getText().trim();
            String month = (String) cmbMonth.getSelectedItem();

            // Input validation
            if (empId.isEmpty() || salary.isEmpty() || increment.isEmpty() || month.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            double salaryValue, incrementValue;
            try {
                salaryValue = Double.parseDouble(salary);
                incrementValue = Double.parseDouble(increment);
                salaryValue += incrementValue;

                if (salaryValue < 40000 || salaryValue > 400000) {
                    JOptionPane.showMessageDialog(null, "Final salary must be between 40,000 and 400,000.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Salary and Increment must be numeric.");
                return;
            }

            // Confirm salary update
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the salary?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Update salary in database for the specific month
            try {
                conn c = new conn();

                // Check if employee exists
                String checkQuery = "SELECT * FROM employee WHERE empId = ?";
                PreparedStatement checkPst = c.connection.prepareStatement(checkQuery);
                checkPst.setString(1, empId);
                ResultSet rs = checkPst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Employee ID not found.");
                    return;
                }

                // Update salary for the specific month
                String query = "UPDATE employee SET salary = ? WHERE empId = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setDouble(1, salaryValue);
                pst.setString(2, empId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Salary for " + month + " updated successfully");
                setVisible(false);
                new Main_class();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating salary.");
            }
        } else if (e.getSource() == btnBack) {
            setVisible(false);
            new Main_class();
        }
    }

    // Method to get months list
    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.getDefault());
        }
        return months;
    }

    public static void main(String[] args) {
        new PayrollSystem();
    }
}
