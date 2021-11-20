/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author doanp
 */
public class ChuyenDe {
    private String maCD;
    private String tenCD;
    private double hocPhi;
    private int thoiLuong;
    private String hinhAnh;
    private String moTa;

    public ChuyenDe(String maCD, String tenCD, double hocPhi, int thoiLuong, String hinhAnh, String moTa) {
        this.maCD = maCD;
        this.tenCD = tenCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
    }

    public ChuyenDe() {
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getTenCD() {
        return tenCD;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
    }

    public double getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(double hocPhi) {
        this.hocPhi = hocPhi;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "ChuyenDe{" + "maCD=" + maCD + ", tenCD=" + tenCD + ", hocPhi=" + hocPhi + ", thoiLuong=" + thoiLuong + ", hinhAnh=" + hinhAnh + ", moTa=" + moTa + '}';
    }
    
    
}
