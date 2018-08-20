package com.android.dev;

import com.android.dev.BarcodeAPI;
import com.eshop.jinxiaocun.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ScanDemoActivity extends Activity {

	TextView tv_scan2_result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_scan2);
		tv_scan2_result=(TextView)findViewById(R.id.tv_scan2_result);

	}
	
	@Override
	protected void onResume() { 
		super.onResume();
		BarcodeAPI.getInstance().m_handler = mHandler;
	}
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BarcodeAPI.BARCODE_READ:				 
				tv_scan2_result.setText(tv_scan2_result.getText() + (String) msg.obj + "\n");
	 
				break;

			}
		}
	};

	
	public void scan(View v){
		BarcodeAPI.getInstance().scan();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_INFO:
		case KeyEvent.KEYCODE_MUTE:
			if (event.getRepeatCount() == 0) {
				BarcodeAPI.getInstance().scan();
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
