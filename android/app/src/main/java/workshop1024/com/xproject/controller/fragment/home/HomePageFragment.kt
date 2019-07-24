package workshop1024.com.xproject.controller.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import workshop1024.com.xproject.R

import workshop1024.com.xproject.controller.fragment.TopFragment
import workshop1024.com.xproject.controller.fragment.XFragment
import workshop1024.com.xproject.databinding.HomepageFragmentBinding


/**
 * 抽屉导航Home Fragment，包含ViewPager来显示Stories和Topies子PageFragment
 */
class HomePageFragment : XFragment(), TopFragment {
    private var mTabTitles: Array<String>? = null

    private var mHomeFragmentPagerAdapter: HomeFragmentPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homepageFragmentBinding = DataBindingUtil.inflate<HomepageFragmentBinding>(inflater, R.layout.homepage_fragment,
                container, false)

        //设置ViewPager允许有所有的存在屏幕外的Fragment不会被销毁
        mTabTitles = resources.getStringArray(R.array.homepage_tabs_strings)
        homepageFragmentBinding.homepageViewpagerFragments.offscreenPageLimit = mTabTitles!!.size - 1

        //不使用getChildFragmentManager，从StoryFragment返回，PageFragment不显示
        mHomeFragmentPagerAdapter = HomeFragmentPagerAdapter(childFragmentManager)
        homepageFragmentBinding.homepageViewpagerFragments.adapter = mHomeFragmentPagerAdapter
        homepageFragmentBinding.homepageTablayoutTabs.setupWithViewPager(homepageFragmentBinding.homepageViewpagerFragments,
                true)

        return homepageFragmentBinding.root
    }

    fun onRefresh() {
        mHomeFragmentPagerAdapter?.mCurrentSubFragmet?.onRefresh()
    }

    fun markAsRead() {
        mHomeFragmentPagerAdapter?.mCurrentSubFragmet?.markAsRead()
    }

    inner class HomeFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        var mCurrentSubFragmet: HomeSubFragment? = null
            private set

        override fun getItem(position: Int): Fragment? {
            var fragment: Fragment? = null

            when (position) {
                0 -> //FIXME 是不是有必要每次都Nes一个Fragment对象
                    fragment = SubscribeFragment.newInstance()
                1 -> fragment = TagFragment.newInstance()
                2 -> fragment = FilterFragment.newInstance()
            }

            return fragment
        }

        override fun getCount(): Int {
            return mTabTitles!!.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTabTitles!![position]
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            mCurrentSubFragmet = `object` as HomeSubFragment
            super.setPrimaryItem(container, position, `object`)
        }
    }

    companion object {
        fun newInstance(): HomePageFragment {
            val homePageFragment = HomePageFragment()
            homePageFragment.mNavigationItemId = R.id.leftnavigator_menu_home
            return homePageFragment
        }
    }
}