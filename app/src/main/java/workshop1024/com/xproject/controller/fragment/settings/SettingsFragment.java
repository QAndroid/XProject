package workshop1024.com.xproject.controller.fragment.settings;

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
        String settings = getArguments().getString(getString(R.string.settings_header_extra_key));
        if (getString(R.string.settings_header_display_extravalue).equals(settings)) {
            addPreferencesFromResource(R.xml.display_preferences);
            PreferenceManager.setDefaultValues(getContext(), R.xml.display_preferences, false);
            setPreferenceSummaryByKey(R.string.settings_preference_displaythemes_key);
            setPreferenceSummaryByKey(R.string.settings_preference_fontsizes_key);
            setPreferenceSummaryByKey(R.string.settings_preference_articlealignment_key);
        } else if (getString(R.string.settings_header_sync_extravalue).equals(settings)) {
            addPreferencesFromResource(R.xml.sync_preferences);
            PreferenceManager.setDefaultValues(getContext(), R.xml.sync_preferences, false);
            setPreferenceSummaryByKey(R.string.settings_preference_refreshinterval_key);
        } else if (getString(R.string.settings_header_widget_extravalue).equals(settings)) {
            addPreferencesFromResource(R.xml.widget_preferences);
            PreferenceManager.setDefaultValues(getContext(), R.xml.widget_preferences, false);
        } else if (getString(R.string.settings_header_advance_extravalue).equals(settings)) {
            addPreferencesFromResource(R.xml.advanced_preferences);
            PreferenceManager.setDefaultValues(getContext(), R.xml.advanced_preferences, false);
        }

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
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
            if (key.equals(getString(R.string.settings_preference_displaythemes_key))) {
                ThemeUtils.refreshThemesShow(getActivity());
            }
        }
    }

    private void setPreferenceSummaryByKey(int preferenceKey) {
        ListPreference listPreference = (ListPreference) findPreference(getString(preferenceKey));
        CharSequence entryCharSequence = listPreference.getEntry();
        listPreference.setSummary(entryCharSequence.toString());
    }
}
