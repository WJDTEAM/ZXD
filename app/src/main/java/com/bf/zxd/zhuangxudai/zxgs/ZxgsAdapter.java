package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2016/11/7.
 */

public class ZxgsAdapter extends RecyclerView.Adapter<ZxgsAdapter.ViewHolder> {

    List<DecoCompanyItem> datas;
    MyItemClickListener mItemClickListener;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String url = "http://211.149.235.17:8080/zxd/upload/zxgl-20170109161416796.jpeg";


    public int getLayout() {
        return R.layout.zxgongsi_list_item;
    }

    public ZxgsAdapter(List<DecoCompanyItem> datas, Context mContext) {
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
        final DecoCompanyItem data = datas.get(position);
        if(data.getCompanyIcon()!=null){
                Picasso.with(mContext).load(data.getCompanyIcon()).placeholder(R.drawable.demo).error(R.drawable.demo).into(holder.img);
        }

        holder.gsTitleTxt.setText(data.getCompanyName());
        holder.gsAddressTxt.setText(data.getAddr());
        holder.jdggImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Zxgs_id = data.getCompanyId();
                Intent intent = new Intent(mContext, ZxgsDetailActivity.class);
                intent.putExtra("Zxgs_id", Zxgs_id);
                Gson g=new Gson();
                intent.putExtra("DecoCompanyItemJson", g.toJson(data));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.i("Daniel","---datas.size()---"+datas.size());
        return datas != null ? datas.size() : 0;
    }

    public void setdatas(List<DecoCompanyItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView;
        MyItemClickListener mListener;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.gsTitle_txt)
        TextView gsTitleTxt;
        @BindView(R.id.gsAddress_txt)
        TextView gsAddressTxt;
        @BindView(R.id.goodSay_txt)
        TextView goodSayTxt;
        @BindView(R.id.jdgg_img)
        ImageView jdggImg;
//        @BindView(R.id.jdgg_btn)
//        Button jdggBtn;
//        @BindView(R.id.RelativeLayout_item)
//        RelativeLayout RelativeLayoutItem;


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
