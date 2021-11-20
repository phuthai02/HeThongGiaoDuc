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
public class NguoiHoc {
    private String maNH;
    private String tenNh;
    private boolean gioiTinh;
    private String ngaySinh;
    private String dienThoai;
    private String email;
    private String ghiChu;
    private String  maNV;
    private String ngayDK;

    public NguoiHoc(String maNH, String tenNh, boolean gioiTinh, String ngaySinh, String dienThoai, String email, String ghiChu, String maNV, String ngayDK) {
        this.maNH = maNH;
        this.tenNh = tenNh;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.dienThoai = dienThoai;
        this.email = email;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.ngayDK = ngayDK;
    }

    public NguoiHoc() {
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getTenNh() {
        return tenNh;
    }

    public void setTenNh(String tenNh) {
        this.tenNh = tenNh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(String ngayDK) {
        this.ngayDK = ngayDK;
    }

    @Override
    public String toString() {
        return "NguoiHoc{" + "maNH=" + maNH + ", tenNh=" + tenNh + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", dienThoai=" + dienThoai + ", email=" + email + ", ghiChu=" + ghiChu + ", maNV=" + maNV + ", ngayDK=" + ngayDK + '}';
    }
    
}
