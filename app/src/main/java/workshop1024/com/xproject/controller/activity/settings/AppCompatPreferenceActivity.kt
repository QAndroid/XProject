package workshop1024.com.xproject.controller.activity.settings

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup

/**
 * A [android.preference.PreferenceActivity] which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
abstract class AppCompatPreferenceActivity : PreferenceActivity() {

    //FIXME 幕后字段的具体作用？？？
    private var mDelegate: AppCompatDelegate? = null
        get() {
            if (field == null) {
                field = AppCompatDelegate.create(this, null)
            }
            return field
        }

    val supportActionBar: ActionBar?
        get() = mDelegate?.supportActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        mDelegate?.installViewFactory()
        mDelegate?.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDelegate?.onPostCreate(savedInstanceState)
    }

    override fun getMenuInflater(): MenuInflater {
        return mDelegate!!.menuInflater
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        mDelegate?.setContentView(layoutResID)
    }

    override fun setContentView(view: View) {
        mDelegate?.setContentView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        mDelegate?.setContentView(view, params)
    }

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        mDelegate?.addContentView(view, params)
    }

    override fun onPostResume() {
        super.onPostResume()
        mDelegate?.onPostResume()
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        mDelegate?.setTitle(title)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDelegate?.onConfigurationChanged(newConfig)
    }

    override fun onStop() {
        super.onStop()
        mDelegate?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelegate?.onDestroy()
    }

    override fun invalidateOptionsMenu() {
        mDelegate?.invalidateOptionsMenu()
    }
}
