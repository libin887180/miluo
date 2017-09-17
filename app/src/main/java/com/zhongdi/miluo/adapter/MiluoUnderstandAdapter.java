package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhongdi.miluo.R;

import java.util.List;


/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class MiluoUnderstandAdapter extends BaseRecyclerAdapter<String> {
private Context mContext;
    public MiluoUnderstandAdapter(Context context, List<String> datas) {
        super(context, R.layout.home_understand_item, datas);
        mContext = context;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        ImageView photo = (ImageView)holder.getView(R.id.iv_photo);
        Glide.with(mContext).load("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190135_14593482_3_640_427.jpg").into(photo);
    }
}
