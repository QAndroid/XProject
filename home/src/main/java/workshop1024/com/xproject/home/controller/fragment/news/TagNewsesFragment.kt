package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent
import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment
import workshop1024.com.xproject.news.model.news.News

class TagNewsesFragment : NewsesFragment(), SubFragment {

    private var mTagId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTagId = arguments?.getString(TAG_ID)
    }

    override fun getNewsList() {
        mNewsRepository.getNewsesByTypeAndKey(News.TAG_TYPE, mTagId!!, this)
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        super.onCachedOrLocalNewsLoaded(newsList)

        if (!mNewsRepository.getIsRequestRemoteBySearchTypeAndKey(News.TAG_TYPE, mTagId!!)) {
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        mNewsRepository.refreshBySearchTypeAndKey(News.TAG_TYPE, mTagId!!, true, false)
        refreshNewsList()
    }

    override fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        mNewsRepository.markNewsesReadedByNewsId(News.SUBSCRIBE_TYPE, mTagId!!, getNewsIdList())
        //TODO 通过适配器的notify更新
        refreshNewsList()
    }

    companion object {
        private const val TAG_ID = "tag_Id"

        fun newInstance(subscribeName: String, navigationItemId: Int): TagNewsesFragment {
            val tagNewsFragment = TagNewsesFragment()
            tagNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(TAG_ID, subscribeName)
            tagNewsFragment.arguments = args
            return tagNewsFragment
        }
    }
}
