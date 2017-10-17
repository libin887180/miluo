package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.BankInfo;

import java.util.List;

/**
 * @ explain:
 * @ author：libin
 */
public class BankCardListAdapter extends BaseRecyclerAdapter<BankInfo> {

    public BankCardListAdapter(Context context, List<BankInfo> datas) {
        super(context, R.layout.bank_card_item, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, BankInfo item, int position) {

        holder.setText(R.id.tv_bank_name,item.getBankname());
        holder.setText(R.id.tv_bank_card_num,item.getCardno());
        holder.setText(R.id.tv_dsc,item.getAmtdesc());
        ImageView ivBankIcon = (ImageView) holder.getView(R.id.iv_logo);
        Glide.with(mContext).load(item.getLogourl()).apply(new RequestOptions().placeholder(R.drawable.icon_bank_default).error(R.drawable.icon_bank_default))
                .into(ivBankIcon);

    }
}
