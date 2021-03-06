package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.HomeFund;

import java.util.List;


/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class MiluoUnderstandAdapter extends BaseRecyclerAdapter<HomeFund> {
private Context mContext;
    public MiluoUnderstandAdapter(Context context, List<HomeFund> datas) {
        super(context, R.layout.home_understand_item, datas);
        mContext = context;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HomeFund item, int position) {
        ImageView photo = (ImageView)holder.getView(R.id.iv_photo);
//        switch (position){
//            case 0:
//                Glide.with(mContext).load(R.drawable.understand_car).into(photo);
//                break;
//            case 1:
//                Glide.with(mContext).load(R.drawable.understand_baby).into(photo);
//                break;
//            case 2:
//                Glide.with(mContext).load(R.drawable.understand_tuixiu).into(photo);
//                break;
//        }

        if(item.getTitle().contains("宝贝")){
            Glide.with(mContext).load(R.drawable.understand_baby).into(photo);
        }else if(item.getTitle().contains("买车")){
            Glide.with(mContext).load(R.drawable.understand_car).into(photo);
        }else{
            Glide.with(mContext).load(R.drawable.understand_tuixiu).into(photo);
        }
    holder.setText(R.id.tv_title,item.getTitle());
    holder.setText(R.id.tv_content,item.getContent());
    holder.setText(R.id.tv_rate,item.getYearrate());


    }
}
