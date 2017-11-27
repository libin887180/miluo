package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeAssetBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class CurAssetAdapter2 extends BaseAdapter {
    private List<HomeAssetBean> datas;
    private Context mContext;
    private boolean assetVisable = true;

    public CurAssetAdapter2(Context context, List<HomeAssetBean> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    public void setAssetVisable(boolean visable) {
        assetVisable = visable;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.asset_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeAssetBean item = datas.get(position);
        String fundType = "";
        switch (item.getFundtype() + "") {
            case MiluoConfig.GUPIAO:
                fundType = "股票型";
                break;
            case MiluoConfig.ZHAIQUAN:
                fundType = "债券型";
                break;
            case MiluoConfig.HUNHE:
                fundType = "混合型";
                break;
            case MiluoConfig.HUOBI:
                fundType = "货币型";
                break;
            case MiluoConfig.ZHISHU:
                fundType = "指数型";
                break;
            case MiluoConfig.BAOBEN:
                fundType = "保本型";
                break;
            case MiluoConfig.ETF:
                fundType = "ETF联接";
                break;
            case MiluoConfig.DQII:
                fundType = "QDII";
                break;
            case MiluoConfig.LOF:
                fundType = "LOF";
                break;
            case MiluoConfig.DUANQI:
                fundType = "短期理财型";
                break;
            case MiluoConfig.ALL:
                fundType = "全部";
                break;
            case MiluoConfig.ZUHE:
                fundType = "组合型";
                break;
        }
        holder.tvFundCode.setText("(" + item.getFundcode() + ") " + fundType);
        holder.tvFundName.setText(item.getFundname());
        holder.tvFundCode.setText("(" + item.getFundcode() + ") " + fundType);

        if (assetVisable) {
            holder.tvAsset.setText(item.getMarketval() + "");
            holder.tvProfit.setText(item.getTotalshareincome() + "");
            if (item.getTotalshareincome().contains("-")) {
                holder.tvProfit.setTextColor( mContext.getResources().getColor(R.color.increase_green));
            }else{
                holder.tvProfit.setTextColor( mContext.getResources().getColor(R.color.red));
            }
        }else{
            holder.tvAsset.setText( "****");
            holder.tvProfit.setText("****");
            holder.tvProfit.setTextColor( mContext.getResources().getColor(R.color.red));
        }
        if (TextUtils.equals(item.getStatus(),"申购中")) {
            holder.ivStatus.setImageResource( R.drawable.bg_buying);
        } else {
            holder.ivStatus.setImageResource(  R.drawable.bg_income);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_fund_name)
        TextView tvFundName;
        @BindView(R.id.tv_fund_code)
        TextView tvFundCode;
        @BindView(R.id.tv_asset)
        TextView tvAsset;
        @BindView(R.id.tv_profit)
        TextView tvProfit;
        @BindView(R.id.tv_profits)
        TextView tvProfits;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
