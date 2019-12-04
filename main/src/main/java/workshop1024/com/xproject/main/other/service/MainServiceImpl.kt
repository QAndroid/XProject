package workshop1024.com.xproject.main.other.service

import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import workshop1024.com.xproject.base.arouter.provider.HomeProvider
import workshop1024.com.xproject.base.service.ServiceFactory
import workshop1024.com.xproject.base.service.main.MainService
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
        //动态创建Fragment：公共通信接口方法，业务组件通过公共组件的接口，接口实例初始化创建。然后通过接口实例跨组件调用，创建tagNewsListFragment
        val tagNewsListFragment = ServiceFactory.getInstance()?.homeService?.newTagNewsFragmentInstance(infoId, R.id.leftnavigator_menu_home)
        tagNewsListFragment?.let {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, it)
                    .addToBackStack("").commit()
            activity.title = title
        }
    }

    override fun showFilterNewsFragment(activity: FragmentActivity?, infoId: String, title: String) {
        //动态创建Fragment：ARouter方案，通过依赖注入解耦，使用服务管理—暴露服务，动态创建filterNewsFragment
        //通过依赖查找发现HomeProvider服务，使用根据路径/home/HomeProvider的方式
        val filterNewsFragment = (ARouter.getInstance().build("/home/HomeProvider").navigation() as HomeProvider).newFilterNewsFragmentInstance(infoId, R.id.leftnavigator_menu_home)
        filterNewsFragment.let {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainright_framelayout_fragments, it)
                    ?.addToBackStack("")?.commit()
            activity?.title = title
        }
    }
}