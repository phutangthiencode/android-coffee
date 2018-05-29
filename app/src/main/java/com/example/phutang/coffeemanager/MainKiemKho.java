package com.example.phutang.coffeemanager;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.Business.DbAccount;
import com.example.phutang.coffeemanager.Model.Entities.Business.bKiemKho;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iThongBao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainKiemKho extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationCompat.Builder notifyBuilder;
    private static final int MY_REQUEST_CODE = 2;

    private Date startDate , endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_kiem_kho);
            Toolbar toolbar = (Toolbar) findViewById(R.id.mainkiemkho_toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.kiemkho_drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.kiemkho_nav_view);
            cauHinhProfileNavigation(navigationView);
            navigationView.setNavigationItemSelectedListener(this);

            if(xlChung.isNetworkAvailable(getApplicationContext()))
                new layThongBao().execute();


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.kiemkho_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.kiemkho_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.menu_kiemkho_kiemkho){
            startActivity(new Intent(this, dsNguyenLieuKiemKho.class));
            finish();
        }else if(id==R.id.menu_kiemkho_logout){
            this.cauHinhDialogLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.kiemkho_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * Hàm cấu hình profile thành viên đăng nhập trên navigation
     */
    private void cauHinhProfileNavigation(NavigationView navigationView) {
        try {
            View headerLayout = navigationView.getHeaderView(0);
            TextView lbHoTen = (TextView) headerLayout.findViewById(R.id.kiemkho_lb_nav_hoTen);
            TextView lbEmail = (TextView) headerLayout.findViewById(R.id.kiemkho_lb_nav_email);
            ImageView imgHinhDD = (ImageView) headerLayout.findViewById(R.id.kiemkho_img_nav_HinhDD);
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

    //region thông báo kiểm kho
    private class layThongBao extends AsyncTask<Object, Object, List<iThongBao>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<iThongBao> doInBackground(Object... params) {
            List<iThongBao> kq = new ArrayList<iThongBao>();
            try {
                kq = new bKiemKho().getListThongBaoKiemKho();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return kq;
        }

        @Override
        protected void onPostExecute(List<iThongBao> list) {
            super.onPostExecute(list);
            try {
                for (int i = 0; i < list.size(); i++)
                    taoThongBaoKiemKho(list.get(i).getMaThongBao(), list.get(i).getNdThongBao());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }




    /**
     * hàm tạo thông báo nhắc nhở kiểm kê kho hàng khi đăng nhập vào ứng dụng
     */
    private void taoThongBaoKiemKho(int maThongBao, String ndThongBao){
        try{
            //--Cấu hình cho thông báo
            this.notifyBuilder = new NotificationCompat.Builder(this);
            this.notifyBuilder.setAutoCancel(true);
            this.notifyBuilder.setSmallIcon(R.mipmap.icon_app);
            this.notifyBuilder.setWhen(System.currentTimeMillis() + 10 * 1000);
            this.notifyBuilder.setContentTitle("Quản lý cà phê");

            this.notifyBuilder.setContentText(ndThongBao);
            //-----------Tạo intent khi người dùng click vào thông báo sẽ khởi động lại ứng dụng
            Intent intent = new Intent(this, Login.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            this.notifyBuilder.setContentIntent(pendingIntent);
            // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
            NotificationManager notificationService =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            // Xây dựng thông báo và gửi nó lên hệ thống.
            Notification notification = notifyBuilder.build();
            notificationService.notify(maThongBao, notification);

            Uri soundNotify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), soundNotify);
            r.play();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //endregion


}
