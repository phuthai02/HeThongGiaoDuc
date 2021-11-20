/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhoaHoc;
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
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer> {

    String SQL_Insert = "insert into KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao) values(?,?,?,?,?,?,?)";
    String SQL_Update = "update KhoaHoc set MaCD=?,HocPhi=?,ThoiLuong=?,NgayKG=?,GhiChu=? where MaKH=?";
    String SQL_Delete = "delete from KhoaHoc where MaKH=?";
    String SQL_SelectAll = "select * from KhoaHoc";
    String SQL_SelectID = "select * from KhoaHoc where MaKH=?";
    String SQL_SelectInCD = "select * from KhoaHoc where MaCD=?";
    String SQL_SelectTenCD = "select * from ChuyenDe";
    ThongKeDAO daoTK = new ThongKeDAO();

    @Override
    public void insert(KhoaHoc entity) {
        Xjdbc.update(SQL_Insert, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNhanVien(), entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        Xjdbc.update(SQL_Update, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaKH());

    }

    @Override
    public void delete(Integer id) {
        Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = new ArrayList<>();
        list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SQL_SelectAll);
    }

    public Map<String, String> selectTenCD() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectTenCD);
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

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new KhoaHoc(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhoaHoc> selectByCD(String MaCD) {
        return this.selectBySql(SQL_SelectInCD, MaCD);
    }

    public List<Object[]> selectByPageIndex(int pageIndex, String MaCD) {
        String sql = "{call sp_ListKhoaHoc(" + pageIndex + "," + MaCD + ")}";
        String[] cols = {"MaKH", "MaCD", "HocPhi", "ThoiLuong","NgayKG","GhiChu","MaNV","NgayTao"};
        return daoTK.getListOfArray(sql, cols);
    }

}
