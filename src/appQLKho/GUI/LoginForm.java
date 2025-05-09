/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appQLKho.GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Đăng nhập - Quản lý Kho");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Tạo một JPanel chính để chứa background và LoginPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 600); // Kích thước của toàn bộ frame
        add(mainPanel);

        // Load hình nền
        JLabel background = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/WareHouse.jpg")));// Thay bằng hình bạn muốn
        URL imageUrl = getClass().getResource("/images/WareHouse.jpg");
        System.out.println("Image URL: " + imageUrl);

        background.setBounds(0, -50, 400, 700);
        mainPanel.add(background);

        // Panel chính để nhập login
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(500, 120, 350, 350);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        mainPanel.add(loginPanel);

        int panelWidth = loginPanel.getWidth();
        int labelWidth = 200; // hoặc logo.getPreferredSize().width sau khi set font
        int x = (panelWidth - labelWidth) / 2;

        JLabel logo = new JLabel("ABC WAREHOUSE");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204));
        logo.setBounds(x, 10, labelWidth, 40);
        loginPanel.add(logo);


        JLabel subtitle = new JLabel("Phần mềm quản lý kho hàng");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setBounds(90, 45, 200, 20);
        subtitle.setForeground(Color.GRAY);
        loginPanel.add(subtitle);

        JTextField storeField = new JTextField();
        storeField.setBounds(30, 80, 290, 35);
        storeField.setBorder(BorderFactory.createTitledBorder("Tên kho"));
        loginPanel.add(storeField);

        JTextField userField = new JTextField();
        userField.setBounds(30, 130, 290, 35);
        userField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        loginPanel.add(userField);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(30, 180, 290, 35);
        passField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        loginPanel.add(passField);

        JCheckBox rememberMe = new JCheckBox("Duy trì đăng nhập");
        rememberMe.setBounds(30, 225, 150, 20);
        loginPanel.add(rememberMe);

        JLabel forgot = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
        forgot.setBounds(200, 225, 120, 20);
        forgot.setForeground(Color.BLUE);
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(forgot);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(30, 260, 290, 40);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm frame = new LoginForm();

            frame.setVisible(true);
        });
    }
}
