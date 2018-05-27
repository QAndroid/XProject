package workshop1024.com.xproject.controller.activity;

import android.support.v7.app.AppCompatActivity;

public class XActivity extends AppCompatActivity {
    boolean mIsForeground;

    @Override
    protected void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsForeground = false;
    }
}
