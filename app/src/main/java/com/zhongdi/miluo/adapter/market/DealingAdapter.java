package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.DealRecord;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class DealingAdapter extends BaseRecyclerAdapter<DealRecord> {

    public DealingAdapter(Context context, List<DealRecord> datas) {
        super(context, R.layout.item_dealing, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, DealRecord item, int position) {
        holder.setText(R.id.iv_type, item.getTypeitem());

        if (TextUtils.equals(item.getTypeitem(),"申购")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_blue);
        } else if (TextUtils.equals(item.getTypeitem(),"赎回")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_green);
        } else if (TextUtils.equals(item.getTypeitem(),"分红")) {
            holder.setBackgroundRes(R.id.iv_type, R.drawable.bg_text_circle_orange);
            holder.getView(R.id.iv_next).setVisibility(View.INVISIBLE);
        }
        holder.setText(R.id.tv_title, item.getFundname());
        holder.setText(R.id.tv_dsc, item.getItem());
        holder.setText(R.id.tv_time, item.getTradetime());
        holder.setText(R.id.tv_status, item.getTransstatus());
    }
}
