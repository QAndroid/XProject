package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import org.greenrobot.eventbus.Subscribe
import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment
import workshop1024.com.xproject.news.model.news.News

class SearchNewsesFragment : NewsesFragment(), SubFragment {
    private var mSearchName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchName = arguments?.getString(SEARCH_NAME)
    }

    override fun getNewsList() {
        mNewsRepository.getNewsesByTypeAndKey(News.SEARCH_TYPE, mSearchName!!, this)
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        super.onCachedOrLocalNewsLoaded(newsList)

        if (!mNewsRepository.getIsRequestRemoteBySearchTypeAndKey(News.SEARCH_TYPE, mSearchName!!)) {
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        mNewsRepository.refreshBySearchTypeAndKey(News.SEARCH_TYPE, mSearchName!!, true, false)
        refreshNewsList()
    }

    @Subscribe
    override fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        mNewsRepository.markNewsesReadedByNewsId(News.SEARCH_TYPE, mSearchName!!, getNewsIdList())
        refreshNewsList()
    }

    companion object {
        private const val SEARCH_NAME = "search_Name"

        fun newInstance(searchName: String, navigationItemId: Int): SearchNewsesFragment {
            val searchNewsFragment = SearchNewsesFragment()
            searchNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(SEARCH_NAME, searchName)
            searchNewsFragment.arguments = args
            return searchNewsFragment
        }
    }
}
