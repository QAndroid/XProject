package workshop1024.com.xproject.controller.fragment.home.news

import android.os.Bundle

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.fragment.SubFragment

class FilterNewsFragment : NewsListFragment(), SubFragment {
    private var mFilterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mFilterName = arguments!!.getString(FILTER_NAME)
        }
    }

    override fun getNewsList() {
        mNewsRepository.getNewsListByFilter(mFilterName!!, this)
    }

    companion object {
        private val FILTER_NAME = "filter_Name"

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
