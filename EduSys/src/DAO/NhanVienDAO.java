/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NhanVien;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    String SQL_insert = "insert into NhanVien values(?,?,?,?)";
    String SQL_Update = "update NhanVien set HoTen=?,MatKhau=?, VaiTro=? where MaNV=?";
    String SQL_Delete = "delete from NhanVien where MaNV=?    ";
    String SQL_SelectALl = "select * from NhanVien";
    String SQL_SelectID = "select * from NhanVien where MaNV=?";
    ThongKeDAO daoTK = new ThongKeDAO();
    @Override
    public void insert(NhanVien entity) {
        Xjdbc.update(SQL_insert, entity.getMaNv(), entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        Xjdbc.update(SQL_Update, entity.getHoTen(), entity.getMatKhau(), entity.isVaiTro(), entity.getMaNv());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SQL_SelectALl);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), true));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> selectByPageIndex(int pageIndex) {
        String sql = "{call sp_ListNhanVien(" + pageIndex + ")}";
        String[] cols = {"MaNV", "MatKhau", "HoTen", "VaiTro"};
        return daoTK.getListOfArray(sql, cols);
    }

}
