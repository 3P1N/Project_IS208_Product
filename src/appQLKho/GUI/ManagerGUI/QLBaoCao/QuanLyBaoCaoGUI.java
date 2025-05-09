package appQLKho.GUI.ManagerGUI.QLBaoCao;

import appQLKho.GUI.ManagerGUI.QLSanPham.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyBaoCaoGUI extends JFrame {

    public QuanLyBaoCaoGUI() {
        setTitle("ABC WAREHOUSE");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar menu bên trái
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(220, 300));

        Font menuFont = new Font("Arial", Font.PLAIN, 16);

        sidebar.add(createSidebarButton("Trang chủ", menuFont, ""));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Quản lý sản phẩm", menuFont, ""));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Quản lý phiếu nhập xuất", menuFont, ""));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Kiểm tra tồn kho", menuFont, ""));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Kiểm kê hàng hóa", menuFont, ""));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Quản lý báo cáo", menuFont, ""));
        
        // Panel tìm kiếm và lựa chọn
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Danh sách báo cáo"));
        filterPanel.setMaximumSize(new Dimension(220, 100));
        filterPanel.add(new JLabel("Tìm kiếm báo cáo:"));
        filterPanel.add(new JTextField(15));
        filterPanel.add(new JButton("Lọc"));

        // Gộp sidebar và filterPanel vào 1 panel bên trái theo chiều dọc
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(240, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        leftPanel.add(sidebar);
        leftPanel.add(Box.createVerticalStrut(20));
//        leftPanel.add(filterPanel);

        add(leftPanel, BorderLayout.WEST);

        // Bảng dữ liệu sản phẩm
        String[] columnNames = {"Mã báo cáo", "Loại báo cáo", "Trạng thái"};
        Object[][] data = new Object[10][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = "BC000" + i;
            data[i][1] = "Theo tháng";
            data[i][2] = "Chưa gửi";
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

        JButton btnAdd = new JButton("Tạo báo cáo");
        JButton btnView = new JButton("Xem báo cáo");
        JButton btnSend = new JButton("Gửi báo cáo");
        JButton btnBack = new JButton("Quay lại");

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnView);
        bottomPanel.add(btnSend);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createSidebarButton(String text, Font font, String iconText) {
        JButton button = new JButton(iconText + "  " + text);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
//        button.setBackground(Color.WHITE);
        button.setBackground(new Color(220, 220, 220)); 
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setMaximumSize(new Dimension(250, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyBaoCaoGUI().setVisible(true));
    }
}
