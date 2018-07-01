package workshop1024.com.xproject.controller.fragment.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import workshop1024.com.xproject.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String settings = getArguments().getString("settings");
        if ("Display".equals(settings)) {
            addPreferencesFromResource(R.xml.display_preferences);
        } else if ("Sync".equals(settings)) {
            addPreferencesFromResource(R.xml.sync_preferences);
        } else if ("Widget".equals(settings)) {
            addPreferencesFromResource(R.xml.widget_preferences);
        } else if ("Advanced".equals(settings)) {
            addPreferencesFromResource(R.xml.advanced_preferences);
        }
    }

}
