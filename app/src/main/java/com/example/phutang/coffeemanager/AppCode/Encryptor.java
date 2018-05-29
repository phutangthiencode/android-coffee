package com.example.phutang.coffeemanager.AppCode;


import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by PhuTang on 4/8/2018.
 */

public class Encryptor {

    private static String AES = "AES";
    private static String khoaBiMat = "Android2018";

    /**
     * Hàm thực hiện mã hóa dữ liệu theo chuẩn AES có khóa
     * @param data Chuỗi dữ liệu cần mã hóa
     * @param key Khóa mã hóa
     * @return Chuỗi dữ liệu đã mã hóa
     */
    public static String encrypt(String data, String key){
        String kq = "";
        try{
            key = khoaBiMat + key; //-------key="Android2018admin"
            SecretKeySpec keySecret = taoKhoa(key);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySecret);
            byte[] dataEnc = cipher.doFinal(data.getBytes());
            kq = Base64.encodeToString(dataEnc, Base64.DEFAULT);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    /**
     * Hàm thực hiện mã hóa dữ liệu theo chuẩn AES không cần khóa. Sử dụng khóa bí mật có trong class
     * @param data Chuỗi dữ liệu cần mã hóa
     * @return Chuỗi dữ liệu đã mã hóa
     */
    public static String encrypt(String data){
        String kq = "";
        try{
            SecretKeySpec keySecret = taoKhoa(khoaBiMat);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySecret);
            byte[] dataEnc = cipher.doFinal(data.getBytes());
            kq = Base64.encodeToString(dataEnc, Base64.DEFAULT);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm thực hiện giải mã 1 chuỗi ký tự có khóa
     * @param data Chuỗi ký tự cần giải mã
     * @param key Khóa giải mã
     * @return Chuỗi đã giải mã
     * @throws Exception
     */
    public static String decrypt(String data, String key){
        String kq="";
        try {
            key = khoaBiMat + key; //-------key="Android2018admin"
            SecretKeySpec keySecret = taoKhoa(key);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySecret);
            byte[] decodeValue = Base64.decode(data, Base64.DEFAULT);
            byte[] decValue = cipher.doFinal(decodeValue);
            kq = new String(decValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    /**
     * Hàm thực hiện giải mã 1 chuỗi ký tự không khóa. Sử dụng khóa bí mật có trong class
     * @param data Chuỗi ký tự cần giải mã
     * @return Chuỗi đã giải mã
     * @throws Exception
     */
    public static String decrypt(String data){
        String kq="";
        try {
            SecretKeySpec keySecret = taoKhoa(khoaBiMat);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySecret);
            byte[] decodeValue = Base64.decode(data, Base64.DEFAULT);
            byte[] decValue = cipher.doFinal(decodeValue);
            kq = new String(decValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm thực hiện tạo khóa
     * @param key
     * @return
     * @throws Exception
     */
    private static SecretKeySpec taoKhoa(String key)throws Exception{
        final MessageDigest deDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = key.getBytes("UTF-8");
        deDigest.update(bytes, 0, bytes.length);
        byte[] keys = deDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keys, "AES");
        return secretKeySpec;
    }

}
