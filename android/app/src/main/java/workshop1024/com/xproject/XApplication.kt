package workshop1024.com.xproject

import android.app.Application
import workshop1024.com.xproject.utils.ThemeUtils

class XApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.refreshThemesShow(this)
    }
}