package com.zhongdi.miluo.adapter.market;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class FinishedTransAdapter extends BaseRecyclerAdapter<String> {

    public FinishedTransAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_dealing, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
