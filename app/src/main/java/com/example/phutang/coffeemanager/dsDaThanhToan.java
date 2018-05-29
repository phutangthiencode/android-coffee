package com.example.phutang.coffeemanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterGridBan;
import com.example.phutang.coffeemanager.AdapterGrid.AdapterSpinnerKhuVuc;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class dsDaThanhToan extends Fragment {

    private List<iBanChoNgoi> listBan;
    private AdapterGridBan adapter;
    private int maKV = 0;
    private int maBanReset=0;
    private GifImageView imgReload;
    private Spinner cbbKhuVuc;
    private GridView gvBanDaThanhToan;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ds_da_thanh_toan, container,false);
        this.anhXaView(view);

        //----------Hiện danh sách khu vực lên Spinner
        final AdapterSpinnerKhuVuc adapterKhuVuc = new AdapterSpinnerKhuVuc(getContext(), android.R.layout.simple_spinner_item, Session.listKhuVuc);
        cbbKhuVuc.setAdapter(adapterKhuVuc);

        //--------Cấu hình hiển thị gridView
        listBan = new ArrayList<iBanChoNgoi>();
        adapter = new AdapterGridBan(listBan, R.layout.grid_single_ban, getContext());
        gvBanDaThanhToan.setAdapter(adapter);
        registerForContextMenu(gvBanDaThanhToan);

        /***
         * Sự kiện chọn 1 item trên cbb khu vực sẽ loại lại danh sách bàn
         */
        cbbKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maKV = adapterKhuVuc.getItem(position).getMaKhuVuc();
                callExecuteGetList(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Sự kiện click chọn vào 1 bàn có trên gridview
         * sẽ hiển thị dialog cảnh báo reset bàn
         */
        gvBanDaThanhToan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tenBan = adapter.getItem(position).getTenBan();
                maBanReset = adapter.getItem(position).getMaBan();
                cauHinhDialogReset(tenBan);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callExecuteGetList(view);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDanhSachDaThanhToan().execute(maKV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.gvDaThanhToan) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.menu_dathanhtoan, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.menu_dathanhtoan_reset:
                //Thực hiện reset bàn
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Hàm ánh xạ các object với giao diện
     * @param view
     */
    private void anhXaView(View view){
        imgReload = (GifImageView)view.findViewById(R.id.img_daThanhToan_reload);
        cbbKhuVuc = (Spinner)view.findViewById(R.id.cbbKhuVucDaThanhToan);
        gvBanDaThanhToan = (GridView)view.findViewById(R.id.gvDaThanhToan);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_dathanhtoan_swipeRefresh);
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getContext()))
            new loadDanhSachDaThanhToan().execute(maKV);
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
     * Hàm cấu hình cho dialog reset khi click vào 1 item trên gridview resetBan
     */
    private void cauHinhDialogReset(String tenBan){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Dọn bàn");
        builder.setMessage("Khách đã rời khỏi và bạn muốn dọn bàn " + tenBan);
        builder.setCancelable(false);
        builder.setPositiveButton("Dọn bàn", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               new xulyResetBan().execute(maBanReset);
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
     * Class thực thi lấy danh sách bàn đã thanh toán trên server qua webservice
     */
    private class loadDanhSachDaThanhToan extends AsyncTask<Integer, Void, List<iBanChoNgoi>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listBan.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iBanChoNgoi> doInBackground(Integer... params) {
            //-------Lấy danh sách bàn đã thanh toán
            return new bNghiepVuBan().layDanhSachTheoTrangThai(params[0],3);
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
     * Class thực thi việc gửi request reset bàn
     */
    private class xulyResetBan extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int kq=0;
            kq = new bNghiepVuBan().resetBan(params[0]);
            return kq;
        }

        @Override
        protected void onPostExecute(Integer kq) {
            super.onPostExecute(kq);
            if(kq>0){
                imgReload.setVisibility(View.INVISIBLE);
                new loadDanhSachDaThanhToan().execute(maKV);
                Toast.makeText(getContext(), "Một bàn đã được dọn", Toast.LENGTH_LONG).show();
            }
        }
    }

}
