package com.eshop.jinxiaocun.jichi;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: wenyongzhe
 * Date: 2019/6/18
 * Desc:  1.21查询计次项目  （t_rm_vip_stored）
 */

public class JichiChaxunBean extends BaseBean{

    public JichiChaxunData JsonData;
    public JichiChaxunBean() {
        setStrCmd(WebConfig.QryCountInfo);
        JsonData = new JichiChaxunData();
    }
    public static class JichiChaxunData{
        public String sheet_no;// 单号，电话，客户号，客户名称

    }
}
