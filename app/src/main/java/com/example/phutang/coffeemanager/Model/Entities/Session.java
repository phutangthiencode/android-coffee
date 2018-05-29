package com.example.phutang.coffeemanager.Model.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhuTang on 3/26/2018.
 */

public class Session {
    public static iTaiKhoan iTaiKhoan;
    public static iThanhVien iThanhVien;

    public static List<Integer> listSoLuongBan;

    public static List<iKhuVuc> listKhuVuc;
    public static List<iLoaiSanPham> listLoaiSanPham;

    public static List<iGioHang> listGioHang;
    public static List<iCtTonKho> listGioKiemKho;

    public Session(){

    }

    /**
     * Hàm xóa bỏ dữ liệu tạm
     */
    public static void Clear(){
        iTaiKhoan = new iTaiKhoan();
        iThanhVien = new iThanhVien();
        listSoLuongBan  = new ArrayList<Integer>();
        listKhuVuc = new ArrayList<iKhuVuc>();
        listLoaiSanPham = new ArrayList<iLoaiSanPham>();
        listGioHang = new ArrayList<iGioHang>();
    }
}
