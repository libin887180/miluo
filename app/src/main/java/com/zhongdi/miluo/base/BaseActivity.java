package com.zhongdi.miluo.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhongdi.miluo.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by isfaaghyth on 6/17/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected Context mContext;
    protected MyApplication applica;
    protected P presenter;
    protected abstract P initPresenter();
    protected abstract void initialize();

    protected void binding(int layoutId) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(layoutId);
        mContext = this;
        applica = MyApplication.getInstance();
        ButterKnife.bind(this);
        presenter = initPresenter();
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.dettachView();
    }

    protected void showAlertMessage(String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alert.show();
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

}
