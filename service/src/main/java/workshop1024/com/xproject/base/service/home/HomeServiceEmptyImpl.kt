package workshop1024.com.xproject.base.service.home

import androidx.fragment.app.Fragment

class HomeServiceEmptyImpl : HomeService {

    override fun newSubscribeNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return null
    }

    override fun newTagNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return null
    }

    override fun newFilterNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment? {
        return null
    }

    override fun isHomePageFragment(fragment: Fragment?): Boolean {
        return false
    }

    override fun isNewsListFragment(fragment: Fragment?): Boolean {
        return false
    }
}