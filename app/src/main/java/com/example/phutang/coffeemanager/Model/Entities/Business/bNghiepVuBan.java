package com.example.phutang.coffeemanager.Model.Entities.Business;

import com.example.phutang.coffeemanager.AppCode.xlDuLieu;
import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iBanChoNgoi;
import com.example.phutang.coffeemanager.Model.Entities.iCtHoaDonTam;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;
import com.example.phutang.coffeemanager.Model.Entities.iHoaDonTam;
import com.example.phutang.coffeemanager.Model.Entities.iKhuVuc;
import com.example.phutang.coffeemanager.Model.Entities.iLoaiSanPham;
import com.example.phutang.coffeemanager.Model.Entities.iSanPham;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Class xử lý kết nối với webservice để xử lý nghiệp vụ danh cho người phục vụ
 */

public class bNghiepVuBan {
    private final String NAME_SPACE = "http://tempuri.org/";
    private final String METHOD_GETLISTNOTUSE = "layDanhSachBanTrong";
    private final String SOAP_GETLISTNOTUSE = NAME_SPACE + METHOD_GETLISTNOTUSE;

    private final String METHOD_GETLISTKHUVUC = "layDanhSachKhuVuc";
    private final String SOAP_GETLISTKHUVUC = NAME_SPACE + METHOD_GETLISTKHUVUC;

    private final String METHOD_GETLISTUSED = "layDanhSachBanTheoTrangThai";
    private final String SOAP_GETLISTUSED = NAME_SPACE + METHOD_GETLISTUSED;

    private final String METHOD_GETLISTTYPE = "layDanhSachLoaiSP";
    private final String SOAP_GETLISTTYPE = NAME_SPACE + METHOD_GETLISTTYPE;

    private final String METHOD_GETSANPHAMTHEOLOAI = "layDanhSachSanPhamTheoLoai";
    private final String SOAP_GETSANPHAMTHEOLOAI = NAME_SPACE + METHOD_GETSANPHAMTHEOLOAI;

    private final String METHOD_TIEPNHANBAN = "tiepNhanBanMoi";
    private final String SOAP_TIEPNHANBAN = NAME_SPACE + METHOD_TIEPNHANBAN;

    private final String METHOD_CAPNHATDAORDER = "capNhatDaOrder";
    private final String SOAP_CAPNHATDAORDER = NAME_SPACE + METHOD_CAPNHATDAORDER;

    private final String METHOD_THEMCHITIETH_HOADONTAM = "themChiTietHoaDonTam";
    private final String SOAP_THEM_HOADONTAM = NAME_SPACE + METHOD_THEMCHITIETH_HOADONTAM;

    private final String METHOD_LAYTONGTIEN_HOADONTAM = "layTongTienHoaDonTam";
    private final String SOAP_LAYTONGTIEN_HOADONTAM = NAME_SPACE + METHOD_LAYTONGTIEN_HOADONTAM;

    private final String METHOD_LAYCTHOADONTAM = "layCtHoaDonTam";
    private final String SOAP_LAYCTHOADONTAM = NAME_SPACE + METHOD_LAYCTHOADONTAM;

    private final String METHOD_TIEPNHANTHANHTOAN = "tiepNhanThanhToan";
    private final String SOAP_TIEPNHANTHANHTOAN = NAME_SPACE + METHOD_TIEPNHANTHANHTOAN;

    private final String METHOD_RESETBAN = "resetBan";
    private final String SOAP_RESETBAN = NAME_SPACE+ METHOD_RESETBAN;

    private final String METHOD_DOIBAN = "doiBan";
    private final String SOAP_DOIBAN = NAME_SPACE+ METHOD_DOIBAN;

    private final String METHOD_LAYBANPHUCVU = "layDanhSachPhucVu";
    private final String SOAP_LAYBANPHUCVU = NAME_SPACE + METHOD_LAYBANPHUCVU;

    private final String METHOD_LAYCHITIETPHUCVU = "layChiTietPhucVu";
    private final String SOAP_LAYCHITIETPHUCVU = NAME_SPACE + METHOD_LAYCHITIETPHUCVU;

    private final String METHOD_CAPNHATDAGIAO = "capNhatDaGiao";
    private final String SOAP_CAPNHATDAGIAO = NAME_SPACE + METHOD_CAPNHATDAGIAO;

    private final String METHOD_THONGKEBAN = "thongKeBanTheoTrangThai";
    private final String SOAP_THONGKEBAN = NAME_SPACE +  METHOD_THONGKEBAN;


    private final String URL_NGHIEPVUBAN = "http://210.2.88.252/models/Services/bnghiepvuban.asmx?wsdl";


    //region NHÓM HÀM LẤY DANH SÁCH

    //region NHÓM HÀM LẤY DANH SÁCH BÀN
    /**
     * Hàm lấy danh sách bàn trống (không có khách trên hệ thống)
     * Thông qua webservice
     * @param maKV mã khu vực cần lấy danh sách bàn
     * @return List object chứa danh sách iBanChoNgoi
     */
    public List<iBanChoNgoi> layDanhSachBanTrong(int maKV){
        List<iBanChoNgoi> kq = new ArrayList<iBanChoNgoi>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETLISTNOTUSE);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("maKV", maKV);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_GETLISTNOTUSE, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                iBanChoNgoi ban = this.ganDuLieuBan(temp);
                kq.add(ban);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm lấy danh sách các khu vực có trên hệ thống
     * @return
     */
    public List<iKhuVuc> layDanhSachKhuVuc(){
        List<iKhuVuc> kq = new ArrayList<iKhuVuc>();
        try{
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;

            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_GETLISTKHUVUC, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                iKhuVuc kv = new iKhuVuc();
                kv.setMaKhuVuc(Integer.parseInt(temp.getProperty("maKhuVuc").toString()));
                kv.setTenKhuVuc(temp.getProperty("tenKhuVuc").toString());
                kv.setDienGiai(temp.getProperty("dienGiai").toString());
                kv.setTongSucChua(Integer.parseInt(temp.getProperty("tongSucChua").toString()));
                kv.setGhiChu(temp.getProperty("ghiChu").toString());
                kq.add(kv);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }


    /**
     * Hàm lấy danh sách bàn dựa vào trạng thái hóa đơn
     * @param maKV
     * @return
     */
    public List<iBanChoNgoi> layDanhSachTheoTrangThai(int maKV, int trangThai){
        List<iBanChoNgoi> kq = new ArrayList<iBanChoNgoi>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETLISTUSED);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("maKV", maKV);
            request.addProperty("trangThai", trangThai);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_GETLISTUSED, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                iBanChoNgoi ban = this.ganDuLieuBan(temp);
                kq.add(ban);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gủi request lên server để lấy danh sách các bàn đã pha chế xong đang chờ giao cho khách
     * @return List Object iHoaDonTam
     */
    public List<iHoaDonTam> layDanhSachBanPhucVu(){
        List<iHoaDonTam> kq = new ArrayList<iHoaDonTam>();
        try{
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;

            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_LAYBANPHUCVU, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu iHoaDonTam
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                if (temp.hasProperty("maBan")) { //------Nếu nhận được thuộc tính mã bàn (Có bàn đang chờ giao)
                    iHoaDonTam itemKQ = new iHoaDonTam();
                    itemKQ.setMaBan(Integer.parseInt(temp.getProperty("maBan").toString()));
                    itemKQ.setTrangThaiHoaDon(Integer.parseInt(temp.getProperty("trangThaiHoadon").toString()));
                    itemKQ.setTrangThaiPhucVu(Integer.parseInt(temp.getProperty("trangthaiphucVu").toString()));
                    itemKQ.setNgayLap(xlDuLieu.convertStringToDateDMYHMS(temp.getProperty("ngayLap").toString()));
                    itemKQ.setThoiDiemDen(xlDuLieu.convertStringToDateDMYHMS(temp.getProperty("thoiDiemDen").toString()));
                    itemKQ.setNguoiPhucVu(temp.getProperty("nguoiPhucVu").toString());
                    itemKQ.setTongTien(Long.parseLong(temp.getProperty("tongTien").toString()));
                    itemKQ.setNguoiPhucVu(temp.getProperty("ghiChu").toString());
                    iBanChoNgoi banHoaDon = new iBanChoNgoi();
                    banHoaDon.setTenBan(temp.getProperty("tenBan").toString());
                    banHoaDon.setHinhAnh(temp.getProperty("hinhAnhBan").toString());
                    itemKQ.setBan(banHoaDon);
                    itemKQ.setSoLuongSanPham(Integer.parseInt((temp.getProperty("soLuongSanPham").toString())));
                    itemKQ.setDienGiaiChiTiet(temp.getProperty("dienGiaiChiTiet").toString());
                    kq.add(itemKQ);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gán dự liệu nhận được có trên SoapObject vào các thuộc tính của bàn
     * @param temp SoapObject chứa dữ liệu
     * @return object bàn đã có các thuộc tính
     */
    private iBanChoNgoi ganDuLieuBan(SoapObject temp){
        iBanChoNgoi ban = new iBanChoNgoi();
        ban.setMaBan(Integer.parseInt(temp.getProperty("maBan").toString()));
        ban.setTenBan(temp.getProperty("tenBan").toString());
        ban.setMaKhuVuc(Integer.parseInt(temp.getProperty("maKhuVuc").toString()));
        ban.setSucChua(Integer.parseInt(temp.getProperty("sucChua").toString()));
        ban.setHinhAnh(temp.getProperty("hinhAnh").toString());
        ban.setGioiThieu(temp.getProperty("gioiThieu").toString());
        ban.setTrangThai(Integer.parseInt(temp.getProperty("trangThai").toString()));
        ban.setGhiChu(temp.getProperty("ghiChu").toString());
        return ban;
    }



    //endregion

    //region NHÓM HÀM LẤY DANH SÁCH SẢN PHẨM

    /**
     * Hàm lấy danh sách tất cả loại sản phẩm có trên hệ thống
     * @return List object iLoaiSanPham
     */
    public List<iLoaiSanPham> layDanhSachLoaiSP(){
        List<iLoaiSanPham> kq = new ArrayList<iLoaiSanPham>();
        try{
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_GETLISTTYPE, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                SoapObject temp = (SoapObject)arrayObject.getProperty(i);
                iLoaiSanPham type = new iLoaiSanPham();
                type.setMaLoai(Integer.parseInt(temp.getProperty("maLoai").toString()));
                type.setTenLoai(temp.getProperty("tenLoai").toString());
                type.setDienGiai(temp.getProperty("dienGiai").toString());
                type.setGhiChu(temp.getProperty("ghiChu").toString());
                kq.add(type);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm lấy danh sách sản phẩm được phép order trên hệ thống
     * @param maLoai
     * @return
     */
    public List<iSanPham> laySanPhamTheoLoai(int maLoai){
        List<iSanPham> kq = new ArrayList<iSanPham>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_GETSANPHAMTHEOLOAI);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("maLoai", maLoai);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_GETSANPHAMTHEOLOAI, envelop);
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for (int i = 0; i < arrayObject.getPropertyCount(); i++) {
                SoapObject temp = (SoapObject) arrayObject.getProperty(i);
                if (temp.hasProperty("maSanPham")) { //------Nếu nhận được thuộc tính mã sản phẩm (Có sản phẩm)
                    iSanPham sp = new iSanPham();
                    sp.setMaSanPham(Integer.parseInt(temp.getProperty("maSanPham").toString()));
                    sp.setTenSanPham(temp.getProperty("tenSanPham").toString());
                    sp.setMaLoai(Integer.parseInt(temp.getProperty("maLoai").toString()));
                    sp.setMoTa(temp.getProperty("moTa").toString());
                    sp.setDonGia(Long.parseLong(temp.getProperty("donGia").toString()));
                    sp.setHinhAnh(temp.getProperty("hinhAnh").toString());
                    sp.setThoiGianPhaChe(Integer.parseInt(temp.getProperty("thoiGianPhaChe").toString()));
                    sp.setTrangThai(Integer.parseInt(temp.getProperty("trangThai").toString()));
                    sp.setGhiChu(temp.getProperty("ghiChu").toString());
                    kq.add(sp);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gủi request lên server để lấy danh sách các sản phẩm của 1 bàn cần giao
     * @param maBan mã bàn cần lấy danh sách
     * @return List object iGioHang
     */
    public List<iGioHang> layDanhSachSanPhamCanGiao(int maBan){
        List<iGioHang> kq = new ArrayList<iGioHang>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_LAYCHITIETPHUCVU);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("maBan", maBan);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_LAYCHITIETPHUCVU, envelop);
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for (int i = 0; i < arrayObject.getPropertyCount(); i++) {
                SoapObject temp = (SoapObject) arrayObject.getProperty(i);
                if (temp.hasProperty("maCtTam")) { //------Nếu nhận được thuộc tính chi tiết (Có sản phẩm)
                    iGioHang itemKQ = new iGioHang();
                    itemKQ.setMaSP(Integer.parseInt(temp.getProperty("maSP").toString()));
                    iSanPham sp = new iSanPham();
                    sp.setTenSanPham(temp.getProperty("tenSP").toString());
                    sp.setHinhAnh(temp.getProperty("hinhAnh").toString());
                    itemKQ.setSanPham(sp);
                    itemKQ.setSoLuong(Integer.parseInt(temp.getProperty("soLuong").toString()));
                    itemKQ.setDonGia(Long.parseLong(temp.getProperty("donGia").toString()));
                    itemKQ.setTrangThaiPhaChe(Integer.parseInt(temp.getProperty("trangThaiPhaChe").toString()));
                    kq.add(itemKQ);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    //endregion

    //region NHÓM HÀM LIÊN QUAN ĐẾN HOADONTAM

    /**
     * Hàm lấy tổng số tiền có trong hóa đơn tạm
     * @param maBan Mã bàn cần lấy tổng tiền
     * @return
     */
    public long layTongTienHoaDonTam(int maBan){
        long kq = 0;
        try{
            SoapObject resquest = new SoapObject(NAME_SPACE, METHOD_LAYTONGTIEN_HOADONTAM);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            resquest.addProperty("maBan", maBan);
            envelope.setOutputSoapObject(resquest);

            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);

            httpTransportSE.call(SOAP_LAYTONGTIEN_HOADONTAM, envelope);
            SoapPrimitive item = (SoapPrimitive) envelope.getResponse();
            kq = Long.parseLong(item.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm lấy danh sách các món đã order có trong hoaDonTam gán vào Session gioHang
     * @param maBan Mã bàn cấn lấy danh sách
     * @return
     */
    public List<iCtHoaDonTam> layCtHoaDonTam(int maBan){
        List<iCtHoaDonTam> kq = new ArrayList<iCtHoaDonTam>();
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_LAYCTHOADONTAM);
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;
            //-----Gửi request
            request.addProperty("maBan", maBan);
            envelop.setOutputSoapObject(request);
            new MarshalFloat().register(envelop);


            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_LAYCTHOADONTAM, envelop);
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for (int i = 0; i < arrayObject.getPropertyCount(); i++) {
                SoapObject temp = (SoapObject) arrayObject.getProperty(i);
                if (temp.hasProperty("maSP")) { //------Nếu nhận được thuộc tính mã sản phẩm (Có sản phẩm)
                    iGioHang item = new iGioHang();
                    item.setMaSP(Integer.parseInt(temp.getProperty("maSP").toString()));
                    item.setDonGia(Long.parseLong(temp.getProperty("donGia").toString()));
                    item.setSoLuong(Integer.parseInt(temp.getProperty("soLuong").toString()));
                    item.setTrangThaiPhaChe(Integer.parseInt(temp.getProperty("trangThaiPhaChe").toString()));
                    iSanPham sp = new iSanPham();
                    sp.setTenSanPham(temp.getProperty("tenSP").toString());
                    sp.setHinhAnh(temp.getProperty("hinhAnh").toString());
                    item.setSanPham(sp);
                    Session.listGioHang.add(item);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    //endregion
    //endregion

    //region NHÓM HÀM CẬP NHẬT TRẠNG THÁI

    /**
     * Hàm cập nhật trạng thái tiếp nhận bàn cho dữ liệu trên hệ thống
     * @param maBan Mã bàn cần tiếp nhận
     * @return 1: Lưu thành công - 2 Thất bại
     */
    public int tiepNhanBan(int maBan){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_TIEPNHANBAN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBan", maBan);
            request.addProperty("tenDangNhap", Session.iTaiKhoan.getTenDangNhap());
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_TIEPNHANBAN, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive item = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(item.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm thực hiện cập nhật trạng thái sang đã order của 1 bàn trên hệ thống để tiếp nhận pha chế
     * @param maBan Mã bàn cần cập nhật
     * @param tongTien tổng số tiền cần cập nhật
     * @param ghiChu ghi chú order của khách muốn thêm
     * @return 1: Cập nhật thành công
     */
    public int capNhatDaOrder(int maBan, long tongTien, String ghiChu){
        int kq = 0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_CAPNHATDAORDER);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBan", maBan);
            request.addProperty("tongTien", tongTien);
            request.addProperty("ghiChu", ghiChu);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_CAPNHATDAORDER, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive item = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(item.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request lên server để cập nhật trạng thái của bàn thành tiếp nhận thanh toán
     * Bàn sẽ không hiển thị trên danh mục bàn nữa
     * @param maBan Mã bàn cần cập nhật trạng thái
     * @return 1: Thành công - 2: Thất bại
     */
    public int tiepNhanThanhToan(int maBan){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_TIEPNHANTHANHTOAN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBan", maBan);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_TIEPNHANTHANHTOAN, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * hàm gửi request lên webservice thực hiện reset bàn khi khách rời khỏi
     * @param maBan Mã bàn cần reset
     * @return 1: thành công - 2: thất bại
     */
    public int resetBan(int maBan){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_RESETBAN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBan", maBan);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_RESETBAN, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request thông tin bàn cần đổi chổ
     * @param maBanCu Mã bàn cũ có yêu cầu đổi chổ
     * @param maBanMoi Mã bàn mới cần chuyển sang
     * @return 1: Thành công - 0: thất bại
     */
    public int doiCho(int maBanCu, int maBanMoi){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_DOIBAN);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBanCu", maBanCu);
            request.addProperty("maBanMoi", maBanMoi);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_DOIBAN, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm gửi request lên server để cập nhật trạng thái sang đã giao của bàn đã pha chế xong
     * @param maBan Mã bàn cần thực hiện cập nhật
     * @return 1: Thành công - 0: Thất bại
     */
    public int capNhatDaGiao(int maBan){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_CAPNHATDAGIAO);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            request.addProperty("maBan", maBan);
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_CAPNHATDAGIAO, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    //endregion


    //region Cập nhật bảng chi tiết hóa đơn tạm

    /**
     * Hàm thêm mới 1 chi tiết hóa đơn tạm trên hệ thống qua webservices
     * @param maBan Mã hóa đơn
     * @param item object iGioHang chứa các thuộc tính cần thêm vào bảng
     * @return 1: Thêm thành công
     */
    public int themChiTietTam(int maBan,iGioHang item){
        int kq=0;
        try{
            SoapObject request = new SoapObject(NAME_SPACE, METHOD_THEMCHITIETH_HOADONTAM);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            double gia = item.getDonGia();
            request.addProperty("maBan", maBan);
            request.addProperty("maSP", item.getMaSP());
            request.addProperty("donGia", item.getDonGia());
            request.addProperty("soLuong", item.getSoLuong());
            request.addProperty("trangThaiPhaChe", item.getTrangThaiPhaChe());
            envelope.setOutputSoapObject(request);
            new MarshalFloat().register(envelope);

            //----Thực hiện kết nối với Webservice
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_THEM_HOADONTAM, envelope);
            //-----Nhận kết quả trả về từ phương thức Login trong Webservice
            SoapPrimitive resultService = (SoapPrimitive) envelope.getResponse();
            //-----Gán kết quả
            kq = Integer.parseInt(resultService.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }


    //endregion

    public List<Integer> thongKeBanTheoTrangThai(){
        List<Integer> kq = new ArrayList<Integer>();
        try{
            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelop.dotNet=true;

            new MarshalFloat().register(envelop);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL_NGHIEPVUBAN);
            httpTransportSE.call(SOAP_THONGKEBAN, envelop);
            // Nhận Kết quả trả về từ Webservices là một mảng dữ liệu
            SoapObject arrayObject = (SoapObject) envelop.getResponse();
            for(int i=0; i<arrayObject.getPropertyCount(); i++){
                int item= Integer.parseInt(arrayObject.getProperty(i).toString());
                kq.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }


}
