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

import com.example.phutang.coffeemanager.AdapterGrid.AdapterDoiChieu;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bKiemKho;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNhapSoLuong;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;

import pl.droidsonroids.gif.GifImageView;

public class DoiChieuSoLuong extends AppCompatActivity {

    private ListView listView;
    private GifImageView imgReload;

    public static AdapterDoiChieu adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            if(Session.listGioKiemKho.size()>0) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_doi_chieu_so_luong);
                Toolbar toolbar = (Toolbar) findViewById(R.id.doichieu_toolbar);
                setSupportActionBar(toolbar);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), dsNguyenLieuKiemKho.class));
                        finish();
                    }
                });
                //--------Cấu hình và hiển thị danh sách nguyên liệu đã kiểm kê
                imgReload = (GifImageView) findViewById(R.id.doichieu_imgReload);
                listView = (ListView) findViewById(R.id.doichieu_lvListCart);
                adapter = new AdapterDoiChieu(Session.listGioKiemKho, R.layout.item_doichieu, this);
                listView.setAdapter(adapter);

                //------Sự kiện nhập lại số lượng kiểm kho
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            iCtTonKho itemSelected = Session.listGioKiemKho.get(position);
                            new bNhapSoLuong(Session.listGioKiemKho, DoiChieuSoLuong.this, 2).configDialog(itemSelected, position);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                });

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.doichieu_fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteHoanTat(view);
                    }
                });
            }
            else{
                startActivity(new Intent(getApplicationContext(), dsNguyenLieuKiemKho.class));
                finish();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private void callExecuteHoanTat(View view){
        try{
            if(xlChung.isNetworkAvailable(getApplicationContext()))
                new HoanTatKiemKho().execute();
            else{
                Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
                snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteHoanTat(view);
                    }
                });
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }





    private class HoanTatKiemKho extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            bKiemKho bService = new bKiemKho();
            int kqTon = bService.themMoiTonKho(Session.iTaiKhoan.getTenDangNhap());
            if(kqTon>0){
                //------Lặp qua danh sách sản phẩm đã kiểm trong cart
                for(iCtTonKho ct : Session.listGioKiemKho){
                    ct.setMaSoKy(kqTon);
                    bService.themChiTietTonKho(ct);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            imgReload.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getApplicationContext(), MainKiemKho.class));
        }
    }
    

}
