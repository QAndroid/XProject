package workshop1024.com.xproject.news.controller.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import workshop1024.com.xproject.base.controller.event.NewsListAsReadEvent
import workshop1024.com.xproject.base.controller.event.NewsListShowBigCardsEvent
import workshop1024.com.xproject.base.controller.event.NewsListShowCompactEvent
import workshop1024.com.xproject.base.controller.event.NewsListShowMinimalEvent
import workshop1024.com.xproject.base.controller.fragment.XFragment
import workshop1024.com.xproject.base.eventbus.controller.event.NewsListRefreshEvent
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.news.R
import workshop1024.com.xproject.news.controller.adapter.BigCardsAdapter
import workshop1024.com.xproject.news.controller.adapter.CompactAdapter
import workshop1024.com.xproject.news.controller.adapter.MinimalAdapter
import workshop1024.com.xproject.news.databinding.NewslistFragmentBinding
import workshop1024.com.xproject.news.model.Injection
import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.news.source.NewsDataSource

/**
 * //FIXME NewsListFragment和业务耦合严重，不太适合放在BaseModule中！！！！
 * 新闻列表Fragmnet，用于展示新闻列表
 */
abstract class NewsesFragment : XFragment(), SwipeRefreshLayout.OnRefreshListener, NewsDataSource.LoadNewsesCallback {
    private var mListAdapter: RecyclerView.Adapter<*>? = null

    protected lateinit var mNewsRepository: NewsDataSource
    protected var mNewsList: List<News>? = null

    protected lateinit var mNewsListFragmentBinding: NewslistFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNewsRepository = Injection.provideNewsRepository(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mNewsListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.newslist_fragment, container, false)

        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.setOnRefreshListener(this)
        mNewsListFragmentBinding.newslistRecyclerviewList.layoutManager = LinearLayoutManager(context)
        mNewsListFragmentBinding.newslistRecyclerviewList.addItemDecoration(RecyclerViewItemDecoration(6))

        return mNewsListFragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        refreshNewsList()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onRefreshEvent(event: NewsListRefreshEvent) {
        onRefresh()
    }

    override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
        mNewsList = newsList
        showBigCardsList()
        Snackbar.make(mNewsListFragmentBinding.root, "Fetch cacheorlocal " + newsList.size + " newses ...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onRemoteNewsLoaded(newsList: List<News>) {
        mNewsList = newsList
        showBigCardsList()
        Snackbar.make(mNewsListFragmentBinding.root, "Fetch remote " + newsList.size + " newses ...", Snackbar.LENGTH_SHORT).show()

        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
    }

    override fun onDataNotAvaiable() {
        Snackbar.make(mNewsListFragmentBinding.root, "No newses refreshBySearchTypeAndKey...", Snackbar.LENGTH_SHORT).show()
        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
    }

    protected fun refreshNewsList() {
        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = true
        getNewsList()
    }

    abstract fun getNewsList()

    abstract fun onMarkAsReadEvent(event: NewsListAsReadEvent)

    @Subscribe
    fun onShowBigCardsListEvent(event: NewsListShowBigCardsEvent) {
        //FIXME 用双引号是不是有点激进
        showBigCardsList()
    }

    @Subscribe
    fun onShowMinimalListEvent(event: NewsListShowMinimalEvent) {
        //FIXME 用双引号是不是有点激进
        mListAdapter = MinimalAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding.newslistRecyclerviewList.adapter = mListAdapter
    }

    @Subscribe
    fun onShowCompactListEvent(event: NewsListShowCompactEvent) {
        //FIXME 用双引号是不是有点激进
        mListAdapter = CompactAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding.newslistRecyclerviewList.adapter = mListAdapter
    }

    fun showBigCardsList() {
        mListAdapter = BigCardsAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding.newslistRecyclerviewList.adapter = mListAdapter
    }

    fun getNewsIdList(): List<String> {
        val newsIdList = ArrayList<String>()
        for (news in mNewsList!!) {
            newsIdList.add(news.mNewsId)
        }
        return newsIdList
    }
}