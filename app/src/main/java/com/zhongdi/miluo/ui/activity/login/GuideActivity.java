package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author libin
 * @ClassName: GuideActivity
 * @Description: 新手引导页面and系统帮助
 * @date 2017-2-9 下午05:03:52
 */
public class GuideActivity extends BaseActivity2 implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;

//    public GestureDetector mGestureDetector;

    private int currentItem = 0;
    private int flaggingWidth;
    private Button button;

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
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();

        View guide_one = inflater.inflate(R.layout.guide_one, null);
        View guide_two = inflater.inflate(R.layout.guide_two, null);
        View guide_three = inflater.inflate(R.layout.guide_three, null);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        views.add(guide_one);
        views.add(guide_two);
        views.add(guide_three);

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        SpCacheUtil.getInstance().setIsFirstUse(false);
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

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
        if (currentItem == 2) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
        System.out.println("------" + currentItem);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
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