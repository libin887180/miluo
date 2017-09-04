package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongdi.miluo.R;

import java.util.List;

/**
 * Created by libin on 2017/8/3.
 */

public class RecycleAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> datas;
    private List<String> msgs;

    //适配器初始化
    public RecycleAdapter(Context context, List<String> datas, List<String> msgs) {
        mContext = context;
        this.datas = datas;
        this.msgs = msgs;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext
        ).inflate(R.layout.fund_list_item, parent,
                false);//这个布局推荐基金
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return msgs.size() + datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}
