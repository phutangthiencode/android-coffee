package com.example.phutang.coffeemanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.phutang.coffeemanager.AdapterGrid.AdpaterNguyenLieuKiemKho;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bKiemKho;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class dsNguyenLieuKiemKho extends AppCompatActivity {

    private ListView listView;
    public static AdpaterNguyenLieuKiemKho adapter;
    private List<iCtTonKho> list;
    private GifImageView imgReload;
    public static FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_nguyen_lieu_kiem_kho);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nguyenlieukiem_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainKiemKho.class));
                finish();
            }
        });

        khoiTaoDoiTuong();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoiChieuSoLuong.class));
                finish();
            }
        });
    }

    private void khoiTaoDoiTuong(){
        View view = (View)findViewById(R.id.content_ds_nguyen_lieu_kiem_kho);
        listView = (ListView)findViewById(R.id.nguyenlieukiem_listview);
        imgReload = (GifImageView)findViewById(R.id.nguyenlieukiem_imgreload);
        fab = (FloatingActionButton) findViewById(R.id.nguyenlieukiem_fab);

        Session.listGioKiemKho = new ArrayList<iCtTonKho>();
        list = new ArrayList<>();
        adapter = new AdpaterNguyenLieuKiemKho(list,R.layout.item_nguyenlieu_kiemkho, this);
        listView.setAdapter(adapter);
        imgReload.setVisibility(View.VISIBLE);
        callExecuteGetList(view);
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getApplicationContext()))
            new LayDanhSach().execute();
        else{
            Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
            snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callExecuteGetList(view);
                }
            });
        }
    }

    /**
     * Sự kiện quay về layout trước
     * Quay về giao diện chính
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainKiemKho.class));
        finish();
    }

    /**
     * Class thực hiện lấy danh sách các nguyên liệu cần kiểm trên Server
     */
    private class LayDanhSach extends AsyncTask<Void, Void, List<iCtTonKho>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list.clear();
        }

        @Override
        protected List<iCtTonKho> doInBackground(Void... params) {
            return new bKiemKho().laySoLuongTonKhoLyThuyet();
        }

        @Override
        protected void onPostExecute(List<iCtTonKho> iCtTonKhos) {
            super.onPostExecute(iCtTonKhos);
            for(int i=0; i<iCtTonKhos.size(); i++)
                list.add(iCtTonKhos.get(i));
            adapter.notifyDataSetChanged();//------Cập nhật lại adapter
            imgReload.setVisibility(View.INVISIBLE);
        }
    }

}
