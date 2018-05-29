package com.example.phutang.coffeemanager.Model.Entities;

import java.util.Date;

/**
 * Created by PhuTang on 3/26/2018.
 */

public class iThanhVien {
    private int maTV;
    private String hoTV;
    private String tenTV;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String noiSinh;
    private String diaChi;
    private String soDT;
    private String email;
    private String Facebook;
    private String soCMND;
    private Date ngayCap;
    private String noiCap;
    private byte[] hinhDD;
    private String hinh;
    private Date ngayThamGia;
    private String ghiChu;

    public  iThanhVien(){

    }


    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTV() {
        return hoTV;
    }

    public void setHoTV(String hoTV) {
        this.hoTV = hoTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public byte[] getHinhDD() {
        return hinhDD;
    }

    public void setHinhDD(byte[] hinhDD) {
        this.hinhDD = hinhDD;
    }

    public Date getNgayThamGia() {
        return ngayThamGia;
    }

    public void setNgayThamGia(Date ngayThamGia) {
        this.ngayThamGia = ngayThamGia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public iThanhVien(int maTV, String hoTV, String tenTV, boolean gioiTinh, Date ngaySinh, String noiSinh, String diaChi, String soDT, String email, String facebook, String soCMND, Date ngayCap, String noiCap, byte[] hinhDD, Date ngayThamGia, String ghiChu) {
        this.maTV = maTV;
        this.hoTV = hoTV;
        this.tenTV = tenTV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
        Facebook = facebook;
        this.soCMND = soCMND;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.hinhDD = hinhDD;
        this.ngayThamGia = ngayThamGia;
        this.ghiChu = ghiChu;
    }


    public iThanhVien(int maTV, String hoTV, String tenTV, boolean gioiTinh, Date ngaySinh, String noiSinh, String diaChi, String soDT, String email, String facebook, String soCMND, Date ngayCap, String noiCap, byte[] hinhDD, String hinh, Date ngayThamGia, String ghiChu) {
        this.maTV = maTV;
        this.hoTV = hoTV;
        this.tenTV = tenTV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
        Facebook = facebook;
        this.soCMND = soCMND;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.hinhDD = hinhDD;
        this.hinh = hinh;
        this.ngayThamGia = ngayThamGia;
        this.ghiChu = ghiChu;
    }
}
