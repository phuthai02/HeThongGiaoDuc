/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys;

import DAO.HocVienDAO;
import DAO.KhoaHocDAO;
import DAO.ThongKeDAO;
import Entity.KhoaHoc;
import Untils.Auth;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_ThongKe extends javax.swing.JInternalFrame {

    DefaultTableModel modelBangDiem;
    DefaultTableModel modelNguoiHoc;
    DefaultTableModel modelChuyenDe;
    DefaultTableModel modelDoanhThu;
    KhoaHocDAO daoKH = new KhoaHocDAO();
    HocVienDAO daoHV = new HocVienDAO();
    ThongKeDAO daoTK = new ThongKeDAO();
    List<KhoaHoc> listKH;
    List<Object[]> listDiemHV;
    List<Object[]> listSoLuongNH;
    List<Object[]> listDiemCD;
    List<Object[]> listDoanhThu;
    Map<String, String> mapTenCD;
    Map<Integer, Integer> mapNam;

    /**
     * Creates new form ThongKeJDialog
     */
    public IF_ThongKe(int pageIndex) {
        initComponents();
        init();
        prepareGui();
        tabs.setSelectedIndex(pageIndex);
        if(Auth.isManager()!=true){
            tabs.remove(3);
        }
    }

    void init() {
        setResizable(false);
        setTitle("EduSys - Tổng hợp & thống kê");
        setDefaultCloseOperation(2);
        setComponentPopupMenu(pmMenu);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
    }

    void prepareGui() {
        String[] h1 = {"MÃ NH", "HỌ TÊN", "ĐIỂM", "XẾP LOẠI"};
        String[] h2 = {"NĂM", "SỐ NGƯỜI HỌC", "ĐK SỚM NHẤT", "ĐK MUỘN NHẤT"};
        String[] h3 = {"CHUYÊN ĐỀ", "SL HỌC VIÊN", "ĐIỂM TN", "ĐIỂM CN", "ĐIỂM TB"};
        String[] h4 = {"CHUYÊN ĐỀ", "SỐ KHÓA HỌC", "SỐ HV", "DOANH THU", "HPLN", "HPNN", "HPTB"};
        modelBangDiem = new DefaultTableModel(h1, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblBangDiem.setModel(modelBangDiem);
        tblBangDiem.setRowHeight(20);
        modelNguoiHoc = new DefaultTableModel(h2, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblNguoiHoc.setModel(modelNguoiHoc);
        tblNguoiHoc.setRowHeight(20);
        modelChuyenDe = new DefaultTableModel(h3, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblDiemChuyenDe.setModel(modelChuyenDe);
        tblDiemChuyenDe.setRowHeight(20);
        modelDoanhThu = new DefaultTableModel(h4, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblDoanhThu.setModel(modelDoanhThu);
        tblDoanhThu.setRowHeight(20);
        fillCboKhoaHoc();
        fillCboNam();
        fillTableBangDiem();
        fillTableNguoHoc();
        fillTableChuyenDe();
        fillTableDoanhThu();
    }

    void fillCboKhoaHoc() {
        cboKhoaHoc.removeAllItems();
        mapTenCD = daoKH.selectTenCD();
        listKH = daoKH.selectAll();
        for (KhoaHoc kh : listKH) {
            cboKhoaHoc.addItem("Khóa học " + kh.getMaKH() + ": " + mapTenCD.get(kh.getMaCD()) + "(" + kh.getGhiChu() + ")");
        }
    }

    void fillCboNam() {
        mapNam = daoTK.selectNam();
        cboNam.removeAllItems();
        Set set = mapNam.keySet();
        for (Object key : set) {
            cboNam.addItem(mapNam.get(key).toString());
        }
    }

    void fillTableBangDiem() {
        listDiemHV = daoTK.getBangDiem(Integer.parseInt(listKH.get(cboKhoaHoc.getSelectedIndex()).getMaKH()));
        modelBangDiem.setRowCount(0);
        for (Object[] obj : listDiemHV) {
            modelBangDiem.addRow(new Object[]{obj[0], obj[1], obj[2], xepLoai(Float.parseFloat(obj[2] + ""))
            });
        }
    }

    String xepLoai(float diem) {
        if (diem < 5) {
            return "Chưa đạt";
        } else if (diem < 6.5) {
            return "Trung bình";
        } else if (diem < 7.5) {
            return "Khá";
        } else if (diem < 9) {
            return "Giỏi";
        }
        return "Xuất sắc";
    }

    void fillTableNguoHoc() {
        listSoLuongNH = daoTK.getLuongNguoiHoc();
        modelNguoiHoc.setRowCount(0);
        for (Object[] obj : listSoLuongNH) {
            modelNguoiHoc.addRow(new Object[]{obj[0], obj[1], obj[2], obj[3]
            });
        }
    }

    void fillTableChuyenDe() {
        listDiemCD = daoTK.getDiemChuyenDe();
        modelChuyenDe.setRowCount(0);
        for (Object[] obj : listDiemCD) {
            double diemTB = (double) Math.round(((double) obj[4] * 100)) / 100;
            modelChuyenDe.addRow(new Object[]{obj[0], obj[1], obj[2], obj[3], diemTB
            });
        }
    }

    void fillTableDoanhThu() {
         int year = Integer.parseInt(cboNam.getSelectedItem().toString());
         listDoanhThu = daoTK.getDoanhThu(year);
         modelDoanhThu.setRowCount(0);
         for (Object[] obj : listDoanhThu) {
            modelDoanhThu.addRow(obj);
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
        jLabel2 = new javax.swing.JLabel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangDiem = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDiemChuyenDe = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
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

        jLabel2.setText("KHÓA HỌC");

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKhoaHoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKhoaHocItemStateChanged(evt);
            }
        });

        tblBangDiem.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblBangDiem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("BẢNG ĐIỂM", jPanel1);

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
        jScrollPane2.setViewportView(tblNguoiHoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabs.addTab("NGƯỜI HỌC", jPanel2);

        tblDiemChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblDiemChuyenDe);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("ĐIỂM CHUYÊN ĐỀ", jPanel3);

        jLabel3.setText("NĂM: ");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });

        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblDoanhThu);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("DOANH THU", jPanel4);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("THỐNG KÊ TỔNG HỢP");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboKhoaHocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKhoaHocItemStateChanged
        if (cboKhoaHoc.getItemCount() > 1) {
            fillTableBangDiem();
        }
    }//GEN-LAST:event_cboKhoaHocItemStateChanged

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
       if (cboNam.getItemCount() > 1) {
            fillTableDoanhThu();
        }
    }//GEN-LAST:event_cboNamItemStateChanged

    private void mnuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDongActionPerformed
      dispose();
    }//GEN-LAST:event_mnuDongActionPerformed

    private void mnuLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLamMoiActionPerformed
       init();
       prepareGui();
    }//GEN-LAST:event_mnuLamMoiActionPerformed

    void selectedTab(int index) {
        tabs.setSelectedIndex(index);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem mnuDong;
    private javax.swing.JMenuItem mnuLamMoi;
    private javax.swing.JPopupMenu pmMenu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblBangDiem;
    private javax.swing.JTable tblDiemChuyenDe;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTable tblNguoiHoc;
    // End of variables declaration//GEN-END:variables
}
