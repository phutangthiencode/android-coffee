package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.os.AsyncTask;
import android.view.View;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by PhuTang on 4/14/2018.
 */

public class bPhucVu extends AsyncTask<Integer, Void, Integer> {

    public GifImageView imgReload;

    public  bPhucVu(){}

    public bPhucVu(GifImageView imgReload) {
        this.imgReload = imgReload;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.imgReload.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        return new bNghiepVuBan().capNhatDaGiao(params[0]);
    }

    @Override
    protected void onPostExecute(Integer kq) {
        super.onPostExecute(kq);
        if(kq>0)
            this.imgReload.setVisibility(View.INVISIBLE);
    }
}
