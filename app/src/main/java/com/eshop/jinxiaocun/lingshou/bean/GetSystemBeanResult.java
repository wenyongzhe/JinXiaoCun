package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class GetSystemBeanResult extends BaseResult{

    private SystemJson jsonData;

    public SystemJson getJsonData() {
        return jsonData;
    }

    public void setJsonData(SystemJson jsonData) {
        jsonData = jsonData;
    }

    public class SystemJson{
        private String name;  // 名称
        private String value;    //值

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
