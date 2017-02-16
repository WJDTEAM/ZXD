package com.bf.zxd.zhuangxudai.collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.MyCollection;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/2/7.
 */

public class MyConllectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<MyCollection> datas;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    private CompositeSubscription mcompositeSubscription;

    public MyCollection getDataItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public MyConllectionListAdapter(Context mContext, List<MyCollection> mDatas) {
        this.mContext = mContext;
        this.datas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
        mcompositeSubscription = new CompositeSubscription();
    }

    public void update(List<MyCollection> mDatas) {
        this.datas = mDatas;
        this.notifyDataSetChanged();
    }

    public int getLayout() {
        return R.layout.conllection_article_item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        //View v = mLayoutInflater.inflate(R.layout.my_order_list_item, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int p) {
        final ViewHolder mHolder = (ViewHolder) holder;
        mHolder.collectionItemLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener!=null){
                    mItemClickListener.onItemCleck(p);
                }
            }
        });
        mHolder.collectionItemTitle.setText(datas.get(p).getTitle());
        mHolder.collectionItemTxt.setText(datas.get(p).getDescription());
        mHolder.collectionItemTime.setText(datas.get(p).getHdrq());
        if(!datas.get(p).getThumbnails().equals("")){
            Picasso.with(mContext).load(datas.get(p).getThumbnails())
                    .placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .into(mHolder.collectionItemImg);
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public interface MyItemClickListener {
        public void onItemCleck(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.collection_item_img)
        ImageView collectionItemImg;
        @BindView(R.id.collection_item_title)
        TextView collectionItemTitle;
        @BindView(R.id.collection_item_txt)
        TextView collectionItemTxt;
        @BindView(R.id.collection_item_time)
        TextView collectionItemTime;
        @BindView(R.id.collection_item_lin)
        LinearLayout collectionItemLin;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
