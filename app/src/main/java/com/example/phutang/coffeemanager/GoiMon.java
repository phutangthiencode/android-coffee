package com.example.phutang.coffeemanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterListSanPham;
import com.example.phutang.coffeemanager.AdapterGrid.AdapterSpinnerTypeProduct;
import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.Model.Entities.Business.bGioHang;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;
import com.example.phutang.coffeemanager.Model.Entities.iSanPham;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class GoiMon extends AppCompatActivity {

    private int maBan;
    private int maLoai;
    private Spinner cbbLoaiSanPham;
    private GridView gvSanPham;
    private GifImageView imgReload;
    private FloatingActionButton floatButton;

    private AdapterSpinnerTypeProduct adapterLoai;
    private List<iSanPham> listSanPham;
    private AdapterListSanPham adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goi_mon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_goimon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            //---------Nhận dữ liệu truyền vào qua bundle
            Bundle bdlMaBan = this.getIntent().getExtras();
            maBan = bdlMaBan.getInt("maBan");
            //------nếu nhận được mã bàn
            if (maBan > 0) {
                anhXaView();

                //----Cấu hình cbbLoaiSanPham
                adapterLoai = new AdapterSpinnerTypeProduct(this, R.layout.support_simple_spinner_dropdown_item, Session.listLoaiSanPham);
                adapterLoai.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                cbbLoaiSanPham.setAdapter(adapterLoai);

                /***
                 * Sự kiện chọn 1 item trên cbb loại sẽ load lại danh sách sản phẩm theo loại
                 */
                cbbLoaiSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            maLoai = adapterLoai.getItem(position).getMaLoai();
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
                 * Sự kiện thêm một sản phẩm vào giỏ hàng
                 */
                gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        iSanPham sp = (iSanPham) adapter.getItem(position);
                        iGioHang cartAdd = new iGioHang();
                        cartAdd.setMaSP(sp.getMaSanPham());
                        cartAdd.setDonGia(sp.getDonGia());
                        cartAdd.setSoLuong(1);
                        cartAdd.setSanPham(sp);
                        cartAdd.setTrangThaiPhaChe(0);
                        int kqLuu = new bGioHang().addCart(cartAdd);
                        if(kqLuu>0)
                            Toast.makeText(getApplicationContext(), sp.getTenSanPham() + " đã được chọn", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Chọn sản phẩm thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });

                floatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callExecuteGetList(view);
                    }
                });

                listSanPham = new ArrayList<iSanPham>();
                adapter = new AdapterListSanPham(listSanPham, R.layout.item_of_list_sanpham, getApplicationContext());
                gvSanPham.setAdapter(adapter);


            } else
                startActivity(new Intent(getApplicationContext(), NghiepVuBan.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_cart:
                Bundle bdlOrder = new Bundle();
                bdlOrder.putInt("maBan", maBan);
                Intent i = new Intent(this, GioHang.class);
                i.putExtras(bdlOrder);
                startActivity(i);
                this.finish();
                return true;
            case R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Hàm thực hiện ánh xạ object với giao diện
     */
    private void anhXaView() {
        cbbLoaiSanPham = (Spinner)findViewById(R.id.goimon_cbbLoaiSP);
        gvSanPham = (GridView)findViewById(R.id.goimon_gvSanPham);
        imgReload = (GifImageView)findViewById(R.id.goimon_imgReload);
        floatButton = (FloatingActionButton)findViewById(R.id.goimon_btnReload);
        imgReload.setVisibility(View.INVISIBLE);
    }

    /**
     * Hàm gọi việc thực thi load danh sách
     * @param view
     */
    private void callExecuteGetList(View view){
        if(xlChung.isNetworkAvailable(getApplicationContext()))
            new laySanPham().execute(maLoai);
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
     * Class lấy danh sách sản phẩm khả dụng từ Webservices
     */
    private class laySanPham extends AsyncTask<Integer, Void, List<iSanPham>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listSanPham.clear();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<iSanPham> doInBackground(Integer... params) {
            return new bNghiepVuBan().laySanPhamTheoLoai(params[0]);
        }

        @Override
        protected void onPostExecute(List<iSanPham> iSanPhams) {
            try {
                super.onPostExecute(iSanPhams);
                for (int i = 0; i < iSanPhams.size(); i++)
                    listSanPham.add(iSanPhams.get(i));
                adapter.notifyDataSetChanged();//-------------Cập nhật lại gridview
                imgReload.setVisibility(View.INVISIBLE);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
