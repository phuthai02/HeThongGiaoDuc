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
public class KhoaHoc {
    private String maKH;
    private String maCD;
    private double hocPhi;
    private int thoiLuong;
    private String ngayKG;
    private String ghiChu;
    private String maNhanVien;
    private String NgayTao;

    public KhoaHoc(String maKH, String maCD, double hocPhi, int thoiLuong, String ngayKG, String ghiChu, String maNhanVien, String NgayTao) {
        this.maKH = maKH;
        this.maCD = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.maNhanVien = maNhanVien;
        this.NgayTao = NgayTao;
    }

    public KhoaHoc(String maCD, double hocPhi, int thoiLuong, String ngayKG, String ghiChu, String maNhanVien, String NgayTao) {
        this.maCD = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.maNhanVien = maNhanVien;
        this.NgayTao = NgayTao;
    }

    public KhoaHoc(String maKH, String maCD, double hocPhi, int thoiLuong, String ngayKG, String ghiChu, String NgayTao) {
        this.maKH = maKH;
        this.maCD = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.NgayTao = NgayTao;
    }

 

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
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

    public String getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(String ngayKG) {
        this.ngayKG = ngayKG;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

    @Override
    public String toString() {
        return "KhoaHoc{" + "maKH=" + maKH + ", maCD=" + maCD + ", hocPhi=" + hocPhi + ", thoiLuong=" + thoiLuong + ", ngayKG=" + ngayKG + ", ghiChu=" + ghiChu + ", maNhanVien=" + maNhanVien + ", NgayTao=" + NgayTao + '}';
    }

   
    
}
