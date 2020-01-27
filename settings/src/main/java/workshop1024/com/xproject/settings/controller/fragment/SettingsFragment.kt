package workshop1024.com.xproject.settings.controller.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import workshop1024.com.xproject.base.utils.ThemeUtils
import workshop1024.com.xproject.settings.R

class SettingsFragment : PreferenceFragment(), LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifecycleRegistry = LifecycleRegistry(this)
        SharedPreferenceManager.bindSharedPreferenceManager(this, this)
        mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()
        mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}
