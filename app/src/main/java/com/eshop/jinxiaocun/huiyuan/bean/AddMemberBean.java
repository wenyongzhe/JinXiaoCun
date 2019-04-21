package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/20
 * Desc: 新增会员参数
 */

public class AddMemberBean extends BaseBean {

    public AddMemberParam JsonData;

    public AddMemberBean(){
        setStrCmd(WebConfig.HuiYuanWeiHu);
        JsonData = new AddMemberParam();
    }

    public class AddMemberParam{
        public String CardNo_TelNo;//会员卡号,
        public String OperInfo;  //操作人员
        public String Branch_No;  //机构编号
        public String Mobile;   //手机号,
        public String Tel;       //电话号码
        public String MemberType; //会员类型
        public String Name;      //姓名
        public String BirthDay;  //生日
        public String Sex;		//性别
        public String Memo;   //备注
    }



}
