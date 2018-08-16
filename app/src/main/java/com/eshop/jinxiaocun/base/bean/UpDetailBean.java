package com.eshop.jinxiaocun.base.bean;

public class UpDetailBean extends BaseBean {
    private String Flow_ID;    //时间流水ID
    private String POSID;
    private String BillNo; //单据号
    private String SupplyCode;
    private String BarCode;//编码
    private String PluBatch;     //批次
    private String SelfCode; //自编码15
    private String Name;//名称
    private String Unit;  //单位
    private String StateCheck;     //标记当前商品是否已盘点
    private String CheckNum;      //盘点数量
    private String StockNum;
    private String ReferenceNum;   //参考数量
    private String BuyPrice;          //进价
    private String SalePrice;        //销价
    private String GoodsSeat;           //货架号
    private String MadeDate;   //生产日期
    private String VaildDate;   //有效日期
    private String Enable_batch;
    private String EndFlag;

    public String getFlow_ID() {
        return Flow_ID;
    }

    public void setFlow_ID(String flow_ID) {
        Flow_ID = flow_ID;
    }

    public String getPOSID() {
        return POSID;
    }

    public void setPOSID(String POSID) {
        this.POSID = POSID;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getSupplyCode() {
        return SupplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        SupplyCode = supplyCode;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getPluBatch() {
        return PluBatch;
    }

    public void setPluBatch(String pluBatch) {
        PluBatch = pluBatch;
    }

    public String getSelfCode() {
        return SelfCode;
    }

    public void setSelfCode(String selfCode) {
        SelfCode = selfCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getStateCheck() {
        return StateCheck;
    }

    public void setStateCheck(String stateCheck) {
        StateCheck = stateCheck;
    }

    public String getCheckNum() {
        return CheckNum;
    }

    public void setCheckNum(String checkNum) {
        CheckNum = checkNum;
    }

    public String getStockNum() {
        return StockNum;
    }

    public void setStockNum(String stockNum) {
        StockNum = stockNum;
    }

    public String getReferenceNum() {
        return ReferenceNum;
    }

    public void setReferenceNum(String referenceNum) {
        ReferenceNum = referenceNum;
    }

    public String getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        BuyPrice = buyPrice;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getGoodsSeat() {
        return GoodsSeat;
    }

    public void setGoodsSeat(String goodsSeat) {
        GoodsSeat = goodsSeat;
    }

    public String getMadeDate() {
        return MadeDate;
    }

    public void setMadeDate(String madeDate) {
        MadeDate = madeDate;
    }

    public String getVaildDate() {
        return VaildDate;
    }

    public void setVaildDate(String vaildDate) {
        VaildDate = vaildDate;
    }

    public String getEnable_batch() {
        return Enable_batch;
    }

    public void setEnable_batch(String enable_batch) {
        Enable_batch = enable_batch;
    }

    public String getEndFlag() {
        return EndFlag;
    }

    public void setEndFlag(String endFlag) {
        EndFlag = endFlag;
    }
}
