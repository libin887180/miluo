package com.zhongdi.miluo.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.ui.activity.market.BuyTiyanjinActivity;
import com.zhongdi.miluo.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * bananlar详情页面
 */
public class TiyanjinInfoActivity extends BaseActivity2 {

    @BindView(R.id.actAgreementPb)
    ProgressBar pbBar;
    @BindView(R.id.webView)
    BaseWebView webView;
    @BindView(R.id.title)
    TextView titleTv;
    String fundCode;
    String url ;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiyanjin_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
         url = URLConfig.TIYANJIN + SpCacheUtil.getInstance().getLoginAccount();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(this, "service");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    pbBar.setVisibility(View.GONE);
                } else {
                    if (pbBar.getVisibility() == View.GONE) {
                        pbBar.setVisibility(View.VISIBLE);
                    }
                    pbBar.setProgress(newProgress);
                }
            }
        });

        if (url != null) {
            ViseLog.i("// URL=  " + url);
            webView.loadUrl(url);
        }
    }

    @JavascriptInterface
    public void test1(String type, String code) {
        fundCode = code;
        switch (type) {
            case "1"://活动已结束

                break;
            case "2"://已登录 ，未开户 跳转到开户
                Intent open = new Intent(mContext, OpenAccountActivity.class);
                open.putExtra(IntentConfig.SOURCE, IntentConfig.TIYANJIN);
                startActivityForResult(open, 102);
                break;
            case "3"://已登录 已开户 ，进入购买
                Intent intent = new Intent(mContext, BuyTiyanjinActivity.class);
                intent.putExtra("fundCode", code);
                startActivity(intent);
                break;
            case "4"://已购买
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 102:
                if (resultCode == RESULT_OK) {
                    webView.loadUrl(url);
                    Intent intent = new Intent(mContext, BuyTiyanjinActivity.class);
                    intent.putExtra("fundCode", fundCode);
                    startActivity(intent);
                }
                break;
        }
    }
}
