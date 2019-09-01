package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import android.util.EventLog
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
import workshop1024.com.xproject.base.controller.fragment.XFragment
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.BigCardsAdapter
import workshop1024.com.xproject.home.controller.adapter.CompactAdapter
import workshop1024.com.xproject.home.controller.adapter.MinimalAdapter
import workshop1024.com.xproject.home.controller.event.NewsListAsReadEvent
import workshop1024.com.xproject.home.controller.event.NewsListShowBigCardsEvent
import workshop1024.com.xproject.home.controller.event.NewsListShowCompactEvent
import workshop1024.com.xproject.home.controller.event.NewsListShowMinimalEvent
import workshop1024.com.xproject.home.databinding.NewslistFragmentBinding
import workshop1024.com.xproject.home.model.Injection
import workshop1024.com.xproject.home.model.news.News
import workshop1024.com.xproject.home.model.news.source.NewsDataSource

/**
 * 新闻列表Fragmnet，用于展示新闻列表
 */
abstract class NewsListFragment : XFragment(), SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback, NewsDataSource.MarkNewsesReadedCallback {
    private var mListAdapter: RecyclerView.Adapter<*>? = null

    protected var mNewsRepository: NewsDataSource? = null
    private var mNewsList: List<News>? = null

    private lateinit var mNewsListFragmentBinding: NewslistFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNewsRepository = Injection.provideNewsRepository()
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


    override fun onRefresh() {
        refreshNewsList()
    }

    override fun onNewsLoaded(newsList: List<News>) {
        if (mIsForeground) {
            mNewsList = newsList
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
            showBigCardsList()
            Snackbar.make(mNewsListFragmentBinding.root, "Fetch " + newsList.size + " newses ...", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDataNotAvaiable() {
        Snackbar.make(mNewsListFragmentBinding.root, "No newses refresh...", Snackbar.LENGTH_SHORT).show()
    }

    private fun refreshNewsList() {
        Snackbar.make(mNewsListFragmentBinding.root, "Fetch more newses ...", Snackbar.LENGTH_SHORT).show()
        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.isRefreshing = true
        getNewsList()
    }

    abstract fun getNewsList()

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

    @Subscribe
    fun onMarkAsReadEvent(event: NewsListAsReadEvent) {
        //将现有文章置为可读
        val newsIdList = ArrayList<String>()
        for (news in mNewsList!!) {
            newsIdList.add(news.newId!!)
        }
        mNewsRepository?.markNewsesReadedByNewsId(newsIdList, this)
    }

    override fun onMarkNewsesReadedSuccess() {
        //刷新列表
        refreshNewsList()
    }

    override fun onMarkNewsesReadedFaild() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showBigCardsList() {
        mListAdapter = BigCardsAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding.newslistRecyclerviewList.adapter = mListAdapter
    }
}