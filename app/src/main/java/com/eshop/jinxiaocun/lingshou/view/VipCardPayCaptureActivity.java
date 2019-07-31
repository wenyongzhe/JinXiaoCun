package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eshop.jinxiaocun.R;
import com.google.zxing.Result;
import com.zxing.android.BeepManager;
import com.zxing.android.CaptureActivity;
import com.zxing.android.InactivityTimer;

public class VipCardPayCaptureActivity extends CaptureActivity {

    RadioGroup rg_vip_car;
    RadioButton rb_e_vip;
    RadioButton rb_vip;
    boolean isECard = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.vip_pay_capture);

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        imageButton_back = (ImageButton) findViewById(R.id.capture_imageview_back);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rg_vip_car=(RadioGroup)findViewById(R.id.rg_vip_car);
        rb_e_vip=(RadioButton)findViewById(R.id.rb_e_vip);
        rb_vip=(RadioButton)findViewById(R.id.rb_vip);

        rg_vip_car.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rb_e_vip.isChecked()){
                    isECard = true;
                }else {
                    isECard = false;
                }
            }
        });
        rb_e_vip.setChecked(true);
    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();

            //Toast.makeText(this, "扫描成功", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            intent.putExtra("codedContent", rawResult.getText());
            intent.putExtra("codedBitmap", barcode);
            intent.putExtra("isECard", isECard);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
