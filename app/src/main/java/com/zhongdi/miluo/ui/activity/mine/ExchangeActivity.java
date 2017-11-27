package com.zhongdi.miluo.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.presenter.ExchangePresenter;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.view.ExchangeView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.ExchangeAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ExchangeActivity extends BaseActivity<ExchangePresenter> implements ExchangeView {

    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String prizeId;
    private String prizeType;
    ExchangeAlertDialog dialog;
    private View sharePopView;
    private PopupWindow mCardPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prizeId = getIntent().getStringExtra("prizeId");
        prizeType = getIntent().getStringExtra("prizeType");
        binding(R.layout.activity_exchange);
    }

    @Override
    protected ExchangePresenter initPresenter() {
        return new ExchangePresenter(this);
    }

    @Override
    protected void initialize() {
        setupSharePopupWindow();
        btnSubmit.setEnabled(false);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhone.getText().length() == 11) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });
        etPhone.setText(SpCacheUtil.getInstance().getLoginAccount());
    }

    // 显示弹窗
    public void setupSharePopupWindow() {
        // 初始化弹窗
        sharePopView = View.inflate(this, R.layout.exchange_share_view, null);
        mCardPopupWindow = new PopupWindow(sharePopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePopView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
                mCardPopupWindow.dismiss();
            }
        });
        sharePopView.findViewById(R.id.tv_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj, SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        sharePopView.findViewById(R.id.tv_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj, SHARE_MEDIA.WEIXIN);
            }
        });
        sharePopView.findViewById(R.id.tv_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCardPopupWindow.dismiss();
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
        new ShareAction(ExchangeActivity.this).withMedia(web).setPlatform(platform).setCallback(umShareListener).share();
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

    @Override
    public void onDataSuccess() {
        if (TextUtils.equals(prizeType,"1")) {
            showpSharePopupWindow();
        } else if (TextUtils.equals(prizeType,"2")) {
            dialog = new ExchangeAlertDialog(mContext).builder().setMsg("话费兑换成功")
                    .setPositiveButton("更多好基", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("to", "home");
                            startActivity(intent);
                            finish();
                        }
                    });
            dialog.show();
        } else if (TextUtils.equals(prizeType,"-2")) {
            showpSharePopupWindow();
        } else {
            finish();
            setResult(RESULT_OK);
        }

    }

    private void showpSharePopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }


    @Override
    public void reLogin() {
        Intent intent = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        presenter.exchange(prizeId, prizeType, etPhone.getText().toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCardPopupWindow != null && mCardPopupWindow.isShowing()) {
                mCardPopupWindow.dismiss();
                setResult(RESULT_OK);
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
