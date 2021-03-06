package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeAssetBean;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class HistoryAssetAdapter extends BaseRecyclerAdapter<HomeAssetBean> {

    public HistoryAssetAdapter(Context context, List<HomeAssetBean> datas) {
        super(context, R.layout.asset_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HomeAssetBean item, int position) {
        holder.setText(R.id.tv_fund_name, item.getFundname());

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
        holder.setText(R.id.tv_fund_code, "(" + item.getFundcode() + ") " + fundType);
        holder.setText(R.id.tv_fund_name, item.getFundname());
        holder.setText(R.id.tv_asset, item.getMarketval() + "");
        holder.setText(R.id.tv_profit, item.getTotalshareincome() + "");
        if(TextUtils.equals(item.getStatus(),"申购中")){
            holder.setImageResource(R.id.iv_status,R.drawable.bg_buying);
        }else {
            holder.setImageResource(R.id.iv_status,R.drawable.bg_income);
        }



    }
}
