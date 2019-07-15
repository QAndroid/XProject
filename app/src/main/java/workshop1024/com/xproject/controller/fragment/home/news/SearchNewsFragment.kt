package workshop1024.com.xproject.controller.fragment.home.news

import android.os.Bundle

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.fragment.SubFragment

class SearchNewsFragment : NewsListFragment(), SubFragment {
    private var mFilterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mFilterName = arguments!!.getString(SEARCH_NAME)
        }
    }

    override fun getNewsList() {
        mNewsRepository.getNewsListBySearch(mFilterName!!, this)
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
