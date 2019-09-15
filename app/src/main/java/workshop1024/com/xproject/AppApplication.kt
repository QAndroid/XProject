package workshop1024.com.xproject

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.utils.ThemeUtils

class AppApplication : Application(), XApplication {
    override fun onCreate() {
        super.onCreate()
        //动态配置Application：各模块需要快速初始化逻辑
        onInitSpeed(this)

        ThemeUtils.refreshThemesShow(this)

        //ARouter：这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        //尽可能早，推荐在Application中初始化
        ARouter.init(this);

        //动态配置Application：各模块需要再初始化逻辑
        onInitLow(this)
    }

    override fun onInitSpeed(application: Application) {
        try {
            for (init in AppConfig.initModules) {
                val clazz = Class.forName(init)
                val moduleInit = clazz.newInstance() as XApplication
                moduleInit.onInitSpeed(this)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    override fun onInitLow(application: Application) {
        try {
            for (init in AppConfig.initModules) {
                val clazz = Class.forName(init)
                val moduleInit = clazz.newInstance() as XApplication
                moduleInit.onInitLow(this)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }
}