package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.MainHold;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuyu on 2017/2/11.
 */

public class MainHoldAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainHold> dataList = new ArrayList<>();

    public MainHoldAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<MainHold> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public MainHold getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MainHold mainHold = dataList.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_main_hold, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(TextUtils.isEmpty(mainHold.getStockCode())){
            holder.tvStockName.setText(mainHold.getStockName() );
        }else {
            holder.tvStockName.setText(mainHold.getStockName() + "(" + mainHold.getStockCode() + ")");
        }
        if(mainHold.getStockChangePercent().contains("-")){
            holder.tvIncrease.setTextColor(mContext.getResources().getColor(R.color.increase_green));
        }else{
            holder.tvIncrease.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        holder.tvIncrease.setText(mainHold.getStockChangePercent() + "%");
        holder.tvRatio.setText(mainHold.getNewPercent()+ "%");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_stock_name)
        TextView tvStockName;
        @BindView(R.id.tv_increase)
        TextView tvIncrease;
        @BindView(R.id.tv_ratio)
        TextView tvRatio;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
