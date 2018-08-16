package com.eshop.jinxiaocun.base.bean;

public class UpMainBean extends BaseBean {
    private String Flow_ID;    //时间流水ID
    private String SheetType;       //单据类型                                
    private String Sheet_No;        //单据号                                 
    private String Branch_No;    //当前门店/仓库                           
    private String T_Branch_No;     //对方门店/仓库                           
    private String SupCust_No;      //供应商客户代码                         
    private String Voucher_No;     //引用单据号                             
    private String Ord_Man_No;    //业务员                                 
    private String Check_Cls;     //盘点类别/品牌                          
    private String Oper_Range;      //盘点范围                               
    private String Voucher_Type;    //入库方式                               
    private String Valid_date; //交货日期                               
    private String Payment; //付款方式                               
    private String AdjReason; //库存调整原因                           
    private String Ord_Amt;             //定金                                  
    private String PDA_No;	//PDA机号                                
    private String USER_ID;       	//用户ID                                 
    private String Oper_Date; 	//操作日期                               
    private String ComfirmMan;        //审核人员                               
    private String ComfirmTime;       //审核时间
    private String Remark;                //备注

    public String getFlow_ID() {
        return Flow_ID;
    }

    public void setFlow_ID(String flow_ID) {
        Flow_ID = flow_ID;
    }

    public String getSheetType() {
        return SheetType;
    }

    public void setSheetType(String sheetType) {
        SheetType = sheetType;
    }

    public String getSheet_No() {
        return Sheet_No;
    }

    public void setSheet_No(String sheet_No) {
        Sheet_No = sheet_No;
    }

    public String getBranch_No() {
        return Branch_No;
    }

    public void setBranch_No(String branch_No) {
        Branch_No = branch_No;
    }

    public String getT_Branch_No() {
        return T_Branch_No;
    }

    public void setT_Branch_No(String t_Branch_No) {
        T_Branch_No = t_Branch_No;
    }

    public String getSupCust_No() {
        return SupCust_No;
    }

    public void setSupCust_No(String supCust_No) {
        SupCust_No = supCust_No;
    }

    public String getVoucher_No() {
        return Voucher_No;
    }

    public void setVoucher_No(String voucher_No) {
        Voucher_No = voucher_No;
    }

    public String getOrd_Man_No() {
        return Ord_Man_No;
    }

    public void setOrd_Man_No(String ord_Man_No) {
        Ord_Man_No = ord_Man_No;
    }

    public String getCheck_Cls() {
        return Check_Cls;
    }

    public void setCheck_Cls(String check_Cls) {
        Check_Cls = check_Cls;
    }

    public String getOper_Range() {
        return Oper_Range;
    }

    public void setOper_Range(String oper_Range) {
        Oper_Range = oper_Range;
    }

    public String getVoucher_Type() {
        return Voucher_Type;
    }

    public void setVoucher_Type(String voucher_Type) {
        Voucher_Type = voucher_Type;
    }

    public String getValid_date() {
        return Valid_date;
    }

    public void setValid_date(String valid_date) {
        Valid_date = valid_date;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getAdjReason() {
        return AdjReason;
    }

    public void setAdjReason(String adjReason) {
        AdjReason = adjReason;
    }

    public String getOrd_Amt() {
        return Ord_Amt;
    }

    public void setOrd_Amt(String ord_Amt) {
        Ord_Amt = ord_Amt;
    }

    public String getPDA_No() {
        return PDA_No;
    }

    public void setPDA_No(String PDA_No) {
        this.PDA_No = PDA_No;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getOper_Date() {
        return Oper_Date;
    }

    public void setOper_Date(String oper_Date) {
        Oper_Date = oper_Date;
    }

    public String getComfirmMan() {
        return ComfirmMan;
    }

    public void setComfirmMan(String comfirmMan) {
        ComfirmMan = comfirmMan;
    }

    public String getComfirmTime() {
        return ComfirmTime;
    }

    public void setComfirmTime(String comfirmTime) {
        ComfirmTime = comfirmTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
