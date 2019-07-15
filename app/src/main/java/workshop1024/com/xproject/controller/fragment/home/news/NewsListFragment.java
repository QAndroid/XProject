package workshop1024.com.xproject.controller.fragment.home.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.BigCardsAdapter;
import workshop1024.com.xproject.controller.adapter.CompactAdapter;
import workshop1024.com.xproject.controller.adapter.MinimalAdapter;
import workshop1024.com.xproject.controller.fragment.XFragment;
import workshop1024.com.xproject.databinding.NewslistFragmentBinding;
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

/**
 * 新闻列表Fragmnet，用于展示新闻列表
 */
public abstract class NewsListFragment extends XFragment implements SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback {
    private RecyclerView.Adapter mListAdapter;

    protected NewsDataSource mNewsRepository;
    private List<? extends News> mNewsList;

    private NewslistFragmentBinding mNewsListFragmentBinding;

    public NewsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsRepository = Injection.INSTANCE.provideNewsRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mNewsListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.newslist_fragment, container, false);

        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.setOnRefreshListener(this);
        mNewsListFragmentBinding.newslistRecyclerviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsListFragmentBinding.newslistRecyclerviewList.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mNewsListFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshNewsList();
    }

    @Override
    public void onRefresh() {
        refreshNewsList();
    }

    @Override
    public void onNewsLoaded(List<? extends News> newsList) {
        if (getMIsForeground()) {
            mNewsList = newsList;
            mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.setRefreshing(false);
            showBigCardsList();
            Snackbar.make(mNewsListFragmentBinding.getRoot(), "Fetch " + newsList.size() + " newses ...", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataNotAvaiable() {
        Snackbar.make(mNewsListFragmentBinding.getRoot(), "No newses refresh...", Snackbar.LENGTH_SHORT).show();
    }

    private void refreshNewsList() {
        Snackbar.make(mNewsListFragmentBinding.getRoot(), "Fetch more newses ...", Snackbar.LENGTH_SHORT).show();
        mNewsListFragmentBinding.newslistSwiperefreshlayoutPullrefresh.setRefreshing(true);
        getNewsList();
    }

    public abstract void getNewsList();

    public void showBigCardsList() {
        mListAdapter = new BigCardsAdapter(getContext(), mNewsList);
        mNewsListFragmentBinding.newslistRecyclerviewList.setAdapter(mListAdapter);
    }

    public void showMinimalList() {
        mListAdapter = new MinimalAdapter(getContext(), mNewsList);
        mNewsListFragmentBinding.newslistRecyclerviewList.setAdapter(mListAdapter);
    }

    public void showCompactList() {
        mListAdapter = new CompactAdapter(getContext(), mNewsList);
        mNewsListFragmentBinding.newslistRecyclerviewList.setAdapter(mListAdapter);
    }

    public void markAsRead() {
        //将现有文章置为可读
        List<String> newsIdList = new ArrayList<>();
        for (News news : mNewsList) {
            newsIdList.add(news.getNewId());
        }
        mNewsRepository.markNewsesReadedByNewsId(newsIdList);

        //刷新列表
        refreshNewsList();
    }
}
