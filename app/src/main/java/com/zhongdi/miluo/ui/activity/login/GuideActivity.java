package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.ui.fragment.login.Guide1Fragment;
import com.zhongdi.miluo.ui.fragment.login.Guide2Fragment;
import com.zhongdi.miluo.ui.fragment.login.Guide3Fragment;


/**
 * @author libin
 * @ClassName: GuideActivity
 * @Description: 新手引导页面and系统帮助
 * @date 2017-2-9 下午05:03:52
 */
public class GuideActivity extends BaseActivity2 implements
        ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

//    public GestureDetector mGestureDetector;

    private int currentItem = 0;
    private int flaggingWidth;
//    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide);

        slipToMain();
        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        LayoutInflater inflater = LayoutInflater.from(this);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Guide1Fragment.newInstance());
        adapter.addFragment(Guide2Fragment.newInstance());
        adapter.addFragment(Guide3Fragment.newInstance());
        viewPager.setAdapter(adapter);
//        button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        SpCacheUtil.getInstance().setIsFirstUse(false);
//        Intent intent = new Intent(mContext, MainActivity.class);
//        startActivity(intent);
//        this.finish();
//    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
//        if (currentItem == 2) {
//            button.setVisibility(View.VISIBLE);
//        } else {
//            button.setVisibility(View.GONE);
//        }
        System.out.println("------" + currentItem);
    }


    private void slipToMain() {
//        mGestureDetector = new GestureDetector(this,
//                new GestureDetector.SimpleOnGestureListener() {
//
//                    @Override
//                    public boolean onFling(MotionEvent e1, MotionEvent e2,
//                                           float velocityX, float velocityY) {
//                        if (currentItem == 2) {
//                            if ((e1.getRawX() - e2.getRawX()) >= flaggingWidth) {
//                                SpCacheUtil.getInstance().setIsFirstUse(false);
//                                Intent intent = new Intent(
//                                        GuideActivity.this,
//                                        MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//
//                });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mGestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (mGestureDetector.onTouchEvent(event)) {
//            event.setAction(MotionEvent.ACTION_CANCEL);
//        }
//        return super.dispatchTouchEvent(event);
//    }
}