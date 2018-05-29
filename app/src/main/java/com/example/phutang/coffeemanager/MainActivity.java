package com.example.phutang.coffeemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.Business.DbAccount;
import com.example.phutang.coffeemanager.Model.Entities.Business.ServiceThongBao;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent svThongBao;
    private List<Integer> listSoLuongBan;
    private TextView lbSoBanTrong, lbSoBanChoOrder, lbSoBanDaOrDer, lbSoBanChoThanhToan, lbSoBanDaThanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            cauHinhProfileNavigation(navigationView);
            navigationView.setNavigationItemSelectedListener(this);

            svThongBao = new Intent(MainActivity.this, ServiceThongBao.class);
            this.startService(svThongBao);

            listSoLuongBan = new ArrayList<Integer>();


            FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.main_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ThongKeBan().execute();
                }
            });

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ThongKeBan().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Sự kiện click vào menu item trên menu navigation
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_ds_tiepNhanBan) {
            startActivity(new Intent(getApplicationContext(), NghiepVuBan.class));
            finish();
        } else if (id == R.id.menu_ds_daPhaChe) {
            startActivity(new Intent(getApplicationContext(), dsDaPhaChe.class));
            finish();
        } else if (id == R.id.menu_logout) {
            this.cauHinhDialogLogout();
        } else if (id == R.id.menu_main) {

        } else if(id==R.id.menu_tanca){
            //-----tắt service nhận thông báo
            cauHinhTanCa();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Hàm cấu hình profile thành viên đăng nhập trên navigation
     */
    private void cauHinhProfileNavigation(NavigationView navigationView) {
        try {
            View headerLayout = navigationView.getHeaderView(0);
            TextView lbHoTen = (TextView) headerLayout.findViewById(R.id.lb_nav_hoTen);
            TextView lbEmail = (TextView) headerLayout.findViewById(R.id.lb_nav_email);
            ImageView imgHinhDD = (ImageView) headerLayout.findViewById(R.id.img_nav_HinhDD);
            lbHoTen.setText(Session.iThanhVien.getHoTV() + " " +  Session.iThanhVien.getTenTV());
            lbEmail.setText(Session.iThanhVien.getEmail());

            Bitmap bmp = xlDuLieu.stringToImage(Session.iThanhVien.getHinh());

            int size = navigationView.getResources().getInteger(R.integer.sizeOfAvatar);

            imgHinhDD.setImageBitmap(Bitmap.createScaledBitmap(bmp, size,
                    size, false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Hàm cấu hình cho dialog logout khi click vào menu logout trên navigation
     */
    private void cauHinhDialogLogout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage("Bạn có đồng ý đăng xuất khỏi hệ thống");
        builder.setCancelable(false);
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Session.Clear();
                new DbAccount(getApplicationContext()).deleteTaiKhoan();
                startActivity(new Intent(getApplicationContext(), Login.class));
                stopService(new Intent(MainActivity.this,ServiceThongBao.class));
                finish();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cauHinhTanCa(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage("Bấm \"Đồng ý\" để tắt hết tất cả các dịch vụ chạy ngầm khi tan ca");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Session.Clear();
                stopService(new Intent(MainActivity.this,ServiceThongBao.class));
                finish();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void anhXaView(){
        lbSoBanChoOrder = (TextView)findViewById(R.id.lbSoBanChoOrder);
        lbSoBanChoThanhToan = (TextView)findViewById(R.id.lbSoBanChoThanhToan);
        lbSoBanDaOrDer = (TextView)findViewById(R.id.lbSoBanDaOrDer);
        lbSoBanDaThanhtoan = (TextView)findViewById(R.id.lbSoBanDaThanhToan);
        lbSoBanTrong = (TextView)findViewById(R.id.lbSoBanTrong);
    }

    /**
     * hàm cấu hình dữ liệu và vẽ biểu đồ thống kê bàn
     */
    private void cauHinhThongKeBan(){
        try{
            anhXaView();
            //----Lặp qua danh sách số lượng bàn:
            int index = 1; //----Vị trí để xác định loại bàn để hiển thị số lượng
            for (Integer soLuong : Session.listSoLuongBan) {
                switch (index){
                    case 1: //---Dành cho bàn trống
                        thietLapThongSoTextView(lbSoBanTrong, soLuong);
                        break;
                    case 2: //---Bàn chờ order
                        thietLapThongSoTextView(lbSoBanChoOrder, soLuong);
                        break;
                    case 3: //--Bàn đã order
                        thietLapThongSoTextView(lbSoBanDaOrDer, soLuong);
                        break;
                    case 4: //--Bàn chờ thanh toán
                        thietLapThongSoTextView(lbSoBanChoThanhToan, soLuong);
                        break;
                    case 5: //--Bàn đã thanh toán
                        thietLapThongSoTextView(lbSoBanDaThanhtoan, soLuong);
                        break;
                    default: break;
                }
                index++;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void thietLapThongSoTextView(TextView tv, Integer soLuong){
        tv.setText(String.valueOf(soLuong) + " bàn");
    }

    private class ThongKeBan extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Session.listSoLuongBan = new bNghiepVuBan().thongKeBanTheoTrangThai();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cauHinhThongKeBan();
        }
    }

}
