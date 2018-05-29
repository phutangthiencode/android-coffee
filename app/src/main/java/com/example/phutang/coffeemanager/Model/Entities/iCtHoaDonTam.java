package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 4/7/2018.
 */

public class iCtHoaDonTam {
    private int maCtTam;
    private int maBan;
    private int maSP;
    private long donGia;
    private int soLuong;
    private int trangThaiPhaChe;
    private String tenSanPham;
    private String hinhAnh;
    public  iCtHoaDonTam(){

    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getMaCtTam() {
        return maCtTam;
    }

    public void setMaCtTam(int maCtTam) {
        this.maCtTam = maCtTam;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThaiPhaChe() {
        return trangThaiPhaChe;
    }

    public void setTrangThaiPhaChe(int trangThaiPhaChe) {
        this.trangThaiPhaChe = trangThaiPhaChe;
    }
}
