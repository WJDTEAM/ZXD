package com.bf.zxd.zhuangxudai.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.Zxd;
import com.bf.zxd.zhuangxudai.template.TemplateListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/10.
 */

public class LoanBankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<Zxd> mDatas;
    private final LayoutInflater mLayoutInflater;
    private TemplateListAdapter.MyItemClickListener mItemClickListener;

    public LoanBankListAdapter(Context mContext, List<Zxd> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.loan_bank_msg_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHoder(v);

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
        MyViewHoder myViewHoder = (MyViewHoder) holder;
//        Picasso.with(mContext).load(mDatas.get(position).getThumbnails())
//                .placeholder(R.drawable.bank_msg)
//                .error(R.drawable.bank_msg)
//                .into(myViewHoder.thumbnails);
        Picasso.with(mContext).load(mDatas.get(position).getBank_img())
                .placeholder(R.drawable.bank_log)
                .error(R.drawable.bank_log)
                .into(myViewHoder.bankImg);
        myViewHoder.title.setText(mDatas.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnails)
        ImageView thumbnails;
        @BindView(R.id.bank_img)
        ImageView bankImg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.bank_apply)
        TextView bank_apply;
        public MyViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}
