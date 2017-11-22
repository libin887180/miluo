package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.widget.ImageView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.BaseRecyclerHolder;
import com.zhongdi.miluo.model.InfomationNote;
import com.zhongdi.miluo.util.TimeUtil;
import com.zhongdi.miluo.util.xUtilsImageUtils;

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

        holder.setText(R.id.tv_title,item.getArticletitle());
        holder.setText(R.id.tv_date, TimeUtil.changeToDate(item.getReleasetime()));
       ImageView imageView = (ImageView) holder.getView(R.id.img_icon);
        xUtilsImageUtils.display(imageView, item.getThumbnail(), 10, R.drawable.no_picture, R.drawable.no_picture);
    }
}
