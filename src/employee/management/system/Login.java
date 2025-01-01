package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField tusername;
    JPasswordField tpassword;
    JButton login, back;
    JComboBox<String> userTypeCombo;

    Login() {

        // Set frame background gradient and layout
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

        // Title Label
        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setBounds(150, 10, 300, 40);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        add(title);

        // User Type ComboBox (Employee or Manager)
        JLabel userTypeLabel = new JLabel("Login As:");
        userTypeLabel.setBounds(100,50, 100, 30);
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userTypeLabel.setForeground(Color.WHITE);
        add(userTypeLabel);

        userTypeCombo = new JComboBox<>(new String[]{"Employee", "Manager"});
        userTypeCombo.setBounds(200,50, 200, 30);
        userTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userTypeCombo);

        // Username Label and TextField
        JLabel username = new JLabel("Username:");
        username.setBounds(100,90, 100, 30);
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(Color.WHITE);
        add(username);

        tusername = new JTextField();
        tusername.setBounds(200,90, 200, 30);
        tusername.setFont(new Font("Arial", Font.PLAIN, 14));
        tusername.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(tusername);

        // Password Label and PasswordField
        JLabel password = new JLabel("Password:");
        password.setBounds(100, 130, 100, 30);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setForeground(Color.WHITE);
        add(password);

        tpassword = new JPasswordField();
        tpassword.setBounds(200, 130, 200, 30);
        tpassword.setFont(new Font("Arial", Font.PLAIN, 14));
        tpassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(tpassword);

        // Login Button
        login = new JButton("LOGIN");
        login.setBounds(200, 200, 90, 30);
        login.setBackground(new Color(60, 179, 113));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.addActionListener(this);
        add(login);

        // Back Button
        back = new JButton("BACK");
        back.setBounds(310, 200, 90, 30);
        back.setBackground(new Color(220, 20, 60));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial", Font.BOLD, 14));
        back.addActionListener(this);
        add(back);

        // Frame settings
        setSize(600, 350);
        setLocation(450, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try {
                String username = tusername.getText();
                String password = String.valueOf(tpassword.getPassword());
                String userType = (String) userTypeCombo.getSelectedItem();

                conn conn = new conn();
                String query;

                // Check login based on selected user type
                if (userType.equals("Manager")) {
                    query = "SELECT * FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                } else { // Employee login
                    query = "SELECT * FROM employeelogin WHERE Employeename = '" + username + "' AND EmployeeID = '" + password + "'";
                }

                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    setVisible(false);
                    if (userType.equals("Manager")) {
                        new Main_class(); // Manager view
                    } else {
                    	String employeeID = resultSet.getString("EmployeeID");
                    	new Main_class_employee(employeeID); // Employee view
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }

            } catch (Exception E) {
                E.printStackTrace();
            }

        } else if (e.getSource() == back) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
