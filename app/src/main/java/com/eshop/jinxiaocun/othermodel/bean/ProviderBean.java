package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/28 0028
 * 描述 查询供应商
 */

public class ProviderBean extends BaseBean {

    public ProviderBean.ProviderJsonData JsonData;

    public ProviderBean (){
        setStrCmd(WebConfig.QrySupplierInfo);
        JsonData = new ProviderBean.ProviderJsonData();
    }

    public class ProviderJsonData{

        public String pos_id;
        public String user_id; //操作员
        public String type;
        public String branchNo;//机构号
        public String sheettype; //单据类型
        public String zjm;      //不生效
        public int page;
        public int pagenum;




    }

}
