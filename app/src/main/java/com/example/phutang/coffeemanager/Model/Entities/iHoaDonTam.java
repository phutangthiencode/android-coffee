package com.example.phutang.coffeemanager.Model.Entities;

import java.util.Date;

/**
 * Created by PhuTang on 4/7/2018.
 */

public class iHoaDonTam {
    private int maBan;
    private int trangThaiHoaDon;
    private int trangThaiPhucVu;
    private Date ngayLap;
    private Date thoiDiemDen;
    private String nguoiPhucVu;
    private long tongTien;
    private String ghiChu;
    private iBanChoNgoi ban;
    private int soLuongSanPham;
    private String dienGiaiChiTiet;

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }

    public String getDienGiaiChiTiet() {
        return dienGiaiChiTiet;
    }

    public void setDienGiaiChiTiet(String dienGiaiChiTiet) {
        this.dienGiaiChiTiet = dienGiaiChiTiet;
    }

    public iBanChoNgoi getBan() {
        return ban;
    }

    public void setBan(iBanChoNgoi ban) {
        this.ban = ban;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getTrangThaiHoaDon() {
        return trangThaiHoaDon;
    }

    public void setTrangThaiHoaDon(int trangThaiHoaDon) {
        this.trangThaiHoaDon = trangThaiHoaDon;
    }

    public int getTrangThaiPhucVu() {
        return trangThaiPhucVu;
    }

    public void setTrangThaiPhucVu(int trangThaiPhucVu) {
        this.trangThaiPhucVu = trangThaiPhucVu;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Date getThoiDiemDen() {
        return thoiDiemDen;
    }

    public void setThoiDiemDen(Date thoiDiemDen) {
        this.thoiDiemDen = thoiDiemDen;
    }

    public String getNguoiPhucVu() {
        return nguoiPhucVu;
    }

    public void setNguoiPhucVu(String nguoiPhucVu) {
        this.nguoiPhucVu = nguoiPhucVu;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


    public iHoaDonTam(int maBan, int trangThaiHoaDon, int trangThaiPhucVu, Date ngayLap, Date thoiDiemDen, String nguoiPhucVu, long tongTien, String ghiChu, iBanChoNgoi ban) {
        this.maBan = maBan;
        this.trangThaiHoaDon = trangThaiHoaDon;
        this.trangThaiPhucVu = trangThaiPhucVu;
        this.ngayLap = ngayLap;
        this.thoiDiemDen = thoiDiemDen;
        this.nguoiPhucVu = nguoiPhucVu;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.ban = ban;
    }

    public iHoaDonTam(){

    }
}
