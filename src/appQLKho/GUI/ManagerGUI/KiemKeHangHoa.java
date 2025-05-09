/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appQLKho.GUI.ManagerGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KiemKeHangHoa extends JFrame {
    private JTable table;
    private JButton btnKiemKe;

    public KiemKeHangHoa() {
        setTitle("Kiểm Kê Hàng Hóa");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("Danh sách kiểm kê hàng hóa", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLUE);
        add(lblTitle, BorderLayout.NORTH);

        // Bảng kiểm kê
        String[] columns = {"Mã SP", "Tên sản phẩm", "Tồn kho hệ thống", "Số lượng thực tế", "Ghi chú"};
        Object[][] data = {
            {"SP001", "Bình giữ nhiệt", 35, "", ""},
            {"SP002", "Nồi inox", 12, "", ""},
            {"SP003", "Đèn LED", 8, "", ""},
            {"SP004", "Tô sứ", 50, "", ""},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép sửa cột "Số lượng thực tế" và "Ghi chú"
                return column == 3 || column == 4;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Nút kiểm kê
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnKiemKe = new JButton("Thực hiện kiểm kê");
        bottomPanel.add(btnKiemKe);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện kiểm kê (chỉ hiển thị ví dụ đơn giản)
        btnKiemKe.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đã lưu kết quả kiểm kê!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KiemKeHangHoa().setVisible(true);
        });
    }
}

