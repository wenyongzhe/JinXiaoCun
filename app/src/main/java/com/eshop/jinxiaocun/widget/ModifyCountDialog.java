package com.eshop.jinxiaocun.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyCountDialog extends Activity implements  KeyboardAdapter.OnKeyboardClickListener, View.OnClickListener {

    @BindView(R.id.txtCountN)
    EditText txtCountN;
    @BindView(R.id.iv_plus)
    ImageView iv_plus;
    @BindView(R.id.iv_minus)
    ImageView iv_minus;


//    @BindView(R.id.tv_key)
//    KeyboardView keyboardView;

    private List<String> keyboardNumbers;
    private boolean refreshStartPoint = true;
    float startPointEvent, stopPointEvent;
    GestureDetector mDetector;
    private int mScreenWidth = 0;
    int curatorIndex;
    Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_count);

        ButterKnife.bind(this);

//        keyboardView.setOnKeyBoardClickListener( this);
//        keyboardView.recyclerView.setOnTouchListener(onTouchListener);

//        keyboardNumbers = keyboardView.getKeyboardWords();

        txtCountN.setFocusable(true);
        txtCountN.setFocusableInTouchMode(true);
        txtCountN.requestFocus();

        Intent intent = getIntent();
        txtCountN.setText(intent.getStringExtra("countN"));
        txtCountN.selectAll();
//        txtCountN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!keyboardView.isVisible()) {
//                    keyboardView.show();
//                }
//            }
//        });

//        closeEditTextKeyboard();
        initGestureDetector();

        int screen_width = DensityUtil.getInstance().getScreenWidth(this);
        int screen_height = DensityUtil.getInstance().getScreenHeight(this);

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(getWindow().getAttributes());
        if(screen_height>1080)
            localLayoutParams.width = screen_width-160;
        else
            localLayoutParams.width = screen_width-100;
        localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);
        mH.sendEmptyMessageDelayed(2,300);

        iv_plus.setOnClickListener(this);
        iv_minus.setOnClickListener(this);
    }

    Handler mH = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSoftKeyboard();
        }
    };

    /**
     * Get the start and stop position:
     * 1. Start position: the first event of 'ACTION_MOVE', because of 'ACTION_DOWN' event is gone
     * 2. Stop position: the last event of 'ACTION_MOVE'
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //Log.d(TAG, "MotionEvent.ACTION_DOWN");
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                //Log.d(TAG, "MotionEvent.ACTION_UP");
                refreshStartPoint = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (refreshStartPoint) {
                    startPointEvent = motionEvent.getX(0);
                    curatorIndex = txtCountN.getSelectionStart();
                }
                refreshStartPoint = false;
                stopPointEvent = motionEvent.getX();
                //Log.d(TAG, "MotionEvent.ACTION_MOVE");
            }
            return mDetector.onTouchEvent(motionEvent);
        }
    };

    private void closeEditTextKeyboard() {
        MyUtils.closeKeyboard(this, txtCountN);
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
        if (txtCountN.getText().toString().trim().equals("")) {
            MyUtils.showToast("请输入数量！", this);
            return;
        }

        if (txtCountN.getText().toString().trim().equals("0")) {
            MyUtils.showToast("请输入大于0的数量！", this);
            return;
        }

        String countN = txtCountN.getText().toString().trim();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ModifyCountDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(!TextUtils.isEmpty(countN)){
            Intent intent = new Intent();
            intent.putExtra("countN",countN);
            setResult(RESULT_OK, intent);
            finish();
        }else {
            MyUtils.showToast("数量不能为空。",ModifyCountDialog.this);
        }
    }

    @OnClick(R.id.btn_cancel)
    void OnCancel()
    {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(ModifyCountDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        try {
            String editText = txtCountN.getText().toString();
            int count = 1;
            switch (position) {
                case 12:
                    count = Integer.decode(editText)+1;
                    txtCountN.setText(count+"");
                    break;
                case 14:
                    if(Integer.decode(editText)>0){
                        count = Integer.decode(editText)-1;
                        txtCountN.setText(count+"");
                    }
                    break;
                case 9:
                    break;
                default:
                    String str = txtCountN.getText().toString().trim();
                    //获取当前光标位置
                    int indexStart = txtCountN.getSelectionStart();
                    int indexEnd = txtCountN.getSelectionEnd();
                    if(indexStart != indexEnd){
                        String str2 = str.substring(0,indexStart)+str.substring(indexEnd,str.length());
                        txtCountN.setText(str2);
                        txtCountN.setSelection(indexStart);
                    }

                    if (indexStart != txtCountN.getText().length()) {
                        //在光标处插入数字
                        String inputEditText = keyboardNumbers.get(position);
                        //Log.d(TAG, "inputEditText:" + inputEditText);
                        txtCountN.getText().insert(indexStart, inputEditText);
                    } else {
                        txtCountN.setText(txtCountN.getText().toString().trim() + keyboardNumbers.get(position));
                        txtCountN.setSelection(txtCountN.getText().length());
                    }
                    if (txtCountN.getText().length() > 0) {
                        //设置按钮颜色以及右删除图标为可见
                        //findViewById(R.id.ai_long_tv_confirm).setBackgroundResource(R.drawable.bg_determine_text);
    //                    Drawable drawable = getResources().getDrawable(R.drawable.ic_delete1);
    //                    txtCountN.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, drawable, null);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. Calculate the distance between start position and stop position
     * 2. Get the direction of gesture
     * 3. Set a new cursor position of EditText by the distance and direction
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initGestureDetector() {
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                //Log.d(TAG, "onDown");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent)
            {
            //    Log.d(TAG, "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                //Log.d(TAG, "onShowPress");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                mScreenWidth = displayMetrics.widthPixels;
                float x = stopPointEvent - startPointEvent;
                float newPercent = Math.abs(x) / mScreenWidth;
                int range = (int) ((txtCountN.getText().length()) * newPercent);
                int newIndex;
                if (x > 0) {
                    newIndex = curatorIndex + range;
                    if (curatorIndex == txtCountN.getText().length()) {
                        txtCountN.setSelection(curatorIndex);
                    }
                    if (newIndex > txtCountN.getText().length()) {
                        txtCountN.setSelection(txtCountN.getText().length());
                    } else {
                        txtCountN.setSelection(newIndex);
                    }
                } else {
                    newIndex = curatorIndex - range;
                    if (curatorIndex == 0) {
                        txtCountN.setSelection(curatorIndex);
                    }
                    if (newIndex < 0) {
                        txtCountN.setSelection(0);
                    } else {
                        txtCountN.setSelection(newIndex);
                    }
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

                //Log.d(TAG, "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                // startPointEvent=motionEvent1;
                //Log.d(TAG, "onFling: " + "motionEvent:" + motionEvent + "motionEvent1" + motionEvent1);
                return false;
            }
        });
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        int currentIndex = txtCountN.getSelectionStart();
        if (currentIndex > 0) {
            txtCountN.getText().delete(currentIndex - 1, currentIndex);
//            if (txtCountN.getText().length() == 0) {
//                txtCountN.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);
//                findViewById(R.id.ai_long_tv_confirm).setBackgroundResource(R.drawable.bg_determine_disable);
//            }
        }
    }

    @Override
    public void onClick(View view) {
        String editText = txtCountN.getText().toString();
        if(editText == null || editText.equals("")){
            editText = "0";
        }
        int count = 1;
        switch (view.getId()){
            case R.id.iv_plus:
                count = Integer.decode(editText)+1;
                txtCountN.setText(count+"");
                break;
            case R.id.iv_minus:
                if(Integer.decode(editText)>0) {
                    count = Integer.decode(editText) - 1;
                    txtCountN.setText(count + "");
                }
                break;
        }
    }
}
