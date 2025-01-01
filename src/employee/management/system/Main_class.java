package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_class extends JFrame {
    Main_class() {

        // Background gradient and layout
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(30, 144, 255);
                Color color2 = new Color(100, 149, 237);
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        // Title Heading
        JLabel heading = new JLabel("Employee Management System", JLabel.CENTER);
        heading.setBounds(0, 60, 1120, 50);
        heading.setFont(new Font("Serif", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        add(heading);
        
        // Welcome Message
        JLabel welcomeMessage = new JLabel("Welcome!");
        welcomeMessage.setBounds(0, 120, 1120, 30);
        welcomeMessage.setFont(new Font("Arial", Font.ITALIC, 20));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessage);

        // Button configuration
        Color buttonColor = new Color(60, 179, 113); // Sea Green color
        Color hoverColor = buttonColor.brighter(); // Lighter color for hover effect

        // Add Employee Button
        JButton addBtn = createButton("Add Employee", buttonColor, hoverColor);
        addBtn.setBounds(235, 200, 250, 50);
        addBtn.addActionListener(e -> {
            new AddEmployee();
            setVisible(false);
        });
        add(addBtn);

        // View Employee Button
        JButton viewBtn = createButton("View Employee", buttonColor, hoverColor);
        viewBtn.setBounds(635, 200, 250, 50);
        viewBtn.addActionListener(e -> {
            new View_Employee();
            setVisible(false);
        });
        add(viewBtn);

        // Remove Employee Button
        JButton removeBtn = createButton("Remove Employee", buttonColor, hoverColor);
        removeBtn.setBounds(235, 300, 250, 50);
        removeBtn.addActionListener(e -> new RemoveEmployee());
        add(removeBtn);

        // Payroll System Button
        JButton payrollBtn = createButton("Payroll System", buttonColor, hoverColor);
        payrollBtn.setBounds(635, 300, 250, 50);
        payrollBtn.addActionListener(e -> {
            new PayrollSystem();
            setVisible(false);
        });
        add(payrollBtn);

        // Manage Leave Button
        JButton manageLeaveBtn = createButton("Manage Leave Requests", buttonColor, hoverColor);
        manageLeaveBtn.setBounds(235, 400, 250, 50);
        manageLeaveBtn.addActionListener(e -> {
            new ManageLeave(); 
            setVisible(false);
        });
        add(manageLeaveBtn);
        
        // Manage Attendance Button
        JButton manageAttendanceBtn = createButton("Attendance Records", buttonColor, hoverColor);
        manageAttendanceBtn.setBounds(635, 400, 250, 50);
        manageAttendanceBtn.addActionListener(e -> {
            new ViewAttendanceRecords(); 
            setVisible(false);
        });
        add(manageAttendanceBtn);

        // Logout Button
        JButton logoutBtn = createButton("Logout", Color.RED, Color.LIGHT_GRAY);
        logoutBtn.setBounds(435, 500, 250, 50);
        logoutBtn.addActionListener(e -> {
            setVisible(false); 
            dispose(); 
            new Login(); 
        });
        add(logoutBtn);

        // Frame settings
        setSize(1120, 630);
        setLocation(80, 30);
        setLayout(null);
        setVisible(true);
    }

    // Method to create buttons with hover effect
    private JButton createButton(String text, Color backgroundColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new Main_class();
    }
}
