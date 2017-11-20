package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeFund;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class AwardedFundAdapter extends BaseRecyclerAdapter<HomeFund> {
    View.OnClickListener childClickListener;
    int childid;

    public AwardedFundAdapter(Context context, List<HomeFund> datas) {
        super(context, R.layout.awarded_fund_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, HomeFund item, int position) {

        holder.setText(R.id.tv_fund_name, item.getFundName());
        TextView tvFundType = holder.getView(R.id.tv_fund_code);
        switch (item.getFundType()) {
            case MiluoConfig.GUPIAO:
                tvFundType.setText("股票型 | " + item.getRemark());
                break;
            case MiluoConfig.ZHAIQUAN:
                tvFundType.setText("债券型 | " + item.getRemark());
                break;
            case MiluoConfig.HUNHE:
                tvFundType.setText("混合型 | " + item.getRemark());
                break;
            case MiluoConfig.HUOBI:
                tvFundType.setText("货币型 | " + item.getRemark());
                break;
            case MiluoConfig.ZHISHU:
                tvFundType.setText("指数型 | " + item.getRemark());
                break;
            case MiluoConfig.BAOBEN:
                tvFundType.setText("保本型 | " + item.getRemark());
                break;
            case MiluoConfig.ETF:
                tvFundType.setText("ETF联接 | " + item.getRemark());
                break;
            case MiluoConfig.DQII:
                tvFundType.setText("QDII | " + item.getRemark());
                break;
            case MiluoConfig.LOF:
                tvFundType.setText("LOF | " + item.getRemark());
                break;
            case MiluoConfig.DUANQI:
                tvFundType.setText("短期理财型 | " + item.getRemark());
                break;
            case MiluoConfig.ALL:
                tvFundType.setText("全部 | " + item.getRemark());
                break;
            case MiluoConfig.ZUHE:
                tvFundType.setText("组合型 | " + item.getRemark());
                break;

        }

        holder.setText(R.id.tv_info, item.getYearrate());
        if (item.getStatus().equals("1")) {
            holder.setImageResource(R.id.iv_collect, R.drawable.home_collected);
        } else {
            holder.setImageResource(R.id.iv_collect, R.drawable.home_collect);
        }
        ImageView imageCollect = (ImageView) holder.getView(R.id.iv_collect);
        imageCollect.setTag(item);
        if (childClickListener != null) {
            setClickListener(holder, childid, childClickListener);
        }

    }

    public void setOnItemChildClickListener(int viewId, View.OnClickListener onClickListener) {
        childid = viewId;
        childClickListener = onClickListener;
    }

}
