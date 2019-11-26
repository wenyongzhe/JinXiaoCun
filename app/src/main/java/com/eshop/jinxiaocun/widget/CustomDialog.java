package com.eshop.jinxiaocun.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.CommonUtility;

/**
 * 自定义弹出对话框
 */

class CustomDialog extends Dialog {

	private Context mContext;
	private TextView title;
	private TextView message;
	private TextView positiveButton;
	private TextView negativeButton;
	private View mLayout;
	private View dividerView;

	CustomDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		initView();
	}

	public void initView(){
		this.mLayout = ((LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.dialog, null);
		this.title = ((TextView) this.mLayout.findViewById(R.id.title));
		this.message = ((TextView) this.mLayout
				.findViewById(R.id.message));
		this.positiveButton = ((TextView) this.mLayout
				.findViewById(R.id.positiveButton));
		this.negativeButton = ((TextView) this.mLayout
				.findViewById(R.id.negativeButton));
		this.dividerView = this.mLayout
				.findViewById(R.id.dividerView);
	}

	void setMessage(CharSequence message) {
		this.message.setText(message);
	}

	/**
	 * Set the Dialog message from resource
	 */
	void setMessage(int message) {
		this.message.setText(mContext.getString(message));
	}

	/**
	 * Set the Dialog title from resource
	 *
	 */
	public void setTitle(int title) {
		this.title.setText(mContext.getString(title));
	}

	/**
	 * Set the Dialog title from String
	 */

	public void setTitle(String title) {
		this.title.setText(title);
	}

	void setPositiveButton(int text,
						   View.OnClickListener listener) {
		this.positiveButton.setText(mContext.getString(text));
		this.positiveButton.setOnClickListener(listener);
	}

	/**
	 * Set the positive button resource and it's listener
	 */
	void setPositiveButton(CharSequence text,
						   View.OnClickListener listener) {
		this.positiveButton.setText(text);
		this.positiveButton.setOnClickListener(listener);
	}

	void setPositiveButton(View.OnClickListener listener) {
		this.positiveButton.setOnClickListener(listener);
	}

	public void setNegativeButtonVisibility(int visibility) {
		negativeButton.setVisibility(visibility);
		dividerView.setVisibility(visibility);
	}

	void setNegativeButtonDisable(boolean is){
		if(is){
			this.negativeButton.setVisibility(View.GONE);
		}else{
			this.negativeButton.setVisibility(View.VISIBLE);
		}
	}

	void setNegativeButton(int text,
						   View.OnClickListener listener) {
		this.negativeButton.setText(mContext.getString(text));
		this.negativeButton.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(this.mLayout);

		DisplayMetrics dm = CommonUtility.getInstance().getScreenWidth(mContext);
		int screen_width = dm.widthPixels;
		int screen_height = dm.heightPixels;
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(getWindow().getAttributes());
		if(screen_height>1080)
			localLayoutParams.width = screen_width-160;
		else
			localLayoutParams.width = screen_width-100;

		localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(localLayoutParams);
	}
}
