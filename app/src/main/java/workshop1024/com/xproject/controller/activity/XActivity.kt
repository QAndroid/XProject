package workshop1024.com.xproject.controller.activity

import android.support.v7.app.AppCompatActivity

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
