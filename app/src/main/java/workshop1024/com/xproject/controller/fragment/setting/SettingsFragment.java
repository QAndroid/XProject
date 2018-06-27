package workshop1024.com.xproject.controller.fragment.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.utils.ThemeUtils;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences mSharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);
        PreferenceManager.setDefaultValues(getContext(), R.xml.settings_preferences, false);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        setPreferenceSummaryByKey(R.string.settings_preference_displaythemes_key);
        setPreferenceSummaryByKey(R.string.settings_preference_fontsizes_key);
        setPreferenceSummaryByKey(R.string.settings_preference_articlealignment_key);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            preference.setSummary(listPreference.getEntry());
            if (preference.getKey().equals(getString(R.string.settings_preference_displaythemes_key))) {
                ThemeUtils.refreshThemesShow(getActivity());
            }
        }
    }

    private void setPreferenceSummaryByKey(int preferenceKey) {
        ListPreference listPreference = (ListPreference) findPreference(getString(preferenceKey));
        listPreference.setSummary(listPreference.getEntry());
    }
}
