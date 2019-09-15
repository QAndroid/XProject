package workshop1024.com.xproject.home

import android.app.Application
import workshop1024.com.xproject.base.XApplication
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.home.controller.service.HomeServiceImpl

class HomeApplication : XApplication {
    override fun onInitSpeed(application: Application) {

    }

    override fun onInitLow(application: Application) {
        //通过反射解耦，初始化组件之间通信的接口实例
        //方案参考：https://github.com/renxuelong/ComponentDemo
        ServiceFactory.getInstance()?.homeService = HomeServiceImpl()
    }
}