package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.Fund;

import java.util.List;

/**
 * Created by libin on 2017/8/3.
 */

public class FundAdapter extends BaseRecyclerAdapter<Fund> {

    public FundAdapter(Context context, List<Fund> datas) {
        super(context, R.layout.fund_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, Fund item, int position) {
        TextView tvFundName = holder.getView(R.id.tv_fund_name);
        tvFundName.setText(item.getFundname());
        TextView tvFundCode = holder.getView(R.id.tv_fund_code);
        tvFundCode.setText(item.getFundcode());
        TextView tvValue = holder.getView(R.id.tv_value);
        tvValue.setText(item.getNetvalue() + "");
        TextView tvInfo = holder.getView(R.id.tv_info);
        tvInfo.setText(item.getDayrate() + "%");
    }
}