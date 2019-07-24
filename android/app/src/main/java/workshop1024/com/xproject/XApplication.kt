package workshop1024.com.xproject

import android.app.Application
import com.facebook.soloader.SoLoader
import workshop1024.com.xproject.utils.ThemeUtils

class XApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.refreshThemesShow(this)
        //Soladlr.init没有调用异常，参考：https://github.com/facebook/react-native/issues/10379
        SoLoader.init(this,false)
    }
}