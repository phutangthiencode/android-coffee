package com.example.phutang.coffeemanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterDaPhaChe;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bLogin;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.iHoaDonTam;
import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class dsDaPhaChe extends AppCompatActivity {
    private ListView lvDaPhaChe;
    public static GifImageView imgReload; //-----Thuộc tính public để sử dụng trong AdapterDaPhaChe
    private FloatingActionButton fabReload;
    private static List<iHoaDonTam> listBanChoGiao;
    private static AdapterDaPhaChe adapter;
    private static boolean trangThai=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ds_da_pha_che);
            Toolbar toolbar = (Toolbar) findViewById(R.id.daphache_toolbar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  quayLaiGiaoDien();
                }
            });

            //---------Nhận dữ liệu truyền vào qua bundle
            Bundle bdlTrangThai = this.getIntent().getExtras();
            if(bdlTrangThai!=null)
                trangThai = bdlTrangThai.getBoolean("trangThaiMo"); //------Lấy trạng thái được nhận vào

            this.khoiTaoDoiTuong();

            /**
             * Sự kiện click xem các món cần giao
             */
            lvDaPhaChe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int maBan = adapter.getItem(position).getMaBan();
                    String tenBan = adapter.getItem(position).getBan().getTenBan();
                    Bundle bdlChiTiet = new Bundle();
                    bdlChiTiet.putInt("maBan", maBan);
                    bdlChiTiet.putString("tenBan", tenBan);
                    Intent i = new Intent(getApplicationContext(), chiTietDaPhaChe.class);
                    i.putExtras(bdlChiTiet);
                    startActivity(i);
                    finish();
                }
            });


            fabReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callExecuteGetList(view);
                }
            });
        }catch(Exception ex){ex.printStackTrace();}
    }

    /**
     * Hàm thực hiện ánh xạ các Object với các component trên giao diện
     * Thực hiện khởi tạo và lấy danh sách các object
     */
    private void khoiTaoDoiTuong(){
        View view = (View)findViewById(R.id.content_ds_da_pha_che);
        lvDaPhaChe = (ListView)findViewById(R.id.daphache_lvBan);
        imgReload = (GifImageView)findViewById(R.id.daphache_imgReload);
        fabReload = (FloatingActionButton) findViewById(R.id.ds_daphache_btnReload);
        listBanChoGiao = new ArrayList<iHoaDonTam>();
        adapter = new AdapterDaPhaChe(listBanChoGiao, R.layout.item_daphache, getApplicationContext());
        lvDaPhaChe.setAdapter(adapter);
        registerForContextMenu(lvDaPhaChe);
        callExecuteGetList(view);
    }


    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    public void callExecuteGetList(View view){
        try {
            if (xlChung.isNetworkAvailable(getApplicationContext()))
                new layDanhSachBanDaPhaChe().execute();
            else {
                Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
                snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteGetList(view);
                    }
                });
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void layDanhSach(){
        new layDanhSachBanDaPhaChe().execute();
    }

    /**
     * Sự kiện khi nhấn vào nút quay lại trên điện thoại
     */
    @Override
    public void onBackPressed() {
        quayLaiGiaoDien();
    }

    /**
     * Hàm quay lại giao diện trước
     */
    private void quayLaiGiaoDien(){
        if(trangThai)
            this.xuLyQuayLaiTuThongBao();
        else
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    /**
     * Hàm xử lý quay lại khi người dùng click vào từ thông báo
     */
    private void xuLyQuayLaiTuThongBao(){
        iTaiKhoan tkLogin = bLogin.kiemTraDaDangNhap(getApplicationContext());
        if(tkLogin!=null)
            startActivity(bLogin.taoIntentVaoProgressLayout(getApplicationContext(), tkLogin.getTenDangNhap(), tkLogin.getMatKhau(), false));
    }

    /**
     * Hàm thực hiện gửi request lấy danh sách bàn cần giao sản phẩm
     */
    public class layDanhSachBanDaPhaChe extends AsyncTask<Void, Void, List<iHoaDonTam>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listBanChoGiao.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iHoaDonTam> doInBackground(Void... params) {
            return new bNghiepVuBan().layDanhSachBanPhucVu();
        }

        @Override
        protected void onPostExecute(List<iHoaDonTam> iHoaDonTams) {
            super.onPostExecute(iHoaDonTams);
            for (int i = 0; i < iHoaDonTams.size(); i++)
                listBanChoGiao.add(iHoaDonTams.get(i));
            adapter.notifyDataSetChanged();//-------------Cập nhật lại gridview
            imgReload.setVisibility(View.INVISIBLE);
        }
    }

}
