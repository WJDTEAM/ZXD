package com.bf.zxd.zhuangxudai.User;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bf.zxd.zhuangxudai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：16/6/24 19:39
 * <p>
 * 描述：默认是反向画 default this VerticalStepViewIndicator draw is reverse
 */
public class VerticalStepViewReverseFragment extends Fragment
{
    View mView;
    private VerticalStepView mSetpview0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = mView.inflate(getActivity(), R.layout.fragment_vertical_stepview, null);
        return mView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mSetpview0 = (VerticalStepView) mView.findViewById(R.id.step_view0);

        List<String> list0 = new ArrayList<>();
        list0.add("申请已提交！");
        list0.add("审核成功！");
        list0.add("审核失败！");
        list0.add("申请完成！00");

        mSetpview0.setStepsViewIndicatorComplectingPosition(1)//设置完成的步数
                .setStepViewTexts(list0)//总步骤
                .setTextSize(12)
                .reverseDraw(false)
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon

    }
}
