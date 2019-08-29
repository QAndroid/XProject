package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.home.R

class FilterNewsFragment : NewsListFragment(), SubFragment {
    private var mFilterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFilterName = arguments?.getString(FILTER_NAME)
    }

    override fun getNewsList() {
        mFilterName?.let { mNewsRepository?.getNewsListByFilter(it, this) }
    }

    companion object {
        private const val FILTER_NAME = "filter_Name"

        fun newInstance(filterName: String): FilterNewsFragment {
            val filterNewsFragment = FilterNewsFragment()
            filterNewsFragment.mNavigationItemId = R.id.leftnavigator_menu_home
            val args = Bundle()
            args.putString(FILTER_NAME, filterName)
            filterNewsFragment.arguments = args
            return filterNewsFragment
        }
    }
}
