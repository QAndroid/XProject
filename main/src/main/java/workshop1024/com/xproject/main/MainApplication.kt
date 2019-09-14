package workshop1024.com.xproject.main

import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.main.controller.service.MainServiceImpl

class MainApplication : XApplication() {
    override fun initModuleApp() {
        ServiceFactory.getInstance()?.mainService = MainServiceImpl()
    }
}