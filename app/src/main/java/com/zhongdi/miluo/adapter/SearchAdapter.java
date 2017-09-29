package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.SearchFund;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class SearchAdapter extends BaseRecyclerAdapter<SearchFund> {

    public SearchAdapter(Context context, List<SearchFund> datas) {
        super(context, R.layout.item_search, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, SearchFund item, int position) {
        TextView tvFundName = holder.getView(R.id.tv_fund_name);
        TextView tvFundCode = holder.getView(R.id.tv_fund_code);
        TextView tvIncreaceType = holder.getView(R.id.tv_increase_type);
        TextView tvFundType = holder.getView(R.id.tv_type);
        TextView tv_increase_percent = holder.getView(R.id.tv_increase_percent);
        tvFundName.setText(item.getFundname());
        tvFundCode.setText(item.getFundcode());
        if(item.getDayrate().contains("-")){
            tv_increase_percent.setTextColor(mContext.getResources().getColor(R.color.increase_green));
        }else{
            tv_increase_percent.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        switch (item.getFundtype()){
            case MiluoConfig.GUPIAO:
                tvFundType.setText("股票型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.ZHAIQUAN:
                tvFundType.setText("债券型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.HUNHE:
                tvFundType.setText("混合型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.HUOBI:
                tvFundType.setText("货币型");
                tvIncreaceType.setText("七日年化");
                if(item.getWeekrate().contains("-")){
                    tv_increase_percent.setTextColor(mContext.getResources().getColor(R.color.increase_green));
                }else{
                    tv_increase_percent.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                tv_increase_percent.setText(item.getWeekrate()+"%");
                break;
            case MiluoConfig.ZHISHU:
                tvFundType.setText("指数型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.BAOBEN:
                tvFundType.setText("保本型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");;
                break;
            case MiluoConfig.ETF:
                tvFundType.setText("ETF联接");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.DQII:
                tvFundType.setText("QDII");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.LOF:
                tvFundType.setText("LOF");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.DUANQI:
                tvFundType.setText("短期理财型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.ALL:
                tvFundType.setText("全部");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
            case MiluoConfig.ZUHE:
                tvFundType.setText("组合型");
                tvIncreaceType.setText("日涨幅");
                tv_increase_percent.setText(item.getDayrate()+"%");
                break;
        }

    }
}
