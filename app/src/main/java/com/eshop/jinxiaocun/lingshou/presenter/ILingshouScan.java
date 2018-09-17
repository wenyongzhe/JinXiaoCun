package com.eshop.jinxiaocun.lingshou.presenter;

import java.util.List;

public interface ILingshouScan {
    public void getFlowNo();//去销售流水
    public void getSheetDetail(String sheetNo);//单据明细
    public void getPLUInfo(String barCode);//销售商品精确查询（编码）
    public void getPLULikeInfo(String barCode);//销售商品模糊查询
    public void getPluPrice();//销售商品取价
    public void sellSub(String flowno);//结算
    public void upSallFlow(List list);//上传销售流水
    public void upPlayFlow(List list);//上传付款流水
    public void getOptAuth();//获取折扣权限

}
