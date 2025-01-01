package employee.management.system;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

public class Apply_Leave extends JFrame implements ActionListener {
    Random ran = new Random();
    int number = ran.nextInt(999999);
    JTextArea reasonArea;
    String empID;
    JDateChooser startDateField, endDateField;
    JLabel tempid;
    JButton submitButton, backButton, checkStatusButton;

    Apply_Leave(String empID) {
        this.empID = empID;

        setTitle("Apply for Leave");
        setLayout(null);

        JLabel leaveidLabel = new JLabel("Leave ID:");
        leaveidLabel.setBounds(50, 400, 150, 30);
        leaveidLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(leaveidLabel);

        tempid = new JLabel("" + number);
        tempid.setBounds(200, 400, 150, 30);
        tempid.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        tempid.setForeground(Color.RED);
        add(tempid);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(30, 30, 200, 30);
        add(startDateLabel);

        startDateField = new JDateChooser();
        startDateField.setBounds(200, 30, 150, 30);
        add(startDateField);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(30, 80, 200, 30);
        add(endDateLabel);

        endDateField = new JDateChooser();
        endDateField.setBounds(200, 80, 150, 30);
        add(endDateField);

        JLabel reasonLabel = new JLabel("Reason:");
        reasonLabel.setBounds(30, 130, 100, 30);
        add(reasonLabel);

        reasonArea = new JTextArea();
        reasonArea.setBounds(200, 130, 250, 100);
        add(reasonArea);

        submitButton = new JButton("Submit Leave Application");
        submitButton.setBounds(50, 250, 200, 30);
        submitButton.addActionListener(this);
        add(submitButton);

        checkStatusButton = new JButton("Check Status");
        checkStatusButton.setBounds(270, 250, 150, 30);
        checkStatusButton.addActionListener(this);
        add(checkStatusButton);

        backButton = new JButton("Back");
        backButton.setBounds(180, 300, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        setSize(500, 500);
        setLocation(450, 200);
        setVisible(true);
        getContentPane().setBackground(new Color(30, 144, 255)); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String leave_id = tempid.getText();
            Date startDate = startDateField.getDate();
            Date endDate = endDateField.getDate();
            String reason = reasonArea.getText();
            String status = "Pending"; 
            Date currentDate = new Date();

            if (startDate == null || endDate == null || reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startDate.before(currentDate) || endDate.before(currentDate)) {
                JOptionPane.showMessageDialog(this, "Start and end dates must be future dates.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            conn conn = new conn();
            try {
            	// Check if there is an existing pending or approved leave application
            	String checkQuery = "SELECT end_date FROM `leave` WHERE employee_id = ? AND (status = 'Pending' OR status = 'Approved')";
            	PreparedStatement checkPs = conn.getConnection().prepareStatement(checkQuery);
            	checkPs.setString(1, empID);
            	ResultSet rs = checkPs.executeQuery();

            	if (rs.next()) {
            	    Date existingLeaveEndDate = rs.getDate("end_date");
            	    if (existingLeaveEndDate.after(currentDate)) {
            	        JOptionPane.showMessageDialog(this, "You already have a pending or approved leave application. Please wait until it is processed.", "Leave Application Exists", JOptionPane.ERROR_MESSAGE);
            	        return;
            	    }
            	}

                // Insert new leave application
                String query = "INSERT INTO `leave` (leave_id, employee_id, start_date, end_date, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.getConnection().prepareStatement(query);
                ps.setString(1, leave_id);
                ps.setString(2, empID);
                ps.setDate(3, new java.sql.Date(startDate.getTime()));
                ps.setDate(4, new java.sql.Date(endDate.getTime()));
                ps.setString(5, reason);
                ps.setString(6, status);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Leave Application Submitted Successfully");
                setVisible(false);
                new Main_class_employee(empID);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error submitting leave application.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == checkStatusButton) {
            new CheckLeaveStatus(empID);
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new Main_class_employee(empID);
        }
    }
}
