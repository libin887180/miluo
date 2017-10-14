package com.zhongdi.miluo.adapter.market;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.InfomationNote;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class BeginnerInfoAdapter extends BaseRecyclerAdapter<InfomationNote> {

    public BeginnerInfoAdapter(Context context, List<InfomationNote> datas) {
        super(context, R.layout.item_infomation, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, InfomationNote item, int position) {

    }
}
