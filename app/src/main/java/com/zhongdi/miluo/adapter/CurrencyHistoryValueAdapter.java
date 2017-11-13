package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.HistoryValue;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class CurrencyHistoryValueAdapter extends BaseRecyclerAdapter<HistoryValue> {

    public CurrencyHistoryValueAdapter(Context context, List<HistoryValue> datas) {
        super(context, R.layout.item_currency_fund_history_value, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HistoryValue item, int position) {
        holder.setText(R.id.tv_date, item.getValueDate());
        holder.setText(R.id.tv_cumvalue, item.getTenthouunitincm());
        if(item.getDayRate().contains("-")){
            holder.setTextColor(R.id.tv_dayrate,mContext.getResources().getColor(R.color.increase_green));
        }else{
            holder.setTextColor(R.id.tv_dayrate,mContext.getResources().getColor(R.color.red));
        }
        holder.setText(R.id.tv_dayrate, item.getYearyld()+"%");

    }
}
