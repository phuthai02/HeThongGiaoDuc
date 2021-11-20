/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HocVien;
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
public class HocVienDAO extends EduSysDAO<HocVien, Integer> {

    String SQL_insert = "insert into HocVien values(?,?,?)";
    String SQL_Update = "update HocVien set Diem=? where MaHV=? and MaKH =?";
    String SQL_Delete = "delete from HocVien where MaHV=?";
    String SQL_SelectALl = "select * from HocVien";
    String SQL_SelectID = "select * from HocVien where MaHV=?";
    String SQL_SelectByKH = "select * from HocVien where MaKH=?";
    String SQL_SelectHoTen = "select MaNH, TenNH from NguoiHoc";
    ThongKeDAO daoTK = new ThongKeDAO();

    @Override
    public void insert(HocVien entity) {
        Xjdbc.update(SQL_insert, entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        Xjdbc.update(SQL_Update, entity.getDiem(), entity.getMaHV(), entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = new ArrayList<>();
        list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SQL_SelectALl);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new HocVien(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getFloat(4)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> selectHoTen() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectHoTen);
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                map.putIfAbsent(key, value);
            }
            rs.getStatement().getConnection().close();
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HocVien> selectByKH(String MaKH) {
        return this.selectBySql(SQL_SelectByKH, MaKH);
    }

    public List<Object[]> selectByPageIndexHV(int pageIndex, int MaKH) {
        String sql = "{call sp_ListHocVien(" + pageIndex + "," + MaKH + ")}";
        String[] cols = {"MaHV", "MaKH", "MaNH", "Diem"};
        return daoTK.getListOfArray(sql, cols);
    }

    public List<Object[]> selectByPageIndexNH(int pageIndex, int MaKH) {
        String sql = "{call sp_ListHocVien2(" + pageIndex + "," + MaKH + ")}";
        String[] cols = {"MaNH", "TenNH", "GioiTinh", "NgaySinh", "DienThoai", "Email", "GhiChu", "MaNV", "NgayDK"};
        return daoTK.getListOfArray(sql, cols);
    }
}
