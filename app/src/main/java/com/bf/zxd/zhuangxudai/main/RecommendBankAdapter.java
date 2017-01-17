package com.bf.zxd.zhuangxudai.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.EnterActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class RecommendBankAdapter extends RecyclerView.Adapter<RecommendBankAdapter.ViewHolder> {

    List<RecommendBank> datas;
    MyItemClickListener mItemClickListener;



    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public int getLayout() {
        return R.layout.loan_recommend;
    }

    public RecommendBankAdapter(List<RecommendBank> datas, Context mContext) {
        this.mContext = mContext;
        this.datas = datas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getLayout(), parent, false);

        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RecommendBank data = datas.get(position);
        holder.banknameRecommendBankTv.setText(data.getBank_name());
        holder.maxMoneyRecommendBankTv.setText(""+data.getMax_money());
        holder.rateRecommendBankTv.setText(data.getRate());
        holder.homeApplyLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View view) {

                LoanApplyActivity.mZxd=data;
                EventBus.getDefault().post(new EnterActivityEvent(LoanApplyActivity.class));
//                mContext.startActivity(new Intent(mContext, LoanApplyActivity.class));


            }
        });

    }

    @Override
    public int getItemCount() {
//        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<RecommendBank> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener mListener;

        @BindView(R.id.bankname_recommendBank_tv)
        TextView banknameRecommendBankTv;
        @BindView(R.id.rate_recommendBank_tv)
        TextView rateRecommendBankTv;
        @BindView(R.id.maxMoney_recommendBank_tv)
        TextView maxMoneyRecommendBankTv;
        @BindView(R.id.home_applyLoan_btn)
        TextView homeApplyLoanBtn;


        ViewHolder(View view, MyItemClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }

        }
    }
}
