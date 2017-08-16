package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class CurAssetAdapter extends BaseRecyclerAdapter<String> {

    public CurAssetAdapter(Context context, List<String> datas) {
        super(context, R.layout.asset_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        TextView tv=holder.getView(R.id.tv_fund_name);
        tv.setText(item);

    }
}
