package com.zhongdi.miluo.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.ui.activity.market.BuyTiyanjinActivity;
import com.zhongdi.miluo.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    String url;
    private View sharePopView;
    private PopupWindow mCardPopupWindow;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiyanjin_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setupSharePopupWindow();
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

    // 显示弹窗
    public void setupSharePopupWindow() {
        // 初始化弹窗
        sharePopView = View.inflate(this, R.layout.invite_share_view, null);
        mCardPopupWindow = new PopupWindow(sharePopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePopView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        sharePopView.findViewById(R.id.tv_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj, SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        sharePopView.findViewById(R.id.tv_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj, SHARE_MEDIA.WEIXIN);
            }
        });
        sharePopView.findViewById(R.id.tv_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj, SHARE_MEDIA.SINA);
            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }

    private void ShareWeb(int thumb_img, SHARE_MEDIA platform) {
        UMImage thumb = new UMImage(mContext, thumb_img);
        UMWeb web = new UMWeb(URLConfig.H5_REGISTER + "?referral_code=" + SpCacheUtil.getInstance().getReferralCode());
        web.setThumb(thumb);
        web.setDescription("好友在米罗基金为您准备了一份大礼，赶紧看看吧");
        web.setTitle("18888元赚钱计划");
        new ShareAction(TiyanjinInfoActivity.this).withMedia(web).setPlatform(platform).setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
//            Toast.makeText(TiyanjinInfoActivity.this, platform + " 分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(TiyanjinInfoActivity.this, platform + " 分享失败", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(TiyanjinInfoActivity.this, platform + " 分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    private void showpSharePopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                open.putExtra(IntentConfig.MAIDIAN, IntentConfig.EX_Gold_Account);
                startActivityForResult(open, 102);
                break;
            case "3"://已登录 已开户 ，进入购买
                Intent intent = new Intent(mContext, BuyTiyanjinActivity.class);
                intent.putExtra("fundCode", code);
                startActivityForResult(intent, 101);
                break;
            case "4"://已购买
                break;
        }

    }

    @JavascriptInterface
    public void test4() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            webView.reload();
            if (requestCode == 102) {
                Intent intent = new Intent(mContext, BuyTiyanjinActivity.class);
                intent.putExtra("fundCode", fundCode);
                startActivity(intent);
            }
        }

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.img_title_right)
    public void onViewClicked() {
        showpSharePopupWindow();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCardPopupWindow != null && mCardPopupWindow.isShowing()) {
                mCardPopupWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
