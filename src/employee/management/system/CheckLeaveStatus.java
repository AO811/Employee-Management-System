package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CheckLeaveStatus extends JFrame {
    JTable statusTable;
    String empID;
    String[][] data;
    String[] columns = {"Leave ID", "Start Date", "End Date", "Reason", "Status"};

    CheckLeaveStatus(String empID) {
        this.empID = empID;

        setTitle("Leave Application Status");
        setSize(600, 400);
        setLocation(450, 200);
        setLayout(new BorderLayout());

        // Fetch leave data
        fetchLeaveStatus();

        // Display data in table
        statusTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(statusTable);
        statusTable.setRowHeight(30);
        statusTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));

        add(scrollPane, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(30, 144, 255)); 
        setVisible(true);
    }

    private void fetchLeaveStatus() {
        ArrayList<String[]> leaveStatusList = new ArrayList<>();
        try {
            conn conn = new conn();
            String query = "SELECT leave_id, start_date, end_date, reason, status FROM `leave` WHERE employee_id = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, empID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = {
                        rs.getString("leave_id"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("reason"),
                        rs.getString("status")
                };
                leaveStatusList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = new String[leaveStatusList.size()][5];
        data = leaveStatusList.toArray(data);
    }
}
