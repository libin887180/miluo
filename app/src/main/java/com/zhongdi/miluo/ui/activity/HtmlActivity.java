package com.zhongdi.miluo.ui.activity;

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
import com.zhongdi.miluo.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * bananlar详情页面
 */
public class HtmlActivity extends BaseActivity2 {

    @BindView(R.id.actAgreementPb)
    ProgressBar pbBar;
    @BindView(R.id.webView)
    BaseWebView webView;
    @BindView(R.id.title)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        String url = "";
        //String titleName = "";
        if (getIntent().getExtras() != null){
            url = getIntent().getStringExtra("url");
            //titleName = getIntent().getStringExtra("title");
        }
        //titleTv.setText(titleName);

        webView.addJavascriptInterface(new JavaScriptInterface(), "service");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    pbBar.setVisibility(View.GONE);
                }else {
                    if (pbBar.getVisibility() == View.GONE) {
                        pbBar.setVisibility(View.VISIBLE);
                    }
                    pbBar.setProgress(newProgress);
                }
            }
        });

        if (url != null){
            ViseLog.i("// URL=  " + url);
            if (url.contains("?")){
                url = url + "&source=app";
            } else {
                url = url + "?source=app";
            }
            webView.loadUrl(url);
        }
    }



    class JavaScriptInterface {
        @JavascriptInterface
        public void openshop(String shopid){

        }

        @JavascriptInterface
        public void appShare(String title, String content, String imageUrl, String detailUrl){
        }

        @JavascriptInterface
        public void openProductDetail(String goodsId){
        }
    }
}
