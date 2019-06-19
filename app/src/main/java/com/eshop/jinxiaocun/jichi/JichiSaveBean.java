package com.eshop.jinxiaocun.jichi;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: wenyongzhe
 * Date: 2019/6/18
 * Desc:  1.22计次项目销售  （t_rm_vip_stored_saleflow/ t_rm_vip_stored）
 */

public class JichiSaveBean extends BaseBean{

    public JichiSaveData JsonData;
    public JichiSaveBean() {
        setStrCmd(WebConfig.SaveCountSale);
        JsonData = new JichiSaveData();
    }
    public static class JichiSaveData{
        public String sheet_no;//xc1905300001” // 单号
        public String branch_no;//0001” // 单号
        public String consum_count;//" :1 // 本单消费次数
        public String oper_oper;//1001” //操作员
        public String oper_date;////2019-05-30 11：20：00” //操作日期时间
        public String memo;//”:””        //备注
        public String aprove;//”:”1”     //审核状态
        public String aprove_date;//”:” 2019-05-30 11：20：00”     //审核日期


    }
}
