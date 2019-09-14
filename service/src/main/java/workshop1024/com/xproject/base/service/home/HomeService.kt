package workshop1024.com.xproject.base.service.home

import androidx.fragment.app.Fragment

/**
 * Home组件通信接口
 */
interface HomeService {
    /**
     * 创建SubscribeNewsFragment实例
     */
    fun newSubscribeNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment?

    fun newTagNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment?

    fun newFilterNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment?


    fun isHomePageFragment(fragment: Fragment?): Boolean

    fun isNewsListFragment(fragment: Fragment?): Boolean
}