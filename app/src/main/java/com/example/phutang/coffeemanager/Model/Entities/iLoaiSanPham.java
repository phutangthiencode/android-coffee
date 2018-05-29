package com.example.phutang.coffeemanager.Model.Entities;

/**
 * Created by PhuTang on 3/31/2018.
 */

public class iLoaiSanPham {
    private int maLoai;
    private String tenLoai;
    private String dienGiai;
    private String ghiChu;


    public iLoaiSanPham(){

    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getDienGiai() {
        return dienGiai;
    }

    public void setDienGiai(String dienGiai) {
        this.dienGiai = dienGiai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public iLoaiSanPham(int maLoai, String tenLoai, String dienGiai, String ghiChu) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.dienGiai = dienGiai;
        this.ghiChu = ghiChu;
    }
}
