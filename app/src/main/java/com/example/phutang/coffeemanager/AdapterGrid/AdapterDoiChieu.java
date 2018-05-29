package com.example.phutang.coffeemanager.AdapterGrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Created by PhuTang on 4/23/2018.
 */

public class AdapterDoiChieu extends BaseAdapter {
    private List<iCtTonKho> list;
    private int layoutInflater;
    private Context context;

    public AdapterDoiChieu(List<iCtTonKho> list, int layoutInflater, Context context) {
        this.list = list;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public iCtTonKho getItem(int position) {
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
                viewHolder.imgHinhNguyenLieu = (ImageView)convertView.findViewById(R.id.item_doichieu_imgHinhNguyenLieu);
                viewHolder.lbTenNguyenLieu = (TextView) convertView.findViewById(R.id.item_doichieu_lbTenNguyenLieu);
                viewHolder.lbSoLuongDauKy = (TextView) convertView.findViewById(R.id.item_doichieu_lbDauKy);
                viewHolder.lbSoLuongCuoiKy = (TextView) convertView.findViewById(R.id.item_doichieu_lbCuoiKy);
                viewHolder.lbSoLuongLyThuyet = (TextView) convertView.findViewById(R.id.item_doichieu_lbLyThuyet);
                viewHolder.lbTyLeHaoHut = (TextView) convertView.findViewById(R.id.item_doichieu_lbTyLeHaoHut);
                viewHolder.lbNguyenNhan = (TextView) convertView.findViewById(R.id.item_doichieu_lbNguyenNhan);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            iCtTonKho chiTiet = this.list.get(position);
            //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
            int sizeImage = convertView.getResources().getInteger(R.integer.sizeImage_item_listview_large);
            Bitmap bitMap = xlDuLieu.stringToImage(chiTiet.getHinhNguyenLieu());
            viewHolder.imgHinhNguyenLieu.setImageBitmap(Bitmap.createScaledBitmap(bitMap, sizeImage, sizeImage, false));
            viewHolder.lbTenNguyenLieu.setText(chiTiet.getTenNguyenLieu());
            if(!chiTiet.getNguyenNhanHaoHut().equals(""))
                viewHolder.lbNguyenNhan.setText("Nguyên nhân: " + chiTiet.getNguyenNhanHaoHut());
            viewHolder.lbTyLeHaoHut.setText("Hao hụt: " + String.valueOf(chiTiet.getTyleHaoHut()) + "%");
            viewHolder.lbSoLuongLyThuyet.setText("Lý thuyết: " + chiTiet.getSoLuongCuoiKyLyThuyet() + " " + chiTiet.getDonViPhaChe());
            viewHolder.lbSoLuongCuoiKy.setText("Cuối kỳ: " + chiTiet.getSoLuongThucTe() + " " + chiTiet.getDonViPhaChe());
            viewHolder.lbSoLuongDauKy.setText("Đầu kỳ: " + chiTiet.getSoLuongDauKy() + " " + chiTiet.getDonViPhaChe());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView imgHinhNguyenLieu;
        TextView lbTenNguyenLieu, lbSoLuongDauKy, lbSoLuongCuoiKy, lbSoLuongLyThuyet, lbTyLeHaoHut, lbNguyenNhan;
    }
}
