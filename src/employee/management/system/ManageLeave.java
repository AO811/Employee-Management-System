package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ManageLeave extends JFrame {

    JTable leaveTable;
    JButton approveButton, rejectButton, backButton;
    ArrayList<String> leaveIds = new ArrayList<>();
    String[][] data;
    String[] columns = {"Leave ID", "Employee ID", "Start Date", "End Date", "Reason", "Status"};

    ManageLeave() {
        setTitle("Manage Leave Applications");
        setSize(700, 400);
        setLocation(400, 200);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 144, 255)); 

        // Initialize Table
        fetchPendingLeaves();  // Fetch data initially
        leaveTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(leaveTable);
        leaveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  

        // Table Styling
        leaveTable.setRowHeight(30);
        leaveTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        leaveTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        leaveTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        // Panel for Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        backButton = new JButton("Back");

        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLeaveStatus("Approved");
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLeaveStatus("Rejected");
            }
        });

        // Action listener for back button to return to main class
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Main_class();  // Assuming Main_class is the main dashboard for manager
            }
        });

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(backButton);  // Add Back button

        // Add Panels to Frame
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Method to fetch pending leaves from database
    private void fetchPendingLeaves() {
        ArrayList<String[]> leaveDataList = new ArrayList<>();
        try {
            conn conn = new conn();
            String query = "SELECT leave_id, employee_id, start_date, end_date, reason, status FROM `leave` WHERE status = 'Pending'";
            ResultSet rs = conn.statement.executeQuery(query);

            while (rs.next()) {
                String[] row = {
                        rs.getString("leave_id"),
                        rs.getString("employee_id"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("reason"),
                        rs.getString("status")
                };
                leaveDataList.add(row);
                leaveIds.add(rs.getString("leave_id"));  // Save leave ID for updating
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = new String[leaveDataList.size()][6];
        data = leaveDataList.toArray(data);
    }

    // Method to update the leave status in the database
    private void updateLeaveStatus(String newStatus) {
        int selectedRow = leaveTable.getSelectedRow();
        if (selectedRow != -1) {
            String leaveId = leaveTable.getValueAt(selectedRow, 0).toString();

            // Confirm before updating
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to " + newStatus.toLowerCase() + " this leave application?",
                    "Confirm Action", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    conn conn = new conn();
                    String query = "UPDATE `leave` SET status = ? WHERE leave_id = ?";
                    PreparedStatement ps = conn.connection.prepareStatement(query);
                    ps.setString(1, newStatus);
                    ps.setString(2, leaveId);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Leave application " + newStatus.toLowerCase() + " successfully!");

                    // Refresh table data after update
                    fetchPendingLeaves();  // Refresh data
                    leaveTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));  // Update table model
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error updating leave status.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a leave application to " + newStatus.toLowerCase() + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ManageLeave();
    }
}
