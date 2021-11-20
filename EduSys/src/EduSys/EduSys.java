/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys;

import DAO.NhanVienDAO;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Ximage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.Timer;

/**
 *
 * @author doanp
 */
public class EduSys extends javax.swing.JFrame {

    IF_DoiMatKhau IF_DoiMatKhau;
    IF_LoginEduSys IF_Login;
    IF_QuanLyChuyenDe IF_ChuyenDe;
    IF_QuanLyNguoiHoc IF_NguoiHoc;
    IF_QuanLyNhanVien IF_NhanVien;
    IF_QuanLyKhoaHoc IF_KhoaHoc;
    IF_QuanLyHocVien IF_HocVien;
    IF_ThongKe IF_ThongKe;
    IF_QuenMatKhau IF_QMK;

    /**
     * Creates new form EduSys
     */
    public EduSys() {
        initComponents();
        init();
    }
    Timer t;

    void init() {
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("EduSys - Hệ quản lý đào đạo");
        setIconImage(Ximage.getAppIcon());
        new ChaoJDialog(this, true).setVisible(true);
        this.startDongHo();
        IF_Login = new IF_LoginEduSys();
        centerFarme(IF_Login);
        toLogin();
    }

    void startDongHo() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date date = new Date();
                lblDongHo.setText(format.format(date));
                if (Auth.user != null && !btnDangXuat.getText().equalsIgnoreCase("Đăng xuất")) {
                    toLogout();
                    System.out.println("ok");
                }
            }
        });
        t.start();
    }

    boolean checkUser() {
        if (Auth.isLogin() != true) {
            MsgBox.alert(this, "Vui lòng đăng nhập vào hệ thống");
            return false;
        }
        return true;
    }

    boolean checkFrame() {
        if (dpDesktop.getAllFrames().length > 1) {
            MsgBox.alert(this, "Chỉ có thể mở 2 cửa sổ 1 lúc");
            return false;
        }
        return true;
    }

    void centerFarme(JInternalFrame frame) {
        dpDesktop.add(frame);
        frame.setLocation((this.getWidth() - 32 - frame.getWidth()) / 2, (this.getHeight() - 180 - frame.getHeight()) / 2);
        frame.setVisible(true);
    }

    void ketthuc() {
        if (MsgBox.confirm(this, "Bạn muốn kết thúc ứng dụng?")) {
            System.exit(0);
        }
    }

    void closedAll() {
        for (JInternalFrame frmChild : dpDesktop.getAllFrames()) {
            frmChild.dispose();
        }
    }

    void toLogin() {
        btnDangXuat.setIcon(new ImageIcon("src/icon/key.png"));
        btnDangXuat.setText("Đăng nhập");
        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (btnDangXuat.getText().trim().equalsIgnoreCase("Đăng nhập")) {
                    showLogin();
                }
            }
        });
    }

    void toLogout() {
        btnDangXuat.setIcon(new ImageIcon("src/icon/Log out.png"));
        btnDangXuat.setText("Đăng xuất");
    }

    void dangxuat() {
        if (MsgBox.confirm(this, "Bạn muốn đăng xuất?")) {
            if (checkUser()) {
                Auth.clear();
                closedAll();
                centerFarme(new IF_LoginEduSys());
                toLogin();
            }
        }
    }

    void huongdan() {
        try {
            Runtime run = Runtime.getRuntime();
            String url = "https://caodang.fpt.edu.vn/";
            run.exec("rundll32 url.dll, FileProtocolHandler " + url);
        } catch (Exception e) {
            MsgBox.alert(this, "Không tìm thấy đường dẫn");
        }
    }

    void showChuyenDe() {
        if (checkUser()) {
            try {
                IF_ChuyenDe.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_ChuyenDe = new IF_QuanLyChuyenDe();
                centerFarme(IF_ChuyenDe);
            }
        }
    }

    void showNguoiHoc() {
        if (checkUser()) {
            try {
                IF_NguoiHoc.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_NguoiHoc = new IF_QuanLyNguoiHoc();
                centerFarme(IF_NguoiHoc);
            }
        }
    }

    void showKhoaHoc() {
        if (checkUser()) {
            try {
                IF_KhoaHoc.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_KhoaHoc = new IF_QuanLyKhoaHoc();
                centerFarme(IF_KhoaHoc);
            }
        }
    }

    void showNhanVien() {
        if (checkUser()) {
            try {
                IF_NhanVien.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_NhanVien = new IF_QuanLyNhanVien();
                centerFarme(IF_NhanVien);
            }
        }
    }

    void showHocVien() {
        if (checkUser()) {
            try {
                IF_HocVien.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_HocVien = new IF_QuanLyHocVien();
                centerFarme(IF_HocVien);
            }
        }
    }

    void showLogin() {
        try {
            IF_Login.setClosed(true);
        } catch (Exception ex) {
        }
        if (checkFrame()) {
            IF_Login = new IF_LoginEduSys();
            centerFarme(IF_Login);
        }
    }

    void showQMK() {
        try {
            IF_QMK.setClosed(true);
        } catch (Exception ex) {
        }
        closedAll();
        IF_QMK = new IF_QuenMatKhau();
        centerFarme(IF_QMK);

    }

    void showThongKe(int tabNumber) {
        if (checkUser()) {
            try {
                IF_ThongKe.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_ThongKe = new IF_ThongKe(tabNumber);
                centerFarme(IF_ThongKe);
            }
        }
    }

    void showDMK() {
        if (checkUser()) {
            try {
                IF_DoiMatKhau.setClosed(true);
            } catch (Exception ex) {
            }
            if (checkFrame()) {
                IF_DoiMatKhau = new IF_DoiMatKhau();
                centerFarme(IF_DoiMatKhau);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbaCongCu = new javax.swing.JToolBar();
        btnChuyenDe = new javax.swing.JButton();
        btnNguoiHoc = new javax.swing.JButton();
        btnKhoaHoc = new javax.swing.JButton();
        btnHocVien = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnHuongDan = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDangXuat = new javax.swing.JButton();
        btnKetThuc = new javax.swing.JButton();
        btnQMK = new javax.swing.JButton();
        pnlTrangThai = new javax.swing.JPanel();
        lblTrangThai = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        dpDesktop = new javax.swing.JDesktopPane();
        mnbControl = new javax.swing.JMenuBar();
        mnuHeThong = new javax.swing.JMenu();
        mniDangNhap = new javax.swing.JMenuItem();
        mniDangXuat = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mniDoiMatKhau = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniKetThuc = new javax.swing.JMenuItem();
        mnuQuanLi = new javax.swing.JMenu();
        mniChuyenDe = new javax.swing.JMenuItem();
        mniKhoaHoc = new javax.swing.JMenuItem();
        mniNguoiHoc = new javax.swing.JMenuItem();
        mniHocVien = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mniNhanVien = new javax.swing.JMenuItem();
        mnuThongKe = new javax.swing.JMenu();
        mniBangDiem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mniLuongNguoiHoc = new javax.swing.JMenuItem();
        mniDiemChuyenDe = new javax.swing.JMenuItem();
        mniDoanhThu = new javax.swing.JMenuItem();
        mnuTroGiup = new javax.swing.JMenu();
        mniHuongDan = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mniGioiThieu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EduSys - HỆ THỐNG QUẢN LÍ ĐÀO TẠO");
        setBackground(new java.awt.Color(255, 255, 255));

        tbaCongCu.setBackground(new java.awt.Color(204, 204, 204));
        tbaCongCu.setRollover(true);
        tbaCongCu.setBorderPainted(false);

        btnChuyenDe.setBackground(new java.awt.Color(204, 204, 204));
        btnChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Lists.png"))); // NOI18N
        btnChuyenDe.setText("  Chuyên đề  ");
        btnChuyenDe.setBorder(null);
        btnChuyenDe.setFocusable(false);
        btnChuyenDe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChuyenDe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenDeActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnChuyenDe);

        btnNguoiHoc.setBackground(new java.awt.Color(204, 204, 204));
        btnNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Conference.png"))); // NOI18N
        btnNguoiHoc.setText("  Người học  ");
        btnNguoiHoc.setBorder(null);
        btnNguoiHoc.setFocusable(false);
        btnNguoiHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNguoiHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNguoiHocActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnNguoiHoc);

        btnKhoaHoc.setBackground(new java.awt.Color(204, 204, 204));
        btnKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Certificate.png"))); // NOI18N
        btnKhoaHoc.setText("  Khóa học  ");
        btnKhoaHoc.setBorder(null);
        btnKhoaHoc.setFocusable(false);
        btnKhoaHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKhoaHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoaHocActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnKhoaHoc);

        btnHocVien.setBackground(new java.awt.Color(204, 204, 204));
        btnHocVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/User.png"))); // NOI18N
        btnHocVien.setText("  Học viên  ");
        btnHocVien.setBorder(null);
        btnHocVien.setFocusable(false);
        btnHocVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHocVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHocVienActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnHocVien);
        tbaCongCu.add(jSeparator3);

        btnHuongDan.setBackground(new java.awt.Color(204, 204, 204));
        btnHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Globe.png"))); // NOI18N
        btnHuongDan.setText(" Hướng dẫn ");
        btnHuongDan.setBorder(null);
        btnHuongDan.setFocusable(false);
        btnHuongDan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHuongDan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuongDanActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnHuongDan);
        tbaCongCu.add(jSeparator2);

        btnDangXuat.setBackground(new java.awt.Color(204, 204, 204));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Log out.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.setBorder(null);
        btnDangXuat.setFocusable(false);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnDangXuat);

        btnKetThuc.setBackground(new java.awt.Color(204, 204, 204));
        btnKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Stop.png"))); // NOI18N
        btnKetThuc.setText("   Kết thúc    ");
        btnKetThuc.setBorder(null);
        btnKetThuc.setFocusable(false);
        btnKetThuc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKetThuc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnKetThuc);

        btnQMK.setBackground(new java.awt.Color(204, 204, 204));
        btnQMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Problem.png"))); // NOI18N
        btnQMK.setText("Quên mật khẩu");
        btnQMK.setBorder(null);
        btnQMK.setFocusable(false);
        btnQMK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQMK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQMKActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnQMK);

        pnlTrangThai.setBackground(new java.awt.Color(204, 204, 204));

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Info.png"))); // NOI18N
        lblTrangThai.setText("Hệ quản lí đào tạo");
        lblTrangThai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangThaiMouseClicked(evt);
            }
        });

        lblDongHo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clock.png"))); // NOI18N
        lblDongHo.setText("00:00:00 AM");

        dpDesktop.setMaximumSize(new java.awt.Dimension(792, 663));
        dpDesktop.setMinimumSize(new java.awt.Dimension(792, 663));

        javax.swing.GroupLayout dpDesktopLayout = new javax.swing.GroupLayout(dpDesktop);
        dpDesktop.setLayout(dpDesktopLayout);
        dpDesktopLayout.setHorizontalGroup(
            dpDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        dpDesktopLayout.setVerticalGroup(
            dpDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlTrangThaiLayout = new javax.swing.GroupLayout(pnlTrangThai);
        pnlTrangThai.setLayout(pnlTrangThaiLayout);
        pnlTrangThaiLayout.setHorizontalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                        .addComponent(lblTrangThai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDongHo))
                    .addComponent(dpDesktop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTrangThaiLayout.setVerticalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTrangThaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrangThai)
                    .addComponent(lblDongHo))
                .addContainerGap())
        );

        mnuHeThong.setText("Hệ thống");

        mniDangNhap.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        mniDangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Key.png"))); // NOI18N
        mniDangNhap.setText("Đăng nhập");
        mniDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangNhapActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDangNhap);

        mniDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mniDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Log out.png"))); // NOI18N
        mniDangXuat.setText("Đăng xuất");
        mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangXuatActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDangXuat);
        mnuHeThong.add(jSeparator4);

        mniDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
        mniDoiMatKhau.setText("Đổi mật khẩu");
        mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoiMatKhauActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDoiMatKhau);
        mnuHeThong.add(jSeparator1);

        mniKetThuc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        mniKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Stop.png"))); // NOI18N
        mniKetThuc.setText("Kết thúc");
        mniKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKetThucActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniKetThuc);

        mnbControl.add(mnuHeThong);

        mnuQuanLi.setText("Quản lý");

        mniChuyenDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
        mniChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Lists.png"))); // NOI18N
        mniChuyenDe.setText("Chuyên đề");
        mniChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniChuyenDeActionPerformed(evt);
            }
        });
        mnuQuanLi.add(mniChuyenDe);

        mniKhoaHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, java.awt.event.InputEvent.CTRL_MASK));
        mniKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Certificate.png"))); // NOI18N
        mniKhoaHoc.setText("Khóa học");
        mniKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKhoaHocActionPerformed(evt);
            }
        });
        mnuQuanLi.add(mniKhoaHoc);

        mniNguoiHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.CTRL_MASK));
        mniNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Conference.png"))); // NOI18N
        mniNguoiHoc.setText("Người học");
        mniNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNguoiHocActionPerformed(evt);
            }
        });
        mnuQuanLi.add(mniNguoiHoc);

        mniHocVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        mniHocVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/User.png"))); // NOI18N
        mniHocVien.setText("Học viên");
        mniHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHocVienActionPerformed(evt);
            }
        });
        mnuQuanLi.add(mniHocVien);
        mnuQuanLi.add(jSeparator5);

        mniNhanVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.CTRL_MASK));
        mniNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/User group.png"))); // NOI18N
        mniNhanVien.setText("Nhân viên");
        mniNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNhanVienActionPerformed(evt);
            }
        });
        mnuQuanLi.add(mniNhanVien);

        mnbControl.add(mnuQuanLi);

        mnuThongKe.setText("Thống kê");

        mniBangDiem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.SHIFT_MASK));
        mniBangDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Card file.png"))); // NOI18N
        mniBangDiem.setText("Bảng điểm");
        mniBangDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniBangDiemActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniBangDiem);
        mnuThongKe.add(jSeparator6);

        mniLuongNguoiHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, java.awt.event.InputEvent.SHIFT_MASK));
        mniLuongNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clien list.png"))); // NOI18N
        mniLuongNguoiHoc.setText("Lượng người học");
        mniLuongNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLuongNguoiHocActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniLuongNguoiHoc);

        mniDiemChuyenDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.SHIFT_MASK));
        mniDiemChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Statistics.png"))); // NOI18N
        mniDiemChuyenDe.setText("Điểm chuyên đề");
        mniDiemChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDiemChuyenDeActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniDiemChuyenDe);

        mniDoanhThu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.SHIFT_MASK));
        mniDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Dollar.png"))); // NOI18N
        mniDoanhThu.setText("Doanh thu");
        mniDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoanhThuActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniDoanhThu);

        mnbControl.add(mnuThongKe);

        mnuTroGiup.setText("Trợ giúp");

        mniHuongDan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mniHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Globe.png"))); // NOI18N
        mniHuongDan.setText("Hướng dẫn sử dụng");
        mniHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHuongDanActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniHuongDan);
        mnuTroGiup.add(jSeparator7);

        mniGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Home.png"))); // NOI18N
        mniGioiThieu.setText("Giới thiệu sản phẩm");
        mniGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniGioiThieuActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniGioiThieu);

        mnbControl.add(mnuTroGiup);

        setJMenuBar(mnbControl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbaCongCu, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
            .addComponent(pnlTrangThai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbaCongCu, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        if (btnDangXuat.getText().trim().equalsIgnoreCase("Đăng xuất")) {
            dangxuat();
        }
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucActionPerformed
        ketthuc();
    }//GEN-LAST:event_btnKetThucActionPerformed

    private void btnHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHocVienActionPerformed
        showHocVien();
    }//GEN-LAST:event_btnHocVienActionPerformed

    private void mniGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniGioiThieuActionPerformed

    }//GEN-LAST:event_mniGioiThieuActionPerformed

    private void mniDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoanhThuActionPerformed
        if (Auth.isManager()) {
            showThongKe(3);
        } else {
            MsgBox.alert(this, "Bạn không có quyền xem doanh thu");
        }
    }//GEN-LAST:event_mniDoanhThuActionPerformed

    private void mniDiemChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDiemChuyenDeActionPerformed
        showThongKe(2);
    }//GEN-LAST:event_mniDiemChuyenDeActionPerformed

    private void mniLuongNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLuongNguoiHocActionPerformed
        showThongKe(1);
    }//GEN-LAST:event_mniLuongNguoiHocActionPerformed

    private void mniBangDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniBangDiemActionPerformed
        showThongKe(0);
    }//GEN-LAST:event_mniBangDiemActionPerformed

    private void mniHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHocVienActionPerformed
        showHocVien();
    }//GEN-LAST:event_mniHocVienActionPerformed

    private void mniNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNguoiHocActionPerformed
        showNguoiHoc();
    }//GEN-LAST:event_mniNguoiHocActionPerformed

    private void mniKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKhoaHocActionPerformed
        showKhoaHoc();
    }//GEN-LAST:event_mniKhoaHocActionPerformed

    private void mniKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKetThucActionPerformed
        ketthuc();
    }//GEN-LAST:event_mniKetThucActionPerformed

    private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangXuatActionPerformed
        dangxuat();
    }//GEN-LAST:event_mniDangXuatActionPerformed

    private void mniDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangNhapActionPerformed
        showLogin();
    }//GEN-LAST:event_mniDangNhapActionPerformed

    private void mniNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNhanVienActionPerformed
        showNhanVien();
    }//GEN-LAST:event_mniNhanVienActionPerformed

    private void btnKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoaHocActionPerformed
        showKhoaHoc();
    }//GEN-LAST:event_btnKhoaHocActionPerformed

    private void btnNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNguoiHocActionPerformed
        showNguoiHoc();
    }//GEN-LAST:event_btnNguoiHocActionPerformed

    private void btnChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenDeActionPerformed
        showChuyenDe();
    }//GEN-LAST:event_btnChuyenDeActionPerformed

    private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoiMatKhauActionPerformed
        showDMK();
    }//GEN-LAST:event_mniDoiMatKhauActionPerformed

    private void mniChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniChuyenDeActionPerformed
        showChuyenDe();
    }//GEN-LAST:event_mniChuyenDeActionPerformed

    private void btnHuongDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuongDanActionPerformed
        huongdan();
    }//GEN-LAST:event_btnHuongDanActionPerformed

    private void mniHuongDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHuongDanActionPerformed
        huongdan();
    }//GEN-LAST:event_mniHuongDanActionPerformed

    private void lblTrangThaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangThaiMouseClicked
        if (evt.getClickCount() == 2) {
            huongdan();
        }
    }//GEN-LAST:event_lblTrangThaiMouseClicked

    private void btnQMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQMKActionPerformed
        showQMK();
    }//GEN-LAST:event_btnQMKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(EduSys.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EduSys.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EduSys.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EduSys.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EduSys().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenDe;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHocVien;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnKetThuc;
    private javax.swing.JButton btnKhoaHoc;
    private javax.swing.JButton btnNguoiHoc;
    private javax.swing.JButton btnQMK;
    private javax.swing.JDesktopPane dpDesktop;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JMenuBar mnbControl;
    private javax.swing.JMenuItem mniBangDiem;
    private javax.swing.JMenuItem mniChuyenDe;
    private javax.swing.JMenuItem mniDangNhap;
    private javax.swing.JMenuItem mniDangXuat;
    private javax.swing.JMenuItem mniDiemChuyenDe;
    private javax.swing.JMenuItem mniDoanhThu;
    private javax.swing.JMenuItem mniDoiMatKhau;
    private javax.swing.JMenuItem mniGioiThieu;
    private javax.swing.JMenuItem mniHocVien;
    private javax.swing.JMenuItem mniHuongDan;
    private javax.swing.JMenuItem mniKetThuc;
    private javax.swing.JMenuItem mniKhoaHoc;
    private javax.swing.JMenuItem mniLuongNguoiHoc;
    private javax.swing.JMenuItem mniNguoiHoc;
    private javax.swing.JMenuItem mniNhanVien;
    private javax.swing.JMenu mnuHeThong;
    private javax.swing.JMenu mnuQuanLi;
    private javax.swing.JMenu mnuThongKe;
    private javax.swing.JMenu mnuTroGiup;
    private javax.swing.JPanel pnlTrangThai;
    private javax.swing.JToolBar tbaCongCu;
    // End of variables declaration//GEN-END:variables

}
