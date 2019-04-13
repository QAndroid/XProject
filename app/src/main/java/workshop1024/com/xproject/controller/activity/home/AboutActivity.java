package workshop1024.com.xproject.controller.activity.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.AboutActivityBinding;

public class AboutActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutActivityBinding aboutActivityBinding = DataBindingUtil.setContentView(this,
                R.layout.about_activity);

        setSupportActionBar(aboutActivityBinding.aboutToolbarNavigator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("About Paperboy");
    }
}
