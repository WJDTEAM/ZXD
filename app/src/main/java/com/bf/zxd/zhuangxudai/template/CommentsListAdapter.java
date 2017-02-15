package com.bf.zxd.zhuangxudai.template;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.Comments;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by johe on 2017/2/15.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<Comments> mDatas;
    private LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public CommentsListAdapter(Context mContext, List<Comments> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.comments_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHoder(v);

        return viewHolder;
    }

    public List<Comments> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<Comments> mDatas) {
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

    public void update(List<Comments> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHoder myViewHoder = (MyViewHoder) holder;
        myViewHoder.commentsTime.setText(mDatas.get(position).getPlrq());

        String userName="";
        if(!mDatas.get(position).getNickname().equals("")){
            userName=mDatas.get(position).getNickname();
        }else{
            userName=mDatas.get(position).getPhone();
            userName=userName.substring(0,3)+"****"+userName.substring(7,userName.length());
        }

        myViewHoder.commentsTxt.setText(mDatas.get(position).getContent());
        myViewHoder.commentsUserName.setText(userName);
        if(mDatas.get(position).getLogoImg()!=null){
            if(!mDatas.get(position).getLogoImg().equals("")){
                Picasso.with(mContext).load(mDatas.get(position).getLogoImg())
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo)
                        .into(myViewHoder.commentsImage);
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
        @BindView(R.id.comments_image)
        CircleImageView commentsImage;
        @BindView(R.id.comments_user_name)
        TextView commentsUserName;
        @BindView(R.id.comments_time)
        TextView commentsTime;
        @BindView(R.id.comments_txt)
        TextView commentsTxt;

        MyViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
