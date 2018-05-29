package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/23/2018.
 */

public class iBanChoNgoi {
    private int maBan;
    private String tenBan;
    private int maKhuVuc;
    private int sucChua;
    private String hinhAnh;
    private String gioiThieu;
    private int trangThai;
    private String ghiChu;

    public iBanChoNgoi(){

    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(int maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public iBanChoNgoi(int maBan, String tenBan, int maKhuVuc, int sucChua, String hinhAnh, String gioiThieu, int trangThai, String ghiChu) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.maKhuVuc = maKhuVuc;
        this.sucChua = sucChua;
        this.hinhAnh = hinhAnh;
        this.gioiThieu = gioiThieu;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }
}
