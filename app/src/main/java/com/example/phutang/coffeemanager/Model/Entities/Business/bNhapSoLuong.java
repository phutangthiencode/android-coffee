package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phutang.coffeemanager.DoiChieuSoLuong;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;
import com.example.phutang.coffeemanager.R;
import com.example.phutang.coffeemanager.dsNguyenLieuKiemKho;

import java.util.List;

/**
 * Created by PhuTang on 5/13/2018.
 */

public class bNhapSoLuong {
    private Context context;
    private TextView lbTenNguyenLieu, lbDonViTinh;
    private EditText txtNguyenNhan, txtSoLuong;

    private List<iCtTonKho> list;

    private int trangThai; //----Trạng thái thực hiện: nhập mới hoặc chỉnh sửa số lượng

    /**
     * Hàm dựng
     * @param list Danh sách nguyên liệu kiểm kê
     * @param context Context layout
     * @param trangThai Trạng thái thực hiện: 1: nhập số lượng - 2: Sửa số lượng
     */
    public bNhapSoLuong(List<iCtTonKho> list, Context context, int trangThai) {
        this.list = list;
        this.context = context;
        this.trangThai = trangThai;
    }

    /**
     * Hàm cấu hình và tạo dialog nhập số lượng tồn
     * @param itemSelected Object chứa thông tin của nguyên liệu cần nhập
     * @param position Vị trí của nguyên liệu trong listview
     */
    public void configDialog(final iCtTonKho itemSelected, final int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_nhaptonkho);
        dialog.setTitle("SỐ LƯỢNG TỒN");

        lbTenNguyenLieu = (TextView) dialog.findViewById(R.id.dialog_nhaptonkho_lbTenNguyenLieu);
        lbDonViTinh = (TextView) dialog.findViewById(R.id.dialog_nhaptonkho_lbDonViTinh);
        txtNguyenNhan =(EditText)dialog.findViewById(R.id.dialog_nhaptonkho_txtNguyenNhan);
        txtSoLuong =(EditText)dialog.findViewById(R.id.dialog_nhaptonkho_txtSoLuongTon);

        lbTenNguyenLieu.setText(itemSelected.getTenNguyenLieu());
        lbDonViTinh.setText(itemSelected.getDonViPhaChe());

        Button btnNhap = (Button) dialog.findViewById(R.id.dialog_nhaptonkho_btnNhap);
        Button btnThoat = (Button) dialog.findViewById(R.id.dialog_nhaptonkho_btnThoat);
        /**
         * Sự kiện nhấn vào nút nhập trên dialog
         */
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventForInputData(position, dialog,  itemSelected);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        /**
         * Sự kiện click vào nút xong trên bàn phím khi nhập liệu tại txtPassword
         */
        txtNguyenNhan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    eventForInputData(position, dialog,  itemSelected);
                    return true;
                }
                return false;
            }
        });

        dialog.show();
    }

    /**
     * Hàm thực thi sự kiện Nhập số lượng tồn kho thực tế tại nút nhập của dialog
     * @param position Vị trí item trên listview để xóa bỏ nguyên liệu không cho nhập liệu
     * @param dialog Dialog
     * @param itemSelected Object chứa các thuộc tính của ctTonKho để thêm vào giỏ
     */
    private void eventForInputData(final int position, final Dialog dialog, final iCtTonKho itemSelected){
        try {
            String strSoLuong = this.txtSoLuong.getText().toString();
            if(!strSoLuong.equals("")){ //-------Nếu chưa nhập số lượng
                int soLuongThucTe = Integer.parseInt(strSoLuong);
                if(this.trangThai==1) //------Nhập mới
                    if(soLuongThucTe>=0) {
                        themHangVaoGio(itemSelected, soLuongThucTe);
                        list.remove(position);
                        dialog.dismiss();
                        //------Cập nhật lại dữ liệu có trên listview tại dsNguyenLieuKiemKho
                        dsNguyenLieuKiemKho.adapter.notifyDataSetChanged();
                        //----Kiểm tra nếu số lượng trên listview đã hết thì cho hiện nút floatAction
                        if (this.list.size() == 0)
                            dsNguyenLieuKiemKho.fab.setVisibility(View.VISIBLE);
                    }
                if(this.trangThai==2) //----cập nhật số lượng
                    if(soLuongThucTe>=0){
                        capNhatSoLuongKiemKho(itemSelected,  soLuongThucTe);
                        dialog.dismiss();
                        //--------cập nhật lại dữ liệu listView tại doichieu
                        DoiChieuSoLuong.adapter.notifyDataSetChanged();
                    }
            } else
                Toast.makeText(context, "Vui lòng nhập số lượng thực tế", Toast.LENGTH_LONG).show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm thêm thông tin sản phẩm vừa kiểm vào cart
     */
    private void themHangVaoGio(iCtTonKho ct, int soLuongThucTe){
        try {
            //----Tính tỷ lệ hao hụt
            double soLuongHaoHut = (double) ct.getSoLuongCuoiKyLyThuyet() - soLuongThucTe;
            double tyleHaoHut = ((double) (soLuongHaoHut / ct.getSoLuongCuoiKyLyThuyet())) * 100;
            tyleHaoHut = Math.round(tyleHaoHut);

            ct.setSoLuongThucTe(soLuongThucTe);
            ct.setNguyenNhanHaoHut(this.txtNguyenNhan.getText().toString());
            ct.setTyleHaoHut(tyleHaoHut);
            Session.listGioKiemKho.add(ct);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm tiến hành cập nhật lại số lượng thực tế của nguyên liệu
     * @param ct Object item chứa thông tin nguyên liệu tồn kho
     * @param soLuongThucTe Số lượng thực tế cần cập nhật
     */
    private void capNhatSoLuongKiemKho(iCtTonKho ct, int soLuongThucTe) {
        try{
            Session.listGioKiemKho.remove(ct);
            this.themHangVaoGio(ct, soLuongThucTe);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
