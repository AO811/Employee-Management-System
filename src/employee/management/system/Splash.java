package employee.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {

    Splash(){
                
        getContentPane().setBackground(new Color(30, 144, 255));         
        JLabel heading = new JLabel("Employee Management System", JLabel.CENTER);
        heading.setBounds(0, 200, 1170, 100);
        heading.setFont(new Font("Serif", Font.BOLD, 50));
        heading.setForeground(Color.WHITE);
        
        heading.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        
        add(heading);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(485, 350, 200, 20);
        progressBar.setForeground(Color.WHITE);
        progressBar.setBackground(Color.LIGHT_GRAY);
        add(progressBar);

        setSize(1170, 650);
        setLocation(60, 20);
        setLayout(null);
        setVisible(true);
        
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(30);
                progressBar.setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        new Splash();
    }
}
