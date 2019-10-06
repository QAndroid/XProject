package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import org.greenrobot.eventbus.Subscribe
import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent
import workshop1024.com.xproject.news.controller.fragment.NewsesFragment

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.news.model.news.News

class SubscribeNewsesFragment : NewsesFragment(), SubFragment {
    private var mSubscribeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSubscribeId = arguments?.getString(SUBSCRIBE_ID)
    }

    override fun getNewsList() {
        mNewsRepository.getNewsesByTypeAndKey(News.SUBSCRIBE_TYPE, mSubscribeId!!, this)
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        super.onCachedOrLocalNewsLoaded(newsList)

        if (!mNewsRepository.getIsRequestRemoteBySearchTypeAndKey(News.SUBSCRIBE_TYPE, mSubscribeId!!)) {
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        mNewsRepository.refreshBySearchTypeAndKey(News.SUBSCRIBE_TYPE, mSubscribeId!!, true, false)
        refreshNewsList()
    }

    @Subscribe
    override fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        mNewsRepository.markNewsesReadedByNewsId(News.SUBSCRIBE_TYPE, mSubscribeId!!, getNewsIdList())
        //TODO 通过适配器的notify更新
        refreshNewsList()
    }

    companion object {
        private const val SUBSCRIBE_ID = "subscribe_Id"

        fun newInstance(subscribeId: String, navigationItemId: Int): SubscribeNewsesFragment {
            val subscribeNewsFragment = SubscribeNewsesFragment()
            subscribeNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(SUBSCRIBE_ID, subscribeId)
            subscribeNewsFragment.arguments = args
            return subscribeNewsFragment
        }
    }
}
