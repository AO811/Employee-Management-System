package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAttendance extends JFrame {
    String empID;
    JTable attendanceTable;
    DefaultTableModel tableModel;

    public ViewAttendance(String empID) {
        this.empID = empID;

        // Frame settings
        setTitle("Attendance Record - Employee ID: " + empID);
        setSize(500, 400);
        setLocation(500, 300);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(95, 203, 250));

        // Initialize table and table model
        String[] columnNames = {"Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        attendanceTable = new JTable(tableModel);

        // Fetch attendance data from the database
        loadAttendanceData();

        // Add components to the frame
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        add(scrollPane, BorderLayout.CENTER);

        // Frame visibility and close operation
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadAttendanceData() {
        try {
            conn c = new conn(); 
            Connection connection = c.getConnection();
            String query = "SELECT date, status FROM attendance WHERE employee_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, empID);
            ResultSet rs = pstmt.executeQuery();

            
            while (rs.next()) {
                String date = rs.getDate("date").toString();
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{date, status});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
