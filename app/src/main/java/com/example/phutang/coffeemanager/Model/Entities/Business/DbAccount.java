package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.phutang.coffeemanager.AppCode.Encryptor;
import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;

/**
 * Created by PhuTang on 4/8/2018.
 */

public class DbAccount extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ManageUser";

    private static final String TABLE_TAIKHOAN = " TaiKhoan ";

    private static final String COT_TENDANGNHAP = " tenDangNhap ";
    private static final String COT_MATKHAU =" matKhau ";
    private static final String COT_MATV = " maTV ";
    private static final String COT_QUYENHAN = " quyenHan ";


    public DbAccount(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String scriptCreateTable = "CREATE TABLE " + TABLE_TAIKHOAN + " ( "
                    + COT_TENDANGNHAP + " TEXT PRIMARY KEY NOT NULL, "
                    + COT_MATKHAU + " TEXT NOT NULL, "
                    + COT_MATV + " INTEGER NOT NULL, "
                    + COT_QUYENHAN + " TEXT NOT NULL ) ";

            db.execSQL(scriptCreateTable);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm thêm một tài khoản vào bảng tạm trong SQLite
     * @param tk tên tài khoản cần thêm
     */
    public boolean insertTaiKhoan(iTaiKhoan tk){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COT_TENDANGNHAP, tk.getTenDangNhap());
            values.put(COT_MATKHAU, tk.getMatKhau());
            values.put(COT_QUYENHAN, tk.getQuyenHan());
            values.put(COT_MATV, tk.getMaTV());
            long result = db.insert(TABLE_TAIKHOAN, null, values);
            db.close();
            if(result!=-1) //------Lưu thành công
                return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Hàm lấy thông tin tài khoản được lưu trong SQLite
     */
    public iTaiKhoan getInfoTaiKhoan(){
        iTaiKhoan kq = new iTaiKhoan();
        try{
            String strQuery = " select " + COT_TENDANGNHAP + ", " + COT_MATKHAU + ", " + COT_MATV + ", " + COT_QUYENHAN + " from "+ TABLE_TAIKHOAN;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(strQuery, null);

            if(cursor.moveToFirst()){ //--------nếu có tài khoản
                kq.setTenDangNhap(Encryptor.decrypt(cursor.getString(0)));
                kq.setMatKhau(Encryptor.decrypt(cursor.getString(1)));
                kq.setMaTV(Integer.parseInt(cursor.getString(2)));
                kq.setQuyenHan(Encryptor.decrypt(cursor.getString(3)));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm xóa 1 tài khoản trong database SQLite
     */
    public void deleteTaiKhoan(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_TAIKHOAN, "1=1", new String[]{}); //----Xóa tất cả dữ liệu
            db.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
