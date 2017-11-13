package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.RateDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuyu on 2017/2/11.
 */

public class BuyPremiumAdapter extends BaseAdapter {
    private Context mContext;
    private List<RateDetail> dataList = new ArrayList<>();

    public BuyPremiumAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<RateDetail> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public RateDetail getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_premium, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDec.setText(dataList.get(position).getAmountDesc());
//        holder.tvDepRate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.tvDepRate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

        if(TextUtils.isEmpty(dataList.get(position).getDiscount())||Double.parseDouble(dataList.get(position).getDiscount())==0||Double.parseDouble(dataList.get(position).getDiscount())==1){//没有折扣
            holder.tvDepRate.setText("");
            holder.tvRate.setText(dataList.get(position).getRateValue());
        }else{
           String rate =  dataList.get(position).getRateValue().substring(0,dataList.get(position).getRateValue().length()-1);
            holder.tvRate.setText(Double.parseDouble(rate)* Double.parseDouble(dataList.get(position).getDiscount())+"%");
            holder.tvDepRate.setText(dataList.get(position).getRateValue());
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_dec)
        TextView tvDec;
        @BindView(R.id.tv_dep_rate)
        TextView tvDepRate;
        @BindView(R.id.tv_rate)
        TextView tvRate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
