package workshop1024.com.xproject.view.floatbutton;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
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
        Log.i("XProject", "dependency.getTop() = " + dependency.getTop());
        Log.i("XProject", "dependency.getHeight() = " + dependency.getHeight());
        //计算菜单栏隐藏了高度的百分比
        float translationPercent = (float) dependency.getTop() / dependency.getHeight();
        Log.i("XProject", "translationPercent = " + translationPercent);
        Log.i("XProject", "child.getHeight() = " + child.getHeight());
        Log.i("XProject", "child.getBottom() = " + child.getBottom());
        //使用该百分比和FloatAtionButton高度和底部边距相乘，计算FloatAtionButton应该移动的距离
        float translationY = Math.abs((child.getHeight() + (parent.getHeight() - child.getBottom())) * translationPercent);
        Log.i("XProject", "translationY = " + translationY);
        child.setTranslationY(translationY);
        return true;
    }
}
