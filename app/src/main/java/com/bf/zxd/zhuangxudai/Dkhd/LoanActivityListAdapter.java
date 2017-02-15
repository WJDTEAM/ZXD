package com.bf.zxd.zhuangxudai.Dkhd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.DkhdItem;
import com.bf.zxd.zhuangxudai.template.TemplateListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/10.
 */

public class LoanActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<DkhdItem> mDatas;
    private final LayoutInflater mLayoutInflater;
    private TemplateListAdapter.MyItemClickListener mItemClickListener;

    public List<DkhdItem> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<DkhdItem> mDatas) {
        this.mDatas = mDatas;
    }

    public LoanActivityListAdapter(Context mContext, List<DkhdItem> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.loan_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new LoanActivityListAdapter.MyViewHoder(v);

        return viewHolder;
    }


    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(TemplateListAdapter.MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LoanActivityListAdapter.MyViewHoder myViewHoder = (LoanActivityListAdapter.MyViewHoder) holder;
        Picasso.with(mContext).load(mDatas.get(position).getThumbnails())
                .placeholder(R.drawable.demo)
                .error(R.drawable.demo)
                .into(myViewHoder.loanActivityImg);
        myViewHoder.loanActivityTitle.setText(mDatas.get(position).getTitle());
        myViewHoder.loanActivityTime.setText(mDatas.get(position).getDescription());
        myViewHoder.loan_activity_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EventBus.getDefault().post(LoanDetailsActivity.class);
                mItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {

        @BindView(R.id.loan_activity_title)
        TextView loanActivityTitle;
        @BindView(R.id.loan_activity_img)
        ImageView loanActivityImg;
        @BindView(R.id.loan_activity_time)
        TextView loanActivityTime;
        @BindView(R.id.loan_activity_lin)
        LinearLayout loan_activity_lin;

        public MyViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}
