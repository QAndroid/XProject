package workshop1024.com.xproject.controller.fragment.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import workshop1024.com.xproject.R;

public class AdvancedFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.advanced_preferences);
    }
}
