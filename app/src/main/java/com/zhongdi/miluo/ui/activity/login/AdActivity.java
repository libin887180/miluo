package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/12/19.
 */
public class AdActivity extends BaseActivity2 {

    private Button jumps;
//    private int recLen = 3;

//    Timer timer = new Timer();
//    private final long SPLASH_LENGTH = 3000;
//    Handler handler = new Handler();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        jumps=(Button)findViewById(R.id.jumps);

//        jumps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(dpf.getBooleanSharedDatas(SharedPreferenceConstants.FIRST_USE,true)){
//                    Intent intent = new Intent(AdActivity.this, GuideActivity.class);
//                    startActivity(intent);
//                }else{
//                    String userName= dpf.getStringSharedDatas(SharedPreferenceConstants.USER_NAME,"");
//                    String psw = dpf.getStringSharedDatas(SharedPreferenceConstants.PSW,"");
//                    if(userName.isEmpty()||psw.isEmpty()) {
//                        Intent intent = new Intent(AdActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        jumps.setText("登录中");
//                        login(userName,psw);
//                    }
//                }
//                downTimer.cancel();
//            }
//        });

        downTimer.start();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        downTimer.cancel();
    }

    /***
     * 倒计时
     */
    private CountDownTimer downTimer = new CountDownTimer(2000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
//            jumps.setText((millisUntilFinished/1000) + "s跳过");
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
//            jumps.setText("0s跳过");

            if(SpCacheUtil.getInstance().isFirstUse()){
                Intent intent = new Intent(AdActivity.this, GuideActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(AdActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();

        }
    };
    /**
     * 登录请求
     */
    private void login(String userName,final String  psw) {

    }

    private void getImInfo() {

    }

}
