package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class GiftListAdapter extends BaseRecyclerAdapter<String> {

    public GiftListAdapter(Context context, List<String> datas) {
        super(context, R.layout.gift_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {


    }
}
