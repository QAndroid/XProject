package workshop1024.com.xproject.main.controller.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.base.service.main.MainService
import workshop1024.com.xproject.base.utils.ReflectUtils
import workshop1024.com.xproject.main.R

class MainServiceImpl : MainService {

    override fun showSubscribeNewsFragment(activity: FragmentActivity?, infoId: String, title: String) {
        //FIXME 这两多次组件之间的调用，感觉比较负责，是否可以通过更的方案eventBus实现跨组件间的通信
        val subscribeNewsFragment = ServiceFactory.getInstance()?.homeService?.newSubscribeNewsFragmentInstance(infoId, R.id.leftnavigator_menu_home)
        subscribeNewsFragment?.let {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, it)
                    .addToBackStack("").commit()
            activity.title = title
        }
    }

    override fun showTagNewsFragment(activity: FragmentActivity?, infoId: String, title: String) {
        val tagNewsListFragment = ServiceFactory.getInstance()?.homeService?.newTagNewsFragmentInstance(infoId, R.id.leftnavigator_menu_home)
        tagNewsListFragment?.let {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, it)
                    .addToBackStack("").commit()
            activity.title = title
        }
    }

    override fun showFilterNewsFragment(activity: FragmentActivity?, infoId: String, title: String) {
        val filterNewsFragment = ServiceFactory.getInstance()?.homeService?.newFilterNewsFragmentInstance(infoId, R.id.leftnavigator_menu_home)
        filterNewsFragment?.let {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainright_framelayout_fragments, it)
                    ?.addToBackStack("")?.commit()
            activity?.title = title
        }
    }
}