package workshop1024.com.xproject

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.utils.ThemeUtils

class AppApplication : XApplication() {
    val moduleApps = listOf<String>("workshop1024.com.xproject.home.HomeApplication", "workshop1024.com.xproject.main.MainApplication")

    override fun onCreate() {
        super.onCreate()
        ThemeUtils.refreshThemesShow(this)

        //这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        //尽可能早，推荐在Application中初始化
        ARouter.init(this);

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