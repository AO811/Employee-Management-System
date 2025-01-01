package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_class_employee extends JFrame {
    private String employeeID; 

    Main_class_employee(String employeeID) {
        this.employeeID = employeeID; 

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
        JLabel welcomeMessage = new JLabel("Welcome, Employee ID: " + employeeID);
        welcomeMessage.setBounds(0, 120, 1120, 30);
        welcomeMessage.setFont(new Font("Arial", Font.ITALIC, 20));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessage);

        // Button configuration
        Color buttonColor = new Color(60, 179, 113); 
        Color hoverColor = buttonColor.brighter(); 

        // Mark Attendance Button
        JButton markAttendance = createButton("Attendance", buttonColor, hoverColor);
        markAttendance.setBounds(335, 200, 200, 50);
        markAttendance.addActionListener(e -> {
            new MarkAttendance(employeeID);
            setVisible(false);
        });
        add(markAttendance);

        // Apply for Leave Button
        JButton applyLeave = createButton("Leave Applications", buttonColor, hoverColor);
        applyLeave.setBounds(565, 200, 200, 50);
        applyLeave.addActionListener(e -> {
            new Apply_Leave(employeeID);
            setVisible(false);
        });
        add(applyLeave);

        // View Profile Button
        JButton viewProfile = createButton("Profile", buttonColor, hoverColor);
        viewProfile.setBounds(335, 300, 200, 50);
        viewProfile.addActionListener(e -> {
            new View_Profile(employeeID);
            setVisible(false);
        });
        add(viewProfile);

        // Logout Button
        JButton logout = createButton("Logout", Color.RED, Color.LIGHT_GRAY);
        logout.setBounds(565, 300, 200, 50);
        logout.addActionListener(e -> logout());
        add(logout);

        // Frame settings
        setSize(1120, 630);
        setLocation(80, 30);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    // Method to log out
    private void logout() {
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        new Main_class_employee("E12345"); // For testing 
    }
}
