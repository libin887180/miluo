package com.zhongdi.miluo.adapter.mine;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class TransAdapter extends BaseRecyclerAdapter<String> {

    public TransAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_transation_recored, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
