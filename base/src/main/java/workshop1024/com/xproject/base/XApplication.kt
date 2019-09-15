package workshop1024.com.xproject.base

import android.app.Application

/**
 * 动态配置Application：基本Application接口，各模块由启动初始化业务，实现该接口
 */
interface XApplication {
    /**
     * 内容最快被初始化
     */
    fun onInitSpeed(application: Application)

    /**
     * 内容可以等其它Application都初始化后再调用
     */
    fun onInitLow(application: Application)
}