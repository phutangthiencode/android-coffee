package com.example.phutang.coffeemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phutang.coffeemanager.Model.Entities.Business.DbAccount;
import com.example.phutang.coffeemanager.Model.Entities.Business.bGeneral;
import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;

public class Login extends Activity {

    EditText txtUser, txtPass;
    CheckBox cbRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Cấu hình không hiện title
        setContentView(R.layout.activity_login);
        //-----------Nếu thông tin đăng nhập chưa có trong sqlite
        if(!this.layDangNhap()) {
            Button btnLogin = (Button) findViewById(R.id.btnDangNhap);
            txtPass = (EditText) findViewById(R.id.txtMatKhau);
            txtUser = (EditText) findViewById(R.id.txtTenDangNhap);
            cbRemind = (CheckBox)findViewById(R.id.login_cbRemindMe);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validateLogin())
                        new XuLyDangNhap().execute(txtUser.getText().toString(), txtPass.getText().toString());
                }
            });

            /**
             * Sự kiện click vào nút xong trên bàn phím khi nhập liệu tại txtPassword
             */
            txtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if(validateLogin())
                            new XuLyDangNhap().execute(txtUser.getText().toString(), txtPass.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    /**
     * Hàm kiểm tra dữ liệu tài khoản, mật khẩu trước khi đăng nhập
     * @return True: Hợp lệ, cho đăng nhập
     */
    private boolean validateLogin(){
        try{
            String thongBao = "";
            String user = this.txtUser.getText().toString(), pass = this.txtPass.getText().toString();
            if(user.equals("") && pass.equals(""))
                thongBao = "Vui lòng nhập tài khoản và mật khẩu đăng nhập";
            else if(user.equals(""))
                thongBao = "Vui lòng nhập tài khoản đăng nhập";
            else if(pass.equals(""))
                thongBao = "Vui lòng nhập mật khẩu đăng nhập";
            //-----Nếu đăng nhập thất bại
            if(!thongBao.equals("")){
                Toast.makeText(this, thongBao, Toast.LENGTH_LONG).show();
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Hàm xử lý vào layout chuẩn bị dữ liệu sau khi đăng nhập thành công
     * @param tenDangNhap Tên đăng nhập để xác định thành viên, quyền hạn
     * @param matKhau Mật khẩu khi có yêu cầu lưu lại mật khẩu vào SQLite
     * @param luuLai Yêu cầu lưu lại SQlite
     */
    private void vaoProgressLayout(String tenDangNhap, String matKhau, boolean luuLai){
        Bundle bdlPrepare = new Bundle();
        bdlPrepare.putString("tenDangNhap", tenDangNhap);
        bdlPrepare.putString("matKhau", matKhau);
        bdlPrepare.putBoolean("luuLai", luuLai);
        Intent prepareClass = new Intent(getApplicationContext(), PrepareData.class);
        prepareClass.putExtras(bdlPrepare);
        startActivity(prepareClass);
        finish();
    }

    /**
     * Hàm lấy thông tin đăng nhập được lưu trong SQLite
     *
     * @return
     */
    private boolean layDangNhap() {
        try {
            DbAccount dbSqlite = new DbAccount(getApplicationContext());
            iTaiKhoan tkLite = dbSqlite.getInfoTaiKhoan();
            //-----------Nếu thông tin tài khoản đã được lưu lại
            if(tkLite.getTenDangNhap()!=null){
                vaoProgressLayout(tkLite.getTenDangNhap(), "", false);
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private class XuLyDangNhap extends AsyncTask<String, Void, Integer> {

        private bGeneral services = new bGeneral();
        /**
         * Hàm xảy ra trước khi gửi request
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Đang xử lý...", Toast.LENGTH_SHORT).show();
        }

        /**
         * Hàm xảy ra khi thực hiện gửi request
         * @param params params[0]: Tên đăng nhập, [1]: Mật khẩu
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) {
            return services.Login(params[0], params[1]);
        }

        /**
         * Hàm xảy ra sau khi gửi request hoàn tất
         * @param kq 0: Đăng nhập thất bại - 1: Đăng nhập thành công (Chưa quyền hạn) 2: Đăng nhập thành công và có quyền hạn
         */
        @Override
        protected void onPostExecute(Integer kq) {
            super.onPostExecute(kq);
            if (kq > 0) //------Đăng nhập thành công. Đã nhận được quyền hạn
                vaoProgressLayout(txtUser.getText().toString(), txtPass.getText().toString(), cbRemind.isChecked());
            else {
                new DbAccount(getApplicationContext()).deleteTaiKhoan();
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
