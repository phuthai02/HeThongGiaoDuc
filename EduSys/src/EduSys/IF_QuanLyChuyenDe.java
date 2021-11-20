/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys;

import DAO.ChuyenDeDAO;
import Entity.ChuyenDe;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Ximage;
import Untils.Xreport;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_QuanLyChuyenDe extends javax.swing.JInternalFrame {

    DefaultTableModel modelChuyenDe;
    ChuyenDeDAO dao;
    int row;
    int pageIndex;
    boolean statusPage;

    /**
     * Creates new form IF_QuanLyChuyenDe
     */
    public IF_QuanLyChuyenDe() {
        initComponents();
        init();
        prepareGui();
    }

    void init() {
        setTitle("EduSys - Quản lý chuyên đề");
        lblHinhAnh.setSize(190, 210);
        tabs.setSelectedIndex(1);
        setDefaultCloseOperation(2);
        setComponentPopupMenu(pmMenu);
        row = -1;
        clearForm();
        updateStatus();
        pageIndex = 0;
        statusPage = false;
        dao = new ChuyenDeDAO();
    }

    void prepareGui() {
        String[] h = {"MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG(GIỜ)", "HÌNH ẢNH"};
        modelChuyenDe = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tblDanhSach.setModel(modelChuyenDe);
        tblDanhSach.setRowHeight(20);
        fillTable();
    }

    void fillTable() {
        try {
            List<Object[]> list = dao.selectByPageIndex(pageIndex);
            if (list.isEmpty()) {
                MsgBox.alert(this, "Không còn dữ liệu!!");
                if (statusPage) {
                    pageIndex--;
                    statusPage = false;
                }
                list = dao.selectByPageIndex(pageIndex);
            }
            modelChuyenDe.setRowCount(0);
            for (Object[] cd : list) {
                modelChuyenDe.addRow(cd);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!!");
        }
    }

    void setForm(ChuyenDe cd) {
        txtMaChuyenDe.setText(cd.getMaCD());
        txtTenChuyenDe.setText(cd.getTenCD());
        txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
        txtMoTa.setText(cd.getMoTa());
        lblHinhAnh.setToolTipText(cd.getHinhAnh());
        if (cd.getHinhAnh() != null) {
            lblHinhAnh.setIcon(Ximage.read(cd.getHinhAnh(), lblHinhAnh.getWidth(), lblHinhAnh.getHeight()));
        }
    }

    ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(txtMaChuyenDe.getText());
        cd.setTenCD(txtTenChuyenDe.getText());
        cd.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        cd.setMoTa(txtMoTa.getText());
        cd.setHinhAnh(lblHinhAnh.getToolTipText());
        return cd;
    }

    void clearForm() {
        txtMaChuyenDe.setText("");
        txtTenChuyenDe.setText("");
        txtThoiLuong.setText("");
        txtHocPhi.setText("");
        txtMoTa.setText("");
        lblHinhAnh.setIcon(null);
        lblHinhAnh.setText("");
        this.row = -1;
        this.updateStatus();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblDanhSach.getRowCount() - 1);
        txtMaChuyenDe.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPre.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void insert() {
        ChuyenDe cd = getForm();
        try {
            dao.insert(cd);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm chuyên đề mới thành công !!");
        } catch (Exception e) {
            if (MsgBox.confirm(this, "Thêm chuyên đề thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                Xreport.writeException(e, cd.toString());
                Xreport.sendMail();
            }
        }
    }

    void update() {
        ChuyenDe cd = getForm();
        try {
            dao.update(cd);
            this.fillTable();
            MsgBox.alert(this, "Chỉnh sửa chuyên đề thành công !!");
        } catch (Exception e) {
            if (MsgBox.confirm(this, "Chỉnh sửa chuyên đề thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                Xreport.writeException(e, cd.toString());
                Xreport.sendMail();
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề !!");
        } else {
            String macd = txtMaChuyenDe.getText();
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa chuyên đề này ?")) {
                try {
                    dao.delete(macd);
                    fillTable();
                    clearForm();
                    MsgBox.alert(this, "Xóa chuyên đề thành công !!");
                } catch (Exception e) {
                    if (MsgBox.confirm(this, "Xóa chuyên đề thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                        Xreport.writeException(e, macd);
                        Xreport.sendMail();
                    }
                }
            }
        }
    }

    void edit() {
        String macd = (String) tblDanhSach.getValueAt(row, 0);
        ChuyenDe cd = dao.selectById(macd);
        setForm(cd);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblDanhSach.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblDanhSach.getRowCount() - 1;
        this.edit();
    }

    void chonAnh() {
        JFileChooser jfc = new JFileChooser("logos");
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            Ximage.save(file);
            lblHinhAnh.setToolTipText(file.getName());
            lblHinhAnh.setIcon(Ximage.read(file.getName(), lblHinhAnh.getWidth(), lblHinhAnh.getHeight()));
        }
    }

    boolean validateForm() {
        if (txtMaChuyenDe.getText().equals("")) {
            MsgBox.alert(this, "Không bỏ trống mã");
            txtMaChuyenDe.requestFocus();
            return false;
        } else if (txtTenChuyenDe.getText().equals("")) {
            MsgBox.alert(this, "Không bỏ trống tên chuyên đề");
            txtTenChuyenDe.requestFocus();
            return false;
        } else if (txtHocPhi.getText().equals("")) {
            MsgBox.alert(this, "không để trống học phí");
            txtHocPhi.requestFocus();
            return false;
        } else {
            try {
                double tien = Double.parseDouble(txtHocPhi.getText());
                if (tien <= 0) {
                    MsgBox.alert(this, "Học phí phải là số dương");
                    txtHocPhi.requestFocus();
                    return false;
                } else {
                    if (txtThoiLuong.getText().equals("")) {
                        MsgBox.alert(this, "không bỏ trống thời lượng");
                        txtThoiLuong.requestFocus();
                        return false;
                    } else {
                        try {
                            int tl = Integer.parseInt(txtThoiLuong.getText());
                            if (tl <= 0) {
                                MsgBox.alert(this, "Thời lượng học là số nguyên dương");
                                txtThoiLuong.requestFocus();
                                return false;
                            } else {
                                if (lblHinhAnh.getIcon() == null) {
                                    MsgBox.alert(this, "Hình ảnh không được để trống");
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } catch (Exception e) {
                            MsgBox.alert(this, "Thời lượng học phải là số");
                            txtThoiLuong.requestFocus();
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Học phí học là một số ");
                txtHocPhi.requestFocus();
                return false;
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
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        txtHocPhi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenChuyenDe = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaChuyenDe = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnNextPage = new javax.swing.JButton();
        btnPrevPage = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        tabs.setMaximumSize(new java.awt.Dimension(400, 400));
        tabs.setMinimumSize(new java.awt.Dimension(400, 400));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Mô Tả Chuyên Đề");

        btnSua.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        txtHocPhi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Học Phí");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Thời Lượng (Giờ)");

        txtTenChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Mã Chuyên Đề");

        txtMaChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tên Chuyên Đề");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Hình Logo");

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        lblHinhAnh.setMaximumSize(new java.awt.Dimension(190, 210));
        lblHinhAnh.setMinimumSize(new java.awt.Dimension(190, 210));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaChuyenDe)
                            .addComponent(txtTenChuyenDe)
                            .addComponent(txtHocPhi)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtThoiLuong)))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong))
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã CD", "Tên CD", "Học phí", "Thời lượng", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        btnNextPage.setText(">>");
        btnNextPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageActionPerformed(evt);
            }
        });

        btnPrevPage.setText("<<");
        btnPrevPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnPrevPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextPage)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrevPage)
                    .addComponent(btnNextPage))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        prev();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateForm()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSach.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnPrevPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPageActionPerformed
        pageIndex--;
        if (pageIndex >= 0) {
            fillTable();
        } else {
            MsgBox.alert(this, "Không còn dữ liệu!!");
            pageIndex++;
        }
    }//GEN-LAST:event_btnPrevPageActionPerformed

    private void btnNextPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPageActionPerformed
        pageIndex++;
        statusPage = true;
        fillTable();
    }//GEN-LAST:event_btnNextPageActionPerformed

    private void mnuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDongActionPerformed
        dispose();
    }//GEN-LAST:event_mnuDongActionPerformed

    private void mnuLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLamMoiActionPerformed
        init();
        prepareGui();
    }//GEN-LAST:event_mnuLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextPage;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnPrevPage;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JMenuItem mnuDong;
    private javax.swing.JMenuItem mnuLamMoi;
    private javax.swing.JPopupMenu pmMenu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyenDe;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenChuyenDe;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
