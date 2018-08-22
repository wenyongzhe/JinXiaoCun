package com.eshop.jinxiaocun.lingshou.presenter;

public interface ILingshouScan {

    public void getFlowNo();//去销售流水

    public void getPLUInfo(String barCode);//销售商品精确查询（编码）
    public void getSheetDetail(String sheetNo);//单据明细
}
