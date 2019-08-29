package workshop1024.com.xproject.settings.controller.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import workshop1024.com.xproject.base.utils.UnitUtils
import workshop1024.com.xproject.settings.R
import workshop1024.com.xproject.settings.controller.fragment.SettingsFragment

class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val headerListView = listView
        headerListView.divider = getDrawable(R.drawable.setting_headerlist_itemdivider)
        headerListView.dividerHeight = UnitUtils.spToPx(this, 1f)
    }

    override fun onBuildHeaders(target: List<Header>) {
        loadHeadersFromResource(R.xml.settings_preferences, target)
    }

    override fun isValidFragment(fragmentName: String): Boolean {
        return SettingsFragment::class.java.name == fragmentName
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
