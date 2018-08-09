package com.eshop.jinxiaocun.netWork.WebService;

import com.eshop.jinxiaocun.login.Bean.LoginBean;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.GsonUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebServiceManager {

    public SoapObject action(String methodName, String jsonData) throws IOException, XmlPullParserException {
        SoapObject soapObject=new SoapObject(WebConfig.getNameSpace(), methodName);
        soapObject.addProperty("DevID", Config.DeviceID);
        soapObject.addProperty("SoftVer", Config.VersionCode);
        soapObject.addProperty("JsonData", jsonData);
        return accessWcf(soapObject, methodName, WebConfig.getTimeOut());
    }

    private SoapObject accessWcf(SoapObject soapObject, String methodName, int timeout) throws IOException , XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //envelope.addMapping(ChangeStaffEntity.NAMESPACE, "ChangeStaffEntity", ChangeStaffEntity.class);
        envelope.setOutputSoapObject(soapObject);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        HttpTransportSE transportSE = new HttpTransportSE(WebConfig.getWsdlUri(), timeout);
        transportSE.debug = true;//使用调式功能
        transportSE.call(WebConfig.getNameSpace() + methodName, envelope);
        return (SoapObject) envelope.bodyIn;
    }

}
