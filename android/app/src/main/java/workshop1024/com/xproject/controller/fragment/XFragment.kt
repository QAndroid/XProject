package workshop1024.com.xproject.controller.fragment

import androidx.fragment.app.Fragment


/**
 * XProject Fragment基类，抽象Fragment一些公共的属性和方法，用于Fragment堆栈变化的时候，更新导航列表当前选项
 */
open class XFragment : Fragment() {
    //该Fragment对应的导航列表的选项的id
    var mNavigationItemId: Int = 0

    //Fragment是否在前台展示
    protected var mIsForeground:Boolean = false

    override fun onResume() {
        super.onResume()
        mIsForeground = true
    }

    override fun onPause() {
        super.onPause()
        mIsForeground = false
    }

}