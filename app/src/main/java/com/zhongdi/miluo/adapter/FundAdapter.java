package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongdi.miluo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by libin on 2017/8/3.
 */

public class FundAdapter extends RecyclerView.Adapter<FundAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> datas;

    //适配器初始化
    public FundAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fund_list_item, parent,
                false);//这个布局推荐基金
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvFundName.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_fund_name)
        TextView tvFundName;
        @BindView(R.id.tv_fund_code)
        TextView tvFundCode;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_info)
        TextView tvInfo;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "aaa", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
