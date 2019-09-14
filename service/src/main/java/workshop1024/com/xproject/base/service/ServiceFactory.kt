package workshop1024.com.xproject.base.service

import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.base.service.home.HomeServiceEmptyImpl
import workshop1024.com.xproject.base.service.main.MainService
import workshop1024.com.xproject.base.service.main.MainServiceEmptyImpl

/**
 * 通信Service工厂类，用于提供Service实例用于组件间的通信
 */
class ServiceFactory private constructor() {

    //FIXME 既然是public访问，那是不是可以直接通过成员访问到该成员？？
    var mainService: MainService? = null
        get() {
            if (field == null) {
                field = MainServiceEmptyImpl()
            }
            return field
        }

    var homeService: HomeService? = null
        get() {
            if (field == null) {
                field = HomeServiceEmptyImpl()
            }

            return field
        }

    companion object {
        private var serviceFactory: ServiceFactory? = null

        fun getInstance(): ServiceFactory? {
            if (serviceFactory == null) {
                serviceFactory = ServiceFactory()
            }

            return serviceFactory
        }
    }
}