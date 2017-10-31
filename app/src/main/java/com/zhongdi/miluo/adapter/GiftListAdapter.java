package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.Prize;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class GiftListAdapter extends BaseRecyclerAdapter<Prize> {

    public GiftListAdapter(Context context, List<Prize> datas) {
        super(context, R.layout.gift_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, Prize item, int position) {
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_dsc,"来源" +item.getType());
        holder.setText(R.id.tv_time, "有效期"+item.getAdd_time()+"-"+item.getInvalid_time());
        holder.setText(R.id.tv_amount, item.getAmount());

        switch (item.getStatus()){
            case "1"://立即兑换
                holder.setImageResource(R.id.iv_hfq,R.drawable.ic_hfq);
                holder.setText(R.id.tv_exchange,"立即兑换");
                holder.setBackgroundRes(R.id.ll_status,R.drawable.bg_byhq);
                break;
            case "2"://已兑换
                holder.setImageResource(R.id.iv_hfq,R.drawable.ic_hfq_grey);
                holder.setVisible(R.id.tv_exchange,false);
                holder.setBackgroundRes(R.id.ll_status,R.drawable.bg_ydh);
                break;
            case "3"://已过期
                holder.setImageResource(R.id.iv_hfq,R.drawable.ic_hfq_grey);
                holder.setVisible(R.id.tv_exchange,false);
                holder.setBackgroundRes(R.id.ll_status,R.drawable.bg_ygq);
                break;
            case "4"://已弃权
                holder.setImageResource(R.id.iv_hfq,R.drawable.ic_hfq_grey);
                holder.setVisible(R.id.tv_exchange,false);
                break;
            case "-1"://收益中
                holder.setImageResource(R.id.iv_hfq,R.drawable.ic_hfq_grey);
                holder.setVisible(R.id.tv_exchange,false);
                break;
        }

    }
}
