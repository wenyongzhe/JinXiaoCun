package com.eshop.jinxiaocun.xiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class DanJuMainBeanResult extends BaseResult{

    public List<DanJuMainBeanJson> JsonData;
    
    public class DanJuMainBeanJson{
        public String Flow_ID;    //时间流水ID   
        public String SheetType;       //单据类型 
        public String Sheet_No;        //单据号  
        public String Branch_No;    //当前门店/仓库 
        public String T_Branch_No;     //对方门店/仓库
        public String SupCust_No;      //供应商客户代码
        public String Voucher_No;     //引用单据号  
        public String Ord_Man_No;    //业务员   
        public String Check_Cls;     //盘点类别/品牌  
        public String Oper_Range;      //盘点范围
        public String Voucher_Type;    //入库方式 
        public String Valid_date; //交货日期  
        public String Payment; //付款方式
        public String AdjReason; //库存调整原因
        public String Ord_Amt;             //定金     
        public String PDA_No;	//PDA机号  
        public String USER_ID;       	//用户ID 
        public String Oper_Date; 	//操作日期 
        public String ComfirmMan;        //审核人员   
        public String ComfirmTime;     //审核时间
        public String Remark;                //备注    
        public String GoodsNum;	//总商品数
        public String ShopName;	//门店名称
        public String YHShopName; 	//要货门店名称   
        public String Oper_Name; 	//操作员名称
        public String SupplyName; 	//供应商机构名称 
                
    }


}
