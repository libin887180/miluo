package com.zhongdi.miluo.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.widget.LoadingProgressDialog;


public class BaseActivity2 extends AppCompatActivity {
    protected Context mContext;
    protected MyApplication applica;
    protected LoadingProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        applica = MyApplication.getInstance();

    }

    public void showToast(String text) {
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(this, this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    public void back(View targv) {
        finish();
    }
    public LoadingProgressDialog getLoadingProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new LoadingProgressDialog(this, "加载中...");
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        return progressDialog;
    }
}
