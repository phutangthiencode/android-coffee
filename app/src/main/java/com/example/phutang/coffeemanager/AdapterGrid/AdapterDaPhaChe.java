package com.example.phutang.coffeemanager.AdapterGrid;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bPhucVu;
import com.example.phutang.coffeemanager.Model.Entities.iHoaDonTam;
import com.example.phutang.coffeemanager.R;
import com.example.phutang.coffeemanager.dsDaPhaChe;

import java.util.List;

/**
 * Created by PhuTang on 3/24/2018.
 */

public class AdapterDaPhaChe extends BaseAdapter{
    private List<iHoaDonTam> list;
    private int layoutInflater;
    private Context context;

    public AdapterDaPhaChe(List<iHoaDonTam> list, int layoutInflater, Context context) {
        this.list = list;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public iHoaDonTam getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = inflater.inflate(layoutInflater, null);
                viewHolder.lbTenBan = (TextView) convertView.findViewById(R.id.item_daphache_lbTenBan);
                viewHolder.lbSoLuong = (TextView) convertView.findViewById(R.id.item_daphache_lbSoLuong);
                viewHolder.lbListSP = (TextView) convertView.findViewById(R.id.item_daphache_lbdsSanPham);
                viewHolder.btnGiao = (Button)convertView.findViewById(R.id.item_daphache_btnDaGiao);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            final iHoaDonTam hoaDon = this.list.get(position);
            viewHolder.lbTenBan.setText(hoaDon.getBan().getTenBan());
            viewHolder.lbSoLuong.setText(String.valueOf(hoaDon.getSoLuongSanPham()) + " sản phẩm");
            viewHolder.lbListSP.setText(hoaDon.getDienGiaiChiTiet());

            /**
             * Sự kiện cho nút giao từng bàn trên danh sách
             */
            final View finalConvertView = convertView;
            viewHolder.btnGiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callExecuteGiaoMonTaiBan(hoaDon.getMaBan(), finalConvertView);
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    private void callExecuteGiaoMonTaiBan(final int maHoaDon, View view){
        try {
            if (xlChung.isNetworkAvailable(context)) {
                new bPhucVu(dsDaPhaChe.imgReload).execute(maHoaDon);
                new dsDaPhaChe().layDanhSach();
            } else {
                Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
                snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteGiaoMonTaiBan(maHoaDon, view);
                    }
                });
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private class ViewHolder{
        TextView lbTenBan, lbSoLuong, lbListSP;
        Button btnGiao;
    }
}


