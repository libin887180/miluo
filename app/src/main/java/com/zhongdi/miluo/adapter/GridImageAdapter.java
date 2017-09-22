package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kenn on 2017/9/22.
 */

public class GridImageAdapter extends BaseAdapter {

    private List<String> datas;
    private Context mContext;

    public GridImageAdapter(Context c, List<String> datas) {
        mContext = c;
        Resources resources = c.getResources();
        this.datas = datas;
//            btnDrawable = resources.getDrawable(R.drawable.bg);
    }

    public int getCount() {
        return datas.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_activity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       switch (position){
           case  0:
               holder.ivIcon.setImageResource(R.drawable.voucher);
               holder.tvTitle.setText("体验金");
               holder.tvDsc.setText("10秒急速体验最高赢20元话费");
               break;
           case  1:
               holder.ivIcon.setImageResource(R.drawable.match);
               holder.tvTitle.setText("月月盈赛");
               holder.tvDsc.setText("跟着牛人赚大钱瓜分10万奖池");
               break;
           case  2:
               holder.ivIcon.setImageResource(R.drawable.compass);
               holder.tvTitle.setText("米罗盘");
               holder.tvDsc.setText("疯狂大转盘拿1000元京东卡");
               break;
           case  3:
               holder.ivIcon.setImageResource(R.drawable.sixbuddy);
               holder.tvTitle.setText("六人同行");
               holder.tvDsc.setText("呼朋唤友赢iphone8");
               break;
       }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_dsc)
        TextView tvDsc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}