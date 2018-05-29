package com.example.phutang.coffeemanager.AppCode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.util.Base64.decode;

/**
 * Created by PhuTang on 2017/09/21.
 */

public class xlDuLieu {
    /**
     * Hàm thực hiện chuyển đổi ngày theo dạng 1996-02-11T00:00:00 sang kiểu Date
     * @param ngay Ngày cần chuyển đồi ở dạng 1996-02-11T00:00:00
     * @return Trả về Date đã chuyền đổi
     */
    public static Date convertDateFromString(String ngay)
    {
        Date kq = null;
        //--Chỉ lấy giá trị ngày tháng năm
        String strNgayThang = ngay.split("T")[0];
        //----Chuyển đổi ký tự trong ngày từ ( - ) thành ( / )
        String strNgayChuan = strNgayThang.replace("-","/");
        //---Ép kiểu thành Date
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            kq = df.parse(strNgayChuan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm thực hiện chuyển đổi chuỗi thành Date
     * @param ngay Tham số ngày truyền vào theo dạng yyyy/MM/dd HH:mm:ss
     * @return
     */
    public static Date convertStringToDateDMYHMS(String ngay){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(ngay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Hàm thực hiện chuyển đổi chuỗi thành Date
     * @param ngay Chuỗi ngày với định dạng EEE MMM dd HH:mm:ss z yyyy (THU SEP 05 00:52:12 GMT+7:00 2017)
     * @return
     */
    public static Date convertStringToDateGMT(String ngay){
        Date date=null;
        try {
            SimpleDateFormat sdf =new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            date=  sdf.parse(ngay);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Hàm chuyển đổi từ ngày thành chuỗi theo dạng 2017-10-05T01:30:48
     * @param date
     * @return
     */
    public static String convertDataToString(Date date){
        String kq="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String month = String.valueOf(cal.get(Calendar.MONTH)+1);
        if(month.length()==1)//-----Nếu tháng chỉ có 1 ký tự thì thêm số 0 phía trước
            month = "0"+month;
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        if(day.length()==1)//-----Nếu tháng chỉ có 1 ký tự thì thêm số 0 phía trước
            day = "0"+day;
        int year = cal.get(Calendar.YEAR);
        String hours = String.valueOf(cal.get(Calendar.HOUR));
        if(hours.length()==1)//-----Nếu tháng chỉ có 1 ký tự thì thêm số 0 phía trước
            hours = "0"+hours;
        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        if(minute.length()==1)//-----Nếu tháng chỉ có 1 ký tự thì thêm số 0 phía trước
            minute = "0"+minute;
        String second = String.valueOf(cal.get(Calendar.SECOND));
        if(second.length()==1)//-----Nếu tháng chỉ có 1 ký tự thì thêm số 0 phía trước
            second = "0"+second;

        kq = year + "-" + month + "-"+day+"T"+hours+ ":" + minute+":"+second;
        return kq;
    }

    /**
     * Hàm thực hiện chuyển đổi Date thành String theo dạng dd/MM/yyyy
     * @param date Ngày cần chuyển đổi
     * @return Chuỗi đã được chuyển đổi
     */
    public static String convertDateToStringDMY(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);

    }

    /**
     * Hàm convert chuỗi x64 chứa hình ảnh thành file hình ảnh
     * @param base64
     * @return
     */
    public static Bitmap stringToImage(String base64) {
        base64.replaceAll("data:image/jpeg;base64,", "");
        byte[] decodedString = decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }



}
