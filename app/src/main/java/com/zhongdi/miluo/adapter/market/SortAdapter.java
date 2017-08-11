package com.zhongdi.miluo.adapter.market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuyu on 2017/2/11.
 */

public class SortAdapter extends BaseAdapter {
    private Context mContext;
    int selectPosotion = -1;
    private  String[] dataList;

    public SortAdapter(Context context) {
        this.mContext = context;
        dataList = new String[]{"默认","正序","倒序"};
    }

    public void setCheck(int index) {
         selectPosotion = index;
    }

    @Override
    public int getCount() {

        return dataList.length;
    }

    @Override
    public Object getItem(int position) {
        return dataList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.sort_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.sortTv.setText(dataList[position]);

        if(selectPosotion==position){
            holder.iv_check.setVisibility(View.VISIBLE);
            holder.sortTv.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            holder.sortTv.setTextColor(mContext.getResources().getColor(R.color.text_color_normal));
            holder.iv_check.setVisibility(View.GONE);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_sort)
        TextView sortTv;
        @BindView(R.id.iv_check)
        ImageView iv_check;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
