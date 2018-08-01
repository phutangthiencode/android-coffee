package com.example.phutang.coffeemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterChiTietDaPhaChe;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Business.bPhucVu;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class chiTietDaPhaChe extends AppCompatActivity {

    private List<iGioHang> listSanPham;
    private AdapterChiTietDaPhaChe adapter;

    private ListView lvChiTiet;
    private GifImageView imgReload;
    private FloatingActionButton fabGiao;

    private int maBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chi_tiet_da_pha_che);
            Toolbar toolbar = (Toolbar) findViewById(R.id.ct_daphache_toolbar);
            setSupportActionBar(toolbar);


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoBackLayout();
                }
            });

            //---------Nhận dữ liệu truyền vào qua bundle
            Bundle bldBundle = this.getIntent().getExtras();
            maBan = bldBundle.getInt("maBan");
            String tenBan = bldBundle.getString("tenBan");

            if (maBan > 0) {
                chuanBiDuLieu();
                getSupportActionBar().setTitle("Phục vụ cho \""+tenBan+"\"");
                fabGiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteGiaoMon(view);
                    }
                });
            } else
                gotoBackLayout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callExecuteGiaoMon(View view){
        try{
            if(xlChung.isNetworkAvailable(getApplicationContext())) {
                new bPhucVu(imgReload).execute(maBan);
                startActivity(new Intent(getApplicationContext(), dsDaPhaChe.class));
                finish();
            }
            else{
                Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
                snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteGiaoMon(view);
                    }
                });
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm chuẩn bị đối tượng và dữ liệu để hiển thị lên giao diện
     */
    private void chuanBiDuLieu() {
        lvChiTiet = (ListView) findViewById(R.id.ctdpc_lvChiTiet);
        imgReload = (GifImageView) findViewById(R.id.ctdpc_imgReload);
        this.listSanPham = new ArrayList<iGioHang>();
        fabGiao = (FloatingActionButton) findViewById(R.id.ctdpc_btnGiao);
        adapter = new AdapterChiTietDaPhaChe(this.listSanPham, R.layout.item_chitiet_daphache, this);
        this.lvChiTiet.setAdapter(adapter);
        new layDanhSachChiTiet().execute(maBan);
    }

    /**
     * Hàm cấu hình trở về giao diện trước
     * Giao diện dsDaPhaChe
     */
    private void gotoBackLayout() {
        startActivity(new Intent(getApplicationContext(), dsDaPhaChe.class));
        finish();
    }

    /**
     * Sự kiện khi nhấn vào nút quay lại trên điện thoại
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.gotoBackLayout();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("chiTietDaPhaChe Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * Class xử lý gửi request lên server để lấy danh sách chi tiết sản phẩm
     */
    private class layDanhSachChiTiet extends AsyncTask<Integer, Void, List<iGioHang>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listSanPham.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iGioHang> doInBackground(Integer... params) {
            return new bNghiepVuBan().layDanhSachSanPhamCanGiao(params[0]);
        }

        @Override
        protected void onPostExecute(List<iGioHang> iGioHangs) {
            super.onPostExecute(iGioHangs);
            for (int i = 0; i < iGioHangs.size(); i++)
                listSanPham.add(iGioHangs.get(i));
            adapter.notifyDataSetChanged();//-------------Cập nhật lại gridview
            imgReload.setVisibility(View.INVISIBLE);
        }
    }
}
