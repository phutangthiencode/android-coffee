package com.example.phutang.coffeemanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterGridBan;
import com.example.phutang.coffeemanager.AdapterGrid.AdapterSpinnerKhuVuc;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;
import com.example.phutang.coffeemanager.Model.Entities.iKhuVuc;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class dsBanTrong extends Fragment {

    private List<iBanChoNgoi> listBan;
    private List<iKhuVuc> listKhuVuc;
    private AdapterGridBan adapter;
    private int maKV = 0;
    private int maBanTiepNhan = 0;

    private GifImageView imgReload;
    private Spinner cbbKhuVuc;
    private GridView gvBanTrong;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMaster = null;
        try {
            viewMaster = inflater.inflate(R.layout.fragment_ds_ban_trong, container, false);
            anhXaView(viewMaster);
            listKhuVuc = Session.listKhuVuc;
            //----------Hiện danh sách khu vực lên Spinner
            final AdapterSpinnerKhuVuc adapterKhuVuc = new AdapterSpinnerKhuVuc(getContext(), R.layout.support_simple_spinner_dropdown_item, listKhuVuc);
            adapterKhuVuc.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            cbbKhuVuc.setAdapter(adapterKhuVuc);

            /***
             * Sự kiện chọn 1 item trên cbb khu vực sẽ loại lại danh sách bàn
             */
            cbbKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        maKV = adapterKhuVuc.getItem(position).getMaKhuVuc();
                        callExecuteGetList(view);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /**
             * Sự kiện kéo thả để load lại danh sách bàn
             */
            final View finalView = viewMaster;
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callExecuteGetList(finalView);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

            /**
             *  Sự kiện click chọn vào 1 item có trên gvBanTrong
             *  Hiện dialog tiếp nhận bàn
             *
             */
            final View finalViewMaster = viewMaster;
            gvBanTrong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    maBanTiepNhan = adapter.getItem(position).getMaBan();
                    String tenBan = adapter.getItem(position).getTenBan();
                    cauHinhDialogTiepNhan(tenBan, finalViewMaster);
                }
            });


            listBan = new ArrayList<iBanChoNgoi>();
            adapter = new AdapterGridBan(listBan, R.layout.grid_single_ban, getContext());
            gvBanTrong.setAdapter(adapter);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return viewMaster;
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDanhSachBan().execute(maKV);
    }

    /**
     * Hàm thực hiện ánh xạ các đối tượng với giao diện
     * @param view
     */
    private void anhXaView(View view){
        imgReload = (GifImageView) view.findViewById(R.id.img_banTrong_reload);
        cbbKhuVuc = (Spinner) view.findViewById(R.id.cbbKhuVucBanTrong);
        gvBanTrong = (GridView) view.findViewById(R.id.gvBantrong);
        registerForContextMenu(gvBanTrong); //----Cấu hình menu click cho gridview
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_bantrong_swipeRefresh);

    }


    /**
     * Hàm cấu hình cho dialog tiếp nhận khi click vào 1 item trên gvBanTrong
     */
    private void cauHinhDialogTiepNhan(String tenBan, final View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tiếp nhận");
        builder.setMessage("Tiếp nhận bàn " + tenBan);
        builder.setCancelable(false);
        builder.setPositiveButton("Tiếp nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callExecuteTiepNhanBan(view, dialog);
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getContext()))
            new loadDanhSachBan().execute(maKV);
        else{
            Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
            snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callExecuteGetList(view);
                }
            });
        }
    }

    /**
     * Hàm thực hiện tiếp nhận bàn
     * @param view
     */
    private void callExecuteTiepNhanBan(View view, final DialogInterface dialog){
        try{
        if(xlChung.isNetworkAvailable(getContext()))
            new tiepNhanBan().execute(maBanTiepNhan);
        else{
            dialog.cancel();
            //Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
            Snackbar snackbar = Snackbar.make(view, "Mất kết nối Internet", Snackbar.LENGTH_LONG);
            snackbar.show();
            snackbar.setAction("THỬ LẠI", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callExecuteTiepNhanBan(view, dialog);
                }
            });
        }}catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Class thực thi lấy danh sách bàn trống trên server qua webservice
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
            return new bNghiepVuBan().layDanhSachBanTrong(params[0]);
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

    /**
     * Class thực thi gửi request tiếp nhận bàn lên webservices
     */
    private class tiepNhanBan extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int kqLuu = 0;
            kqLuu = new bNghiepVuBan().tiepNhanBan(params[0]);
            return kqLuu;
        }

        @Override
        protected void onPostExecute(Integer kqLuu) {
            super.onPostExecute(kqLuu);
            if(kqLuu>0) {
                //----------Chuyển đến layout order sản phẩm sau khi tiếp nhận thành công
                //---reset giỏ hàng.
                Session.listGioHang = new ArrayList<iGioHang>();
                Bundle bdlOrder = new Bundle();
                bdlOrder.putInt("maBan", maBanTiepNhan);
                Intent i = new Intent(getContext(), GoiMon.class);
                i.putExtras(bdlOrder);
                startActivity(i);
            }
        }
    }


}
