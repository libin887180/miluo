package com.zhongdi.miluo.adapter.market;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.DealRecord;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class FinishedTransAdapter extends BaseRecyclerAdapter<DealRecord> {

    public FinishedTransAdapter(Context context, List<DealRecord> datas) {
        super(context, R.layout.item_dealing, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, DealRecord item, int position) {
        holder.setText(R.id.iv_type, item.getTypeitem());

        if (item.getTypeitem().equals("申购")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_blue);
        } else if (item.getTypeitem().equals("赎回")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_green);
        } else if (item.getTypeitem().equals("分红")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_orange);
        }
        holder.setText(R.id.tv_title, item.getFundname());
        holder.setText(R.id.tv_dsc, item.getItem());
        holder.setText(R.id.tv_time, item.getTradetime());
        holder.setText(R.id.tv_status, item.getTransstatus());
    }
}
