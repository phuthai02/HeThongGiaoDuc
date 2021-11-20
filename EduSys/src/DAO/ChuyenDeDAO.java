/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.ChuyenDe;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    String SQL_insert = "insert into ChuyenDe values(?,?,?,?,?,?)";
    String SQL_Update = "update ChuyenDe set TenCD=?,HocPhi=?,ThoiLuong=?,Hinh=?,MoTa=? where MaCD=?";
    String SQL_Delete = "delete from ChuyenDe where MaCD=?";
    String SQL_SelectALl = "select * from ChuyenDe";
    String SQL_SelectID = "select * from ChuyenDe where MaCD=?";
    String SQL_SelectSize = "select cou from ChuyenDe";
    ThongKeDAO daoTK = new ThongKeDAO();

    @Override
    public void insert(ChuyenDe entity) {
        Xjdbc.update(SQL_insert, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinhAnh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        Xjdbc.update(SQL_Update, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinhAnh(), entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = new ArrayList<>();
        list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SQL_SelectALl);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new ChuyenDe(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> selectByPageIndex(int pageIndex) {
        String sql = "{call sp_ListChuyenDe(" + pageIndex + ")}";
        String[] cols = {"MaCD", "TenCD", "HocPhi", "ThoiLuong", "Hinh", "MoTa"};
        return daoTK.getListOfArray(sql, cols);
    }

}
