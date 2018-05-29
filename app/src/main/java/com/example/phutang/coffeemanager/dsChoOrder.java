package com.example.phutang.coffeemanager;


import android.content.Intent;
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

import com.example.phutang.coffeemanager.AdapterGrid.AdapterGridBan;
import com.example.phutang.coffeemanager.AdapterGrid.AdapterSpinnerKhuVuc;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class dsChoOrder extends Fragment {


    private List<iBanChoNgoi> listBan;
    private AdapterGridBan adapter;
    private int maKV = 0, maBanCu=0;

    private GifImageView imgReload;
    private Spinner cbbKhuVuc;
    private GridView gvBanTrong;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ds_cho_order, container, false);
        anhXaView(view);
        try {
            //----------Hiện danh sách khu vực lên Spinner
            final AdapterSpinnerKhuVuc adapterKhuVuc = new AdapterSpinnerKhuVuc(getContext(), android.R.layout.simple_spinner_item, Session.listKhuVuc);
            cbbKhuVuc.setAdapter(adapterKhuVuc);

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
             * sự kiện click vào 1 item girdview bàn sẽ mở giao diện order
             */
            gvBanTrong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //---reset giỏ hàng.
                    Session.listGioHang = new ArrayList<iGioHang>();
                    int maBan = adapter.getItem(position).getMaBan();
                    Bundle bdlOrder = new Bundle();
                    bdlOrder.putInt("maBan", maBan);
                    Intent i = new Intent(getContext(), GoiMon.class);
                    i.putExtras(bdlOrder);
                    startActivity(i);
                    new loadDanhSachBanChoOrder().cancel(true);
                }
            });

            /**
             * Sự kiện khi click mạnh để chọn chuyển đổi 1 bàn trên gridview
             */
            gvBanTrong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    maBanCu = adapter.getItem(position).getMaBan();
                    return false;
                }
            });

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callExecuteGetList(view);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });


            listBan = new ArrayList<iBanChoNgoi>();
            adapter = new AdapterGridBan(listBan, R.layout.grid_single_ban, getContext());
            gvBanTrong.setAdapter(adapter);
            registerForContextMenu(gvBanTrong);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDanhSachBanChoOrder().execute(maKV);
    }

    //region MENU CHUYỂN BÀN

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.gvChoOrder) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.menu_doicho, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_doicho:
                Bundle bdlDoiBan = new Bundle();
                bdlDoiBan.putInt("maBan", maBanCu);
                Intent iDoiCho = new Intent(getContext(), doiCho.class);
                iDoiCho.putExtras(bdlDoiBan);
                startActivity(iDoiCho);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //endregion

    /**
     * Hàm ánh xạ các object với giao diện
     *
     * @param view
     */
    private void anhXaView(View view) {
        imgReload = (GifImageView) view.findViewById(R.id.img_choOrder_reload);
        cbbKhuVuc = (Spinner) view.findViewById(R.id.cbbKhuVucChoOrDer);
        gvBanTrong = (GridView) view.findViewById(R.id.gvChoOrder);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_choorder_swipeRefresh);
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getContext()))
            new loadDanhSachBanChoOrder().execute(maKV);
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
     * Class thực thi lấy danh sách bàn chờ order trên server qua webservice
     */
    private class loadDanhSachBanChoOrder extends AsyncTask<Integer, Void, List<iBanChoNgoi>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listBan.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iBanChoNgoi> doInBackground(Integer... params) {
            //-------Lấy danh sách bàn chờ order
            return new bNghiepVuBan().layDanhSachTheoTrangThai(params[0], 0);
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


}
