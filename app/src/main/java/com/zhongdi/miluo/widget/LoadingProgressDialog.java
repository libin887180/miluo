package com.zhongdi.miluo.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhongdi.miluo.R;


public class LoadingProgressDialog extends Dialog {

    private TextView tipTextView;
    private LoadBullView loadingImg;
//    private volatile static LoadingProgressDialog instance;

//    /**
//     * Double Check 单例模式
//     *
//     * @return
//     */
//    public static LoadingProgressDialog getInstance(Context context) {
//        if (instance == null) {
//            synchronized (NetRequestUtil.class) {
//                if (instance == null) {
//                    instance = new LoadingProgressDialog(context, "");
//                }
//            }
//        }
//        return instance;
//    }

    public LoadingProgressDialog(Context context, String msg) {

        super(context, R.style.loading_dialog);

        setContentView(R.layout.loading_dialog_view);
        loadingImg = (LoadBullView) findViewById(R.id.loadingImg);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        if (TextUtils.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        }
        tipTextView.setText(msg);
    }

    public void setLoadingText(CharSequence msg) {
        tipTextView.setText(msg);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
//        loadingImg.startAnim();
        super.show();
    }


}
