package workshop1024.com.xproject;

import android.app.Application;

import workshop1024.com.xproject.utils.ThemeUtils;

public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ThemeUtils.refreshThemesShow(this);
    }
}