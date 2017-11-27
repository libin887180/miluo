package com.zhongdi.miluo.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.TradeRecord;
import com.zhongdi.miluo.util.xUtilsImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuyu on 2017/2/11.
 */

public class TransInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<TradeRecord.Part3Bean> dataList = new ArrayList<>();

    public TransInfoAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<TradeRecord.Part3Bean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public TradeRecord.Part3Bean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_trans_record_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            holder.contentView.setBackgroundColor(Color.WHITE);
        } else {
            holder.contentView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.cpxx.setText(dataList.get(position).getKey1());
        if (dataList != null) {
            if (TextUtils.equals(dataList.get(position).getKey1(),"产品信息")) {
                holder.ivNext.setVisibility(View.VISIBLE);
            } else {
                if (TextUtils.equals(dataList.get(position).getKey1(),"支付方式")) {
                    holder.ivBankIcon.setVisibility(View.VISIBLE);
                    xUtilsImageUtils.display(holder.ivBankIcon, dataList.get(position).getKey2(), R.drawable.icon_bank_default, R.drawable.icon_bank_default);
                } else {
                    holder.ivBankIcon.setVisibility(View.GONE);
                }
                holder.ivNext.setVisibility(View.GONE);
            }
            if (TextUtils.equals(dataList.get(position).getKey1(),"支付方式")) {
                holder.tvName.setText(dataList.get(position).getKey3());
            } else {
                holder.tvName.setText(dataList.get(position).getKey2());
            }

        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.cpxx)
        TextView cpxx;
        @BindView(R.id.iv_next)
        ImageView ivNext;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_bank_icon)
        ImageView ivBankIcon;
        @BindView(R.id.contentView)
        RelativeLayout contentView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
