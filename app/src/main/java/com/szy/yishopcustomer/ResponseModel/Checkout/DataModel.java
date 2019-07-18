package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.RcModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zongren on 16/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public List<AddressItemModel> address_list;
    public List<SendTimeItemModel> send_time_list;
    public CartInfoModel cart_info;
    public List<PayItemModel> pay_list;
    public ArrayList<InvoiceItemModel> invoice_info;
    public List invoice_desc;
    public boolean send_time_show;
    public RcModel rc_model;



}
