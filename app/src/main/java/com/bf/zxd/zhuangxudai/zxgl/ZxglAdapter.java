package com.bf.zxd.zhuangxudai.zxgl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.zxgl;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class ZxglAdapter extends RecyclerView.Adapter<ZxglAdapter.ViewHolder> {

    List<zxgl> datas;
    MyItemClickListener mItemClickListener;
    private Context mContext;


    public int getLayout() {
        return R.layout.zhuangxiugonglue_list_item;
    }

    public ZxglAdapter(List<zxgl> datas,Context mContext) {
        this.mContext=mContext;
        this.datas = datas;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final zxgl memoryCode = datas.get(position);
        Picasso.with(mContext).load(memoryCode.getThumbnails()).into(holder.img);
        holder.aboveTxt.setText(memoryCode.getTitle());
        holder.bottomTxt.setText(memoryCode.getDescription());




    }

    @Override
    public int getItemCount() {
//        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<zxgl> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // View rootView;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.above_txt)
        TextView aboveTxt;
        @BindView(R.id.bottom_txt)
        TextView bottomTxt;
        @BindView(R.id.RelativeLayout_item)
        RelativeLayout RelativeLayoutItem;


        ViewHolder(View view) {
            super(view);
//            rootView = view;
            ButterKnife.bind(this, view);
//            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mListener != null) {
//                mListener.onItemClick(view, getPosition());
//            }

        }
    }
}
