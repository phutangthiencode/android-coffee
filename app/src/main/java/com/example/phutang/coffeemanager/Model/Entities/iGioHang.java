package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/24/2018.
 */

public class iGioHang {
    private int maSP;
    private int soLuong;
    private long donGia;
    private int trangThaiPhaChe;
    private iSanPham sanPham;

    public int getTrangThaiPhaChe() {
        return trangThaiPhaChe;
    }

    public void setTrangThaiPhaChe(int trangThaiPhaChe) {
        this.trangThaiPhaChe = trangThaiPhaChe;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public iSanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(iSanPham sanPham) {
        this.sanPham = sanPham;
    }

    public  iGioHang(){

    }
}
