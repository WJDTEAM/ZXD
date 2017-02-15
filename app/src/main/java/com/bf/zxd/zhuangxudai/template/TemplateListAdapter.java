package com.bf.zxd.zhuangxudai.template;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyYbjItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/10.
 */

public class TemplateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context mContext;
    private List<DecoCompanyYbjItem> mDatas;
    private LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public TemplateListAdapter(Context mContext, List<DecoCompanyYbjItem> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.template_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHoder(v);

        return viewHolder;
    }

    public List<DecoCompanyYbjItem> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<DecoCompanyYbjItem> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener= listener;
    }

    public void update(List<DecoCompanyYbjItem> mDatas){
        this.mDatas=mDatas;
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHoder myViewHoder = (MyViewHoder) holder;
        final View view= myViewHoder.templateItemLin;
        myViewHoder.templateItemLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener!=null){
                    mItemClickListener.onItemClick(view,position);
                }
            }
        });
        myViewHoder.templateItemTitle.setText(mDatas.get(position).getCaseName());
        myViewHoder.templateItemDetails.setText(mDatas.get(position).getDesignInspiration());
        myViewHoder.templateItemNum.setText(mDatas.get(position).getComments()+"");
        if(mDatas.get(position).getThumbnails()!=null){
            if(!mDatas.get(position).getThumbnails().equals("")){
                Picasso.with(mContext).load(mDatas.get(position).getThumbnails())
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo)
                        .into(myViewHoder.templateItemImg);
            }
        }


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {

        @BindView(R.id.template_item_title)
        TextView templateItemTitle;
        @BindView(R.id.template_item_num)
        TextView templateItemNum;
        @BindView(R.id.template_item_img)
        ImageView templateItemImg;
        @BindView(R.id.template_item_details)
        TextView templateItemDetails;
        @BindView(R.id.template_item_lin)
        LinearLayout templateItemLin;
        private MyItemClickListener mListener;

        public MyViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}
