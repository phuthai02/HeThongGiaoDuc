/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class ThongKeDAO {

    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();;
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getBangDiem(int maKH) {
        String sql = "{call sp_BangDiem(" + maKH + ")}";
        String[] cols = {"MaNH", "TenNH", "Diem"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getLuongNguoiHoc() {
        String sql = "{call sp_LuongNguoiHoc}";
        String[] cols = {"Nam", "SoLuong", "DauTien", "CuoiCung"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDiemChuyenDe() {
        String sql = "{call sp_DiemChuyenDe}";
        String[] cols = {"chuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int year) {
        String sql = "{call sp_DoanhThu(" + year + ")}";
        String[] cols = {"ChuyenDe", "SoKH", "SoHV", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols);
    }

    public Map<Integer, Integer> selectNam() {
        String SQL_SelectNam = "SELECT  DISTINCT YEAR(NgayKG) FROM dbo.KhoaHoc ORDER BY YEAR(NgayKG) DESC";
        Map<Integer, Integer> mapNam = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectNam);
            int count = 0;
            while (rs.next()) {
                mapNam.putIfAbsent(count, rs.getInt(1));;
                count++;
            }
            return mapNam;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
