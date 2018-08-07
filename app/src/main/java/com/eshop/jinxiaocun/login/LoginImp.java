package com.eshop.jinxiaocun.login;

import android.os.AsyncTask;
import android.os.Handler;

import com.eshop.jinxiaocun.utils.Config;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class LoginImp extends AsyncTask implements ILogin {

    private int timeOut = 5 * 1000 * 60;
    private Handler mHandler;

    public LoginImp(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void loginAction(String userName, String passWord) {

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    public SoapObject login(String orgCode, long position, int bufferlen) throws IOException, XmlPullParserException {
        // TODO Auto-generated method stub
        String methodName = "POS-LOGIN";
        SoapObject soapObject=new SoapObject(Config.getNameSpace(), methodName);
        soapObject.addProperty("pdaNo", orgCode);
        soapObject.addProperty("position", position);
        soapObject.addProperty("bufferlen", bufferlen);
        return accessWcf(soapObject, methodName, timeOut);
    }

    private SoapObject accessWcf(SoapObject soapObject, String methodName, int timeout) throws IOException , XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //envelope.addMapping(ChangeStaffEntity.NAMESPACE, "ChangeStaffEntity", ChangeStaffEntity.class);
        envelope.setOutputSoapObject(soapObject);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        HttpTransportSE transportSE = new HttpTransportSE(Config.getWebUrl(), timeout);
        transportSE.debug = true;//使用调式功能
        transportSE.call(Config.getWebUrl() + methodName, envelope);
        return (SoapObject) envelope.bodyIn;
    }
}
