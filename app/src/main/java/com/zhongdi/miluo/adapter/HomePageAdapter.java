package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.widget.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class HomePageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> datas;
    private List<String> msgs;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    LayoutInflater inflater;

    public HomePageAdapter(Context context, List<String> datas, List<String> msgs) {
        mContext = context;
        this.datas = datas;
        this.msgs = msgs;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 1 ? TYPE_2 : TYPE_1;
    }

    @Override
    public int getCount() {
        return msgs.size() + datas.size();
    }

    @Override
    public Object getItem(int i) {
        return i == 1 ? TYPE_2 : TYPE_1;
    }

    @Override
    public int getViewTypeCount() {
// TODO Auto-generated method stub 
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        MarqueeView marqueeView1;

        int type = getItemViewType(position);

        //无convertView，需要new出各个控件
        if (convertView == null) {
            //按当前所需的样式，确定new的布局
            switch (type) {
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.home_list_item, parent, false);
                    holder1 = new ViewHolder1();
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.home_list_item2, parent, false);
                    holder2 = new ViewHolder2();
                    //初始化布局的控件
                    marqueeView1 = (MarqueeView) convertView.findViewById(R.id.upview1);
                    List<View> views2 = new ArrayList<>();
                    views2.clear();//记得加这句话，不然可能会产生重影现象
                    for (int i = 0; i < msgs.size(); i++) {
                        //设置滚动的单个布局
                        LinearLayout moreView = (LinearLayout) inflater.inflate(R.layout.item_view_single, null);
                        //初始化布局的控件
                        TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);

                        /**
                         * 设置监听
                         */
                        moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        //进行对控件赋值
                        tv1.setText(msgs.get(i).toString());

                        //添加到循环滚动数组里面去
                        views2.add(moreView);
                    }

                    marqueeView1.setViews(views2);
                    convertView.setTag(holder2);
                    break;
            }
        } else {
            //有convertView，按样式，取得不用的布局
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        //设置资源
        switch (type) {
            case TYPE_1:
                break;
            case TYPE_2:
                break;
        }

        return convertView;
    }


    public class ViewHolder1 {
        public TextView nameTv;
    }

    public class ViewHolder2 {
        public TextView nameTv;
    }
}
