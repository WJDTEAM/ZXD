package com.bf.zxd.zhuangxudai.User;

/**
 * Created by Daniel on 2017/2/10.
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepViewIndicator;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.ApplyStatusItem;

import java.util.List;

/**
 * 日期：16/6/24 11:48
 * <p/>
 * 描述：
 */
public class VerticalStepView extends LinearLayout implements VerticalStepViewIndicator.OnDrawIndicatorListener {
    TextView titleTv;
    TextView commentsTv;
    TextView dateTv;
    private RelativeLayout mTextContainer;
    private VerticalStepViewIndicator mStepsViewIndicator;
    private List<String> mTexts;
    private int mComplectingPosition;
    private List<ApplyStatusItem> mApplyStatusItems = ApplyScheduleActivity.mApplyStatusItems;
    private int mUnComplectedTextColor = ContextCompat.getColor(getContext(), com.baoyachi.stepview.R.color.uncompleted_text_color);//定义默认未完成文字的颜色;
    private int mComplectedTextColor = ContextCompat.getColor(getContext(), android.R.color.white);//定义默认完成文字的颜色;

    private int mTextSize = 14;//default textSize
    private TextView mTextView;


    public VerticalStepView(Context context) {
        this(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(com.baoyachi.stepview.R.layout.widget_vertical_stepsview, this);
        mStepsViewIndicator = (VerticalStepViewIndicator) rootView.findViewById(com.baoyachi.stepview.R.id.steps_indicator);
        mStepsViewIndicator.setOnDrawListener(this);
        mTextContainer = (RelativeLayout) rootView.findViewById(com.baoyachi.stepview.R.id.rl_text_container);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置显示的文字
     *
     * @param texts
     * @return
     */
    public VerticalStepView setStepViewTexts(List<String> texts) {
        mTexts = texts;
        if (texts != null) {
            mStepsViewIndicator.setStepNum(mTexts.size());
        } else {
            mStepsViewIndicator.setStepNum(0);
        }
        return this;
    }

    /**
     * 设置正在进行的position
     *
     * @param complectingPosition
     * @return
     */
    public VerticalStepView setStepsViewIndicatorComplectingPosition(int complectingPosition) {
        mComplectingPosition = complectingPosition;
        mStepsViewIndicator.setComplectingPosition(complectingPosition);
        return this;
    }

    /**
     * 设置未完成文字的颜色
     *
     * @param unComplectedTextColor
     * @return
     */
    public VerticalStepView setStepViewUnComplectedTextColor(int unComplectedTextColor) {
        mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    /**
     * 设置完成文字的颜色
     *
     * @param complectedTextColor
     * @return
     */
    public VerticalStepView setStepViewComplectedTextColor(int complectedTextColor) {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    /**
     * 设置StepsViewIndicator未完成线的颜色
     *
     * @param unCompletedLineColor
     * @return
     */
    public VerticalStepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor) {
        mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator完成线的颜色
     *
     * @param completedLineColor
     * @return
     */
    public VerticalStepView setStepsViewIndicatorCompletedLineColor(int completedLineColor) {
        mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator默认图片
     *
     * @param defaultIcon
     */
    public VerticalStepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon) {
        mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator已完成图片
     *
     * @param completeIcon
     */
    public VerticalStepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon) {
        mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator正在进行中的图片
     *
     * @param attentionIcon
     */
    public VerticalStepView setStepsViewIndicatorAttentionIcon(Drawable attentionIcon) {
        mStepsViewIndicator.setAttentionIcon(attentionIcon);
        return this;
    }

    /**
     * is reverse draw 是否倒序画
     *
     * @param isReverSe default is true
     * @return
     */
    public VerticalStepView reverseDraw(boolean isReverSe) {
        this.mStepsViewIndicator.reverseDraw(isReverSe);
        return this;
    }

    /**
     * set linePadding  proportion 设置线间距的比例系数
     *
     * @param linePaddingProportion
     * @return
     */
    public VerticalStepView setLinePaddingProportion(float linePaddingProportion) {
        this.mStepsViewIndicator.setIndicatorLinePaddingProportion(linePaddingProportion);
        return this;
    }


    /**
     * set textSize
     *
     * @param textSize
     * @return
     */
    public VerticalStepView setTextSize(int textSize) {
        if (textSize > 0) {
            mTextSize = textSize;
        }
        return this;
    }



    @Override
    public void ondrawIndicator() {
        if (mTextContainer != null) {
            mTextContainer.removeAllViews();//clear ViewGroup
            List<Float> complectedXPosition = mStepsViewIndicator.getCircleCenterPointPositionList();
            Log.e("Daniel","----complectedXPosition.size()---"+complectedXPosition.size());
            if (mTexts != null && complectedXPosition != null && complectedXPosition.size() > 0) {
                for (int i = 0; i < mTexts.size(); i++) {
                    //                    LinearLayout layout = new LinearLayout(getContext());
                    //                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    //                            ViewGroup.LayoutParams.MATCH_PARENT,
                    //                            ViewGroup.LayoutParams.WRAP_CONTENT);// 定义布局管理器的参数
                    //                    layout.setOrientation(LinearLayout.VERTICAL);// 所有组件垂直摆放
                    //                    // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
                    //                    LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                    //                            ViewGroup.LayoutParams.WRAP_CONTENT,
                    //                            ViewGroup.LayoutParams.WRAP_CONTENT);// 定义文本显示组件
                    //                    layout.setY(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() / 2);
                    //
                    //                    TextView txt = new TextView(getContext());
                    //                    txt.setLayoutParams(text);// 配置文本显示组件的参数
                    //                    txt.setText("动态生成内容");// 配置显示文字
                    //                    txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    //                    layout.addView(txt, text);
                    //
                    //                    mTextView = new TextView(getContext());
                    //                    mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    //                    mTextView.setText(mTexts.get(i));
                    //                    //                    mTextView.setY(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() / 2);
                    //                    mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //                    layout.addView(mTextView, text);

                    View view = LayoutInflater.from(getContext()).inflate(R.layout.content_applyschedule, null, false);
                    LinearLayout linearLayout  = (LinearLayout) view.findViewById(R.id.linear);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 定义布局管理器的参数
                    linearLayout.setLayoutParams(param);
                    Log.e("Daniel","---complectedXPosition.get(i)---"+complectedXPosition.get(i)+"---mStepsViewIndicator.getCircleRadius()---"+mStepsViewIndicator.getCircleRadius());
                    linearLayout.setY(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() / 2);
                    titleTv = (TextView) view.findViewById(R.id.title_tv);
                    commentsTv = (TextView) view.findViewById(R.id.comments_tv);
                    dateTv = (TextView) view.findViewById(R.id.date_tv);

                    ApplyStatusItem applyStatusItem = mApplyStatusItems.get(i);
                    Log.e("Daniel",applyStatusItem.toString());
                    switch (i){
                        case 0:titleTv.setText("申请已提交");break;
                        case 1:titleTv.setText("正在审核");break;
                        case 2:titleTv.setText("申请失败");break;
                        case 3:titleTv.setText("申请成功！");break;

                    }
                    commentsTv.setText(applyStatusItem.getComment());
                    dateTv.setText(applyStatusItem.getStatusRq());
                    //                    view.findViewById()
//                    if (i <= mComplectingPosition) {
//                        //                        mTextView.setTypeface(null, Typeface.BOLD);
//                        //                        mTextView.setTextColor(mComplectedTextColor);
//                    } else {
//                        //                        mTextView.setTextColor(mUnComplectedTextColor);
//                    }

                    mTextContainer.addView(view);
                }
            }
        }
    }
}