package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.SearchFund;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class SearchAdapter extends BaseRecyclerAdapter<SearchFund> {

    public SearchAdapter(Context context, List<SearchFund> datas) {
        super(context, R.layout.item_search, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, SearchFund item, int position) {

    }
}
