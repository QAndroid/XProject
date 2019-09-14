package workshop1024.com.xproject.home

import android.app.Application
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.home.controller.service.HomeServiceImpl

class HomeApplication : XApplication() {
    override fun initModuleApp() {
        ServiceFactory.getInstance()?.homeService = HomeServiceImpl()
    }
}