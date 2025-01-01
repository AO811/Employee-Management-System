package employee.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class View_Employee extends JFrame implements ActionListener {

    private JTable table;
    private Choice choiceEMP;
    private JButton searchbtn, print, update, back;

    View_Employee() {
        // Frame setup
        setTitle("View Employee Details");
        setSize(900, 700);
        setLocation(200, 30);
        setLayout(null);
        getContentPane().setBackground(new Color(95, 203, 250));

        // Title label
        JLabel titleLabel = new JLabel("Employee Management System - View Employee");
        titleLabel.setBounds(250, 10, 500, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(39, 60, 117));
        add(titleLabel);

        // Search section
        JLabel searchLabel = new JLabel("Search by Employee ID:");
        searchLabel.setBounds(30, 60, 180, 25);
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(searchLabel);

        choiceEMP = new Choice();
        choiceEMP.setBounds(220, 60, 150, 25);
        choiceEMP.setFont(new Font("Arial", Font.PLAIN, 14));
        add(choiceEMP);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                choiceEMP.add(resultSet.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table setup
        table = new JTable();
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM employee");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 130, 860, 500);
        add(tableScrollPane);

        // Button setup
        searchbtn = createButton("Search", 400, 60);
        add(searchbtn);

        print = createButton("Print", 500, 60);
        add(print);

        update = createButton("Update", 600, 60);
        add(update);

        back = createButton("Back", 700, 60);
        add(back);

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 90, 25);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(this);
        button.setFocusable(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchbtn) {
            String query = "SELECT * FROM employee WHERE empId = '" + choiceEMP.getSelectedItem() + "'";
            try {
                conn c = new conn();
                ResultSet resultSet = c.statement.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(choiceEMP.getSelectedItem());
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
