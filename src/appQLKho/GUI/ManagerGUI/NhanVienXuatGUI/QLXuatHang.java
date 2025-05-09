package appQLKho.GUI.ManagerGUI.NhanVienXuatGUI;

import appQLKho.GUI.NhanVienNhapGUI.*;
import appQLKho.GUI.ManagerGUI.QLPhieuNhapXuat.*;
import appQLKho.GUI.ManagerGUI.QLSanPham.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLXuatHang extends JFrame {

    public QLXuatHang() {
        setTitle("KiotViet Sync");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());        
        
        // Panel tìm kiếm và lựa chọn
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Danh mục phiếu nhập xuất"));
        filterPanel.setMaximumSize(new Dimension(220, 100));
        filterPanel.add(new JLabel("Tìm kiếm phiếu nhập xuất:"));
        filterPanel.add(new JTextField(15));
        filterPanel.add(new JButton("Lọc"));



        // Bảng dữ liệu sản phẩm
        String[] columnNames = {"Mã Phiếu", "Loại Phiếu", "Trạng thái gửi", "Trạng thái duyệt"};
        Object[][] data = new Object[10][4];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = "PH000" + i;
            data[i][1] = "Phiếu xuất";
            data[i][2] = "Chưa gửi";
            data[i][3] = "Chưa duyệt";
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        
        
        
        // Panel các nút chức năng: Thêm, Sửa, Xóa, Quay lại
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAdd = new JButton("Tạo phiếu xuất");
        JButton btnView = new JButton("Xem chi tiết");
        JButton btnSend = new JButton("Gửi phiếu");
        JButton btnBack = new JButton("Quay lại");

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnView);
        bottomPanel.add(btnSend);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QLXuatHang().setVisible(true));
    }
}
