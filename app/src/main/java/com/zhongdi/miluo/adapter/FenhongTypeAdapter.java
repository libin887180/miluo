package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class FenhongTypeAdapter extends BaseRecyclerAdapter<String> {
    int selectPosotion =0;
    private Context mContext;
    public void setCheck(int index) {
        selectPosotion = index;
        notifyDataSetChanged();
    }

    public FenhongTypeAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_fenhong_type, datas);
        mContext =context;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        TextView typeName = holder.getView(R.id.tv_name);
        if (position == selectPosotion) {
            holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
            typeName.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            holder.getView(R.id.iv_check).setVisibility(View.GONE);
            typeName.setTextColor(mContext.getResources().getColor(R.color.text_222));
        }

        typeName.setText(item);

    }
}
