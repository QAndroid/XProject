package workshop1024.com.xproject.utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import workshop1024.com.xproject.R;

public class ThemeUtils {

    public static void refreshThemesShow(Context context) {
        Resources resources = context.getResources();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);

        String defaultThemeString = resources.getString(R.string.settings_preference_displaythemes_default);
        String displayThemes = sharedPreferences.getString(resources.getString(R.string.settings_preference_displaythemes_key), defaultThemeString);

        if (displayThemes.equals(defaultThemeString)) {
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        } else {
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        }
    }
}
