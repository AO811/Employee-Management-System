package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ViewAttendanceRecords extends JFrame {
    private JComboBox<String> employeeComboBox;
    private JButton viewButton, backButton; // Add backButton
    private JTextArea attendanceTextArea;

    public ViewAttendanceRecords() {
        setTitle("View Attendance Records");
        setLayout(new FlowLayout());
        setSize(400, 350); // Increased height for the back button
        setLocation(500, 300);
        getContentPane().setBackground(new Color(95, 203, 250));

        // Label for Employee Selection
        JLabel selectEmployeeLabel = new JLabel("Select Employee:");
        add(selectEmployeeLabel);

        // ComboBox for Employee Selection
        employeeComboBox = new JComboBox<>(getEmployeeIDs());
        add(employeeComboBox);

        // View Button
        viewButton = new JButton("View Attendance");
        viewButton.addActionListener(new ViewButtonListener());
        add(viewButton);

        // Back Button
        backButton = new JButton("Back"); // Initialize Back button
        backButton.addActionListener(new BackButtonListener()); // Add ActionListener
        add(backButton);

        // Text Area to Display Attendance Records
        attendanceTextArea = new JTextArea(10, 30);
        attendanceTextArea.setEditable(false);
        add(new JScrollPane(attendanceTextArea));

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private String[] getEmployeeIDs() {
        ArrayList<String> employeeIDs = new ArrayList<>();
        try {
            conn c = new conn();
            String query = "SELECT empID FROM employee"; 
            Statement stmt = c.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                employeeIDs.add(rs.getString("empID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeIDs.toArray(new String[0]);
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedEmpID = (String) employeeComboBox.getSelectedItem();
            displayAttendanceRecords(selectedEmpID);
        }
    }

    private void displayAttendanceRecords(String empID) {
        StringBuilder records = new StringBuilder();
        try {
            conn c = new conn();
            String query = "SELECT date, status FROM attendance WHERE employee_id = ?";
            PreparedStatement pstmt = c.getConnection().prepareStatement(query);
            pstmt.setString(1, empID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.append("Date: ").append(rs.getDate("date"))
                       .append(", Status: ").append(rs.getString("status")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        attendanceTextArea.setText(records.toString());
    }

    // ActionListener for the Back button
    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); 
            new Main_class(); 
        }
    }

    public static void main(String[] args) {
        new ViewAttendanceRecords(); 
    }
}
