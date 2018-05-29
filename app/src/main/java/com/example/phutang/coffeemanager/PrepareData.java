package com.example.phutang.coffeemanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phutang.coffeemanager.AppCode.Encryptor;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.DbAccount;
import com.example.phutang.coffeemanager.Model.Entities.Business.bGeneral;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;

public class PrepareData extends AppCompatActivity {

    public ProgressBar progressBar;
    String tenDangNhap="", matKhau="";
    boolean luuLai =false; //-----Biến nhận yêu cầu lưu lại thông tin trong SQLite
    private Button btnThuLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_prepare_data);
            progressBar = (ProgressBar) findViewById(R.id.prepare_progressBar);
            //------Cài đặt màu sắc của progress bar
            progressBar.getProgressDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
            btnThuLai = (Button)findViewById(R.id.prepare_btnThuLai);

            Bundle bdlGetData = this.getIntent().getExtras();
            tenDangNhap = bdlGetData.getString("tenDangNhap");
            if (tenDangNhap != null) {
                matKhau = bdlGetData.getString("matKhau");
                luuLai = bdlGetData.getBoolean("luuLai");
                getData();
                /**
                 * Sự kiện click vào nút thử lại trên màn hình sẽ lấy lại dữ liệu
                 */
                btnThuLai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });

            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Hàm gọi class thực thi lấy dữ liệu
     */
    private void getData(){
        //--------Có kết nối internet
        if (xlChung.isNetworkAvailable(getApplicationContext())) {
            btnThuLai.setVisibility(View.INVISIBLE);
            new ChuanBiDuLieu().execute(tenDangNhap);
        }
        else{
            btnThuLai.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra lại kết nối Internet để tiếp tục sử dụng ứng dụng", Toast.LENGTH_LONG).show();
        }
    }



    /**
     * Hàm thiết lập lại giá trị cho progress bar sau khi xong mỗi bước
     * @param value
     */
    private void configValueProgressBar(final Integer value){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                progressBar.setProgress(value);
            }
        };
        thread.start();
    }
    /**
     * Hàm lưu thông tin đăng nhập vào CSDL Sqlite
     * @param taiKhoan Tên tài khoản đăng nhập
     * @param matKhau Mật khẩu đăng nhập
     */
    private void storeUserLogin(String taiKhoan, String matKhau, boolean luuLai){
        if(luuLai) {
            DbAccount dbSqlite = new DbAccount(getApplicationContext());
            dbSqlite.deleteTaiKhoan();
            iTaiKhoan accountSave = new iTaiKhoan();
            accountSave.setTenDangNhap(Encryptor.encrypt(taiKhoan.toString()));
            accountSave.setMatKhau(Encryptor.encrypt(matKhau.toString()));
            accountSave.setMaTV(Session.iTaiKhoan.getMaTV());
            accountSave.setQuyenHan(Encryptor.encrypt(Session.iTaiKhoan.getQuyenHan()));
            dbSqlite.insertTaiKhoan(accountSave);
        }
    }


    private class ChuanBiDuLieu extends AsyncTask<String, Void, Integer> {

        /**
         *
         * @param params params[0] tên tài khoản
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) {
            int kq=0;
            try{
                bGeneral services = new bGeneral();
                configValueProgressBar(0);

                //-------Gán thông tin tài khoản vào biến tạm
                Session.iTaiKhoan = services.getInforTaiKhoan(params[0]);
                configValueProgressBar(20);
                Session.iThanhVien = services.getInforThanhVien(Session.iTaiKhoan.getMaTV());
                configValueProgressBar(40);
                //-------------Nếu quyền hạn dành cho nghiệp vụ bàn và phục vụ
                if(Session.iTaiKhoan.getQuyenHan().contains("501") && Session.iTaiKhoan.getQuyenHan().contains("602")) {
                    bNghiepVuBan servicesNghiepVu = new bNghiepVuBan();
                    Session.listKhuVuc = servicesNghiepVu.layDanhSachKhuVuc();
                    configValueProgressBar(60);
                    Session.listLoaiSanPham = servicesNghiepVu.layDanhSachLoaiSP();
                    configValueProgressBar(80);
                    Session.listSoLuongBan = servicesNghiepVu.thongKeBanTheoTrangThai();
                    kq=1;//-----Thành công với vài trò người phục vụ
                }else if(Session.iTaiKhoan.getQuyenHan().contains("803") && Session.iTaiKhoan.getQuyenHan().contains("804")){
                    //------Quyền hạn dành cho nhân viên kiểm kho
                    kq=2;
                }
            }catch(Exception ex) {

            }
            return kq;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            try {
                if (integer == 1) { //--------Danh cho nhom tai khoan nguoi phuc vu
                    //-----Lưu lại tài khoản và databse sqlLite
                    storeUserLogin(tenDangNhap, matKhau, luuLai);
                    configValueProgressBar(100);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else if (integer == 2) {
                    //-----Lưu lại tài khoản và databse sqlLite
                    storeUserLogin(tenDangNhap, matKhau, luuLai);
                    configValueProgressBar(100);
                    startActivity(new Intent(getApplicationContext(), MainKiemKho.class));
                    finish();
                } else {//--------Nếu đăng nhập thất bại thì xóa tài khoản được lưu trên thiết bị
                    new DbAccount(getApplicationContext()).deleteTaiKhoan();
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }catch(Exception ex){

            }
        }
    }
}
