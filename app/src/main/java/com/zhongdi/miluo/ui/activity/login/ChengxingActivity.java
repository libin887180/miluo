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
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.ui.activity.market.BuyFundActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * bananlar详情页面
 */
public class ChengxingActivity extends BaseActivity2 {

    @BindView(R.id.actAgreementPb)
    ProgressBar pbBar;
    @BindView(R.id.webView)
    BaseWebView webView;
    @BindView(R.id.title)
    TextView titleTv;
    String  type;
    String  fundCode;
    String  id;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        fundCode = getIntent().getStringExtra("code");
        id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_chenxing);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = URLConfig.CHEN_XING+"type="+type+"fundCode="+fundCode+"id="+id;
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
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
    public void test2(String to, String type, String code,String  fundId) {

        switch (to){
            case "1"://购买方法
                if (!MyApplication.getInstance().isLogined) {
                    Intent intent = new Intent(mContext, QuickLoginActivity.class);
                    startActivityForResult(intent, 101);
                    return;
                }
                Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
                buyIntent.putExtra("fundCode", code);
                startActivity(buyIntent);
                break;
            case "0"://详情方法
                Intent intent;
                if(type.equals(MiluoConfig.HUOBI)||type.equals(MiluoConfig.DUANQI)){
                    intent  = new Intent(mContext, FundCurrencyDetailActivity.class);
                }else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId",fundId);
                intent.putExtra("fundcode",code);
                ViseLog.i("fundid-->"+fundId);
                startActivity(intent);

                break;
        }

    }
    @JavascriptInterface
    public void test3(String to) {
        switch (to){
            case "1"://跳转体验金
                Intent intent = new Intent(MyApplication.getInstance(), JuniorActivity.class);
                startActivity(intent);
                break;
            case "0"://跳转新手
                Intent buyIntent = new Intent(mContext, TiyanjinInfoActivity.class);
                startActivity(buyIntent);
                break;
        }

    }

}