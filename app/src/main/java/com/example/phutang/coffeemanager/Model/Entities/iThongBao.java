package com.example.phutang.coffeemanager.Model.Entities;

import java.util.Date;

/**
 * Created by PhuTang on 4/16/2018.
 */

public class iThongBao {
    private int maThongBao;
    private String ndThongBao;
    private String taiKhoan;
    private Date ngayTao;
    private boolean daXem;
    private String ghiChu;

    public int getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(int maThongBao) {
        this.maThongBao = maThongBao;
    }

    public String getNdThongBao() {
        return ndThongBao;
    }

    public void setNdThongBao(String ndThongBao) {
        this.ndThongBao = ndThongBao;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public boolean isDaXem() {
        return daXem;
    }

    public void setDaXem(boolean daXem) {
        this.daXem = daXem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public iThongBao(){}
}
