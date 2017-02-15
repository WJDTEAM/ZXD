package com.bf.zxd.zhuangxudai.template;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.Comments;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/2/15.
 */

public class CommentsActivity extends AppCompatActivity {

    CompositeSubscription mcompositeSubscription;
    Realm realm;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.comments_list)
    RecyclerView commentsList;
    @BindView(R.id.comments_edi)
    EditText commentsEdi;
    @BindView(R.id.comments_commit_txt)
    TextView commentsCommitTxt;

    CommentsListAdapter commentsListAdapter;
    int objId=0;

    private void setToolbar(String toolstr) {

                baseToolBar.setTitle(toolstr);
                baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
                setSupportActionBar(baseToolBar);
                baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
                baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        mcompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        setToolbar("评论详情");
        objId= getIntent().getIntExtra("Zxgs_id",0);

        Log.i("gqf","objId"+objId);
        initData(objId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        mcompositeSubscription.unsubscribe();
    }

    @OnClick(R.id.comments_commit_txt)
    public void onClick() {
        if(commentsEdi.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"请输入评论后再提交",Toast.LENGTH_SHORT).show();
        }else{
            commitComments(commentsEdi.getText().toString());
        }

    }
    public void commitComments(String comments){
        Subscription subscription = NetWork.getNewZXD1_4Service().saveComments(realm.where(NewUser.class).findFirst().getUserId(),
                objId,comments
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultCode zxgs) {
                        Log.i("gqf", "mListener" + zxgs.toString());
                        if(zxgs.getCode()==10001){
                            Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                            commentsEdi.setText("");
                            //重新刷新列表
                            initData(objId);
                        }else{
                            Toast.makeText(getApplicationContext(),"评论失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(subscription);
    }
    public void initData(int objId){
        Subscription subscription = NetWork.getNewZXD1_4Service().getComments(objId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comments>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        initView(commentses);
                    }
                });
        mcompositeSubscription.add(subscription);
    }
    public void initView(List<Comments> commentses){

        if(commentsListAdapter==null){
            commentsListAdapter=new CommentsListAdapter(this,commentses);
            commentsList.setLayoutManager(new LinearLayoutManager(this));
            commentsList.setAdapter(commentsListAdapter);
        }else{
            commentsListAdapter.update(commentses);
        }
    }
}
