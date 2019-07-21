package workshop1024.com.xproject.controller.fragment.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import workshop1024.com.xproject.R
import workshop1024.com.xproject.utils.ThemeUtils

class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (arguments.getString(getString(R.string.settings_header_extra_key))) {
            getString(R.string.settings_header_display_extravalue) -> {
                addPreferencesFromResource(R.xml.display_preferences)
                PreferenceManager.setDefaultValues(context, R.xml.display_preferences, false)
                setPreferenceSummaryByKey(R.string.settings_preference_displaythemes_key)
                setPreferenceSummaryByKey(R.string.settings_preference_fontsizes_key)
            }
            getString(R.string.settings_header_sync_extravalue) -> {
                addPreferencesFromResource(R.xml.sync_preferences)
                PreferenceManager.setDefaultValues(context, R.xml.sync_preferences, false)
                setPreferenceSummaryByKey(R.string.settings_preference_refreshinterval_key)
            }
            getString(R.string.settings_header_widget_extravalue) -> {
                addPreferencesFromResource(R.xml.widget_preferences)
                PreferenceManager.setDefaultValues(context, R.xml.widget_preferences, false)
            }
            getString(R.string.settings_header_advance_extravalue) -> {
                addPreferencesFromResource(R.xml.advanced_preferences)
                PreferenceManager.setDefaultValues(context, R.xml.advanced_preferences, false)
            }
        }

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onResume() {
        super.onResume()
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference = findPreference(key)
        if (preference is ListPreference) {
            preference.setSummary(preference.entry)
            if (key == getString(R.string.settings_preference_displaythemes_key)) {
                ThemeUtils.refreshThemesShow(activity)
            }
        }
    }

    private fun setPreferenceSummaryByKey(preferenceKey: Int) {
        val listPreference = findPreference(getString(preferenceKey)) as ListPreference
        val entryCharSequence = listPreference.entry
        listPreference.summary = entryCharSequence.toString()
    }
}
