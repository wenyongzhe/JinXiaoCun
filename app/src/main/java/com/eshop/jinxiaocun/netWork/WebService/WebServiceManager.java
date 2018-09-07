package com.eshop.jinxiaocun.netWork.WebService;

import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.WebConfig;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebServiceManager {

    public SoapObject action(String strCmd, String jsonData) throws IOException, XmlPullParserException {
        SoapObject soapObject=new SoapObject(WebConfig.getNameSpace(), "PostData");
        soapObject.addProperty("DevID", Config.DeviceID);
        soapObject.addProperty("SoftVer", Config.VersionCode);
        soapObject.addProperty("strCmd", strCmd);
        soapObject.addProperty("jsonData", jsonData);
        return accessWcf(soapObject, "PostData", WebConfig.getTimeOut());
    }

    private SoapObject accessWcf(SoapObject soapObject, String methodName, int timeout) throws IOException , XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        //envelope.addMapping(ChangeStaffEntity.NAMESPACE, "ChangeStaffEntity", ChangeStaffEntity.class);
        envelope.setOutputSoapObject(soapObject);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        HttpTransportSE transportSE = new HttpTransportSE(WebConfig.getPostWsdlUri(), timeout);
        transportSE.debug = true;//使用调式功能
//        transportSE.call(WebConfig.getNameSpace() + methodName, envelope);
        transportSE.call(null, envelope);
        return (SoapObject) envelope.bodyIn;
    }

}
