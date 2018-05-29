package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/31/2018.
 */

public class iKhuVuc {
    private int maKhuVuc;
    private String tenKhuVuc;
    private String dienGiai;
    private int dienTich;
    private int tongSucChua;
    private String ghiChu;

    public iKhuVuc(){

    }

    public int getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(int maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public String getDienGiai() {
        return dienGiai;
    }

    public void setDienGiai(String dienGiai) {
        this.dienGiai = dienGiai;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public int getTongSucChua() {
        return tongSucChua;
    }

    public void setTongSucChua(int tongSucChua) {
        this.tongSucChua = tongSucChua;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public iKhuVuc(int maKhuVuc, String tenKhuVuc, String dienGiai, int dienTich, int tongSucChua, String ghiChu) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
        this.dienGiai = dienGiai;
        this.dienTich = dienTich;
        this.tongSucChua = tongSucChua;
        this.ghiChu = ghiChu;
    }
}
