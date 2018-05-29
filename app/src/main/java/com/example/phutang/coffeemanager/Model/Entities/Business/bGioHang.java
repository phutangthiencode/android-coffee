package com.example.phutang.coffeemanager.Model.Entities.Business;

import com.example.phutang.coffeemanager.Model.Entities.Session;
import com.example.phutang.coffeemanager.Model.Entities.iGioHang;

/**
 * Created by PhuTang on 4/6/2018.
 */

public class bGioHang {

    public static long tongTienDtb; //-------Biến lưu trữ tổng tiền của hóa đơn tạm đã order

    /**
     * Hàm thêm mới một sản phẩm vào giỏ
     * @param item Object mỗi item trong giỏ hàng
     */
    public int addCart(iGioHang item){
        int kq=0;
        try{
            //-----------Nếu sản phẩm chưa tồn tại thì thêm mới vào list
            if(!this.checkExists(item.getMaSP())) {
                Session.listGioHang.add(item);
                kq++;
            }
            else{
                iGioHang oldItem = this.getItemInfoForUpdate(item.getMaSP());
                int soLuongCu = oldItem.getSoLuong();
                kq=this.updateCart(oldItem, ++soLuongCu);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm kiểm tra sản phẩm đã có trong giỏ
     * @param maSP mã sản phẩm cần kiểm tra
     * @return True: Đã tồn tại, False: chưa tồn tại
     */
    private boolean checkExists(int maSP){
        try{
            for(iGioHang item : Session.listGioHang){
                //------Trạng thái chưa tiếp nhận thì cho phép thêm mới vào giỏ
                if(item.getMaSP() == maSP && item.getTrangThaiPhaChe()<=0)
                    return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Hàm cập nhật lại số lượng cho 1 item trong cart
     * @param newItem Item mới cần cập nhật
     * @param quatity Số lượng cần cập nhật
     */
    public int updateCart(iGioHang newItem, int quatity){
        int kq=0;
        try{
            for(iGioHang oldItem :Session.listGioHang){
                if(oldItem.getMaSP() == newItem.getMaSP() && oldItem.getTrangThaiPhaChe()<=0){
                    //-----------Xóa item cũ trong giỏ
                    this.deleteCart(oldItem.getMaSP());
                    //-----------Thêm item mới
                    newItem.setSoLuong(quatity);
                    Session.listGioHang.add(newItem);
                    kq++;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm xóa bỏ 1 sản phẩm khỏi giỏ hàng
     * @param maSP Mã sản phẩm cần xóa
     */
    public int deleteCart(int maSP){
        int kq=0;
        try{
            for(iGioHang itemDlt : Session.listGioHang){
                if(itemDlt.getMaSP() == maSP && itemDlt.getTrangThaiPhaChe()<=0){
                    Session.listGioHang.remove(itemDlt);
                    kq++;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    /**
     * Hàm xóa tất cả các sản phẩm trong giỏ
     * @return
     */
    public int deleteAllItem(){
        int kq=0;
        try{
            for(iGioHang item : Session.listGioHang)
            {
                if(item.getTrangThaiPhaChe()<=0) //-Sản phẩm chưa tiếp nhận pha chế thì cho xóa
                    Session.listGioHang.remove(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }



    /**
     * Hàm lấy thông tin sản phẩm được update số lượng
     * Có trạng thái pha chế chưa tiếp nhận
     * @param maSP
     * @return
     */
    public iGioHang getItemInfoForUpdate(int maSP){
        try{
            for(iGioHang item : Session.listGioHang)
                if(item.getMaSP() == maSP && item.getTrangThaiPhaChe()<=0)
                    return item;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Hàm lấy tổng số tiền có trong hóa đơn đang order
     * @return
     */
    public long getTotalMoney(){
        long kq=0;
        try{
            for(iGioHang item : Session.listGioHang){
                kq+=(item.getSoLuong()*item.getDonGia());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }


}
