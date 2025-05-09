/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appQLKho.GUI.ManagerGUI.NhanVienXuatGUI;

import appQLKho.GUI.NhanVienNhapGUI.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TaoPhieuXuatHang extends JFrame {
    private JTextField txtMaPhieu, txtNhaCungCap, txtNguoiNhap;
    private JTable tableSanPham;
    private JButton btnThemSanPham, btnTaoPhieu;

    public TaoPhieuXuatHang() {
        setTitle("Thêm Phiếu Xuất Hàng");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel thông tin phiếu
        JPanel pnlThongTin = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu xuất"));

        txtMaPhieu = new JTextField("PN" + System.currentTimeMillis());
        txtNhaCungCap = new JTextField();
        txtNguoiNhap = new JTextField();

        JLabel lblMaPhieu = new JLabel("Mã phiếu:");
        JLabel lblNgayNhap = new JLabel("Ngày nhập:");
        JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:");
        JLabel lblNguoiNhap = new JLabel("Người nhập:");

        pnlThongTin.add(lblMaPhieu);       pnlThongTin.add(txtMaPhieu);
        pnlThongTin.add(lblNgayNhap);      pnlThongTin.add(new JLabel(LocalDate.now().toString()));
        pnlThongTin.add(lblNhaCungCap);    pnlThongTin.add(txtNhaCungCap);
        pnlThongTin.add(lblNguoiNhap);     pnlThongTin.add(txtNguoiNhap);

        add(pnlThongTin, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] columns = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá nhập"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableSanPham = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableSanPham);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm nhập"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel dưới cùng
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnTaoPhieu = new JButton("Tạo phiếu xuất");
        pnlBottom.add(btnThemSanPham);
        pnlBottom.add(btnTaoPhieu);
        add(pnlBottom, BorderLayout.SOUTH);

        // Sự kiện
        btnThemSanPham.addActionListener(e -> {
            JTextField txtMaSP = new JTextField();
            JTextField txtTenSP = new JTextField();
            JTextField txtSoLuong = new JTextField();
            JTextField txtDonGia = new JTextField();

            Object[] inputs = {
                "Mã SP:", txtMaSP,
                "Tên SP:", txtTenSP,
                "Số lượng:", txtSoLuong,
                "Đơn giá nhập:", txtDonGia
            };

            int result = JOptionPane.showConfirmDialog(this, inputs, "Thêm sản phẩm", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                model.addRow(new Object[]{
                    txtMaSP.getText(),
                    txtTenSP.getText(),
                    txtSoLuong.getText(),
                    txtDonGia.getText()
                });
            }
        });

        btnTaoPhieu.addActionListener(e -> {
            if (txtNhaCungCap.getText().isEmpty() || txtNguoiNhap.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin phiếu xuất.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tableSanPham.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Đã tạo phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // TODO: Ghi vào CSDL
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TaoPhieuXuatHang().setVisible(true);
        });
    }
}
