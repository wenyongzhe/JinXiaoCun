package com.eshop.jinxiaocun.lingshou.presenter;

import java.util.List;

public interface ILingshouScan {
    public void getFlowNo();//去销售流水
    public void getSheetDetail(String sheetNo);//单据明细
    public void getPLUInfo(String barCode);//销售商品精确查询（编码）
    public void getPLULikeInfo(String barCode);//销售商品模糊查询
    public void getPluPrice(String flow_no,int isBillDiscount);//销售商品取价
    public void sellSub(String flowno);//结算
    public void upSallFlow(List list,int isBillDiscount);//上传销售流水
    public void upPlayFlow(List list);//上传付款流水
    public void getOptAuth(String ai_grant);//获取折扣权限
    public void getBillDiscount(Double total,String FlowNo);//整单议价、折扣
    public void getPayMode();//付款方式
    public void RtWzfPay(String payWay,String auth_code,String flowNo,String payAmount,String totalAmount);//网络支付扣款
    public void sellVipPay(String name, String password,Double money);

}
