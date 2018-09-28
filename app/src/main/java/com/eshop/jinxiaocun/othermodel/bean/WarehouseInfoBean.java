package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/28 0028
 * 描述 仓库查询参数
 */

public class WarehouseInfoBean extends BaseBean {

    public WarehouseInfoBean.WarehouseInfo JsonData;

    public WarehouseInfoBean(){
        setStrCmd(WebConfig.GetBranchInfo);
        JsonData = new WarehouseInfoBean.WarehouseInfo();
    }

    public class WarehouseInfo{
        public String pos_id;
        public String user_id;  //操作员
        public String type; // Y  是要获取 branch_no  字段的值 P  是获取   d_branch_no  字段的值
        public String branchNo;  //机构号
        public String sheettype; //单据类型
        public String zjm; //不生效
        public int page;  //不生效
        public int pagenum;//不生效
    }

}
