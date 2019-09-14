package workshop1024.com.xproject.home.controller.service

import androidx.fragment.app.Fragment
import workshop1024.com.xproject.base.service.home.HomeService
import workshop1024.com.xproject.home.controller.fragment.HomePageFragment
import workshop1024.com.xproject.home.controller.fragment.news.FilterNewsFragment
import workshop1024.com.xproject.home.controller.fragment.news.SubscribeNewsFragment
import workshop1024.com.xproject.home.controller.fragment.news.TagNewsFragment
import workshop1024.com.xproject.news.controller.fragment.NewsListFragment

class HomeServiceImpl : HomeService {

    override fun newSubscribeNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return SubscribeNewsFragment.newInstance(subscribeId, navigationItemId)
    }

    override fun newTagNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return TagNewsFragment.newInstance(subscribeId, navigationItemId)
    }

    override fun newFilterNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return FilterNewsFragment.newInstance(subscribeId, navigationItemId)
    }


    override fun isHomePageFragment(fragment: Fragment?): Boolean {
        return fragment is HomePageFragment
    }

    override fun isNewsListFragment(fragment: Fragment?): Boolean {
        return fragment is NewsListFragment
    }
}