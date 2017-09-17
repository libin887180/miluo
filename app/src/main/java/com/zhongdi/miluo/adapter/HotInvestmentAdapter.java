package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HotInvestmentAdapter extends BaseRecyclerAdapter<String> {

    public HotInvestmentAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_hot_investment, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
