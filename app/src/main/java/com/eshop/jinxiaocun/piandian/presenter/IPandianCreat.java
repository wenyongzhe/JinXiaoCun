package com.eshop.jinxiaocun.piandian.presenter;

import com.eshop.jinxiaocun.base.bean.BaseBean;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public interface IPandianCreat {

    //取盘点范围数据
    void getPandianFanweiData(BaseBean bean);
    //取盘点类别数据
    void getPandianTypeData(BaseBean bean);
    //取盘点门店机构数据
    void getPandianStoreJigouData(BaseBean bean);
    //盘点批号生成
    void getPandianPihaoCreateData(BaseBean bean);

}
