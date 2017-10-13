package com.zhongdi.miluo.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * webview控件
 * 
 */
@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class BaseWebView extends WebView {

	private Context c = null;
	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public BaseWebView(Context context) {
		super(context);
		this.c = context;

		initSetting();
	}

	public BaseWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.c = context;

		initSetting();
	}

	public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.c = context;

		initSetting();
	}

	/*
	 * 初始化属性。
	 */
	private void initSetting() {
		WebSettings webSettings = getSettings();
		webSettings.setSupportMultipleWindows(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true); // 支持缩放
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setAppCacheEnabled(false);
		webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
		webSettings.setDatabaseEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持JS

		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		this.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}

//			@Override
//			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//				super.onReceivedSslError(view, handler, error);
//				handler.proceed();
//			}
		});
	}

	/**
	 * 添加js调用本地接口方法
	 * 
	 * @param webview
	 */
	public void addJavaScriptInterface(WebView webview, Activity a) {
		webview.addJavascriptInterface(new JavaScriptInterface(c, a),
				"AndroidFunction");
	}

	class JavaScriptInterface {
		Context mContext;
		Activity mActivity;

		JavaScriptInterface(Context context, Activity a) {
			mContext = context;
			mActivity = a;
		}

		/**
		 * 去拍照
		 */
		@JavascriptInterface
		public void toCamera() {
			//mActivity.startActivityForResult(BaseUtils.toCamera(mContext), Const.CAMERA);
		}

		/**
		 * 相册
		 */
		@JavascriptInterface
		public void localImage() {
			//mActivity.startActivityForResult(BaseUtils.localImage(), Const.LOCAL_IMAGE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(canGoBack())
			{
				goBack();//返回上一页面
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
