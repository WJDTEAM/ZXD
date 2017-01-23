package com.bf.zxd.zhuangxudai.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.EnterActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.ZxdBank;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/17.
 */

public class LoanBankList3Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context mContext;
    private List<ZxdBank> mDatas;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public LoanBankList3Adapter(Context mContext, List<ZxdBank> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void update(List<ZxdBank> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.loan_bank_msg_list_item2, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHoder(v);

        return viewHolder;
    }


    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHoder myViewHoder = (ViewHoder) holder;
        myViewHoder.loanTitleName.setText("");
        myViewHoder.goToLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoanApplyActivity.bankId=mDatas.get(position).getBank_id();
                EventBus.getDefault().post(new EnterActivityEvent(LoanApplyActivity.class));
            }
        });
        if(mDatas.get(position).getBank_logo()!=null&&!mDatas.get(position).getBank_logo().equals("")) {
            Picasso.with(mContext).load(mDatas.get(position).getBank_logo())
                    .placeholder(R.drawable.bank_log)
                    .error(R.drawable.bank_log)
                    .into(myViewHoder.loanTitleImg);
        }
        if(mDatas.get(position).getLoan_type()==1){
            myViewHoder.loanDkfw.setText("(" + "装修贷" + ")");
        }else{
            myViewHoder.loanDkfw.setText("(" + "房贷" + ")");
        }
        myViewHoder.loanRate.setText("费率：" + mDatas.get(position).getRate() );
        myViewHoder.makeLoadDays.setText(mDatas.get(position).getMake_load_days() + mDatas.get(position).getLoad_unit()+"放贷");
        myViewHoder.loanCycle.setText("贷款期限:"+mDatas.get(position).getCycle()+"月");
        myViewHoder.loanMaxMoney.setText(mDatas.get(position).getMax_money()+"");
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    static class ViewHoder extends RecyclerView.ViewHolder {

        @BindView(R.id.loan_title_img)
        ImageView loanTitleImg;
        @BindView(R.id.loan_title_name)
        TextView loanTitleName;
        @BindView(R.id.loan_dkfw)
        TextView loanDkfw;
        @BindView(R.id.loan_max_money)
        TextView loanMaxMoney;
        @BindView(R.id.make_load_days)
        TextView makeLoadDays;
        @BindView(R.id.loan_rate)
        TextView loanRate;
        @BindView(R.id.go_to_loan)
        Button goToLoan;
        @BindView(R.id.loan_cycle)
        TextView loanCycle;
        public ViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}