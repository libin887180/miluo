package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HomeSpecialityAdapter extends BaseRecyclerAdapter<String> {

    public HomeSpecialityAdapter(Context context, List<String> datas) {
        super(context, R.layout.home_speciality_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
