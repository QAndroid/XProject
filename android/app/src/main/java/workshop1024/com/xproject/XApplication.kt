package workshop1024.com.xproject

import android.app.Application
import com.facebook.soloader.SoLoader
import workshop1024.com.xproject.utils.ThemeUtils

class XApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.refreshThemesShow(this)

        SoLoader.init(this,false)
    }
}