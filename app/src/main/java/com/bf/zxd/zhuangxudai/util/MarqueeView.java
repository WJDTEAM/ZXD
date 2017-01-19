package com.bf.zxd.zhuangxudai.util;

/**
 * Created by johe on 2017/1/19.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sunfusheng.marqueeview.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 16/5/31.
 */
public class MarqueeView extends ViewFlipper {

    private Context mContext;
    private List<String> notices;
    private boolean isSetAnimDuration = false;
    private com.sunfusheng.marqueeview.MarqueeView.OnItemClickListener onItemClickListener;

    private int interval = 2000;
    private int animDuration = 500;
    private int textSize = 14;
    private int textColor = 0xffffffff;

    private boolean singleLine = false;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int TEXT_GRAVITY_LEFT = 0, TEXT_GRAVITY_CENTER = 1, TEXT_GRAVITY_RIGHT = 2;

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        if (notices == null) {
            notices = new ArrayList<>();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvInterval, interval);
        isSetAnimDuration = typedArray.hasValue(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvAnimDuration);
        singleLine = typedArray.getBoolean(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvSingleLine, false);
        animDuration = typedArray.getInteger(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        if (typedArray.hasValue(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = DisplayUtil.px2sp(mContext, textSize);
        }
        textColor = typedArray.getColor(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        int gravityType = typedArray.getInt(com.sunfusheng.marqueeview.R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT);
        switch (gravityType) {
            case TEXT_GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();

        setFlipInterval(interval);

        Animation animIn = AnimationUtils.loadAnimation(mContext, com.sunfusheng.marqueeview.R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(mContext, com.sunfusheng.marqueeview.R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    // 根据公告字符串启动轮播
    public void startWithText(final String notice) {
        if (TextUtils.isEmpty(notice)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startWithFixedWidth(notice, getWidth());
            }
        });
    }

    // 根据公告字符串列表启动轮播
    public void startWithList(List<String> notices) {
        setNotices(notices);
        start();
    }

    // 根据宽度和公告字符串启动轮播
    private void startWithFixedWidth(String notice, int width) {
        int noticeLength = notice.length();
        int dpW = DisplayUtil.px2dip(mContext, width);
        int limit = dpW / textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width !");
        }

        if (noticeLength <= limit) {
            notices.add(notice);
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                notices.add(notice.substring(startIndex, endIndex));
            }
        }
        start();
    }

    // 启动轮播
    public boolean start() {
        if (notices == null || notices.size() == 0) return false;
        removeAllViews();

        for (int i = 0; i < notices.size(); i++) {
            final LinearLayout textView = createTextView(notices.get(i), i);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(finalI, null);
                    }
                }
            });
            textView.setGravity(Gravity.CENTER);
            addView(textView);
        }

        if (notices.size() > 1) {
            startFlipping();
        }
        return true;
    }

    // 创建ViewFlipper下的TextView
    private LinearLayout createTextView(String text, int position) {
        LinearLayout lin=new LinearLayout(mContext);
        lin.setOrientation(LinearLayout.HORIZONTAL);

        String []txt=text.split(",");

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT);
        lp.weight=1;
        TextView tv = new TextView(mContext);
        tv.setGravity(gravity);
        tv.setText(txt[0]);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        tv.setSingleLine(singleLine);
        tv.setTag(position);
        tv.setLayoutParams(lp);

        TextView tv2 = new TextView(mContext);
        tv2.setGravity(Gravity.CENTER);
        tv2.setText(txt[1]);
        tv2.setTextColor(textColor);
        tv2.setTextSize(textSize);
        tv2.setSingleLine(singleLine);
        tv2.setTag(position);
        tv2.setLayoutParams(lp);

        TextView tv3 = new TextView(mContext);
        tv3.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        tv3.setText(txt[2]);
        tv3.setTextColor(textColor);
        tv3.setTextSize(textSize);
        tv3.setSingleLine(singleLine);
        tv3.setTag(position);
        tv3.setLayoutParams(lp);

        lin.addView(tv);
        lin.addView(tv2);
        lin.addView(tv3);
        return lin;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }

    public void setOnItemClickListener(com.sunfusheng.marqueeview.MarqueeView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);
    }

}
