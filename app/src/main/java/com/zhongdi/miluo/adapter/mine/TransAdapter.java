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

    }
}
