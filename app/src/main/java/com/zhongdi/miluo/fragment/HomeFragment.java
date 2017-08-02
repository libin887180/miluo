package com.zhongdi.miluo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.util.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HomeFragment extends Fragment {
    private List<String> images;
    private  List<String> titles;
    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("info").toString();
            Logger.d("HomeFragment", name);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initBanner(view);
        return view;
    }

    private void initBanner(View view) {
        images = new ArrayList<>();
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190138_14593482_5_640_448.jpg");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190135_14593482_3_640_427.jpg");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190134_14593482_2_640_446.jpg");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190133_14593482_1_640_397.jpg");

        titles = new ArrayList<>();
        titles.add("1111111111111");
        titles.add("22222222222");
        titles.add("3333333333333");
        titles.add("4444444444444");
        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.NUM_INDICATOR_TITLE);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}