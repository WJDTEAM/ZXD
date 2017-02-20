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
import com.bf.zxd.zhuangxudai.pojo.PersonYyItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> implements View.OnClickListener {

    List<PersonYyItem> datas;
    MyItemClickListener mItemClickListener;



    private Context mContext;
    private LayoutInflater mLayoutInflater;
    PersonYyItem data;
    private String url = "http://211.149.235.17:8080/zxd/upload/zxgl-20170109161416796.jpeg";


    public int getLayout() {
        return R.layout.myappointment_item;
    }

    public MyAppointmentAdapter(List<PersonYyItem> datas, Context mContext) {
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

    @Override
    public void onClick(View v) {
        ApplyScheduleActivity.applyType="02";
        ApplyScheduleActivity.applyId=data.getApplyId();
        mContext.startActivity(new Intent(mContext, ApplyScheduleActivity.class));
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
        data = datas.get(position);
        holder.nameMyappointmentTv.setText(data.getProposer());
        holder.phoneMyappointmentTv.setText(data.getTel());
        holder.companynameMyappointmentTv.setText(data.getDecoCompany());
        holder.dataMyappointmentTv.setText(data.getSqrq());
        holder.applicationProgressMyappointmentTv.setOnClickListener(this);
        holder.templateItemLin.setOnClickListener(this);




    }

    @Override
    public int getItemCount() {
        //        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<PersonYyItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView;
        MyItemClickListener mListener;
        @BindView(R.id.name_myappointment_tv)
        TextView nameMyappointmentTv;
        @BindView(R.id.applicationProgress_myappointment_tv)
        TextView applicationProgressMyappointmentTv;
        @BindView(R.id.phone_myappointment_tv)
        TextView phoneMyappointmentTv;
        @BindView(R.id.companyname_myappointment_tv)
        TextView companynameMyappointmentTv;
        @BindView(R.id.data_myappointment_tv)
        TextView dataMyappointmentTv;
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
