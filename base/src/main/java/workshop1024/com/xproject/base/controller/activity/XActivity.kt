package workshop1024.com.xproject.base.controller.activity

import androidx.appcompat.app.AppCompatActivity

/**
 * XActivity，完成了一些Activity基础逻辑，如是否在其前台检测
 */
open class XActivity : AppCompatActivity() {
    var mIsForeground: Boolean = false

    override fun onResume() {
        super.onResume()
        mIsForeground = true
    }

    override fun onPause() {
        super.onPause()
        mIsForeground = false
    }
}
