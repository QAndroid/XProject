package workshop1024.com.xproject.main

import android.app.Application
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.main.other.service.MainServiceImpl

class MainApplication : XApplication {
    override fun onInitSpeed(application: Application) {

    }

    override fun onInitLow(application: Application) {
        ServiceFactory.getInstance()?.mainService = MainServiceImpl()
    }
}