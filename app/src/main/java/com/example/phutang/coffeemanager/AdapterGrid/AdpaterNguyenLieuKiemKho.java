package com.example.phutang.coffeemanager.AdapterGrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNhapSoLuong;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Created by PhuTang on 4/22/2018.
 */

public class AdpaterNguyenLieuKiemKho extends BaseAdapter {
    private List<iCtTonKho> list;
    private int layoutInflater;
    private Context context;
    private TextView lbTenNguyenLieu, lbDonViTinh;
    private EditText txtSoLuong, txtNguyenNhan;

    public AdpaterNguyenLieuKiemKho(List<iCtTonKho> list, int layoutInflater, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = inflater.inflate(layoutInflater, null);
                this.anhXaView(convertView, viewHolder);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            iCtTonKho chiTiet = this.list.get(position);
            this.fillDataToView(chiTiet, viewHolder,  convertView);

            /**
             * Sự kiện click vào nút nhập số lượng có trên mỗi item trên listView
             */
            viewHolder.btnNhapSoLuong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iCtTonKho itemSelected = list.get(position);
                    //configDialog(itemSelected, position);
                    new bNhapSoLuong(list, context,1).configDialog(itemSelected,position);
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    private void anhXaView(View convertView, ViewHolder viewHolder){
        viewHolder.imgHinhNguyenLieu = (ImageView)convertView.findViewById(R.id.item_nguyenlieukiem_imgHinh);
        viewHolder.lbTenNguyenLieu = (TextView) convertView.findViewById(R.id.item_nguyenlieukiem_lbTenNguyenLieu);
        viewHolder.lbDonViKiemKho = (TextView) convertView.findViewById(R.id.item_nguyenlieukiem_lbDonViTinh);
        viewHolder.btnNhapSoLuong = (Button)convertView.findViewById(R.id.item_nguyenlieukiem_btnNhapSoLuong);
    }

    private void fillDataToView(iCtTonKho chiTiet, ViewHolder viewHolder, View convertView){
        //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
        int sizeImage = convertView.getResources().getInteger(R.integer.sizeImage_item_listview);
        Bitmap bitMap = xlDuLieu.stringToImage(chiTiet.getHinhNguyenLieu());
        viewHolder.imgHinhNguyenLieu.setImageBitmap(Bitmap.createScaledBitmap(bitMap, sizeImage, sizeImage, false));
        viewHolder.lbTenNguyenLieu.setText(chiTiet.getTenNguyenLieu());
        viewHolder.lbDonViKiemKho.setText("Đơn vị kiểm kê: " + chiTiet.getDonViPhaChe());
    }



    private class ViewHolder{
        ImageView imgHinhNguyenLieu;
        TextView lbTenNguyenLieu, lbDonViKiemKho;
        Button btnNhapSoLuong;
    }
}
