package com.eshop.jinxiaocun.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;


public class AlertUtil {
	private static CustomDialog mDialog;
	private static Context mContext;
	private static ProgressDialog mProgressDialog;

	public static void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		mDialog = null;
	}

	public static void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		mProgressDialog = null;
	}

	private static void dimBehind(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 1.0f;
		lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
	}

	public static String getString(int resId) {
		return mContext.getString(resId);
	}

	public static void showAlert(Context paramContext, CharSequence title,
                                 CharSequence message, CharSequence positiveText,
                                 View.OnClickListener listener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, listener);
		mDialog.setNegativeButtonDisable(true);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	public static void showAlert(Context paramContext, int title, int message,
                                 int positiveText, View.OnClickListener positiveBtnListener,
                                 int negativeText, View.OnClickListener negativeBtnListener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, positiveBtnListener);
		mDialog.setNegativeButton(negativeText, negativeBtnListener);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	public static void showAlert(Context paramContext, CharSequence title, CharSequence message,
								 CharSequence positiveText, View.OnClickListener positiveBtnListener,
								 int negativeText, View.OnClickListener negativeBtnListener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, positiveBtnListener);
		mDialog.setNegativeButton(negativeText, negativeBtnListener);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	/**
	 * 这个把message用String类型了，方便格式化
	 *
	 */
	public static void showAlert(Context paramContext, int title,
                                 CharSequence message, int positiveText,
                                 View.OnClickListener positiveBtnListener, int negativeText,
                                 View.OnClickListener negativeBtnListener) {
		showAlert(paramContext, title, R.string.dialog_title, positiveText,
				positiveBtnListener, negativeText, negativeBtnListener);
		mDialog.setMessage(message);
	}

	public static void showAlert(Context paramContext, String title,
                                 String message) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissDialog();
			}
		});
		mDialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();
			}
		});
		mDialog.show();
	}

	public static void showAlertSingle(Context paramContext,int title,
									   CharSequence message, int positiveText,
									   View.OnClickListener listener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, listener);
		mDialog.setNegativeButtonVisibility(View.GONE);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	// 无按钮dialog
	public static ProgressDialog showNoButtonProgressDialog(Context context,
                                                            String message) {
		mContext = context;
		mProgressDialog = ProgressDialog.show(context, "请稍等", "", true);
		mProgressDialog.setContentView(R.layout.progressbar_nobutton);
		((TextView) mProgressDialog
				.findViewById(R.id.progressbar_button_textview1))
				.setText(message);
		mProgressDialog.setCancelable(false);
		return mProgressDialog;
	}

	public static void setNoButtonMessage(String message){
		if(mProgressDialog!=null)
			((TextView) mProgressDialog.findViewById(R.id.progressbar_button_textview1))
					.setText(message);
	}

	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(int message, Context paramContext) {
//		Toast.makeText(paramContext, getString(message), Toast.LENGTH_LONG).show();
		Toast toast = Toast.makeText(paramContext, getString(message), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}
	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(String message, Context paramContext) {
//		Toast.makeText(paramContext, message, Toast.LENGTH_LONG).show();
		Toast toast = Toast.makeText(paramContext, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}

	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(int message) {
		Toast toast = Toast.makeText(Application.mContext, getString(message), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}
	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(String message) {
		Toast toast = Toast.makeText(Application.mContext, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}
}

