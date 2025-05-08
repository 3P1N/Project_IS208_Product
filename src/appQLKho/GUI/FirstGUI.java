/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appQLKho.GUI;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class FirstGUI extends JFrame {
    public FirstGUI() {
        setTitle("KiotViet - Phiếu kiểm kho");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // 1. Menu bar / toolbar trên cùng
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Tổng quan"));
        menuBar.add(new JMenu("Hàng hóa"));
        menuBar.add(new JMenu("Giao dịch"));
        menuBar.add(new JMenu("Đối tác"));
        menuBar.add(new JMenu("Sổ quỹ"));
        menuBar.add(new JMenu("Báo cáo"));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(new JMenu("Thiết lập"));
        menuBar.add(new JMenu("admin"));
        setJMenuBar(menuBar);

        // 2. Panel bên trái (Tìm kiếm + bộ lọc)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.add(new JLabel("Tìm kiếm"));
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(new JTextField("Theo mã phiếu kiểm"));
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(new JTextField("Theo mã, tên hàng"));
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(new JTextField("Theo Serial/IMEI"));

        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(new JLabel("Trạng thái"));
        JCheckBox cbTemp = new JCheckBox("Phiếu tạm", true);
        JCheckBox cbBalanced = new JCheckBox("Đã cân bằng kho", true);
        JCheckBox cbCanceled = new JCheckBox("Đã huỷ");
        leftPanel.add(cbTemp);
        leftPanel.add(cbBalanced);
        leftPanel.add(cbCanceled);

        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(new JLabel("Thời gian"));
        JRadioButton rbAll = new JRadioButton("Toàn thời gian", true);
        JRadioButton rbCustom = new JRadioButton("Lựa chọn khác");
        ButtonGroup bgTime = new ButtonGroup();
        bgTime.add(rbAll);
        bgTime.add(rbCustom);
        leftPanel.add(rbAll);
        leftPanel.add(rbCustom);
        leftPanel.add(new JSpinner(new SpinnerDateModel())); // giả lập picker

        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(new JLabel("Lựa chọn hiển thị"));
        JComboBox<String> cbRows = new JComboBox<>(new String[]{"10", "20", "50", "100"});
        leftPanel.add(cbRows);

        // 3. Panel chính bên phải
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // 3.1 Toolbar chức năng bên trên bảng
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton btnCheck = new JButton("Kiểm kho");
        JButton btnExport = new JButton("Xuất file");
        toolBar.add(btnCheck);
        toolBar.add(btnExport);
        toolBar.addSeparator();
        toolBar.add(new JButton("Grid"));
        rightPanel.add(toolBar, BorderLayout.NORTH);

        // 3.2 Bảng danh sách phiếu kiểm
        String[] mainCols = {
            "Mã kiểm kho", "Thời gian", "Ngày cân bằng", 
            "Tổng chênh lệch", "Tổng giá trị lệch", 
            "SL lệch tăng", "Tổng giá trị tăng"
        };
        DefaultTableModel mainModel = new DefaultTableModel(mainCols, 0);
        JTable mainTable = new JTable(mainModel);
        // demo 1 dòng
        mainModel.addRow(new Object[]{"KK000001", "17/05/2018 19:00", "17/05/2018 19:00", 0, 0, 0, 0});
        rightPanel.add(new JScrollPane(mainTable), BorderLayout.CENTER);

        // 3.3 Phần chi tiết ở dưới
        JTabbedPane tabDetails = new JTabbedPane();

        // Tab Thông tin chung
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Mã kiểm kho:"));
        infoPanel.add(new JLabel("KK000001"));
        infoPanel.add(new JLabel("Thời gian:"));
        infoPanel.add(new JLabel("17/05/2018 19:00"));
        infoPanel.add(new JLabel("Ngày cân bằng:"));
        infoPanel.add(new JLabel("17/05/2018 19:00"));
        infoPanel.add(new JLabel("Trạng thái:"));
        infoPanel.add(new JLabel("Đã cân bằng kho"));
        tabDetails.addTab("Thông tin", infoPanel);

        // Tab Chi tiết hàng hóa
        String[] detailCols = {"Mã hàng", "Tên hàng", "Tồn kho", "Thực tế", "SL lệch"};
        DefaultTableModel detailModel = new DefaultTableModel(detailCols, 0);
        detailModel.addRow(new Object[]{"SP000001", "Nho Mỹ-27/05/2018 (kg)", 10, 10, 0});
        JTable detailTable = new JTable(detailModel);
        JPanel detailTablePanel = new JPanel(new BorderLayout());
        detailTablePanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);
        tabDetails.addTab("Chi tiết hàng hóa", detailTablePanel);

        rightPanel.add(tabDetails, BorderLayout.SOUTH);
        tabDetails.setPreferredSize(new Dimension(800, 200));

        // 4. Kết hợp bằng JSplitPane
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        split.setDividerLocation(280);
        getContentPane().add(split);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Thiết lập Look & Feel (tuỳ chọn)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(FirstGUI::new);
    }
}
