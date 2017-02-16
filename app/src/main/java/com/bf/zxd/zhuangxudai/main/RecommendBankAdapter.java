package com.bf.zxd.zhuangxudai.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.EnterActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.Recommends;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页-推荐银行列表Adapter
 * Created by wjy on 2016/11/7.
 */

public class RecommendBankAdapter extends RecyclerView.Adapter<RecommendBankAdapter.ViewHolder> {

    List<Recommends> datas;
    MyItemClickListener mItemClickListener;


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Random random;


    public int getLayout() {
        return R.layout.loan_recommend;
    }

    public RecommendBankAdapter(List<Recommends> datas, Context mContext) {
        this.mContext = mContext;
        this.datas = datas;
        mLayoutInflater = LayoutInflater.from(mContext);
        random = new Random();
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
        final Recommends data = datas.get(position);
//        holder.rateRecommendBankDowntv.setText(data.getRate_unit() + "费率");
        if (data.getCompanyIcon()!=null){
            Picasso.with(mContext).load(data.getCompanyIcon()).error(R.drawable.myhb).into(holder.picRecommendBankImg);
        }else {
            holder.picRecommendBankImg.setImageResource(R.drawable.myhb);
        }

        holder.numRecommendBankUptv.setText(13+"");
        holder.rateRecommendBankUptv.setText(data.getRate());

//                holder.numRecommendBankUptv.setText(data.getMax_money()+"");
                holder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LoanApplyActivity.bankId = data.getCompanyId();
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

    public void setdatas(List<Recommends> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener mListener;

        @BindView(R.id.pic_recommendBank_img)
        ImageView picRecommendBankImg;
        @BindView(R.id.rate_recommendBank_uptv)
        TextView rateRecommendBankUptv;
        @BindView(R.id.rate_recommendBank_downtv)
        TextView rateRecommendBankDowntv;
        @BindView(R.id.num_recommendBank_uptv)
        TextView numRecommendBankUptv;
        @BindView(R.id.num_recommendBank_downtv)
        TextView numRecommendBankDowntv;
        @BindView(R.id.linear)
        LinearLayout linear;


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
