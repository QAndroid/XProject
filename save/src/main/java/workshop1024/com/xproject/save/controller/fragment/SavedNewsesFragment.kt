package workshop1024.com.xproject.save.controller.fragment

import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent
import workshop1024.com.xproject.base.controller.fragment.TopFragment
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment
import workshop1024.com.xproject.news.model.news.News

/**
 * 保存Fragment
 * //FIXME 和NewsListFragmet大量重复的代码，如何抽象，处理TopFragment和SubFragment的关系？？
 */
class SavedNewsesFragment : NewsesFragment(), TopFragment {
    override fun getNewsList() {
        mNewsRepository.getNewsesByTypeAndKey(News.Companion.SAVED_TYPE, "saved", this)
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        super.onCachedOrLocalNewsLoaded(newsList)

        if (!mNewsRepository.getIsRequestRemoteBySearchTypeAndKey(News.SAVED_TYPE, "saved")) {
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        mNewsRepository.refreshBySearchTypeAndKey(News.SAVED_TYPE, "saved", true, false)
        refreshNewsList()
    }


    override fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        //FIXME Saved页面中，没有已读按钮
    }

    companion object {

        fun newInstance(navigationItemId: Int): SavedNewsesFragment {
            val savedFragment = SavedNewsesFragment()
            savedFragment.mNavigationItemId = navigationItemId
            return savedFragment
        }
    }
}
