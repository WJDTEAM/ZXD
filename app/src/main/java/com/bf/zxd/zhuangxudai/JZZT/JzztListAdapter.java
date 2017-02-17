package com.bf.zxd.zhuangxudai.JZZT;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/2/13.
 */

public class JzztListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<JzhdItem> mDatas;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public List<JzhdItem> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<JzhdItem> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    public JzztListAdapter(Context mContext, List<JzhdItem> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.jzzt_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(v);

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
        ViewHolder myViewHoder = (ViewHolder) holder;
        if (mDatas.get(position).getThumbnails() != null) {
            if (!mDatas.get(position).getThumbnails().equals("")) {
                Picasso.with(mContext).load(mDatas.get(position).getThumbnails())
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo)
                        .into(myViewHoder.jzzztItemImg);
            }
        }
        myViewHoder.jzztSponsorTxt.setText(mDatas.get(position).getSponsor());
        myViewHoder.jzztAddrTxt.setText(mDatas.get(position).getAddr());
        myViewHoder.jzztTitleTxt.setText(mDatas.get(position).getTitle());
        myViewHoder.jzztHdrqTxt.setText(mDatas.get(position).getHdrq());
        myViewHoder.jzztListLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EventBus.getDefault().post(LoanDetailsActivity.class);
                mItemClickListener.onItemClick(view, position);
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jzzzt_item_img)
        ImageView jzzztItemImg;
        @BindView(R.id.jzzt_title_txt)
        TextView jzztTitleTxt;
        @BindView(R.id.jzzt_addr_txt)
        TextView jzztAddrTxt;
        @BindView(R.id.jzzt_sponsor_txt)
        TextView jzztSponsorTxt;
        @BindView(R.id.jzzt_hdrq_txt)
        TextView jzztHdrqTxt;
        @BindView(R.id.jzzt_list_lin)
        LinearLayout jzztListLin;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
