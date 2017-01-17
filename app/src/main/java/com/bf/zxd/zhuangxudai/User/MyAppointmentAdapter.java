package com.bf.zxd.zhuangxudai.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.YysqItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {

    List<YysqItem> datas;
    MyItemClickListener mItemClickListener;


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String url = "http://211.149.235.17:8080/zxd/upload/zxgl-20170109161416796.jpeg";


    public int getLayout() {
        return R.layout.myappointment_item;
    }

    public MyAppointmentAdapter(List<YysqItem> datas, Context mContext) {
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
        final YysqItem data = datas.get(position);
        holder.addressMyappointmentTv.setText(data.getUnit_addr());
        holder.areastyleMyappointmentTv.setText((data.getHouseType()+"*"+data.getArea()));
        holder.companynameMyappointmentTv.setText(data.getCompany_name());
        holder.dataMyappointmentTv.setText(data.getSq_date());
        holder.nameMyappointmentTv.setText(data.getFull_name());
        holder.phoneMyappointmentTv.setText(data.getPhone());


    }

    @Override
    public int getItemCount() {
//        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<YysqItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView;
        MyItemClickListener mListener;

        @BindView(R.id.name_myappointment_tv)
        TextView nameMyappointmentTv;
        @BindView(R.id.phone_myappointment_tv)
        TextView phoneMyappointmentTv;
        @BindView(R.id.address_myappointment_tv)
        TextView addressMyappointmentTv;
        @BindView(R.id.companyname_myappointment_tv)
        TextView companynameMyappointmentTv;
        @BindView(R.id.data_myappointment_tv)
        TextView dataMyappointmentTv;
        @BindView(R.id.areastyle_myappointment_tv)
        TextView areastyleMyappointmentTv;
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
