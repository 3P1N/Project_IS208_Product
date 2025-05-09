/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appQLKho.GUI.ManagerGUI.QLKho;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyKhoGUI extends JFrame {
    public QuanLyKhoGUI() {
        setTitle("KiotViet Sync");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel trên cùng
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCauHinh = new JButton("Cấu hình");
        JButton btnHangHoa = new JButton("Hàng hóa");
        JButton btnBangGia = new JButton("Bảng giá");
        JButton btnDonHang = new JButton("Đơn hàng");

        topPanel.add(btnCauHinh);
        topPanel.add(btnHangHoa);
        topPanel.add(btnBangGia);
        topPanel.add(btnDonHang);

        add(topPanel, BorderLayout.NORTH);

        // Panel tìm kiếm và lựa chọn
        JPanel filterPanel = new JPanel(new GridLayout(2, 1));

        JPanel danhMucPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        danhMucPanel.setBorder(BorderFactory.createTitledBorder("Danh mục hàng hóa"));
        danhMucPanel.add(new JLabel("Tìm kiếm danh mục:"));
        danhMucPanel.add(new JTextField(20));
        danhMucPanel.add(new JButton("Lọc"));

        JPanel hangHoaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hangHoaPanel.setBorder(BorderFactory.createTitledBorder("Hàng hóa trên KiotViet"));
        hangHoaPanel.add(new JLabel("Tìm kiếm hàng hóa:"));
        hangHoaPanel.add(new JTextField(20));
        hangHoaPanel.add(new JButton("Lọc"));

        filterPanel.add(danhMucPanel);
        filterPanel.add(hangHoaPanel);

        add(filterPanel, BorderLayout.WEST);

        // Bảng dữ liệu sản phẩm
        String[] columnNames = {"", "Mã SP", "Tên SP", "Danh mục", "Đồng bộ"};
        Object[][] data = new Object[10][5];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = false;
            data[i][1] = "LH000" + i;
            data[i][2] = "AP3534596" + i;
            data[i][3] = "Lẵng hoa";
            data[i][4] = false;
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 4:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }

            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 4;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút điều hướng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("Quay lại");
        JButton btnNext = new JButton("Tiếp tục");
        JButton btnCancel = new JButton("Hủy");
        bottomPanel.add(btnBack);
        bottomPanel.add(btnNext);
        bottomPanel.add(btnCancel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyKhoGUI().setVisible(true));
    }
}

