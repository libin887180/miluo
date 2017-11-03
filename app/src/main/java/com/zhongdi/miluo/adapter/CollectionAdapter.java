package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.OptionalFund;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class CollectionAdapter extends BaseRecyclerAdapter<OptionalFund> {

    public CollectionAdapter(Context context, List<OptionalFund> datas) {
        super(context, R.layout.collection_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, OptionalFund item, int position) {
        holder.setText(R.id.tv_fund_name, item.getFundName());
        holder.setText(R.id.tv_fund_code, item.getFundCode());
        if (item.getFundType().equals(MiluoConfig.HUOBI)) {
            holder.getView(R.id.tv_wfsy).setVisibility(View.VISIBLE);
            holder.getView(R.id.tv_qrnh).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_wfsy).setVisibility(View.GONE);
            holder.getView(R.id.tv_qrnh).setVisibility(View.GONE);

        }
        if (item.getRate().contains("-")) {
            holder.setTextColor(R.id.tv_info, mContext.getResources().getColor(R.color.increase_green));
        } else {
            holder.setTextColor(R.id.tv_info, mContext.getResources().getColor(R.color.red));
        }
        holder.setText(R.id.tv_value, item.getNetValue());
        holder.setText(R.id.tv_info, item.getRate()+"%");

    }
}
