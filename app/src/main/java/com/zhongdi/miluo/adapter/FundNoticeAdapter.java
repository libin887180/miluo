package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class FundNoticeAdapter extends BaseRecyclerAdapter<String> {

    public FundNoticeAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_fund_notice, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
