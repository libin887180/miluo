package com.zhongdi.miluo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.BackHandlerHelper;
import com.zhongdi.miluo.BottomNavigationViewHelper;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Update;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.fragment.CollectionFragment;
import com.zhongdi.miluo.ui.fragment.HomeFragment2;
import com.zhongdi.miluo.ui.fragment.MarketFragment;
import com.zhongdi.miluo.ui.fragment.MineFragment;
import com.zhongdi.miluo.util.UpdateUtils;
import com.zhongdi.miluo.widget.NoScrollViewPager;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity2 {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private MenuItem prevMenuItem;
    private int toTab = -1;
    private String TO;
    ViewPagerAdapter adapter;
    private int versionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        requestPermission();
        cheUpdate();
        initView();
        setupViewPager(viewPager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
//        getLoadingProgressDialog().show();
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private void setupViewPager(NoScrollViewPager viewPager) {
        viewPager.setScroll(false);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(HomeFragment2.newInstance("首页"));
        adapter.addFragment(MarketFragment.newInstance("超市"));
        adapter.addFragment(CollectionFragment.newInstance("自选"));
        adapter.addFragment(MineFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                super.onBackPressed();
            }


        }
    }

    private void initView() {


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!TextUtils.isEmpty(intent.getStringExtra("to"))) {
            if (intent.getStringExtra("to").equals("mine")) {
                navigation.getMenu().getItem(3).setChecked(true);
                viewPager.setCurrentItem(3);
                ((MineFragment) adapter.getItem(3)).fetchData();
            }
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    toTab = 0;
                    return true;
                case R.id.navigation_market:
                    viewPager.setCurrentItem(1);
                    toTab = 1;
                    return true;
                case R.id.navigation_self:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(2);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
                        toTab = 2;
                        return false;
                    }


                case R.id.navigation_mine:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(3);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
                        toTab = 3;
                        return false;
                    }
            }
            return false;
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (toTab != -1) {
                    if (toTab == 2) {
                        navigation.getMenu().getItem(2).setChecked(true);
                        viewPager.setCurrentItem(2);
                        ((CollectionFragment) adapter.getItem(2)).fetchData();
                    } else if (toTab == 3) {
                        navigation.getMenu().getItem(3).setChecked(true);
                        viewPager.setCurrentItem(3);
                        ((MineFragment) adapter.getItem(3)).fetchData();
                    }
                }
                break;
            case 1001://退出登录操作
                navigation.getMenu().getItem(0).setChecked(true);
                viewPager.setCurrentItem(0);
                break;
        }
    }

    public void cheUpdate() {
        Map<String, String> map = new HashMap<>();
        map.put("type","3");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.UPDATE, map, 103,
                new NetRequestUtil.NetResponseListener<MResponse<Update>>() {
                    @Override
                    public void onSuccess(MResponse<Update> response, int requestCode) {
                        if (response.getBody().getVersion_code() > versionCode) {
                            UpdateUtils.getUpdateUtils().checkAppUpdate(mContext, true, response.getBody());
                        } else {
//                        showToast("已经是最新版本");
//                        if (CommonUtils.isApplicationExpire("20170630")){  //判断是否已到期6月30号
//                            if (versionCode < 150){
//                                showDiage();
//                            }
//                        }
                        }
                    }

                    @Override
                    public void onFailed(MResponse<Update> response, int requestCode) {
                        ViseLog.e("请求失败");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

}
