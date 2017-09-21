package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.SellFundPresenter;
import com.zhongdi.miluo.view.SellFundView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;

import butterknife.BindView;
import butterknife.OnClick;

public class SellFundActivity extends BaseActivity<SellFundPresenter> implements SellFundView, View.OnClickListener {

    @BindView(R.id.et_money)
    ClearEditText etMoney;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_sell);
    }

    // 显示弹窗
    public void setupPswPopupWindow() {
        // 初始化弹窗
        popView = View.inflate(this, R.layout.pop_window, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPayView = (PayView) popView.findViewById(R.id.pv_pop_win);
        mPayView.getTitle().setText("输入六位数字密码");
        // 设置动画
        mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mPopupWindow.setOutsideTouchable(true);
        mPayView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                Toast.makeText(mContext, mPayView.getPassword(), Toast.LENGTH_SHORT).show();
            }
        });
        mPayView.getCancel().setOnClickListener(this);
        mPayView.getForgetPsw().setOnClickListener(this);
    }


    @Override
    protected SellFundPresenter initPresenter() {
        return new SellFundPresenter(this);
    }

    @Override
    protected void initialize() {
        setupPswPopupWindow();
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etMoney.getText().length() > 0) {
                    enableSubmitBtn();
                } else {
                    disableSubmitBtn();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void disableSubmitBtn() {
        btnSubmit.setEnabled(false);
    }

    @Override
    public void enableSubmitBtn() {
        btnSubmit.setEnabled(true);
    }

    @OnClick({R.id.btn_submit, R.id.tv_sell_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sell_all:
                break;
            case R.id.btn_submit:
                showPswPopupWindow();
                break;
        }
    }

    private void showPswPopupWindow() {
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_back:
                mPopupWindow.dismiss();
                break;
            case R.id.tv_pay_forgetPwd:
                Toast.makeText(mContext, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
