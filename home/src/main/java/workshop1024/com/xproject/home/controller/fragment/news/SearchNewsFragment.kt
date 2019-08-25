package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.home.R

class SearchNewsFragment : NewsListFragment(), SubFragment {
    private var mFilterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFilterName = arguments?.getString(SEARCH_NAME)
    }

    override fun getNewsList() {
        mFilterName?.let { mNewsRepository?.getNewsListBySearch(it, this) }
    }

    companion object {
        private const val SEARCH_NAME = "search_Name"

        fun newInstance(searchName: String): SearchNewsFragment {
            val searchNewsFragment = SearchNewsFragment()
            searchNewsFragment.mNavigationItemId = R.id.leftnavigator_menu_home
            val args = Bundle()
            args.putString(SEARCH_NAME, searchName)
            searchNewsFragment.arguments = args
            return searchNewsFragment
        }
    }
}
