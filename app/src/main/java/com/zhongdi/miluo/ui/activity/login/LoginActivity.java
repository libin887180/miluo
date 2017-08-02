package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.net.xutils.NetRequestUtil;
import com.zhongdi.miluo.view.ClearEditText;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    ClearEditText etUsername;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.email_login_form)
    RelativeLayout emailLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        setListener();
        initData();

        // Set up the login form.
    }

    private void initData() {

    }


    private void setListener() {

    }


    /**
     * 账号格式验证
     */
    private boolean isEmailValid(String email) {
        boolean flag = false;
        String[] accountArray = email.split("@");
        System.out.println("accountArray.length=" + accountArray.length);
        if (accountArray.length == 2 && !"".equals(accountArray[0]) && !"".equals
                (accountArray[1])) {
            flag = true;
        } else {
            showToast("账号格式不正确");
        }

        return flag;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        overridePendingTransition(R.anim.default_anim_in, R.anim.default_anim_out);
    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
//                startActivity(new Intent(applica, MainActivity.class));
                Toast.makeText(applica, "登录" + etUsername.getText().toString(), Toast
                        .LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                break;
        }
    }


    private void request_post_3() {
        Map<String,String> map = new HashMap<>();
        map.put("mobile","admin");
        map.put("validateseq","123");
        Callback.Cancelable post = NetRequestUtil.getInstance().post("http://192.168.64.121:8085/lead/mobile/v1/user/pay", map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<Manager>>>() {


            @Override
            public void onSuccess(MResponse<List<Manager>> response, int requestCode) {
                Logger.w(response.getCode());
            }

            @Override
             public void onFailed(Throwable e) {
              e.printStackTrace();
              }
         });
    }
    private void login() {
        Map<String,String> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","123456");
        Callback.Cancelable post = NetRequestUtil.getInstance().post("http://192.168.64.121:8085/login", map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<Manager>>>() {


            @Override
            public void onSuccess(MResponse<List<Manager>> response, int requestCode) {
                Logger.w(response.getCode());
                request_post_3();
            }

            @Override
             public void onFailed(Throwable e) {
              e.printStackTrace();
              }
         });
    }
}

