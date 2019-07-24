package workshop1024.com.xproject.controller.activity

import androidx.appcompat.app.AppCompatActivity

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
