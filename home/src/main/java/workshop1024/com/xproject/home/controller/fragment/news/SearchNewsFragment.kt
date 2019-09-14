package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.news.controller.fragment.NewsListFragment

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

        fun newInstance(searchName: String, navigationItemId: Int): SearchNewsFragment {
            val searchNewsFragment = SearchNewsFragment()
            searchNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(SEARCH_NAME, searchName)
            searchNewsFragment.arguments = args
            return searchNewsFragment
        }
    }
}
