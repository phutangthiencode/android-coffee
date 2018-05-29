package com.example.phutang.coffeemanager.Model.Entities.Business;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.iTaiKhoan;
import com.example.phutang.coffeemanager.Model.Entities.iThanhVien;
import com.example.phutang.coffeemanager.Model.Entities.iThongBao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class thực thi kết nối với webservice để truyền tải dữ liệu
 */

public class bGeneral {
    private final String NAME_SPACE = "http://tempuri.org/";
    private final String METHOD_LOGIN = "Login";
    private final String SOAP_LOGIN = NAME_SPACE + METHOD_LOGIN;

    private final String METHOD_GETTAIKHOAN = "getInForTaiKhoan";
    private final String SOAP_GETTAIKHOAN = NAME_SPACE + METHOD_GETTAIKHOAN;

    private final String METHOD_GETTHANHVIEN = "getInForThanhVien";
    private final String SOAP_GETTHANHVIEN = NAME_SPACE + METHOD_GETTHANHVIEN;

    private final String METHOD_GETTHONGBAO = "getListNotificationsOfUser";
    private final String SOAP_GETTHONGBAO = NAME_SPACE + METHOD_GETTHONGBAO;


    private final String URL_THANHVIEN = "http://210.2.88.252/Models/Services/bNhanVien.asmx?wsdl";


    /**
     * Hàm thực hiện login vào hệ thống chính thông qua Webservices
     * @param user Tên đăng nhập
     * @param pass MẬt khẩu đăng nhập (Chưa mã hóa)
     * @return 1: Thành công, 2: Thất bại
     */
    public int Login(String user, String pass) {
        int kq = 0;
        try {
            //----- Khởi tạo đối tượng để thiết lập kết nối.
            SoapObject resquest = new SoapObject(NAME_SPACE, METHOD_LOGIN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            //----Thêm các tham số truyền vào phương thức addObjectNhanVien có trên Webservice.
            resquest.addProperty("user", user);
            resquest.addProperty("pass", pass);
            envelope.setOutputSoapObject(resquest);

            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_THANHVIEN);

            httpTransportSE.call(SOAP_LOGIN, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive item = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(item.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm lấy thông tin của 1 tài khoản
     * @param tenDangNhap tên đăng nhập cần lấy thông tin
     * @return Object taiKhoan
     */
    public iTaiKhoan getInforTaiKhoan(String tenDangNhap) {
        iTaiKhoan kq = new iTaiKhoan();
        try {
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETTAIKHOAN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            request.addProperty("tenDangNhap", tenDangNhap);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_THANHVIEN);
            httpTransportSE.call(SOAP_GETTAIKHOAN, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            kq.setMaTV(Integer.parseInt(result.getProperty("maTV").toString()));
            kq.setTenDangNhap(result.getProperty("tenDangNhap").toString());
            kq.setMaTV(Integer.parseInt(result.getProperty("maTV").toString()));
            kq.setMaNhomTK(Integer.parseInt(result.getProperty("maNhomTK").toString()));
            kq.setTrangThai(result.getProperty("trangThai").toString().equals("true") ? true : false);
            kq.setGhiChu(result.getProperty("ghiChu").toString());
            kq.setQuyenHan(result.getProperty("quyenHan").toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return kq;
    }

    /***
     * Hàm lấy thông tin của 1 thành viên có trên global database
     * @param maTV Mã thành viên cần lấy thông tin
     * @return Object thành viên
     */
    public iThanhVien getInforThanhVien(int maTV) {
        iThanhVien kq = new iThanhVien();
        try {
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETTHANHVIEN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            request.addProperty("maTV", maTV);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_THANHVIEN);
            httpTransportSE.call(SOAP_GETTHANHVIEN, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            //----------Gán dữ liệu đã nhận vào object---------------
            kq.setMaTV(Integer.parseInt(result.getProperty("maTV").toString()));
            kq.setHoTV(result.getProperty("hoTV").toString());
            kq.setTenTV(result.getProperty("tenTV").toString());
            kq.setGioiTinh(result.getProperty("gioiTinh").toString().equals("true") ? true : false);
            kq.setNgaySinh(xlDuLieu.convertDateFromString(result.getProperty("ngaySinh").toString()));
            kq.setNoiSinh(result.getProperty("noiSinh").toString());
            kq.setDiaChi(result.getProperty("diaChi").toString());
            kq.setSoDT(result.getProperty("soDT").toString());
            kq.setEmail(result.getProperty("Email").toString());
            kq.setFacebook(result.getProperty("Facebook").toString());
            kq.setSoCMND(result.getProperty("soCMND").toString());
            kq.setNgayCap(xlDuLieu.convertDateFromString(result.getProperty("ngayCap").toString()));
            kq.setNoiCap(result.getProperty("noiCap").toString());
            kq.setNgayThamGia(xlDuLieu.convertDateFromString(result.getProperty("ngayThamGia").toString()));
            kq.setGhiChu(result.getProperty("ghiChu").toString());
            kq.setHinhDD(result.getProperty("hinhDD").toString().getBytes());
            kq.setHinh(result.getProperty("hinhDD").toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request lên webservice để lấy danh sách thông báo của nhân viên
     * @return List object iThongBao
     */
    public List<iThongBao> getListThongBao(String tenDangNhap){
        List<iThongBao> kq = new ArrayList<iThongBao>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETTHONGBAO);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("tenDangNhap", tenDangNhap);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_THANHVIEN);
            httpTransportSE.call(SOAP_GETTHONGBAO, envelop);
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for (int i = 0; i < arrayObject.getPropertyCount(); i++) {
                SoapObject temp = (SoapObject) arrayObject.getProperty(i);
                if (temp.hasProperty("maThongBao")) { //------Nếu nhận được thuộc tính mã thông báo (Có thông báo)
                    iThongBao itemKQ = new iThongBao();
                    itemKQ.setMaThongBao(Integer.parseInt(temp.getProperty("maThongBao").toString()));
                    itemKQ.setNdThongBao(temp.getProperty("ndThongBao").toString());
                    itemKQ.setTaiKhoan(temp.getProperty("taiKhoan").toString());
                    itemKQ.setNgayTao(xlDuLieu.convertStringToDateDMYHMS(temp.getProperty("ngayTao").toString()));
                    itemKQ.setDaXem(temp.getProperty("daXem").toString().equals("true")?true:false);
                    itemKQ.setGhiChu(temp.getProperty("ghiChu").toString());
                    kq.add(itemKQ);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
}
