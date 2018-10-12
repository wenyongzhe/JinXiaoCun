package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/10/12 0012
 * 描述
 */

public class CiteOrderBean extends BaseBean {

    public CiteOrderBean.CiteOrder JsonData;

    public CiteOrderBean(){
        setStrCmd(WebConfig.QryVouSheetHead);
        JsonData = new CiteOrderBean.CiteOrder();
    }

    public class CiteOrder{
        public String pos_id;
        public String branchNo;
        public String sheettype;
        public String begintime;//开始时间
        public String endtime;//结束时间
        public String checkflag;//审核标志
        public int pagenum;//每页数量
        public int page;//页码
        public String operid; //可以是操作员、单号
    }

}
