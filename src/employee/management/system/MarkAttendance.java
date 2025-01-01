package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class MarkAttendance extends JFrame implements ActionListener {
    JComboBox<String> statusBox;
    JButton markAttendance, viewAttendance, backButton;
    String empID;

    MarkAttendance(String empID) {
        this.empID = empID;

        // Set up the frame
        setTitle("Mark Attendance");
        setSize(400, 300);  
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(95, 203, 250));

        // Use GridBagLayout for better layout control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  

        // Label for attendance status
        JLabel statusLabel = new JLabel("Attendance Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(statusLabel, gbc);

        // ComboBox for status selection
        String[] statusOptions = {"Present"}; 
        statusBox = new JComboBox<>(statusOptions);
        statusBox.setPreferredSize(new Dimension(200, 30)); 
        gbc.gridx = 1;
        add(statusBox, gbc);

        // Mark Attendance button
        markAttendance = new JButton("Mark Attendance");
        markAttendance.setPreferredSize(new Dimension(180, 40));  
        markAttendance.setFont(new Font("Arial", Font.BOLD, 14));
        markAttendance.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(markAttendance, gbc);

        // View Attendance button
        viewAttendance = new JButton("View Attendance");
        viewAttendance.setPreferredSize(new Dimension(180, 40));
        viewAttendance.setFont(new Font("Arial", Font.BOLD, 14));
        viewAttendance.addActionListener(this);
        gbc.gridy = 2;
        add(viewAttendance, gbc);

        // Back button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(180, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        gbc.gridy = 3;
        add(backButton, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == markAttendance) {
            String status = (String) statusBox.getSelectedItem();
            LocalDate currentDate = LocalDate.now();
            String month = currentDate.getMonthValue() + "-" + currentDate.getYear();

            try {
                conn c = new conn();

                // Check if attendance is already marked for today
                String checkQuery = "SELECT * FROM attendance WHERE employee_id = ? AND date = ?";
                PreparedStatement checkStmt = c.getConnection().prepareStatement(checkQuery);
                checkStmt.setString(1, empID);
                checkStmt.setDate(2, Date.valueOf(currentDate));
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "You have already marked your attendance for today.");
                } else {
                    
                    String query = "INSERT INTO attendance (employee_id, date, status, month) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = c.getConnection().prepareStatement(query);
                    pstmt.setString(1, empID);
                    pstmt.setDate(2, Date.valueOf(currentDate));
                    pstmt.setString(3, status);
                    pstmt.setString(4, month);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Attendance marked as Present.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error marking attendance: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else if (e.getSource() == viewAttendance) {
            new ViewAttendance(empID);  
        } else if (e.getSource() == backButton) {
            dispose(); 
            new Main_class_employee(empID); 
        }
    }
}
