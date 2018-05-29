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
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Created by PhuTang on 3/24/2018.
 */

public class AdapterGridBan extends BaseAdapter{
    private List<iBanChoNgoi> banList;
    private int layoutInflater;
    private Context context;


    public AdapterGridBan(List<iBanChoNgoi> banList, int layoutInflater, Context context) {
        this.banList = banList;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.banList.size();
    }

    @Override
    public iBanChoNgoi getItem(int position) {
        return this.banList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(layoutInflater, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.gridban_img_hinhDD);
                holder.lbTenBan = (TextView) convertView.findViewById(R.id.gridban_lb_tenBan);
                holder.lbSucChua = (TextView) convertView.findViewById(R.id.gridban_lb_sucChua);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            //---------Gán giá trị lên view
            iBanChoNgoi ban = this.banList.get(position);
            Bitmap bmp = xlDuLieu.stringToImage(ban.getHinhAnh());

            //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
            int sizeWidth = convertView.getResources().getInteger(R.integer.sizeOfImageBan_width);
            int sizeHeight = convertView.getResources().getInteger(R.integer.sizeOfImageBan_height);

            holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, sizeWidth, sizeHeight, false));
            holder.lbSucChua.setText("- " + String.valueOf(ban.getSucChua()) + " chỗ");
            holder.lbTenBan.setText(ban.getTenBan());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView lbTenBan, lbSucChua;
    }
}
