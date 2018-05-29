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
import com.example.phutang.coffeemanager.Model.Entities.iSanPham;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Class cấu hình adapter hiện danh sách sản phẩm được bán trên listView gọi món
 */

public class AdapterListSanPham extends BaseAdapter{
    private List<iSanPham> sanPhamList;
    private int layoutInflater;
    private Context context;

    public AdapterListSanPham(List<iSanPham> sanPhamList, int layoutInflater, Context context) {
        this.sanPhamList = sanPhamList;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.sanPhamList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.sanPhamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolderOfItemProduct viewHolder = new ViewHolderOfItemProduct();
            if (convertView == null) {
                convertView = inflater.inflate(layoutInflater, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_sp_img_hinhDD);
                viewHolder.lbTenSP = (TextView) convertView.findViewById(R.id.item_sp_lbTenSP);
                viewHolder.lbDonGia = (TextView) convertView.findViewById(R.id.item_sp_lbDonGia);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolderOfItemProduct) convertView.getTag();
            iSanPham sp = this.sanPhamList.get(position);
            Bitmap bmp = xlDuLieu.stringToImage(sp.getHinhAnh());
            //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
            int sizeWidth = convertView.getResources().getInteger(R.integer.sizeOfImageBan_width);
            int sizeHeight = convertView.getResources().getInteger(R.integer.sizeOfImageBan_height);

            viewHolder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, sizeWidth, sizeHeight, false));
            viewHolder.lbTenSP.setText(sp.getTenSanPham());
            viewHolder.lbDonGia.setText("Giá: " + String.valueOf(sp.getDonGia()) + " VNĐ");
        }
        catch (Exception ex){
        }
        return convertView;
    }

    private class ViewHolderOfItemProduct {
        ImageView imageView;
        TextView lbTenSP, lbDonGia;
    }
}
