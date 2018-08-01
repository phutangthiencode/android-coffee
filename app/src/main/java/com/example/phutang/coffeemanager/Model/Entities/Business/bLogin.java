package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;
import com.example.phutang.coffeemanager.PrepareData;

/**
 * Created by PhuTang on 8/1/2018.
 */

public class bLogin {
    /**
     * Hàm lấy thông tin đăng nhập được lưu trong SQLite
     * @return Thông tin tài khoản được lưu trữ
     */
    public static iTaiKhoan kiemTraDaDangNhap(Context context) {
        iTaiKhoan tkLite = new iTaiKhoan();
        try {
            DbAccount dbSqlite = new DbAccount(context);
            tkLite = dbSqlite.getInfoTaiKhoan();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tkLite;
    }

    /**
     * Hàm xử lý vào layout chuẩn bị dữ liệu sau khi đăng nhập thành công
     * @param context Context giao điện
     * @param tenDangNhap Tên đăng nhập để xác định thành viên, quyền hạn
     * @param matKhau Mật khẩu khi có yêu cầu lưu lại mật khẩu vào SQLite
     * @param luuLai Yêu cầu lưu lại SQlite
     */
    public static  Intent taoIntentVaoProgressLayout(Context context, String tenDangNhap, String matKhau, boolean luuLai){
        Bundle bdlPrepare = new Bundle();
        bdlPrepare.putString("tenDangNhap", tenDangNhap);
        bdlPrepare.putString("matKhau", matKhau);
        bdlPrepare.putBoolean("luuLai", luuLai);
        Intent prepareClass = new Intent(context, PrepareData.class);
        prepareClass.putExtras(bdlPrepare);
        return prepareClass;
    }


}
