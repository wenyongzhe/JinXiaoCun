package com.eshop.jinxiaocun.xiaoshou.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_vip_normal_cert")
public class TVipNormalCertBean {

    @DatabaseField(columnName = "card_type")
    private String card_type;

    @DatabaseField(columnName = "cert_code")
    private String cert_code;

    @DatabaseField(columnName = "send_type")
    private String send_type;

    @DatabaseField(columnName = "send_num")
    private String send_num;

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCert_code() {
        return cert_code;
    }

    public void setCert_code(String cert_code) {
        this.cert_code = cert_code;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getSend_num() {
        return send_num;
    }

    public void setSend_num(String send_num) {
        this.send_num = send_num;
    }
}
