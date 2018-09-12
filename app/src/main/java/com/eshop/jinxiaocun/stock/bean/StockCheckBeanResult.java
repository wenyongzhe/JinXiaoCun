package com.eshop.jinxiaocun.stock.bean;

/**
 * @Author Lu An
 * 创建时间  2018/9/12 0012
 * 描述  商品库存查询返回的数据
 */

public class StockCheckBeanResult {

    private String ItemCode;//商品编码；
    private String ItemName;//	商品名称；
    private String ShopInfo;//门店信息；
    private String ItemSize;    //规格；
    private String ItemUnitNo;    //单位；
    private String ItemStock;//cBranch当前仓库库存；
    private String ItemBatchNo;//批号
    private String BeginDate;//起始时间
    private String EndDate;    //结束时间

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getShopInfo() {
        return ShopInfo;
    }

    public void setShopInfo(String shopInfo) {
        ShopInfo = shopInfo;
    }

    public String getItemSize() {
        return ItemSize;
    }

    public void setItemSize(String itemSize) {
        ItemSize = itemSize;
    }

    public String getItemUnitNo() {
        return ItemUnitNo;
    }

    public void setItemUnitNo(String itemUnitNo) {
        ItemUnitNo = itemUnitNo;
    }

    public String getItemStock() {
        return ItemStock;
    }

    public void setItemStock(String itemStock) {
        ItemStock = itemStock;
    }

    public String getItemBatchNo() {
        return ItemBatchNo;
    }

    public void setItemBatchNo(String itemBatchNo) {
        ItemBatchNo = itemBatchNo;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
