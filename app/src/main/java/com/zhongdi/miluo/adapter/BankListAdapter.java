package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.BankInfo;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class BankListAdapter extends BaseRecyclerAdapter<BankInfo> {
    //    int selectPosotion =0;
    String selectBankName;

    //    public void setCheck(int index) {
//        selectPosotion = index;
//        notifyDataSetChanged();
//    }
    public void setCheck(String bankName) {
        selectBankName = bankName;
        notifyDataSetChanged();
    }


    public BankListAdapter(Context context, List<BankInfo> datas) {
        super(context, R.layout.bank_list_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, BankInfo item, int position) {
        TextView bankName = (TextView) holder.getView(R.id.tv_bank_name);
        TextView translimit = (TextView) holder.getView(R.id.tv_desc);
        ImageView ivBankIcon = (ImageView) holder.getView(R.id.iv_bank_icon);


        bankName.setText(item.getBankname());
        if(TextUtils.equals(item.getBankname(),selectBankName)){
            holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.iv_check).setVisibility(View.GONE);
        }
        translimit.setText(item.getAmtdesc());
        Glide.with(mContext).load(item.getLogourl()).apply(new RequestOptions().placeholder(R.drawable.icon_bank_default).error(R.drawable.icon_bank_default))
                .into(ivBankIcon);
    }
}
