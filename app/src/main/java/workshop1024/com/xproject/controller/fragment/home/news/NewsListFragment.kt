package workshop1024.com.xproject.controller.fragment.home.news

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.adapter.BigCardsAdapter
import workshop1024.com.xproject.controller.adapter.CompactAdapter
import workshop1024.com.xproject.controller.adapter.MinimalAdapter
import workshop1024.com.xproject.controller.fragment.XFragment
import workshop1024.com.xproject.databinding.NewslistFragmentBinding
import workshop1024.com.xproject.model.Injection
import workshop1024.com.xproject.model.news.News
import workshop1024.com.xproject.model.news.source.NewsDataSource
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration

/**
 * 新闻列表Fragmnet，用于展示新闻列表
 */
abstract class NewsListFragment : XFragment(), SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback {
    private var mListAdapter: RecyclerView.Adapter<*>? = null

    protected var mNewsRepository: NewsDataSource? = null
    private var mNewsList: List<News>? = null

    private var mNewsListFragmentBinding: NewslistFragmentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNewsRepository = Injection.provideNewsRepository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mNewsListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.newslist_fragment, container, false)

        mNewsListFragmentBinding?.newslistSwiperefreshlayoutPullrefresh?.setOnRefreshListener(this)
        mNewsListFragmentBinding?.newslistRecyclerviewList?.layoutManager = LinearLayoutManager(context)
        mNewsListFragmentBinding?.newslistRecyclerviewList?.addItemDecoration(RecyclerViewItemDecoration(6))

        return mNewsListFragmentBinding?.root
    }

    override fun onStart() {
        super.onStart()
        refreshNewsList()
    }

    override fun onRefresh() {
        refreshNewsList()
    }

    override fun onNewsLoaded(newsList: List<News>) {
        if (mIsForeground) {
            mNewsList = newsList
            mNewsListFragmentBinding!!.newslistSwiperefreshlayoutPullrefresh.isRefreshing = false
            showBigCardsList()
            Snackbar.make(mNewsListFragmentBinding!!.root, "Fetch " + newsList.size + " newses ...", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDataNotAvaiable() {
        Snackbar.make(mNewsListFragmentBinding!!.root, "No newses refresh...", Snackbar.LENGTH_SHORT).show()
    }

    private fun refreshNewsList() {
        Snackbar.make(mNewsListFragmentBinding!!.root, "Fetch more newses ...", Snackbar.LENGTH_SHORT).show()
        mNewsListFragmentBinding!!.newslistSwiperefreshlayoutPullrefresh.isRefreshing = true
        getNewsList()
    }

    abstract fun getNewsList()

    fun showBigCardsList() {
        mListAdapter = BigCardsAdapter(context, mNewsList)
        mNewsListFragmentBinding!!.newslistRecyclerviewList.adapter = mListAdapter
    }

    fun showMinimalList() {
        mListAdapter = MinimalAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding!!.newslistRecyclerviewList.adapter = mListAdapter
    }

    fun showCompactList() {
        mListAdapter = CompactAdapter(context!!, mNewsList!!)
        mNewsListFragmentBinding!!.newslistRecyclerviewList.adapter = mListAdapter
    }

    fun markAsRead() {
        //将现有文章置为可读
        val newsIdList = ArrayList<String>()
        for (news in mNewsList!!) {
            newsIdList.add(news.newId)
        }
        mNewsRepository?.markNewsesReadedByNewsId(newsIdList)

        //刷新列表
        refreshNewsList()
    }
}