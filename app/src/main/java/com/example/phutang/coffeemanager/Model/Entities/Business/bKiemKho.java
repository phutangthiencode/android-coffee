package com.example.phutang.coffeemanager.Model.Entities.Business;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.iCtTonKho;
import com.example.phutang.coffeemanager.Model.Entities.iThongBao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhuTang on 4/21/2018.
 */

public class bKiemKho {
    private final String NAME_SPACE = "http://tempuri.org/";
    private final String URL_KIEMKHO = "http://210.2.88.252/models/Services/bkiemkho.asmx?wsdl";

    private final String METHOD_LAYSOLUONG_LYTHUYET = "layTonKhoLyThuyet";
    private final String SOAP_LAYSOLUONG_LYTHUYET = NAME_SPACE + METHOD_LAYSOLUONG_LYTHUYET;

    private final String METHOD_THEMMOITONKHO = "themMoiTonKho";
    private final String SOAP_THEMMOITONKHO = NAME_SPACE + METHOD_THEMMOITONKHO;

    private final String METHOD_THEMCHITIETTONKHO = "themMoiChiTietTonKho";
    private final String SOAP_THEMCHITIETTONKHO = NAME_SPACE + METHOD_THEMCHITIETTONKHO;

    private final String METHOD_LAYTHONGBAOKIEMKHO = "getListThongBaoKiemKho";
    private final String SOAP_LAYTHONGBAOKIEMKHO = NAME_SPACE + METHOD_LAYTHONGBAOKIEMKHO;



    /**
     * Hàm lấy danh sách số lượng nguyên liệu tồn kho theo lý thuyết tại thời điểm hiện tại cho đến đợt kiểm kho của tháng trước
     * @return List object ctTonKho
     */
    public List<iCtTonKho> laySoLuongTonKhoLyThuyet(){
        List<iCtTonKho> kq = new ArrayList<iCtTonKho>();
        try{
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;

            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_KIEMKHO);
            httpTransportSE.call(SOAP_LAYSOLUONG_LYTHUYET, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                iCtTonKho ctKQ = new iCtTonKho();
                ctKQ.setMaSoKy(Integer.parseInt(temp.getProperty("maSoKy").toString()));
                ctKQ.setMaNguyenLieu(Integer.parseInt(temp.getProperty("maNguyenLieu").toString()));
                ctKQ.setDonGia(Long.parseLong(temp.getProperty("donGia").toString()));
                ctKQ.setSoLuongDauKy(Integer.parseInt(temp.getProperty("soLuongDauKy").toString()));
                ctKQ.setSoLuongCuoiKyLyThuyet(Integer.parseInt(temp.getProperty("soLuongCuoiKyLyThuyet").toString()));
                ctKQ.setTenNguyenLieu(temp.getProperty("tenNguyenLieu").toString());
                ctKQ.setHinhNguyenLieu(temp.getProperty("hinhNguyenLieu").toString());
                ctKQ.setDonViPhaChe(temp.getProperty("donViPhaChe").toString());
                kq.add(ctKQ);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request lên server để thực hiện thêm mới 1 tồn kho,
     * @param tenDangNhap Tên đăng nhập (Tên người kiểm kho)
     * @return Mã số kỳ tồn kho vừa thêm
     */
    public int themMoiTonKho(String tenDangNhap){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_THEMMOITONKHO);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("tenDangNhap", tenDangNhap);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_KIEMKHO);
            httpTransportSE.call(SOAP_THEMMOITONKHO, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request lên server để thêm mới 1 chi tiết tồn kho vào CSDL
     * @param ct Object chứa các thông tin chi tiết tồn kho
     * @return 2: Thành công
     */
    public int themChiTietTonKho(iCtTonKho ct){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_THEMCHITIETTONKHO);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maSoKy", String.valueOf(ct.getMaSoKy()));
            request.addProperty("maNguyenLieu", String.valueOf(ct.getMaNguyenLieu()));
            request.addProperty("donGia", String.valueOf(ct.getDonGia()));
            request.addProperty("soLuongDauKy", String.valueOf(ct.getSoLuongDauKy()));
            request.addProperty("soLuongCuoiKyLyThuyet", String.valueOf(ct.getSoLuongCuoiKyLyThuyet()));
            request.addProperty("soLuongThucTe", String.valueOf(ct.getSoLuongThucTe()));
            request.addProperty("tyLeHaoHut", String.valueOf(ct.getTyleHaoHut()));
            request.addProperty("nguyenNhan", ct.getNguyenNhanHaoHut());
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_KIEMKHO);
            httpTransportSE.call(SOAP_THEMCHITIETTONKHO, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }


    /**
     * Hàm gửi request lên webservice để lấy danh sách thông báo nhắc nhở kiểm kho
     * @return List object iThongBao
     */
    public List<iThongBao> getListThongBaoKiemKho(){
        List<iThongBao> kq = new ArrayList<iThongBao>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_LAYTHONGBAOKIEMKHO);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_KIEMKHO);
            httpTransportSE.call(SOAP_LAYTHONGBAOKIEMKHO, envelop);
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for (int i = 0; i < arrayObject.getPropertyCount(); i++) {
                SoapObject temp = (SoapObject) arrayObject.getProperty(i);
                if (temp.hasProperty("maThongBao")) { //------Nếu nhận được thuộc tính mã thông báo (Có thông báo)
                    iThongBao itemKQ = new iThongBao();
                    itemKQ.setMaThongBao(Integer.parseInt(temp.getProperty("maThongBao").toString()));
                    itemKQ.setNdThongBao(temp.getProperty("ndThongBao").toString());
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
