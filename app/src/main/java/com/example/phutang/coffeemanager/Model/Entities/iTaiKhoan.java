package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/26/2018.
 */

public class iTaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private int maTV;
    private int maNhomTK;
    private boolean trangThai;
    private String ghiChu;
    private String quyenHan;

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public  iTaiKhoan(){

    }


    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaNhomTK() {
        return maNhomTK;
    }

    public void setMaNhomTK(int maNhomTK) {
        this.maNhomTK = maNhomTK;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
