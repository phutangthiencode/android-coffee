package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterGridBan;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by PhuTang on 3/31/2018.
 * Class thực hiện xử lý nghiệp vụ lấy danh sách bàn từ Webservices
 */

public class bBan {

    private List<iBanChoNgoi> listBan;
    private AdapterGridBan adapter;
    private GifImageView imgReload;

    public List<iBanChoNgoi> getListBan() {
        return listBan;
    }

    public void setListBan(List<iBanChoNgoi> listBan) {
        this.listBan = listBan;
    }

    public AdapterGridBan getAdapter() {
        return adapter;
    }

    public void setAdapter(AdapterGridBan adapter) {
        this.adapter = adapter;
    }

    public GifImageView getImgReload() {
        return imgReload;
    }

    public void setImgReload(GifImageView imgReload) {
        this.imgReload = imgReload;
    }

    public bBan(List<iBanChoNgoi> listBan, AdapterGridBan adapter, GifImageView imgReload) {
        this.listBan = new ArrayList<iBanChoNgoi>();
        this.adapter = new AdapterGridBan(listBan, 0, null);
        this.imgReload = new GifImageView(null);

        this.listBan = listBan;
        this.adapter = adapter;
        this.imgReload = imgReload;
    }

    public bBan(){


    }

    public void layDanhSachSauKhiChonItem(final int maKV, final int trangThai){
        try {
            new loadDanhSachBan().execute(maKV, trangThai);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layDanhSachSauKhiChonItem(maKV, trangThai);
                }
            },30000); //-----Ngừng 30s
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Class thực thi lấy danh sách bàn chờ order trên server qua webservice
     */
    private class loadDanhSachBan extends AsyncTask<Integer, Void, List<iBanChoNgoi>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listBan.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iBanChoNgoi> doInBackground(Integer... params) {
            //-------Lấy danh sách bàn chờ order
            return new bNghiepVuBan().layDanhSachTheoTrangThai(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(List<iBanChoNgoi> iBanChoNgois) {
            try {
                super.onPostExecute(iBanChoNgois);
                for (int i = 0; i < iBanChoNgois.size(); i++)
                    listBan.add(iBanChoNgois.get(i));
                adapter.notifyDataSetChanged();//-------------Cập nhật lại gridview
                imgReload.setVisibility(View.INVISIBLE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



}
