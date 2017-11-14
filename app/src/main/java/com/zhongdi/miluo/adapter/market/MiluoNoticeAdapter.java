package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.MessageBean;

import java.util.List;

/**
 * Created by liuyu on 2017/2/11.
 */

public class MiluoNoticeAdapter extends BaseRecyclerAdapter<MessageBean> {

    public MiluoNoticeAdapter(Context context, List<MessageBean> datas) {
        super(context, R.layout.item_message, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, MessageBean item, int position) {

        TextView title = holder.getView(R.id.tv_title);
        TextView date = holder.getView(R.id.tv_date);
        TextView dsc = holder.getView(R.id.tv_dsc);
        ImageView status = holder.getView(R.id.iv_status);

        title.setText(item.getTitle());
        date.setText(item.getAdd_time());
        dsc.setText(item.getContent());
        if (item.getStatus().equals("0")) {
//            status.setVisibility(View.VISIBLE);
            title.setTextColor(mContext.getResources().getColor(R.color.text_222));

        } else {
//            status.setVisibility(View.INVISIBLE);
            title.setTextColor(mContext.getResources().getColor(R.color.text_color_normal));
        }


    }
}
