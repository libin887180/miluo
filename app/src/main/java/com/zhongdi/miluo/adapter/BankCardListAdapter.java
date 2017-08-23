package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class BankCardListAdapter extends BaseRecyclerAdapter<String> {

    public BankCardListAdapter(Context context, List<String> datas) {
        super(context, R.layout.bank_card_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
