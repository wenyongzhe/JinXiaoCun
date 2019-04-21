package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/21
 * Desc: 积分兑换商品参数(查询积分商品)
 */

public class IntegralExchangeGoodsBean extends BaseBean {

    public IntegralExchangeGoodsParam JsonData;

    public IntegralExchangeGoodsBean() {
        setStrCmd(WebConfig.QryGiftInfo);
        JsonData = new IntegralExchangeGoodsParam();
    }

    public class IntegralExchangeGoodsParam {
        public String CardNo;  //卡号
        public String BranchNo; //机构号
        public String UserId;   //操作员
        public float VipAcc;  //可兑换积分

    }


}
