package com.example.phutang.coffeemanager.AppCode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by PhuTang on 5/22/2018.
 */

public class xlChung {
    /**
     * Hàm kiểm tra thiết bị có kết nối integer
     * @param context Context để kiểm tra
     * @return True: Có kết nối - False: không kết nối
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Hàm khởi tạo snackbar hiển thị lỗi kết nối dưới footer layout
     * @param view
     * @return
     */
    public static Snackbar configSnackbarNoInternet(View view){
        Snackbar snackbar = Snackbar.make(view, "Mất kết nối Internet", Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }


}
