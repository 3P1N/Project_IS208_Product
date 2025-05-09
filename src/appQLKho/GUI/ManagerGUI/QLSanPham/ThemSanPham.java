import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThemSanPham extends JFrame {
    private JTextField txtTenSanPham;
    private JTextField txtSoLuong;
    private JTextField txtLoaiSanPham;
    private JButton btnTaoSanPham;

    public ThemSanPham() {
        setTitle("Thêm Sản Phẩm");
        setSize(400, 250);
        setLocationRelativeTo(null); // Center form trên màn hình
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng form này
        setLayout(new BorderLayout());

        // Tạo panel chứa form nhập liệu với 3 hàng và 2 cột
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Tạo label và textField cho Tên Sản Phẩm
        JLabel lblTenSanPham = new JLabel("Tên Sản Phẩm:");
        txtTenSanPham = new JTextField();

        // Tạo label và textField cho Số Lượng
        JLabel lblSoLuong = new JLabel("Số Lượng:");
        txtSoLuong = new JTextField();

        // Tạo label và textField cho Loại Sản Phẩm
        JLabel lblLoaiSanPham = new JLabel("Loại Sản Phẩm:");
        txtLoaiSanPham = new JTextField();

        formPanel.add(lblTenSanPham);
        formPanel.add(txtTenSanPham);
        formPanel.add(lblSoLuong);
        formPanel.add(txtSoLuong);
        formPanel.add(lblLoaiSanPham);
        formPanel.add(txtLoaiSanPham);

        // Tạo nút Tạo Sản Phẩm
        btnTaoSanPham = new JButton("Tạo Sản Phẩm");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnTaoSanPham);

        // Xử lý sự kiện cho nút Tạo Sản Phẩm
        btnTaoSanPham.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ten = txtTenSanPham.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();
                String loai = txtLoaiSanPham.getText().trim();

                // Kiểm tra xem có bỏ trống trường nào không
                if (ten.isEmpty() || soLuongStr.isEmpty() || loai.isEmpty()) {
                    JOptionPane.showMessageDialog(ThemSanPham.this,
                            "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra định dạng số cho trường Số Lượng
                try {
                    int soLuong = Integer.parseInt(soLuongStr);
                    JOptionPane.showMessageDialog(ThemSanPham.this,
                            "Đã tạo sản phẩm:\nTên: " + ten + "\nSố Lượng: " + soLuong + "\nLoại: " + loai);
                    
                    // TODO: Thêm mã gọi BUS hoặc lưu vào cơ sở dữ liệu ở đây
                    dispose(); // Đóng form sau khi tạo xong sản phẩm
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ThemSanPham.this,
                            "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Hàm main để chạy thử form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemSanPham().setVisible(true));
    }
}
