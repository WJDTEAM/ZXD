package com.bf.zxd.zhuangxudai.User;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.DksqItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class MyLoanAdapter extends RecyclerView.Adapter<MyLoanAdapter.ViewHolder> {

    List<DksqItem> datas;
    MyItemClickListener mItemClickListener;


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String url = "http://211.149.235.17:8080/zxd/upload/zxgl-20170109161416796.jpeg";


    public int getLayout() {
        return R.layout.myloan_item;
    }

    public MyLoanAdapter(List<DksqItem> datas, Context mContext) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DksqItem data = datas.get(position);
        holder.nameMyloanTv.setText(data.getFull_name());
        holder.nameMyloanTv.setVisibility(View.INVISIBLE);
        holder.phoneMyloanTv.setText(data.getPhone());
        holder.phoneMyloanTv.setVisibility(View.INVISIBLE);
        holder.dateMyloanTv.setText(data.getSq_date());
        holder.companynameMyloanTv.setText(data.getCompany_name());
        holder.banknameMyloanTv.setText(data.getBank_name());
        holder.applymoneyMyloanTv.setText(data.getApply_money()+"万");
        holder.statusMyloanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ApplyScheduleActivity.class));
            }
        });
     



    }

    @Override
    public int getItemCount() {
//        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<DksqItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView;
        MyItemClickListener mListener;

        @BindView(R.id.name_myloan_tv)
        TextView nameMyloanTv;
        @BindView(R.id.phone_myloan_tv)
        TextView phoneMyloanTv;
        @BindView(R.id.applymoney_myloan_tv)
        TextView applymoneyMyloanTv;
        @BindView(R.id.status_myloan_tv)
        TextView statusMyloanTv;
        @BindView(R.id.bankname_myloan_tv)
        TextView banknameMyloanTv;
        @BindView(R.id.date_myloan_tv)
        TextView dateMyloanTv;
        @BindView(R.id.companyname_myloan_tv)
        TextView companynameMyloanTv;
        @BindView(R.id.template_item_lin)
        LinearLayout templateItemLin;

        ViewHolder(View view, MyItemClickListener listener) {
            super(view);
            rootView = view;
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
