package com.zhongdi.miluo.adapter.mine;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.DealRecord;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class TransAdapter extends BaseRecyclerAdapter<DealRecord> {

    public TransAdapter(Context context, List<DealRecord> datas) {
        super(context, R.layout.item_transation_recored, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, DealRecord item, int position) {
        holder.setText(R.id.tv_title, item.getTypeitem());
        holder.setText(R.id.tv_desc, item.getItem());
        holder.setText(R.id.tv_date,item.getTradetime());
        holder.setText(R.id.tv_status,item.getTransstatus());
        if(item.getTransstatus().contains("确认")){
            holder.setImageResource(R.id.iv_status,R.drawable.ic_wating);
        }else if(item.getTransstatus().contains("失败")){
            holder.setImageResource(R.id.iv_status,R.drawable.ic_fail);
        }else{
            holder.setImageResource(R.id.iv_status,R.drawable.ic_finshed);
        }
    }
}
