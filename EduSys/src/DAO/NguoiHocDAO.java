/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NguoiHoc;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author doanp
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

    String SQL_insert = "insert into NguoiHoc values(?,?,?,?,?,?,?,?,GETDATE())";
    String SQL_Update = "update NguoiHoc set TenNH=?,GioiTinh=?,NgaySinh=?,DienThoai=?,Email=?,GhiChu=?,MaNV=? where MaNH=? ";
    String SQL_Delete = "delete from NguoiHoc where MaNH=?";
    String SQL_SelectALl = "select * from NguoiHoc";
    String SQL_SelectID = "select * from NguoiHoc where MaNH=?";
    String SQL_SelectByKH = "select * from dbo.NguoiHoc where MaNH NOT IN (SELECT MaNH FROM dbo.HocVien WHERE MaKH = ?) and TenNH LIKE ?";
    String SQL_SelectByKW = "SELECT * FROM NguoiHoc WHERE TenNH LIKE ?";
    String SQL_SelectHoTenNV = "select * from NhanVien";
    ThongKeDAO daoTK = new ThongKeDAO();

    @Override
    public void insert(NguoiHoc entity) {
        Xjdbc.update(SQL_insert, entity.getMaNH(), entity.getTenNh(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV());
    }

    @Override
    public void update(NguoiHoc entity) {
        Xjdbc.update(SQL_Update, entity.getTenNh(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = new ArrayList<>();
        list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<NguoiHoc> selectByKeyWord(String keyword) {
        return this.selectBySql(SQL_SelectByKW, "%" + keyword + "%");
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SQL_SelectALl);
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new NguoiHoc(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiHoc> selectByKH(String MaKH, String keyword) {
        return selectBySql(SQL_SelectByKH, MaKH, "%" + keyword + "%");
    }

    public Map<String, String> selectHoTenNV() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectHoTenNV);
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(3);
                map.putIfAbsent(key, value);
            }
            rs.getStatement().getConnection().close();
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> selectByPageIndex(int pageIndex) {
        String sql = "{call sp_ListNguoiHoc(" + pageIndex + ")}";
        String[] cols = {"MaNH", "TenNH", "GioiTinh", "NgaySinh", "DienThoai", "Email", "GhiChu", "MaNV", "NgayDK"};
        return daoTK.getListOfArray(sql, cols);
    }

}
