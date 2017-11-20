package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FriendsInfo;
import com.zhongdi.miluo.model.TiyanjinDetail;
import com.zhongdi.miluo.presenter.TiyanjinTransDetailPresenter;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.view.TiyanjinTransDetailView;
import com.zhongdi.miluo.widget.AlertDialog;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class TiyanjinTransDetailActivity extends BaseActivity<TiyanjinTransDetailPresenter> implements TiyanjinTransDetailView {

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.tv_fund_name)
    TextView tvFundName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_risk)
    TextView tvRisk;
    @BindView(R.id.iv_invite_friends)
    ImageView ivInviteFriends;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_trans_status)
    TextView tvTransStatus;
    @BindView(R.id.btn_exchange)
    Button btnExchange;
    @BindView(R.id.tv_friends_num)
    TextView tvFriendsNum;
    @BindView(R.id.tv_friends_amount)
    TextView tvFriendsAmount;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    TiyanjinDetail tiyanjinDetail;
    private String fundcode = "";
    private String fundId = "";
    private View sharePopView;
    private PopupWindow mCardPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fundcode = getIntent().getStringExtra("fundcode");
        binding(R.layout.activity_tiyanjin_trans_detail);
    }

    @Override
    protected TiyanjinTransDetailPresenter initPresenter() {
        return new TiyanjinTransDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        setupSharePopupWindow();
        refreshLayout.setEnableRefresh(false);//不可下拉刷新
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                pageIndex=1;
//                presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {

            }
        });
        if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {//没有测评
            showTestDialog();
        }
        Glide.with(mContext).asGif().load(R.drawable.invite_friends).into(ivInviteFriends);
        presenter.getTiYanjinDetail();
        presenter.getFriendsNum();
    }
    public void showTestDialog() {
        new AlertDialog(mContext).builder().setTitle("风险测评邀请").setMsg("国家证监会发《130号文》邀请您参与基金投资者风险评测，完成后可以参与更多优惠活动哦！")
                .setNegativeButton("残忍拒绝", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setPositiveButton("立即测评", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.TIYANJIN);
                startActivity(intent);
                finish();
            }
        }).show();
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
                ShareWeb(R.drawable.share_tyj,SHARE_MEDIA.WEIXIN);
            }
        });
        sharePopView.findViewById(R.id.tv_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
                ShareWeb(R.drawable.share_tyj,SHARE_MEDIA.SINA);
            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }
    private void ShareWeb(int thumb_img,SHARE_MEDIA platform) {
        UMImage thumb = new UMImage(mContext, thumb_img);
        UMWeb web = new UMWeb(URLConfig.H5_REGISTER+"?referral_code="+SpCacheUtil.getInstance().getReferralCode());
        web.setThumb(thumb);
        web.setDescription("好友在米罗基金为您准备了一份大礼，赶紧看看吧");
        web.setTitle("18888元赚钱计划");
        new ShareAction(TiyanjinTransDetailActivity.this).withMedia(web).setPlatform(platform).setCallback(umShareListener).share();
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
    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    @Override
    public void OnDataSuccess(TiyanjinDetail body) {
        tiyanjinDetail = body;
        tvFundName.setText(body.getFundname() + "(" + body.getFundcode() + ")");
        tvAmount.setText(body.getAmount());

        DecimalFormat mFormat = new DecimalFormat("#0.00");
        tvProfit.setText(mFormat.format(Double.parseDouble(body.getProfit())));
        tvValue.setText(mFormat.format(Double.parseDouble(body.getProfits())));
        fundId=body.getId();
        fundcode = body.getFundcode();
        switch (body.getStatus()) {
//            101:份额确认中 102:收益中 103 已到期 104 已弃权
            case "101":
                tvTransStatus.setText("份额确认中");
                break;
            case "102":
                tvTransStatus.setText("收益中");
                break;
            case "103":
                tvTransStatus.setText("已到期");
                break;
            case "104":
                tvTransStatus.setText("已弃权");
                break;
        }
//        按钮状态（状态(101:份额确认中 如有一位数字的就为 [还剩4天收益] 103兑换话费 104邀请好友)

        if (body.getActivity_status().length() > 1) {
            if (body.getActivity_status().equals("101")) {
                btnExchange.setText("份额确认中");
                btnExchange.setEnabled(false);
            } else if (body.getActivity_status().equals("103")) {
                btnExchange.setText("兑换话费");
                btnExchange.setEnabled(true);
            } else if (body.getActivity_status().equals("104")) {
                btnExchange.setText("邀请好友");
                btnExchange.setEnabled(true);
            }
        } else {
            btnExchange.setText("还剩" + body.getActivity_status() + "天收益");
            btnExchange.setEnabled(false);
        }

    }

    @Override
    public void OnFriendsSuccess(FriendsInfo friendsInfo) {
        tvFriendsAmount.setText(friendsInfo.getAmount());
        tvFriendsNum.setText(friendsInfo.getNums());
//        邀请好友按钮状态(0:邀请好友 1:兑换)

        if(friendsInfo.getStatus()==0){
           tvInvite.setText("邀请好友");
        }else{
            tvInvite.setText("兑换");
        }
    }


    @OnClick({R.id.tv_invite, R.id.rl_fund_info,R.id.btn_exchange,R.id.img_title_right,R.id.iv_invite_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_exchange:
                if (btnExchange.getText().equals("兑换话费")) {
                    Intent intent = new Intent(mContext, ExchangeActivity.class);
                    intent.putExtra("prizeType","1");//充值类别(-2、推荐(不需要传id) 1、我的奖品体验金；2、我的奖品新手;3、我的奖品米罗盘；4、我的奖品pk组团；
                    intent.putExtra("prizeId", tiyanjinDetail.getWinprize_id());
                    startActivity(intent);
                } else if (btnExchange.getText().equals("邀请好友")) {
                    showpSharePopupWindow();
//                    showToast("邀请好友");
                }
                break;
            case R.id.tv_invite:
                if (tvInvite.getText().equals("邀请好友")) {
//                    showToast("邀请好友");
                    showpSharePopupWindow();
                } else if (tvInvite.getText().equals("兑换")) {
                    Intent intent = new Intent(mContext, ExchangeActivity.class);
                    intent.putExtra("prizeType","1");//充值类别(-2、推荐(不需要传id) 1、我的奖品体验金；2、我的奖品新手;3、我的奖品米罗盘；4、我的奖品pk组团；
                    intent.putExtra("prizeId", tiyanjinDetail.getWinprize_id());
                    startActivity(intent);
                }
                break;
            case R.id.rl_fund_info:
                if (!TextUtils.isEmpty(fundId)) {
                    Intent detail = new Intent(mContext, FundDetailActivity.class);
                    detail.putExtra("fundId", fundId);
                    detail.putExtra("fundcode", fundcode);
                    startActivity(detail);
                }
                break;
            case R.id.img_title_right:
                showpSharePopupWindow();
                break;
            case R.id.iv_invite_friends:
                showpSharePopupWindow();
                break;
        }
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
