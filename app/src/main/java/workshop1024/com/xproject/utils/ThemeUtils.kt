package workshop1024.com.xproject.utils

import android.app.UiModeManager
import android.content.Context
import android.preference.PreferenceManager
import workshop1024.com.xproject.R

object ThemeUtils {

    fun refreshThemesShow(context: Context) {
        val resources = context.resources
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

        val defaultThemeString = resources.getString(R.string.settings_preference_displaythemes_default)
        val displayThemes = sharedPreferences.getString(resources.getString(R.string.settings_preference_displaythemes_key), defaultThemeString)

        if (displayThemes == defaultThemeString) {
            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO
        } else {
            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_YES
        }
    }
}
