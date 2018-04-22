package workshop1024.com.xproject.view.floatbutton;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

//https://www.jianshu.com/p/c174edcce58d
public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior {

    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        //计算菜单栏隐藏了高度的百分比
        float translationPercent = (float) dependency.getTop() / dependency.getHeight();
        //使用该百分比和FloatAtionButton高度和底部边距相乘，计算FloatAtionButton应该移动的距离
        float translationY = Math.abs((child.getHeight() + (parent.getHeight() - child.getBottom())) * translationPercent);
        child.setTranslationY(translationY);
        return true;
    }
}
