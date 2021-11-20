/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys;

import DAO.ChuyenDeDAO;
import DAO.HocVienDAO;
import DAO.KhoaHocDAO;
import Entity.ChuyenDe;
import Entity.HocVien;
import Entity.KhoaHoc;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xdate;
import Untils.Xreport;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_QuanLyHocVien extends javax.swing.JInternalFrame {

    DefaultTableModel modelHocVien;
    DefaultTableModel modelNguoiHoc;
    List<ChuyenDe> listCD;
    List<KhoaHoc> listKH;
    Map<String, String> mapTenNV;
    HocVienDAO daoHV;
    Map<String, String> mapTenCD;
    int pageIndexHV;
    int pageIndexNH;
    boolean statusPageHV;
    boolean statusPageNH;

    /**
     * Creates new form HocVienForm
     */
    public IF_QuanLyHocVien() {
        initComponents();
        init();
        prepareGui();
    }

    void init() {
        setResizable(false);
        setTitle("EduSys - Quản lý học viên");
        setDefaultCloseOperation(2);
        tabs.setSelectedIndex(1);
        setComponentPopupMenu(pmMenu);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(false);
        pageIndexHV = 0;
        pageIndexNH = 0;
        statusPageHV = false;
        statusPageNH = false;
        txtTim.setText("");
        daoHV = new HocVienDAO();
    }

    void prepareGui() {
        String[] h1 = {"STT", "MÃ HV", "MÃ NH", "HỌ TÊN", "ĐIỂM"};
        String[] h2 = {"MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL"};
        modelHocVien = new DefaultTableModel(h1, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblHocVien.setModel(modelHocVien);
        tblHocVien.setRowHeight(20);
        modelNguoiHoc = new DefaultTableModel(h2, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblNguoiHoc.setModel(modelNguoiHoc);
        tblNguoiHoc.setRowHeight(20);
        fillCboChuyenDe();
        fillTableHV();
        fillTableNH();
    }

    void fillCboChuyenDe() {
        listCD = new ChuyenDeDAO().selectAll();
        cboChuyenDe.removeAllItems();
        for (ChuyenDe cd : listCD) {
            cboChuyenDe.addItem(cd.getMaCD() + " - " + cd.getTenCD());
        }
        fillCboKhoaHoc();
    }

    void fillCboKhoaHoc() {
        mapTenCD = new KhoaHocDAO().selectTenCD();
        listKH = new KhoaHocDAO().selectByCD(listCD.get(cboChuyenDe.getSelectedIndex()).getMaCD());
        cboKhoaHoc.removeAllItems();
        for (KhoaHoc kh : listKH) {
            cboKhoaHoc.addItem("Khóa học " + kh.getMaKH() + ": " + mapTenCD.get(kh.getMaCD())
                    + "(" + Xdate.toString(Xdate.toDate(kh.getNgayKG(), "yyyy-MM-dd"), "dd/MM/yyyy") + ")");
        }
        fillTableHV();
        fillTableNH();
    }

    void fillTableHV() {
        modelHocVien.setRowCount(0);
        if (cboKhoaHoc.getItemCount() == 0) {
            return;
        }
        mapTenNV = daoHV.selectHoTen();
        int maKH = Integer.parseInt(listKH.get(cboKhoaHoc.getSelectedIndex()).getMaKH());
        List<Object[]> listHV = daoHV.selectByPageIndexHV(pageIndexHV, maKH);
        int index = 0;
        if (listHV.isEmpty()) {
            MsgBox.alert(this, "Không còn dữ liệu!!");
            if (statusPageHV) {
                pageIndexHV--;
                statusPageHV = false;
            }
            listHV = daoHV.selectByPageIndexHV(pageIndexHV, maKH);
        }
        for (Object[] hv : listHV) {
            index++;
            modelHocVien.addRow(new Object[]{index, hv[0], hv[2], mapTenNV.get(hv[2]), hv[3]});
        }
    }

    void fillTableNH() {
        modelNguoiHoc.setRowCount(0);
        if (cboKhoaHoc.getItemCount() == 0) {
            return;
        }
        int maKH = Integer.parseInt(listKH.get(cboKhoaHoc.getSelectedIndex()).getMaKH());
        List<Object[]> listHN = daoHV.selectByPageIndexNH(pageIndexNH, maKH);
        if (listHN.isEmpty()) {
            MsgBox.alert(this, "Không còn dữ liệu!!");
            if (statusPageNH) {
                pageIndexNH--;
                statusPageNH = false;
            }
            listHN = daoHV.selectByPageIndexNH(pageIndexNH, maKH);
        }
        for (Object[] nh : listHN) {
            if (nh[1].toString().contains(txtTim.getText()) || nh[0].toString().contains(txtTim.getText())) {
                modelNguoiHoc.addRow(new Object[]{nh[0], nh[1], nh[2].toString().equalsIgnoreCase("true") ? "Nữ" : "Nam", Xdate.toString(Xdate.toDate(nh[3].toString(), "yyyy-MM-dd"), "dd/MM/yyyy"), nh[4], nh[5]});
            }
        }

    }

    HocVien getForm(boolean check) {
        HocVien hv = null;
        if (check) {
            hv = new HocVien(Integer.parseInt(listKH.get(cboKhoaHoc.getSelectedIndex()).getMaKH()), tblNguoiHoc.getValueAt(tblNguoiHoc.getSelectedRow(), 0).toString(), 0);
        } else {
            int index = tblHocVien.getSelectedRow();
            String diemStr = MsgBox.promt(this, "Nhập điểm cần chỉnh sửa");
            float diem = Float.parseFloat(diemStr);
            if (diemStr.length() == 0) {
                MsgBox.alert(this, "Vui lòng nhập điểm cho sinh viên");
            } else if (diem < 0 || diem > 10) {
                MsgBox.alert(this, "Vui lòng nhập điểm trong khoảng 1-10");
            } else {
                hv = new HocVien(Integer.parseInt(tblHocVien.getValueAt(index, 1).toString()), Integer.parseInt(listKH.get(cboKhoaHoc.getSelectedIndex()).getMaKH()), tblHocVien.getValueAt(index, 3).toString(), diem);
            }
        }
        return hv;
    }

    void insert() {
        try {
            daoHV.insert(getForm(true));
            fillTableHV();
            fillTableNH();
            MsgBox.alert(this, "Thêm học viên thành công !!");
        } catch (Exception e) {
            if (MsgBox.confirm(this, "Thêm học viên thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                Xreport.writeException(e, getForm(true));
                Xreport.sendMail();
            }
        }
    }

    void update() {
        try {
            daoHV.update(getForm(false));
            fillTableHV();
            fillTableNH();
            MsgBox.alert(this, "Chỉnh sửa điểm học viên thành công !!");
        } catch (Exception e) {
            if (MsgBox.confirm(this, "Chỉnh sửa diểm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                Xreport.writeException(e, getForm(true));
                Xreport.sendMail();
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề !!");
            return;
        }
        try {
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa học viên này?")) {
                daoHV.delete(Integer.parseInt(tblHocVien.getValueAt(tblHocVien.getSelectedRow(), 1).toString()));
                fillTableHV();
                fillTableNH();
                MsgBox.alert(this, "Xóa học viên thành công !!");
            }
        } catch (Exception e) {
            if (MsgBox.confirm(this, "Xóa học viên thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                Xreport.writeException(e, Integer.parseInt(tblHocVien.getValueAt(tblHocVien.getSelectedRow(), 1).toString()));
                Xreport.sendMail();
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

        pmMenu = new javax.swing.JPopupMenu();
        mnuLamMoi = new javax.swing.JMenuItem();
        mnuDong = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnPrevPageHV = new javax.swing.JButton();
        btnNextPageHV = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnPrevPageNH = new javax.swing.JButton();
        btnNextPageNH = new javax.swing.JButton();

        mnuLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
        mnuLamMoi.setText("Làm mới");
        mnuLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLamMoiActionPerformed(evt);
            }
        });
        pmMenu.add(mnuLamMoi);

        mnuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        mnuDong.setText("Đóng");
        mnuDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDongActionPerformed(evt);
            }
        });
        pmMenu.add(mnuDong);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(662, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "CHUYÊN ĐỀ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 0, 255))); // NOI18N

        cboChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChuyenDe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboChuyenDeItemStateChanged(evt);
            }
        });
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "KHÓA HỌC", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKhoaHoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKhoaHocItemStateChanged(evt);
            }
        });
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHocVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHocVien);

        btnXoa.setText("Xóa khỏi khóa hoc");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật điểm");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnPrevPageHV.setText("<<");
        btnPrevPageHV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPageHVActionPerformed(evt);
            }
        });

        btnNextPageHV.setText(">>");
        btnNextPageHV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageHVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btnPrevPageHV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextPageHV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa)
                        .addGap(30, 30, 30)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnCapNhat)
                    .addComponent(btnPrevPageHV)
                    .addComponent(btnNextPageHV))
                .addContainerGap())
        );

        tabs.addTab("HỌC VIÊN", jPanel8);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        txtTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimActionPerformed(evt);
            }
        });
        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTim)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblNguoiHoc);

        btnThem.setText("Thêm vào khóa hoc");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnPrevPageNH.setText("<<");
        btnPrevPageNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPageNHActionPerformed(evt);
            }
        });

        btnNextPageNH.setText(">>");
        btnNextPageNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageNHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(btnPrevPageNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextPageNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnPrevPageNH)
                    .addComponent(btnNextPageNH))
                .addContainerGap())
        );

        tabs.addTab("NGƯỜI HỌC", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void cboChuyenDeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboChuyenDeItemStateChanged
        if (cboChuyenDe.getItemCount() > 1) {
            pageIndexHV = 0;
            pageIndexNH = 0;
            fillCboKhoaHoc();
        }
    }//GEN-LAST:event_cboChuyenDeItemStateChanged

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void cboKhoaHocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKhoaHocItemStateChanged
        if (cboKhoaHoc.getItemCount() > 1) {
            pageIndexHV = 0;
            pageIndexNH = 0;
            fillTableHV();
            fillTableNH();
        }
    }//GEN-LAST:event_cboKhoaHocItemStateChanged

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (tblNguoiHoc.getSelectedRow() >= 0) {
            insert();
        } else {
            MsgBox.alert(this, "Vui lòng chọn học viên cần thêm");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHocVienMouseClicked
        if (evt.getClickCount() == 2) {
            update();
        }
    }//GEN-LAST:event_tblHocVienMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (tblHocVien.getSelectedRow() >= 0) {
            update();
        } else {
            MsgBox.alert(this, "Vui lòng chọn học viên cần chỉnh sửa");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (tblHocVien.getSelectedRow() >= 0) {
            delete();
        } else {
            MsgBox.alert(this, "Vui lòng chọn học viên cần xóa");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnPrevPageHVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPageHVActionPerformed
        pageIndexHV--;
        if (pageIndexHV >= 0) {
            fillTableHV();
        } else {
            MsgBox.alert(this, "Không còn dữ liệu!!");
            pageIndexHV++;
        }
    }//GEN-LAST:event_btnPrevPageHVActionPerformed

    private void btnNextPageHVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPageHVActionPerformed
        pageIndexHV++;
        statusPageHV = true;
        fillTableHV();
    }//GEN-LAST:event_btnNextPageHVActionPerformed

    private void btnPrevPageNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPageNHActionPerformed
        pageIndexNH--;
        if (pageIndexNH >= 0) {
            fillTableNH();
        } else {
            MsgBox.alert(this, "Không còn dữ liệu!!");
            pageIndexNH++;
        }
    }//GEN-LAST:event_btnPrevPageNHActionPerformed

    private void btnNextPageNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPageNHActionPerformed
        pageIndexNH++;
        statusPageNH = true;
        fillTableNH();
    }//GEN-LAST:event_btnNextPageNHActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed

    }//GEN-LAST:event_txtTimActionPerformed

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        fillTableNH();
    }//GEN-LAST:event_txtTimKeyReleased

    private void mnuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDongActionPerformed
       dispose();
    }//GEN-LAST:event_mnuDongActionPerformed

    private void mnuLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLamMoiActionPerformed
        init();
        prepareGui();
    }//GEN-LAST:event_mnuLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnNextPageHV;
    private javax.swing.JButton btnNextPageNH;
    private javax.swing.JButton btnPrevPageHV;
    private javax.swing.JButton btnPrevPageNH;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem mnuDong;
    private javax.swing.JMenuItem mnuLamMoi;
    private javax.swing.JPopupMenu pmMenu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
