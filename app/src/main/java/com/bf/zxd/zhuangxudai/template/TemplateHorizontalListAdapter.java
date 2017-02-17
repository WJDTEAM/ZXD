package com.bf.zxd.zhuangxudai.template;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bf.zxd.zhuangxudai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/12.
 */

public class TemplateHorizontalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public TemplateHorizontalListAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.template_horizontal_list_img, parent, false);
        RecyclerView.ViewHolder viewHolder = new TemplateHorizontalListAdapter.ViewHoder(v);

        return viewHolder;
    }

    public List<String> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void update(List<String> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHoder myViewHoder = (ViewHoder) holder;
        myViewHoder.listImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(myViewHoder.listImg, position);
            }
        });
        if (mDatas.get(position) != null) {
            if (!mDatas.get(position).equals("")) {
                Picasso.with(mContext).load(mDatas.get(position))
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo2)
                        .into(myViewHoder.listImg);
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

    static class ViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_img)
        ImageView listImg;

        public ViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
