package workshop1024.com.xproject.controller.activity.settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.settings.SettingsFragment;

public class SettingsActivity extends AppCompatPreferenceActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle("Settings");
        }

        ListView headerListView = getListView();
        headerListView.setDivider(getDrawable(R.drawable.setting_headerlist_itemdivider));
        headerListView.setDividerHeight(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_preferences, target);
    }

    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || SettingsFragment.class.getName().equals(fragmentName);
    }
}
