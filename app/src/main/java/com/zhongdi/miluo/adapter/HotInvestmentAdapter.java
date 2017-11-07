package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.HotSpots;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HotInvestmentAdapter extends BaseRecyclerAdapter<HotSpots> {
private Context mContext;
    public HotInvestmentAdapter(Context context, List<HotSpots> datas) {
        super(context, R.layout.item_hot_investment, datas);
        mContext =  context;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HotSpots item, int position) {
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_dsc, item.getContent());
        switch (item.getType()){
            case "5":
                holder.setBackgroundRes(R.id.iv_type,R.drawable.bg_dashgap_circle);
                holder.setText(R.id.iv_type,"人工\n智能");
                holder.setTextColor(R.id.iv_type,mContext.getResources().getColor(R.color.dash_circle_blue));
                break;
            case "6":
                holder.setBackgroundRes(R.id.iv_type,R.drawable.bg_dashgap_circle_green);
                holder.setTextColor(R.id.iv_type,mContext.getResources().getColor(R.color.dash_circle_green));
                holder.setText(R.id.iv_type,"医药\n行业");
                break;
            case "7":
                holder.setBackgroundRes(R.id.iv_type,R.drawable.bg_dashgap_circle_orange);
                holder.setTextColor(R.id.iv_type,mContext.getResources().getColor(R.color.dash_circle_orange));
                holder.setText(R.id.iv_type,"银行\n板块");
                break;

        }
    }
}
