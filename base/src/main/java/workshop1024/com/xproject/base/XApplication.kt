package workshop1024.com.xproject.base

import android.app.Application

abstract class XApplication : Application() {
    abstract fun initModuleApp()
}