package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class View_Profile extends JFrame {
    View_Profile(String employeeID) {
        // Frame settings
        setSize(800, 500);
        setLocation(300, 100);
        
        // Gradient background
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

        // Title
        JLabel heading = new JLabel("Employee Profile", JLabel.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        heading.setBounds(0, 20, 800, 40);
        panel.add(heading);

        // Info Panel for Employee Details
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 255, 255, 180)); // Semi-transparent background
        infoPanel.setBounds(150, 80, 500, 300);
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        String[] fieldNames = {"Name", "Father's Name", "DOB", "Salary", "Address", "Phone", "Email", "Education", "Designation", "Aadhar"};
        JLabel[] labels = new JLabel[fieldNames.length];
        JLabel[] dataLabels = new JLabel[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            // Label
            labels[i] = new JLabel(fieldNames[i] + ": ");
            labels[i].setFont(new Font("Arial", Font.BOLD, 14));
            labels[i].setForeground(new Color(0, 102, 204));
            gbc.gridx = 0;
            gbc.gridy = i;
            infoPanel.add(labels[i], gbc);

            // Data Label (initially empty)
            dataLabels[i] = new JLabel();
            dataLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
            dataLabels[i].setForeground(Color.DARK_GRAY);
            gbc.gridx = 1;
            infoPanel.add(dataLabels[i], gbc);
        }

        panel.add(infoPanel);

        // Database Query for Employee Info
        try {
            conn conn = new conn();
            String query = "SELECT * FROM employee WHERE empID = '" + employeeID + "'";
            ResultSet resultSet = conn.statement.executeQuery(query);

            if (resultSet.next()) {
                dataLabels[0].setText(resultSet.getString("name"));
                dataLabels[1].setText(resultSet.getString("fname"));
                dataLabels[2].setText(resultSet.getString("dob"));
                dataLabels[3].setText(resultSet.getString("salary"));
                dataLabels[4].setText(resultSet.getString("address"));
                dataLabels[5].setText(resultSet.getString("phone"));
                dataLabels[6].setText(resultSet.getString("email"));
                dataLabels[7].setText(resultSet.getString("education"));
                dataLabels[8].setText(resultSet.getString("designation"));
                dataLabels[9].setText(resultSet.getString("addhar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setBounds(350, 400, 100, 30);
        backButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new Main_class_employee(employeeID);
        });
        panel.add(backButton);

        setVisible(true);
    }
}
