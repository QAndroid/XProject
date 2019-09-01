package workshop1024.com.xproject.home.view.floatbutton

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs

//https://www.jianshu.com/p/c174edcce58d
class FloatingActionButtonBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        Log.i("XProject", "dependency.getTop() =  ${dependency.top}")
        Log.i("XProject", "dependency.getHeight() = ${dependency.height}")

        //计算菜单栏隐藏了高度的百分比
        val translationPercent = dependency.top.toFloat() / dependency.height

        Log.i("XProject", "translationPercent = $translationPercent")
        Log.i("XProject", "child.getHeight() = ${child.height}")
        Log.i("XProject", "child.getBottom() = ${child.bottom}")

        //使用该百分比和FloatAtionButton高度和底部边距相乘，计算FloatAtionButton应该移动的距离
        val translationY = abs((child.height + (parent.height + child.bottom)) * translationPercent)
        Log.i("XProject", "translationY = $translationY")
        child.translationY = translationY
        return true
    }
}