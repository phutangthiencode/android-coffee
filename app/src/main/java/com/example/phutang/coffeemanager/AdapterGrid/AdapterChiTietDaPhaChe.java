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
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Class cấu hình adapter cho danh sách chi tiết đã pha chế
 */

public class AdapterChiTietDaPhaChe extends BaseAdapter {
    private List<iGioHang> listGio;
    private int layoutInflater;
    private Context context;

    public AdapterChiTietDaPhaChe(List<iGioHang> listGio, int layoutInflater, Context context) {
        this.listGio = listGio;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }


    @Override
    public int getCount() {
        return this.listGio.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listGio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolderChiTiet viewHolder = new ViewHolderChiTiet();
            if (convertView == null) {
                convertView = inflater.inflate(layoutInflater, null);
                anhXaItemListView(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderChiTiet) convertView.getTag();
            }
            iGioHang cart = this.listGio.get(position);
            Bitmap bmp = xlDuLieu.stringToImage(cart.getSanPham().getHinhAnh());

            //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
            int sizeWidth = convertView.getResources().getInteger(R.integer.sizeImage_itemSanPham_listView);
            int sizeHeight = convertView.getResources().getInteger(R.integer.sizeImage_itemSanPham_listView);

            viewHolder.imgHinhDD.setImageBitmap(Bitmap.createScaledBitmap(bmp, sizeWidth, sizeHeight, false));
            viewHolder.lbTenSP.setText(cart.getSanPham().getTenSanPham());
            viewHolder.lbDonGia.setText("Đơn giá: " + String.valueOf(cart.getDonGia()) + " VNĐ");
            viewHolder.lbSoLuong.setText("Số lượng: " + String.valueOf(cart.getSoLuong()) + " (sản phẩm)");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    /**
     * Hàm thực hiện ánh xạ các object với thành phần cho mỗi item trên listview
     * @param viewHolder
     * @param convertView
     */
    private void anhXaItemListView(ViewHolderChiTiet viewHolder, View convertView){
        viewHolder.imgHinhDD = (ImageView) convertView.findViewById(R.id.item_ctdpc_imgSanPham);
        viewHolder.lbTenSP = (TextView) convertView.findViewById(R.id.item_ctdpc_lbTenSP);
        viewHolder.lbDonGia = (TextView) convertView.findViewById(R.id.item_ctdpc_lbDonGia);
        viewHolder.lbSoLuong = (TextView)convertView.findViewById(R.id.item_ctdpc_lbSoLuong);
    }


    /**
     * Class chứa các object cho các thành phân trên item listview gioHang
     */
    private class ViewHolderChiTiet{
        ImageView imgHinhDD;
        TextView lbTenSP, lbDonGia, lbSoLuong;
    }






}
