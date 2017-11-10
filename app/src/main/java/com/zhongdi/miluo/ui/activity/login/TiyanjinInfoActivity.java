package com.zhongdi.miluo.ui.activity.login;

import android.annotation.SuppressLint;
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
import com.zhongdi.miluo.constants.URLConfig;
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

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = URLConfig.TIYANJIN;
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
    public void test1(String type, String code) {


    }

}
