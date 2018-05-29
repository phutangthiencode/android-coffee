package com.example.phutang.coffeemanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phutang.coffeemanager.AppCode.xlChung;
import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.Business.bGioHang;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class GioHang extends AppCompatActivity {

    private int maBan = 0;
    private static long tongTien=0;
    private ListView lvSanPham;
    private AdapterGioHang adapter;
    private TextView lbTongTien;
    private String ghiChu;
    private GifImageView imgReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.giohang_toolbar);
        setSupportActionBar(toolbar);
        /**
         * Sự kiện click vào nút quay lại trên toolbar
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoClassOrder();
            }
        });

        try {
            //---------Nhận dữ liệu truyền vào qua bundle
            Bundle bdlMaBan = this.getIntent().getExtras();
            maBan = bdlMaBan.getInt("maBan");
            if (maBan > 0) {
                this.showData();
                FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.giohang_btnSave);
                fabSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hoanTatOrder(view);
                    }
                });
                FloatingActionButton fabGhiChu = (FloatingActionButton) findViewById(R.id.giohang_btnGhiChu);
                fabGhiChu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cauHinhDialogGhiChu();
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_removeAllCart:
                new bGioHang().deleteAllItem();
                this.gotoClassOrder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Hàm hiện dữ liệu lên giao diện
     */
    public void showData(){
        imgReload = (GifImageView)findViewById(R.id.giohang_imgreload);
        imgReload.setVisibility(View.INVISIBLE);
        lvSanPham = (ListView) findViewById(R.id.giohang_lvGioHang);
        adapter = new AdapterGioHang(Session.listGioHang, R.layout.item_of_giohang, this);
        lvSanPham.setAdapter(adapter);

        lbTongTien = (TextView)findViewById(R.id.giohang_lbTongTien);
        bGioHang cart = new bGioHang();
        tongTien = cart.tongTienDtb + cart.getTotalMoney();
        this.lbTongTien.setText("Tổng cộng: " + String.valueOf(tongTien));
        adapter.notifyDataSetChanged();

    }

    /**
     * Sự kiện quay về layout trước
     * Quay về giao diện chính
     */
    @Override
    public void onBackPressed() {
        this.gotoClassOrder();
    }

    /**
     * Hàm thực hiện quay lại giao diện gọi món
     */
    private void gotoClassOrder(){
        Bundle bdlOrder = new Bundle();
        bdlOrder.putInt("maBan", maBan);
        Intent iArrowBack = new Intent(this, GoiMon.class);
        iArrowBack.putExtras(bdlOrder);
        startActivity(iArrowBack);
        this.finish();
    }


    /**
     * Hàm cấu hình dialog nhập ghi chú khi người dùng click vào nút thêm ghi chú
     */
    private void cauHinhDialogGhiChu(){
        final Dialog commentDialog = new Dialog(this);
        commentDialog.setContentView(R.layout.layout_nhapghichuorder);
        final EditText txtGhiChu = (EditText)commentDialog.findViewById(R.id.nhapghichu_txtGhiChu);
        Button btnNhap = (Button) commentDialog.findViewById(R.id.nhapghichu_btnNhap);
        Button cancelBtn = (Button) commentDialog.findViewById(R.id.nhapghichu_btnThoat);
        commentDialog.setTitle("Nhập ghi chú");
        commentDialog.show();

        btnNhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ghiChu = txtGhiChu.getText().toString();
                commentDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog.dismiss();
            }
        });
    }

    private void hoanTatOrder(View view){
        if(Session.listGioHang.size()>0){
            if(xlChung.isNetworkAvailable(getApplicationContext()))
                new HoanTatOrder().execute();
            else{
                Snackbar snackbar = xlChung.configSnackbarNoInternet(view);
                snackbar.setAction("THỬ LẠI", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        hoanTatOrder(v);
                    }
                });
            }
        }else{
            Snackbar snackbar = Snackbar.make(view, "Vui lòng chọn món", Snackbar.LENGTH_LONG);
            snackbar.setAction("CHỌN MÓN", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    gotoClassOrder();
                }
            });
            snackbar.show();
        }
    }


    /**
     * Class cấu hình adapter cho mỗi item trên listview order
     */
    public class AdapterGioHang extends BaseAdapter {

        private List<iGioHang> listGio;
        private int layoutInflater;
        private Context context;

        public AdapterGioHang(List<iGioHang> listGio, int layoutInflater, Context context) {
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
                ViewHolderCart viewHolder = new ViewHolderCart();
                if (convertView == null) {
                    convertView = inflater.inflate(layoutInflater, null);
                    anhXaItemListView(viewHolder, convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderCart) convertView.getTag();
                }
                iGioHang cart = this.listGio.get(position);
                Bitmap bmp = xlDuLieu.stringToImage(cart.getSanPham().getHinhAnh());

                //------Lấy kích thước hình ảnh đã được cấu hình trong file res/integer
                int sizeWidth = convertView.getResources().getInteger(R.integer.sizeImage_itemSanPham_listView);
                int sizeHeight = convertView.getResources().getInteger(R.integer.sizeImage_itemSanPham_listView);

                viewHolder.imgHinhDD.setImageBitmap(Bitmap.createScaledBitmap(bmp, sizeWidth, sizeHeight, false));
                viewHolder.lbTenSP.setText(cart.getSanPham().getTenSanPham());
                viewHolder.lbDonGia.setText("Đơn giá: " + String.valueOf(cart.getDonGia()) + " VNĐ");
                viewHolder.lbSoLuong.setText(String.valueOf(cart.getSoLuong()));

                //------KIểm tra trạng thái pha chế của món để hiển thị nút cập nhật số lượng
                if (cart.getTrangThaiPhaChe() > 0) {
                    //------Ẩn các nút
                    viewHolder.btnBot.setVisibility(View.INVISIBLE);
                    viewHolder.btnThem.setVisibility(View.INVISIBLE);
                }
                else{
                    //------Hiện các nút
                    viewHolder.btnBot.setVisibility(View.VISIBLE);
                    viewHolder.btnThem.setVisibility(View.VISIBLE);
                }

                /**
                 * Sự kiện cập nhật tăng số lượng sản phẩm đã order
                 */
                viewHolder.btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateSoLuongOrder(1, position);
                    }
                });

                viewHolder.btnBot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateSoLuongOrder(2, position);
                    }
                });
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
        private void anhXaItemListView(ViewHolderCart viewHolder, View convertView){
            viewHolder.imgHinhDD = (ImageView) convertView.findViewById(R.id.item_giohang_imgHinhDD);
            viewHolder.lbTenSP = (TextView) convertView.findViewById(R.id.item_giohang_lbTenSP);
            viewHolder.lbDonGia = (TextView) convertView.findViewById(R.id.item_giohang_lbDonGia);
            viewHolder.lbSoLuong = (TextView)convertView.findViewById(R.id.item_giohang_lbSoLuong);
            viewHolder.btnThem = (Button)convertView.findViewById(R.id.giohang_btnAdd);
            viewHolder.btnBot = (Button)convertView.findViewById(R.id.giohang_btnMinus);
        }

        /**
         * Hàm cập nhật số lượng dựa vào sự kiện click vào nút tăng hoạc giảm trên mỗi item listview
         * @param trangThai Trạng thái thực hiện - 1: Tăng - 2: Giảm
         * @param position vị trí lấy thông tin item
         */
        private void updateSoLuongOrder(int trangThai, int position){
            iGioHang cart = listGio.get(position);
            int soLuongHienTai = cart.getSoLuong();
            bGioHang gioHang = new bGioHang();
            if(trangThai==1) //------Trạng thái tăng số lượng
                gioHang.updateCart(cart, ++soLuongHienTai);
            else if(trangThai==2){ //----Trạng thái bớt số lượng
                //---------Nếu số lượng đã đặt bằng 1 mà nhấn nút Trừ thì xóa bỏ sản phẩm khỏi giỏ hàng
                if(soLuongHienTai==1)
                    gioHang.deleteCart(cart.getMaSP());
                else
                    gioHang.updateCart(cart, --soLuongHienTai);
            }
            adapter.notifyDataSetChanged(); //------Reload lại listview
            lbTongTien.setText("Tổng cộng: "+String.valueOf(gioHang.getTotalMoney()));
        }

        /**
         * Class chứa các object cho các thành phân trên item listview gioHang
         */
        private class ViewHolderCart{
            ImageView imgHinhDD;
            TextView lbTenSP, lbDonGia, lbSoLuong;
            Button btnThem, btnBot;
        }

    }

    /**
     * Class xử lý khi hoàn tất order
     */
    private class HoanTatOrder extends AsyncTask<Void, Void, Integer>{

        private int soLuongItem =0; //---Số lượng item cần thêm vào bảng ctHoaDonTam
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int kqLuu=0;
            bNghiepVuBan nghiepVu = new bNghiepVuBan();
            kqLuu = nghiepVu.capNhatDaOrder(maBan, tongTien, ghiChu);
            if(kqLuu>0){
                soLuongItem = Session.listGioHang.size();
                //------Lặp qua danh sách sản phẩm đã order trong giỏ hàng
                for(iGioHang item : Session.listGioHang){
                    kqLuu+= nghiepVu.themChiTietTam(maBan, item);
                }
            }
            return kqLuu;
        }

        @Override
        protected void onPostExecute(Integer kqLuu) {
            super.onPostExecute(kqLuu);
            imgReload.setVisibility(View.INVISIBLE);
            if(kqLuu>soLuongItem) {
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), "Thực hiện thất bại, vui lòng thử lại!!!", Toast.LENGTH_LONG).show();
        }
    }


}
