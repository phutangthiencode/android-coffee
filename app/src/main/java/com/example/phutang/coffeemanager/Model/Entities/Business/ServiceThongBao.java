package com.example.phutang.coffeemanager.Model.Entities.Business;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.phutang.coffeemanager.Model.Entities.iThongBao;
import com.example.phutang.coffeemanager.R;
import com.example.phutang.coffeemanager.dsDaPhaChe;

import java.util.ArrayList;
import java.util.List;

/**
 * Class xử lý dịch vụ chạy ngầm để nhận thông báo phục vụ bàn
 */

public class ServiceThongBao extends Service {
    private MediaPlayer mediaPlayer;
    private Thread task;//Tác vụ xử lý truy xuất vào Webservice
    private NotificationCompat.Builder notifyBuilder;
    private static final int MY_REQUEST_CODE = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Hàm khởi động service
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        try {
            task = new Thread(){
                @Override
                public void run() {
                    while(true){
                        try {
                            layThongBao excThongBao = new layThongBao();
                            excThongBao.execute();
                            Thread.sleep(5000); //------Nghỉ 5 giây
                            excThongBao.cancel(true);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            };
            task.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        task.interrupt(); //Tắt tiến trình
        Toast.makeText(getApplicationContext(),"Đã tắt ứng dụng", Toast.LENGTH_LONG).show();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    private class layThongBao extends AsyncTask<Object, Object, List<iThongBao>> {

        private String tenDangNhap="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<iThongBao> doInBackground(Object... params) {
            List<iThongBao> kq = new ArrayList<iThongBao>();
            try {
                tenDangNhap = new DbAccount(getApplicationContext()).getInfoTaiKhoan().getTenDangNhap();
                kq = new bGeneral().getListThongBao(tenDangNhap);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return kq;
        }

        @Override
        protected void onPostExecute(List<iThongBao> list) {
            super.onPostExecute(list);
            try {
                for (int i = 0; i < list.size(); i++)
                    khoiTaoThongBao(list.get(i).getMaThongBao(), list.get(i).getNdThongBao());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * hàm tạo một thông báo khi nhận được thông báo mới từ server
     * @param maThongBao Mã thông báo
     * @param ndThongBao Nội dung thông báo
     */
    private void khoiTaoThongBao(int maThongBao, String ndThongBao) {
        try {
            //--Cấu hình cho thông báo
            this.notifyBuilder = new NotificationCompat.Builder(this);
            this.notifyBuilder.setAutoCancel(true);
            this.notifyBuilder.setSmallIcon(R.mipmap.icon_app);
            this.notifyBuilder.setWhen(System.currentTimeMillis() + 10 * 1000);
            this.notifyBuilder.setContentTitle("Quản lý cà phê");
            this.notifyBuilder.setContentText(ndThongBao);
            //-----------Tạo intent khi người dùng click vào thông báo sẽ khởi động lại ứng dụng
            Intent intent = new Intent(this, dsDaPhaChe.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            this.notifyBuilder.setContentIntent(pendingIntent);
            // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
            NotificationManager notificationService =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            // Xây dựng thông báo và gửi nó lên hệ thống.
            Notification notification = notifyBuilder.build();
            notificationService.notify(maThongBao, notification);

            Uri soundNotify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), soundNotify);
            r.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
