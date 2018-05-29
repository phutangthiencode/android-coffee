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
import android.widget.Toast;

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
public class dsDaOrder extends Fragment {

    private List<iBanChoNgoi> listBan;
    private AdapterGridBan adapter;
    private int maKV = 0;
    private int maBanCapNhat =0; //------------Biến lưu trữ mã bàn cần cập nhật trạng thái
    private GifImageView imgReload;
    private Spinner cbbKhuVuc;
    private GridView gvBanDaOrder;
    private Intent intentOrder;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ds_da_order, container,false);
        anhXaView(view);
        try{
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

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callExecuteGetList(view);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });


            listBan = new ArrayList<iBanChoNgoi>();
            adapter = new AdapterGridBan(listBan, R.layout.grid_single_ban, getContext());
            gvBanDaOrder.setAdapter(adapter);
            registerForContextMenu(gvBanDaOrder);

            //------------Sự kiện mở menu đổi bàn khi nhấn giữ item trên gridview
            gvBanDaOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    maBanCapNhat = adapter.getItem(position).getMaBan();
                    return false;
                }
            });

            /**
             * sự kiện click vào 1 item girdview bàn sẽ mở giao diện order
             */
            gvBanDaOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int maBan = adapter.getItem(position).getMaBan();
                    Bundle bdlOrder = new Bundle();
                    bdlOrder.putInt("maBan", maBan);
                    intentOrder = new Intent(getContext(), GoiMon.class);
                    intentOrder.putExtras(bdlOrder);
                    new loadDanhSachBanDaOrder().cancel(true);
                    new ChuanBiHoaDonTam().execute(maBan);
                }
            });

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDanhSachBanDaOrder().execute(maKV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.gvDaOrder) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.menu_daorder, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.menu_daorder_doicho:
                //Thực hiện đổi chổ cho bàn
                Bundle bdlDoiBan = new Bundle();
                bdlDoiBan.putInt("maBan", maBanCapNhat);
                Intent iDoiCho = new Intent(getContext(), doiCho.class);
                iDoiCho.putExtras(bdlDoiBan);
                startActivity(iDoiCho);
                return true;
            case R.id.menu_daorder_thanhtoan:
                new xulyTiepNhanThanhToan().execute(maBanCapNhat);
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
        imgReload = (GifImageView)view.findViewById(R.id.img_daOrder_reload);
        cbbKhuVuc = (Spinner)view.findViewById(R.id.cbbKhuVucDaOrDer);
        gvBanDaOrder = (GridView)view.findViewById(R.id.gvDaOrder);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_daorder_swipeRefresh);
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getContext()))
            new loadDanhSachBanDaOrder().execute(maKV);
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
     * Class thực thi lấy danh sách bàn đã order trên server qua webservice
     */
    private class loadDanhSachBanDaOrder extends AsyncTask<Integer, Void, List<iBanChoNgoi>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listBan.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iBanChoNgoi> doInBackground(Integer... params) {
            //-------Lấy danh sách bàn đã order
            return new bNghiepVuBan().layDanhSachTheoTrangThai(params[0],1);
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
     * Class thực hiện Lấy Tổng tiền và danh sách các món đã order
     */
    public class ChuanBiHoaDonTam extends AsyncTask<Integer, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            bNghiepVuBan nghiepVu = new bNghiepVuBan();
            Session.listGioHang = new ArrayList<iGioHang>();//-------reset giỏ hàng
            //----Gán danh sách sản phẩm vào giỏ hàng
            nghiepVu.layCtHoaDonTam(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(intentOrder);
        }


    }

    /**
     * Class thực thi cập nhật trạng thái tiếp nhận thanh toán cho bàn
     */
    private class xulyTiepNhanThanhToan extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int kq=0;
            kq = new bNghiepVuBan().tiepNhanThanhToan(params[0]);
            return kq;
        }

        @Override
        protected void onPostExecute(Integer kq) {
            super.onPostExecute(kq);
            if(kq>0){
                imgReload.setVisibility(View.INVISIBLE);
                //--------Load lại danh sách bàn
                new loadDanhSachBanDaOrder().execute(maKV);
                Toast.makeText(getContext(), "Một bàn đang chờ thanh toán", Toast.LENGTH_LONG).show();
            }
        }
    }
}
