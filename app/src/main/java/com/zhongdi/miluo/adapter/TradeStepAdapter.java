package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.TradeRecord.Part2Bean.StepsBean;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 16:42
 * @ email：gdutxiaoxu@163.com
 */
public class TradeStepAdapter extends BaseRecyclerAdapter<StepsBean> {
    String cancelstatus;
    int currentStep;
    String tradType;
    View.OnClickListener childClickListener;
    int childid;

    public TradeStepAdapter(Context context, List<StepsBean> datas, String cancelstatus, int currentStep, String tradType) {

        super(context, R.layout.item_trade_step, datas);
        this.cancelstatus = cancelstatus;
        this.currentStep = currentStep;
        this.tradType = tradType;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, StepsBean item, int position) {
        holder.setText(R.id.setp_name, item.getTitle());
        holder.setText(R.id.step_time, item.getTime());
        holder.setVisible(R.id.tv_cancel_status, false);
        if (position == 0) {
            if (cancelstatus.equals("0") && currentStep == 1) {//cancelstatus 1 是不可撤回 0 是可撤回
                holder.setVisible(R.id.tv_cancel_status, true);
                holder.setText(R.id.tv_cancel_status, "下单撤回");
            } else {
                holder.setVisible(R.id.tv_cancel_status, false);
            }
        }
        if (position == 2) {
            if (tradType.contains("申购") && currentStep == 3) {

                if (item.getTitle().contains("回款")) {
                    holder.setVisible(R.id.tv_cancel_status, false);
                } else {
                    holder.setVisible(R.id.tv_cancel_status, true);
                    holder.setText(R.id.tv_cancel_status, "赎回");
                }
            } else {
                holder.setVisible(R.id.tv_cancel_status, false);
            }
        }
        if (childClickListener != null) {
            setClickListener(holder, childid, childClickListener);
        }

    }

    public void setOnItemChildClickListener(int viewId, View.OnClickListener onClickListener) {
        childid = viewId;
        childClickListener = onClickListener;
    }
}
