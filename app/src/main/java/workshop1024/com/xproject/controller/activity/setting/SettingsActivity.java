package workshop1024.com.xproject.controller.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import workshop1024.com.xproject.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;

    private TextView mDisplayTextView;
    private TextView mSynTextView;
    private TextView mWidgetTextView;
    private TextView mAdvanceTextView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        mToolbar = findViewById(R.id.settings_toolbar_navigator);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Settings");

        mDisplayTextView = findViewById(R.id.settings_textview_display);
        mSynTextView = findViewById(R.id.settings_textview_sync);
        mWidgetTextView = findViewById(R.id.settings_textview_widget);
        mAdvanceTextView = findViewById(R.id.settings_textview_advance);

        mDisplayTextView.setOnClickListener(this);
        mSynTextView.setOnClickListener(this);
        mWidgetTextView.setOnClickListener(this);
        mAdvanceTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mDisplayTextView) {
            DisplayActivity.startActivity(this);
        } else if (v == mSynTextView) {
            SyncActivity.startActivity(this);
        } else if (v == mWidgetTextView) {
            WidgetActivity.startActivity(this);
        } else if (v == mAdvanceTextView) {
            AdvancedActivity.startActivity(this);
        }
    }
}
