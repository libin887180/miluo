package com.zhongdi.miluo.adapter;

import android.content.Context;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.HomeFund;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HomeSpecialityAdapter extends BaseRecyclerAdapter<HomeFund> {

    public HomeSpecialityAdapter(Context context, List<HomeFund> datas) {
        super(context, R.layout.home_speciality_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HomeFund item, int position) {
        holder.setText(R.id.tv_title,item.getTitle());
        holder.setText(R.id.tv_content,item.getContent());
        holder.setText(R.id.tv_rate,item.getYearrate());
        holder.setText(R.id.tv_min_amount,Double.parseDouble(item.getMinsubscribeamt())/100+"元起购");
    }
}
