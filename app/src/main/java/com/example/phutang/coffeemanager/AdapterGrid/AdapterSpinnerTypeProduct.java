package com.example.phutang.coffeemanager.AdapterGrid;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phutang.coffeemanager.Model.Entities.iLoaiSanPham;
import com.example.phutang.coffeemanager.R;

import java.util.List;

/**
 * Created by PhuTang on 4/1/2018.
 */

public class AdapterSpinnerTypeProduct  extends ArrayAdapter<iLoaiSanPham> {
    private List<iLoaiSanPham> list;
    private Context context;
    private int layoutInflater;

    public  AdapterSpinnerTypeProduct(Context context, int textViewID, List<iLoaiSanPham> listType){
        super(context, textViewID, listType);
        this.layoutInflater = textViewID;
        this.context = context;
        this.list = listType;
    }

    /**
     * Hàm lấy tổng số loại có trên list
     * @return
     */
    @Override
    public int getCount(){
        return list.size();
    }

    /**
     * Hàm lấy một item trong list
     * @param position
     * @return
     */
    @Override
    public iLoaiSanPham getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layoutInflater, null);
        TextView label = new TextView(context);
        try {
            this.configTextView(label, convertView);
            label.setText(list.get(position).getTenLoai());

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return label;
    }


    /**
     * Hàm cấu hình các textview trong item của spinner
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutInflater, null);
            this.configTextView(label, convertView);
            label.setText(list.get(position).getTenLoai());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return label;
    }

    /**
     * Hàm thực hiện cấu hình textview item của spinner trên giao diện
     * @param label
     * @param convertView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void configTextView(TextView label, View convertView){
        label.setTextColor(Color.BLACK);
        int textSize = convertView.getResources().getInteger(R.integer.textsize_spinner);
        label.setTextSize(textSize);
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        label.setPadding(0,10,0,0);
    }

}
