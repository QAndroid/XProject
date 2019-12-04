package workshop1024.com.xproject.base.view.floatbutton

import android.content.Context
import android.util.AttributeSet
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
        //计算菜单栏隐藏了高度的百分比
        val translationPercent = dependency.top.toFloat() / dependency.height

        //使用该百分比和FloatAtionButton高度和底部边距相乘，计算FloatAtionButton应该移动的距离
        val translationY = abs((child.height + (parent.height + child.bottom)) * translationPercent)

        child.translationY = translationY
        return true
    }
}