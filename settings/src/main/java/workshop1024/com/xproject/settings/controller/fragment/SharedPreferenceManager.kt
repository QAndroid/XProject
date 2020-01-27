package workshop1024.com.xproject.settings.controller.fragment

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import workshop1024.com.xproject.base.utils.ThemeUtils
import workshop1024.com.xproject.settings.R

/**
 * LifeCycle的使用
 * 参考：https://developer.android.com/topic/libraries/architecture/lifecycle
 */
object SharedPreferenceManager {
    fun bindSharedPreferenceManager(lifecycleOwner: LifecycleOwner, preferenceFragment: SettingsFragment) {
        SharedPreferenceListener(lifecycleOwner, preferenceFragment)
    }

    internal class SharedPreferenceListener(lifecycleOwner: LifecycleOwner, private val mPreferenceFragment
    : PreferenceFragment) : LifecycleObserver, SharedPreferences.
    OnSharedPreferenceChangeListener {

        private lateinit var mSharedPreferences: SharedPreferences
        private var mContext: Context
        private var mArguments: Bundle

        init {
            mContext = mPreferenceFragment.context
            mArguments = mPreferenceFragment.arguments

            lifecycleOwner.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            when (mArguments.getString(mContext.getString(R.string.settings_header_extra_key))) {
                mContext.getString(R.string.settings_header_display_extravalue) -> {
                    mPreferenceFragment.addPreferencesFromResource(R.xml.display_preferences)
                    PreferenceManager.setDefaultValues(mContext, R.xml.display_preferences, false)
                    setPreferenceSummaryByKey(R.string.settings_preference_displaythemes_key)
                    setPreferenceSummaryByKey(R.string.settings_preference_fontsizes_key)
                }
                mContext.getString(R.string.settings_header_sync_extravalue) -> {
                    mPreferenceFragment.addPreferencesFromResource(R.xml.sync_preferences)
                    PreferenceManager.setDefaultValues(mContext, R.xml.sync_preferences, false)
                    setPreferenceSummaryByKey(R.string.settings_preference_refreshinterval_key)
                }
                mContext.getString(R.string.settings_header_widget_extravalue) -> {
                    mPreferenceFragment.addPreferencesFromResource(R.xml.widget_preferences)
                    PreferenceManager.setDefaultValues(mContext, R.xml.widget_preferences, false)
                }
                mContext.getString(R.string.settings_header_advance_extravalue) -> {
                    mPreferenceFragment.addPreferencesFromResource(R.xml.advanced_preferences)
                    PreferenceManager.setDefaultValues(mContext, R.xml.advanced_preferences, false)
                }
            }

            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mPreferenceFragment.context)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            mSharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            val preference = mPreferenceFragment.findPreference(key)
            if (preference is ListPreference) {
                preference.setSummary(preference.entry)
                if (key == mContext.getString(R.string.settings_preference_displaythemes_key)) {
                    ThemeUtils.refreshThemesShow(mPreferenceFragment.activity)
                }
            }
        }

        private fun setPreferenceSummaryByKey(preferenceKey: Int) {
            val listPreference = mPreferenceFragment.findPreference(mContext.getString(preferenceKey))
                    as ListPreference
            val entryCharSequence = listPreference.entry
            listPreference.summary = entryCharSequence.toString()
        }
    }
}