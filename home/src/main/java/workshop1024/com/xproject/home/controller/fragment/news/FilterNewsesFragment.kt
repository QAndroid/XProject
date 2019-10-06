package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import org.greenrobot.eventbus.Subscribe
import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment
import workshop1024.com.xproject.news.model.news.News

class FilterNewsesFragment : NewsesFragment(), SubFragment {

    private var mFilterId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFilterId = arguments?.getString(FILTER_ID)
    }

    override fun getNewsList() {
        mNewsRepository.getNewsesByTypeAndKey(News.FILTER_TYPE, mFilterId!!, this)
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        super.onCachedOrLocalNewsLoaded(newsList)

        if (!mNewsRepository.getIsRequestRemoteBySearchTypeAndKey(News.FILTER_TYPE, mFilterId!!)) {
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        mNewsRepository.refreshBySearchTypeAndKey(News.FILTER_TYPE, mFilterId!!, true, false)
        refreshNewsList()
    }

    @Subscribe
    override fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        mNewsRepository.markNewsesReadedByNewsId(News.FILTER_TYPE, mFilterId!!, getNewsIdList())
        refreshNewsList()
    }

    companion object {
        private const val FILTER_ID = "filter_Id"

        fun newInstance(filterId: String, navigationItemId: Int): FilterNewsesFragment {
            val filterNewsFragment = FilterNewsesFragment()
            filterNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(FILTER_ID, filterId)
            filterNewsFragment.arguments = args
            return filterNewsFragment
        }
    }
}
