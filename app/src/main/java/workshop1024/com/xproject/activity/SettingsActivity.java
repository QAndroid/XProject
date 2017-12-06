package workshop1024.com.xproject.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        setSupportActionBar(binding.toolbarNavigator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
