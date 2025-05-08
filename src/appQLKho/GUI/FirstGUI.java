package appQLKho.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class FirstGUI extends JFrame {
    public FirstGUI() {
        setTitle("PMIT - Quản lý kho hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Tạo menu chính
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(173, 216, 230));
        String[] menuTitles = {"Quản lý tài khoản", "Quản lý phân quyền"};
        JMenu[] menus = new JMenu[menuTitles.length];
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        // CardLayout chứa 2 giao diện tách biệt
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Giao diện Quản lý tài khoản
        JSplitPane splitAccount = createAccountUI();
        // Giao diện Quản lý phân quyền
        JSplitPane splitPermission = createPermissionUI();

        cardPanel.add(splitAccount, menuTitles[0]);
        cardPanel.add(splitPermission, menuTitles[1]);

        // Cài menu và chuyển giao diện
        for (int i = 0; i < menuTitles.length; i++) {
            menus[i] = new JMenu(menuTitles[i]);
            menus[i].setOpaque(true);
            menus[i].setBackground(new Color(173, 216, 230));
            menus[i].setFont(defaultFont);
            int idx = i;
            menus[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < menus.length; j++) {
                        menus[j].setFont(j == idx ? boldFont : defaultFont);
                    }
                    cardLayout.show(cardPanel, menuTitles[idx]);
                }
            });
            menuBar.add(menus[i]);
        }
        setJMenuBar(menuBar);
        // Hiển thị mặc định trang tài khoản và in đậm menu
        menus[0].setFont(boldFont);

        getContentPane().add(cardPanel);
        setVisible(true);
    }

    // Functional DocumentListener tiện dụng
    @FunctionalInterface
    interface SimpleDocumentListener extends DocumentListener {
        void update(DocumentEvent e);
        @Override default void insertUpdate(DocumentEvent e) { update(e); }
        @Override default void removeUpdate(DocumentEvent e) { update(e); }
        @Override default void changedUpdate(DocumentEvent e) { update(e); }
    }

    // Giao diện Quản lý tài khoản
    private JSplitPane createAccountUI() {
        JPanel left = new JPanel(new BorderLayout(0, 10));
        left.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Toolbar nút Thêm, Sửa, Xóa
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton btnAddAcc = new JButton("Thêm");
        JButton btnEditAcc = new JButton("Sửa");
        JButton btnDeleteAcc = new JButton("Xóa");
        toolbar.add(btnAddAcc);
        toolbar.add(btnEditAcc);
        toolbar.add(btnDeleteAcc);
        left.add(toolbar, BorderLayout.NORTH);

        // Panel tìm kiếm
        JPanel search = new JPanel();
        search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
        search.add(new JLabel("Tìm kiếm tài khoản"));
        search.add(Box.createVerticalStrut(5));
        search.add(new JLabel("Theo ID"));
        JTextField txtId = new JTextField();
        txtId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        search.add(Box.createVerticalStrut(2));
        search.add(txtId);
        search.add(Box.createVerticalStrut(10));
        search.add(new JLabel("Theo tên"));
        JTextField txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        search.add(Box.createVerticalStrut(2));
        search.add(txtName);
        search.add(Box.createVerticalStrut(15));
        search.add(new JLabel("Vai trò"));
        JCheckBox cbAdmin = new JCheckBox("Admin", true);
        JCheckBox cbQL = new JCheckBox("Quản lý", true);
        JCheckBox cbNVNhap = new JCheckBox("Nhân viên nhập kho", true);
        JCheckBox cbNVXuat = new JCheckBox("Nhân viên xuất kho", true);
        search.add(cbAdmin);
        search.add(cbQL);
        search.add(cbNVNhap);
        search.add(cbNVXuat);
        left.add(search, BorderLayout.CENTER);

        // Panel phải với bảng kết quả
        JPanel right = new JPanel(new BorderLayout(10, 10));
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Username", "Tên", "Email", "Vai trò"}, 0
        );
        JTable table = new JTable(model) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        right.add(new JScrollPane(table), BorderLayout.CENTER);

        // Dữ liệu mẫu
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[]{"ACC001", "abc", "Nguyễn A", "a@example.com", "Admin"});
        sampleData.add(new Object[]{"ACC002", "cdb", "Trần B", "b@example.com", "Nhân viên nhập kho"});
        sampleData.add(new Object[]{"ACC003", "altf4", "Lê C", "c@example.com", "Nhân viên xuất kho"});
        filterAndRefresh(model, sampleData, "", "", true, true, true, true);

        // Logic lọc
        Runnable doFilter = () -> {
            String idKey = txtId.getText().trim().toUpperCase();
            String nameKey = txtName.getText().trim().toUpperCase();
            filterAndRefresh(model, sampleData, idKey, nameKey,
                cbAdmin.isSelected(), cbQL.isSelected(),
                cbNVNhap.isSelected(), cbNVXuat.isSelected());
        };
        txtId.getDocument().addDocumentListener((SimpleDocumentListener) e -> doFilter.run());
        txtName.getDocument().addDocumentListener((SimpleDocumentListener) e -> doFilter.run());
        cbAdmin.addItemListener(e -> doFilter.run());
        cbQL.addItemListener(e -> doFilter.run());
        cbNVNhap.addItemListener(e -> doFilter.run());
        cbNVXuat.addItemListener(e -> doFilter.run());

        // CRUD actions
        btnAddAcc.addActionListener(e -> { /* TODO: thêm */ });
        btnEditAcc.addActionListener(e -> { /* TODO: sửa */ });
        btnDeleteAcc.addActionListener(e -> { /* TODO: xóa */ });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(280);
        return split;
    }

    // Giao diện Quản lý phân quyền
    private JSplitPane createPermissionUI() {
        JPanel left = new JPanel(new BorderLayout(0, 10));
        left.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton btnAddPerm = new JButton("Thêm"), btnEditPerm = new JButton("Sửa"), btnDeletePerm = new JButton("Xóa");
        toolbar.add(btnAddPerm); toolbar.add(btnEditPerm); toolbar.add(btnDeletePerm);
        left.add(toolbar, BorderLayout.NORTH);

        JPanel search = new JPanel();
        search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
        search.add(new JLabel("Tìm kiếm phân quyền"));
        search.add(Box.createVerticalStrut(5));
        search.add(new JLabel("Theo ID"));
        JTextField quyenId = new JTextField(); quyenId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        search.add(Box.createVerticalStrut(2)); search.add(quyenId);
        search.add(Box.createVerticalStrut(10));
        search.add(new JLabel("Theo tên"));
        JTextField quyenName = new JTextField(); quyenName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        search.add(Box.createVerticalStrut(2)); search.add(quyenName);
        left.add(search, BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout(10, 10));
        // Thêm cột Vai trò vào sau Tên quyền
        DefaultTableModel permModel = new DefaultTableModel(
            new Object[]{"Quyền ID", "Tên quyền", "Vai trò", "Mô tả"}, 0
        );
        JTable permTable = new JTable(permModel) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        right.add(new JScrollPane(permTable), BorderLayout.CENTER);

        // Dữ liệu mẫu phân quyền có thêm Vai trò
        List<Object[]> permData = new ArrayList<>();
        permData.add(new Object[]{"P001", "Quyền hệ thống", "Admin", "Toàn quyền hệ thống"});
        permData.add(new Object[]{"P002", "Quản lý kho", "Quản lý", "Quản lý kho và báo cáo"});
        permData.add(new Object[]{"P003", "Quản lý phiếu nhập", "Nhân viên nhập kho", "Tạo, sửa phiếu nhập kho"});
        permData.add(new Object[]{"P004", "Quản lý phiếu xuất", "Nhân viên xuất kho", "Tạo, sửa phiếu xuất kho"});
        for (Object[] row : permData) {
            permModel.addRow(row);
        }

        // CRUD actions
        btnAddPerm.addActionListener(e -> { /* TODO: thêm */ });
        btnEditPerm.addActionListener(e -> { /* TODO: sửa */ });
        btnDeletePerm.addActionListener(e -> { /* TODO: xóa */ });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(280);
        return split;
    }

    // Hàm chung lọc và làm mới bảng
    private void filterAndRefresh(DefaultTableModel model, List<Object[]> data,
                                  String idKey, String nameKey,
                                  boolean admin, boolean ql,
                                  boolean nvNhap, boolean nvXuat) {
        model.setRowCount(0);
        for (Object[] row : data) {
            String id = ((String) row[0]).toUpperCase();
            String name = ((String) row[2]).toUpperCase();
            String role = (String) row[4];
            if (id.contains(idKey) && name.contains(nameKey) &&
               ((admin && role.equals("Admin")) ||
                (ql && role.equals("Quản lý")) ||
                (nvNhap && role.equals("Nhân viên nhập kho")) ||
                (nvXuat && role.equals("Nhân viên xuất kho")))) {
                model.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(FirstGUI::new);
    }
}
