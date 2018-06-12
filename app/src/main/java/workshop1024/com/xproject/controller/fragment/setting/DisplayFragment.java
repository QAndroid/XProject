package workshop1024.com.xproject.controller.fragment.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import workshop1024.com.xproject.R;

public class DisplayFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.display_preferences);
    }
}
