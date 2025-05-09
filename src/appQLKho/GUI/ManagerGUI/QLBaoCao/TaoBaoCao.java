/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appQLKho.GUI.ManagerGUI.QLBaoCao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaoBaoCao extends JFrame {
    private JTextField txtTenSanPham, txtMaSanPham, txtDonViTinh;
    private JTextField txtTonDauKy, txtNhapTrongKy, txtXuatTrongKy;
    private JTextField txtTonCuoiKy, txtGiaTriTonCuoiKy;

    private JComboBox<String> cboLoaiBaoCao;
    private JComboBox<String> cboKhoangThoiGian;
    private JTextField txtNgayBatDau, txtNgayKetThuc;
    private JComboBox<String> cboLoaiSanPham;

    private JButton btnGuiBaoCao;

    public TaoBaoCao() {
        setTitle("Tạo Báo Cáo Quản Lý");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(14, 2, 10, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Thông tin sản phẩm
        txtTenSanPham = new JTextField();
        txtMaSanPham = new JTextField();
        txtDonViTinh = new JTextField();
        txtTonDauKy = new JTextField();
        txtNhapTrongKy = new JTextField();
        txtXuatTrongKy = new JTextField();
        txtTonCuoiKy = new JTextField();
        txtGiaTriTonCuoiKy = new JTextField();

        mainPanel.add(new JLabel("Tên sản phẩm:"));
        mainPanel.add(txtTenSanPham);

        mainPanel.add(new JLabel("Mã sản phẩm:"));
        mainPanel.add(txtMaSanPham);

        mainPanel.add(new JLabel("Đơn vị tính:"));
        mainPanel.add(txtDonViTinh);

        mainPanel.add(new JLabel("Tồn đầu kỳ:"));
        mainPanel.add(txtTonDauKy);

        mainPanel.add(new JLabel("Nhập trong kỳ:"));
        mainPanel.add(txtNhapTrongKy);

        mainPanel.add(new JLabel("Xuất trong kỳ:"));
        mainPanel.add(txtXuatTrongKy);

        mainPanel.add(new JLabel("Tồn cuối kỳ:"));
        mainPanel.add(txtTonCuoiKy);

        mainPanel.add(new JLabel("Giá trị tồn kho cuối kỳ:"));
        mainPanel.add(txtGiaTriTonCuoiKy);

        // Thông tin báo cáo
        cboLoaiBaoCao = new JComboBox<>(new String[]{
            "Báo cáo tồn kho", "Báo cáo nhập kho", "Báo cáo xuất kho", "Báo cáo tổng hợp"
        });
        mainPanel.add(new JLabel("Loại báo cáo:"));
        mainPanel.add(cboLoaiBaoCao);

        cboKhoangThoiGian = new JComboBox<>(new String[]{
            "Theo tuần", "Theo tháng", "Theo năm", "Tùy chọn ngày"
        });
        mainPanel.add(new JLabel("Khoảng thời gian:"));
        mainPanel.add(cboKhoangThoiGian);

        txtNgayBatDau = new JTextField("yyyy-MM-dd");
        txtNgayKetThuc = new JTextField("yyyy-MM-dd");
        mainPanel.add(new JLabel("Ngày bắt đầu:"));
        mainPanel.add(txtNgayBatDau);
        mainPanel.add(new JLabel("Ngày kết thúc:"));
        mainPanel.add(txtNgayKetThuc);

        cboLoaiSanPham = new JComboBox<>(new String[]{
            "Tất cả", "Đồ gia dụng", "Đồ làm bếp", "Đồ trang trí"
        });
        mainPanel.add(new JLabel("Loại sản phẩm (lọc):"));
        mainPanel.add(cboLoaiSanPham);

        // Nút gửi báo cáo
        btnGuiBaoCao = new JButton("Tạo Báo Cáo");
        btnGuiBaoCao.addActionListener(e -> guiBaoCao());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuiBaoCao);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void guiBaoCao() {
        if (txtTenSanPham.getText().trim().isEmpty() ||
            txtMaSanPham.getText().trim().isEmpty() ||
            txtDonViTinh.getText().trim().isEmpty() ||
            txtTonDauKy.getText().trim().isEmpty() ||
            txtNhapTrongKy.getText().trim().isEmpty() ||
            txtXuatTrongKy.getText().trim().isEmpty() ||
            txtTonCuoiKy.getText().trim().isEmpty() ||
            txtGiaTriTonCuoiKy.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Thiếu dữ liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Bạn có thể xử lý lưu dữ liệu hoặc gọi BUS tại đây
        JOptionPane.showMessageDialog(this, "Đã gửi báo cáo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaoBaoCao().setVisible(true));
    }
}

