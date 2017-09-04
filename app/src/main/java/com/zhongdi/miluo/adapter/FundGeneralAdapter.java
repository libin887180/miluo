package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.graphics.Color;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class FundGeneralAdapter extends BaseRecyclerAdapter<String> {

    public FundGeneralAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_fund_general, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        if (position % 2 == 0) {
            holder.getConvertView().setBackgroundColor(Color.WHITE);
        } else {
            holder.getConvertView().setBackgroundColor(Color.parseColor("#f9f9f9"));
        }

    }
}
