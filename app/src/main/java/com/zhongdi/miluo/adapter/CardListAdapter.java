package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class CardListAdapter extends BaseRecyclerAdapter<String> {
    int selectPosotion =0;

    public void setCheck(int index) {
        selectPosotion = index;
        notifyDataSetChanged();
    }

    public CardListAdapter(Context context, List<String> datas) {
        super(context, R.layout.card_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        if (position == selectPosotion) {
            holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.iv_check).setVisibility(View.GONE);
        }
    }
}
