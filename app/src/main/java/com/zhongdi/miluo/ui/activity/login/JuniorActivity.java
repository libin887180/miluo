package com.zhongdi.miluo.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.constants.IntentConfig;
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
public class JuniorActivity extends BaseActivity2 {

    @BindView(R.id.actAgreementPb)
    ProgressBar pbBar;
    @BindView(R.id.webView)
    BaseWebView webView;
    @BindView(R.id.title)
    TextView titleTv;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junor_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = URLConfig.JEUNIOR;
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

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                showErrorPage();//显示错误页面
            }


        });
        // 只需要将第一种方法的loadUrl()换成下面该方法即可
//        webView.evaluateJavascript("javascript:;", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                //此处为 js 返回的结果
//            }
//        });


        if (url != null) {
            ViseLog.i("// URL=  " + url);
            webView.loadUrl(url);
        }
    }
    private View mErrorView;
    boolean mIsErrorPage;
    protected void showErrorPage() {
        LinearLayout webParentView = (LinearLayout)webView.getParent();
        initErrorPage();//初始化自定义页面
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        webParentView.addView(mErrorView, 0, lp);
        mIsErrorPage = true;
    }
    /***
     * 显示加载失败时自定义的网页
     */
    protected void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.activity_html_error, null);
            RelativeLayout layout = (RelativeLayout)mErrorView.findViewById(R.id.online_error_btn_retry);
            layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    webView.reload();
                }
            });
            mErrorView.setOnClickListener(null);
        }
    }
    /****
     * 把系统自身请求失败时的网页隐藏
     */
    protected void hideErrorPage() {
        LinearLayout webParentView = (LinearLayout)webView.getParent();
        mIsErrorPage = false;
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
    }

    @JavascriptInterface
    public void test2(String to, String type, String code, String fundId) {

        switch (to) {
            case "1"://购买方法
                if (!MyApplication.getInstance().isLogined) {
                    Intent intent = new Intent(mContext, QuickLoginActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.Novice_Register);
                    startActivityForResult(intent, 101);
                    return;
                }
                Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
                buyIntent.putExtra("fundCode", code);
                buyIntent.putExtra("from", "newer");
                startActivity(buyIntent);
                break;
            case "0"://详情方法
                Intent intent;
                if (type.equals(MiluoConfig.HUOBI) || type.equals(MiluoConfig.DUANQI)) {
                    intent = new Intent(mContext, FundCurrencyDetailActivity.class);
                } else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId", fundId);
                intent.putExtra("fundcode", code);
                ViseLog.i("fundid-->" + fundId);
                startActivity(intent);

                break;
        }

    }

    @JavascriptInterface
    public void test5(String to, String type, String code, String fundId, String buyType) {

        if (!MyApplication.getInstance().isLogined) {
            Intent intent = new Intent(mContext, QuickLoginActivity.class);
            intent.putExtra(IntentConfig.SOURCE, IntentConfig.Novice_Register);
            startActivityForResult(intent, 101);
            return;
        }
        Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
        buyIntent.putExtra("fundCode", code);
        buyIntent.putExtra("from", "newer");
        buyIntent.putExtra("buyType", buyType);//类型-1 普通 0 体验金 1 新手日日赚 2新手周周赚 3新手月月赚

        switch (buyType) {
            case "1":
                buyIntent.putExtra(IntentConfig.MAIDIAN, IntentConfig.Novice_DD_Apply);
                break;
            case "2":
                buyIntent.putExtra(IntentConfig.MAIDIAN, IntentConfig.Novice_WW_Apply);
                break;
            case "3":
                buyIntent.putExtra(IntentConfig.MAIDIAN, IntentConfig.Novice_MM_Apply);
                break;
            default:
                buyIntent.putExtra(IntentConfig.MAIDIAN, IntentConfig.Fund_Details_Apply);
                break;
        }


        startActivity(buyIntent);

    }
}
