package com.zhongdi.miluo.adapter.market;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.FundDividend;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class FundDistributeAdapter extends BaseRecyclerAdapter<FundDividend> {

    public FundDistributeAdapter(Context context, List<FundDividend> datas) {
        super(context, R.layout.item_fund_distribute, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, FundDividend item, int position) {
        holder.setText(R.id.tv_dengji_date, item.getRegisterDate());
        holder.setText(R.id.tv_chuxi_date, item.getExdividendDate());
        holder.setText(R.id.tv_fenhong, item.getEveryDividend());
        holder.setText(R.id.tv_fafang_date, item.getDividendDate());

    }
}
