package workshop1024.com.xproject.home.controller.service

import androidx.fragment.app.Fragment
import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.home.controller.fragment.HomePageFragment
import workshop1024.com.xproject.home.controller.fragment.news.SubscribeNewsesFragment
import workshop1024.com.xproject.home.controller.fragment.news.TagNewsesFragment
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment

class HomeServiceImpl : HomeService {

    override fun newSubscribeNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return SubscribeNewsesFragment.newInstance(subscribeId, navigationItemId)
    }

    override fun newTagNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return TagNewsesFragment.newInstance(subscribeId, navigationItemId)
    }

    override fun isHomePageFragment(fragment: Fragment?): Boolean {
        return fragment is HomePageFragment
    }

    override fun isNewsListFragment(fragment: Fragment?): Boolean {
        return fragment is NewsesFragment
    }
}