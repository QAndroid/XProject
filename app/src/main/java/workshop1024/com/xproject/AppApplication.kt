package workshop1024.com.xproject

import android.app.Application
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.utils.ThemeUtils

class AppApplication : XApplication() {
    val moduleApps = listOf<String>("workshop1024.com.xproject.home.HomeApplication", "workshop1024.com.xproject.main.MainApplication")

    override fun onCreate() {
        super.onCreate()
        ThemeUtils.refreshThemesShow(this)

        //通过反射解耦，初始化组件之间通信的接口实例
        //方案参考：https://github.com/renxuelong/ComponentDemo
        initModuleApp()
    }

    override fun initModuleApp() {
        for (moduleApp in moduleApps) {
            try {
                val clazz = Class.forName(moduleApp)
                val baseApp = clazz.newInstance() as XApplication
                baseApp.initModuleApp()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }

        }
    }
}