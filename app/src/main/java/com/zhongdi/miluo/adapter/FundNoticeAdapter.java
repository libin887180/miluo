package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.FundNotice;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class FundNoticeAdapter extends BaseRecyclerAdapter<FundNotice> {

    public FundNoticeAdapter(Context context, List<FundNotice> datas) {
        super(context, R.layout.item_fund_notice, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, FundNotice item, int position) {
        holder.setText(R.id.tv_title,item.getTitle());
        holder.setText(R.id.tv_date,item.getPubDate());
    }
}
