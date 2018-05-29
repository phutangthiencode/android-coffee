package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/24/2018.
 */

public class iSanPham {


    public iSanPham(){

    }

    private int maSanPham;
    private String tenSanPham;
    private int maLoai;
    private String moTa;
    private long donGia;
    private String hinhAnh;
    private int thoiGianPhaChe;
    private int trangThai;
    private String ghiChu;

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getThoiGianPhaChe() {
        return thoiGianPhaChe;
    }

    public void setThoiGianPhaChe(int thoiGianPhaChe) {
        this.thoiGianPhaChe = thoiGianPhaChe;
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

    public iSanPham(int maSanPham, String tenSanPham, int maLoai, String moTa, long donGia, String hinhAnh, int thoiGianPhaChe, int trangThai, String ghiChu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.maLoai = maLoai;
        this.moTa = moTa;
        this.donGia = donGia;
        this.hinhAnh = hinhAnh;
        this.thoiGianPhaChe = thoiGianPhaChe;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }
}
