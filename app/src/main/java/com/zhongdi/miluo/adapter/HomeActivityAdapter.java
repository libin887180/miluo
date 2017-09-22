package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;


/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HomeActivityAdapter extends BaseRecyclerAdapter<String> {
private Context mContext;
    public HomeActivityAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_home_activity, datas);
        mContext = context;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
    }
}
