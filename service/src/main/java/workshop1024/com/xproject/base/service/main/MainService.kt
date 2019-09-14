package workshop1024.com.xproject.base.service.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Main组件通信接口
 */
interface MainService {


    /**
     * 展示SubscribeNewsFragment
     * @param activity 展示展示SubscribeNewsFragment的activity容器页面
     * @param infoId
     * @param title 展示SubscribeNewsFragment后标题Title
     */
    fun showSubscribeNewsFragment(activity: FragmentActivity?, infoId: String, title: String)

    fun showTagNewsFragment(activity: FragmentActivity?, infoId: String, title: String)

    fun showFilterNewsFragment(activity: FragmentActivity?, infoId: String, title: String)

}