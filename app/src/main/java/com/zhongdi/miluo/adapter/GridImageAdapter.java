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
import com.zhongdi.miluo.model.HomeActiv;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kenn on 2017/9/22.
 */

public class GridImageAdapter extends BaseAdapter {

    private List<HomeActiv> datas;
    private Context mContext;

    public GridImageAdapter(Context c, List<HomeActiv> datas) {
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
        HomeActiv activ = datas.get(position);
        String desc = activ.getTitle().replace("\\n", "\n");
        holder.tvDsc.setText(desc);
        switch (activ.getType()){
           case  "1":
               holder.ivIcon.setImageResource(R.drawable.voucher);
               holder.tvTitle.setText("体验金");
               break;
           case  "5":
               holder.ivIcon.setImageResource(R.drawable.match);
               holder.tvTitle.setText("小白学基");
               break;
           case  "3":
               holder.ivIcon.setImageResource(R.drawable.compass);
               holder.tvTitle.setText("米罗盘");
               break;
           case  "4":
               holder.ivIcon.setImageResource(R.drawable.sixbuddy);
               holder.tvTitle.setText("六人同行");
               break;
            case  "2":
                holder.ivIcon.setImageResource(R.drawable.sixbuddy);
                holder.tvTitle.setText("新手");
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