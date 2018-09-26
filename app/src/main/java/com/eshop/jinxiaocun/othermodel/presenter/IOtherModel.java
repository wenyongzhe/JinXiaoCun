package com.eshop.jinxiaocun.othermodel.presenter;

import com.eshop.jinxiaocun.base.bean.BaseBean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/5
 * Desc:
 */

public interface IOtherModel {
    //获取业务单据号数据
    void getSheetNoData(BaseBean bean);
    //获取商品批次信息
    void getGoodsPiciInfo(BaseBean bean);
    //获取客户信息
    void getCustomerInfo(String type ,String sheetType ,String zjm,int pageIndex,int pageSize);
    //上传单据主表信息
    void uploadDanjuMainInfo(BaseBean bean);
    //上传单据明细信息
    void uploadDanjuDetailInfo(BaseBean bean);
    //SheetSave保存业务单据
    void sheetSave(String orderType ,String orderNo );
}
