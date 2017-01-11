package com.bf.zxd.zhuangxudai.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.bf.zxd.zhuangxudai.Interfaces.ScrollViewListener;

/**
 * Created by johe on 2017/1/10.
 */

public class CustomScrollView extends ScrollView{

    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
