package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.BankInfo;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class BankListAdapter extends BaseRecyclerAdapter<BankInfo> {
    int selectPosotion =0;

    public void setCheck(int index) {
        selectPosotion = index;
        notifyDataSetChanged();
    }
    public BankListAdapter(Context context, List<BankInfo> datas) {
        super(context, R.layout.bank_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, BankInfo item, int position) {
        if (position == selectPosotion) {
            holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.iv_check).setVisibility(View.GONE);
        }
    }
}
