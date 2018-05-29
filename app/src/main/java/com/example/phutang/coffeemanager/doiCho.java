package com.example.phutang.coffeemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phutang.coffeemanager.AdapterGrid.AdapterGridBan;
import com.example.phutang.coffeemanager.AdapterGrid.AdapterSpinnerKhuVuc;
import com.example.phutang.coffeemanager.Model.Entities.Business.bNghiepVuBan;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class doiCho extends AppCompatActivity {

    private List<iBanChoNgoi> listBan;
    private AdapterGridBan adapter;
    private int maKV = 0;
    private int maBanMoi = 0, maBanCu=0;

    private GifImageView imgReload;
    private Spinner cbbKhuVuc;
    private GridView gvBanTrong;
    private FloatingActionButton fabRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_doi_cho);
            Toolbar toolbar = (Toolbar) findViewById(R.id.doicho_toolbar);
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

            //---------Nhận dữ liệu truyền vào qua bundle
            Bundle bdlMaBan = this.getIntent().getExtras();
            maBanCu = bdlMaBan.getInt("maBan");
            if (maBanCu > 0) {
                this.anhXaView();
                //----------Hiện danh sách khu vực lên Spinner
                final AdapterSpinnerKhuVuc adapterKhuVuc = new AdapterSpinnerKhuVuc(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, Session.listKhuVuc);
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
                            new loadDanhSachBan().execute(maKV);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                /**
                 *  Sự kiện click chọn vào 1 item có trên gvBanTrong
                 *  Hiện dialog tiếp nhận bàn
                 *
                 */
                gvBanTrong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        maBanMoi = adapter.getItem(position).getMaBan();
                        String tenBan = adapter.getItem(position).getTenBan();
                        cauHinhDialogTiepNhan(tenBan);
                    }
                });


                /**
                 * Sự kiện cho floatButton refresh lại danh sách
                 */

                fabRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new loadDanhSachBan().execute(maKV);
                    }
                });

                listBan = new ArrayList<iBanChoNgoi>();
                adapter = new AdapterGridBan(listBan, R.layout.grid_single_ban, getApplicationContext());
                gvBanTrong.setAdapter(adapter);
            } else
                this.gotoClassOrder();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm cấu hình tạo dialog thông báo đổi chổ khi click vào item trên gvBanTrong
     * @param tenBan Tên bàn mới cần chuyển sang
     */
    private void cauHinhDialogTiepNhan(String tenBan) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Đổi chổ");
            builder.setMessage("Chuyển đổi sang bàn " + tenBan);
            builder.setCancelable(false);
            builder.setPositiveButton("Chuyển đổi", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new xulyDoiCho().execute(maBanCu, maBanMoi);
                }
            });
            builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Hàm thực hiện ánh xạ các đối tượng với giao diện
     */
    private void anhXaView(){
        imgReload = (GifImageView) findViewById(R.id.doicho_imgReload);
        cbbKhuVuc = (Spinner) findViewById(R.id.doicho_cbbKhuVuc);
        gvBanTrong = (GridView)findViewById(R.id.doicho_gvBanTrong);
        fabRefresh = (FloatingActionButton) findViewById(R.id.doicho_fabRefresh);
        registerForContextMenu(gvBanTrong); //----Cấu hình menu click cho gridview
    }

    /**
     * Hàm quay lại giao diện trước khi vào giao diện này
     */
    private void gotoClassOrder() {
        startActivity(new Intent(getApplicationContext(), NghiepVuBan.class));
        this.finish();
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
     * Class xử lý gửi request đổi chổ
     */
    private class xulyDoiCho extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgReload.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int kq=0;
            kq = new bNghiepVuBan().doiCho(params[0], params[1]);
            return kq;
        }

        @Override
        protected void onPostExecute(Integer kq) {
            super.onPostExecute(kq);
            if(kq>0){
                Toast.makeText(getApplicationContext(), "Đổi bàn thành công", Toast.LENGTH_SHORT).show();
                //-------chuyển về giao diện trước
                gotoClassOrder();
            }

        }
    }
}
