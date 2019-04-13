package workshop1024.com.xproject.controller.fragment.home.news;

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
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

/**
 * 新闻列表Fragmnet，用于展示新闻列表
 */
public abstract class NewsListFragment extends XFragment implements SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback {
    private View mRootView;
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;
    private RecyclerView.Adapter mListAdapter;

    protected NewsDataSource mNewsRepository;
    private List<News> mNewsList;

    public NewsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsRepository = Injection.provideNewsRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.homelist_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.homelist_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = mRootView.findViewById(R.id.homelist_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStoryRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
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
    public void onNewsLoaded(List<News> newsList) {
        if (mIsForeground) {
            mNewsList = newsList;
            mSwipeRefreshLayoutPull.setRefreshing(false);
            showBigCardsList();
            Snackbar.make(mRootView, "Fetch " + newsList.size() + " newses ...", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataNotAvaiable() {
        Snackbar.make(mRootView, "No newses refresh...", Snackbar.LENGTH_SHORT).show();
    }

    private void refreshNewsList() {
        Snackbar.make(mRootView, "Fetch more newses ...", Snackbar.LENGTH_SHORT).show();
        mSwipeRefreshLayoutPull.setRefreshing(true);
        getNewsList();
    }

    public abstract void getNewsList();

    public void showBigCardsList() {
        mListAdapter = new BigCardsAdapter(getContext(), mNewsList);
        mStoryRecyclerView.setAdapter(mListAdapter);
    }

    public void showMinimalList() {
        mListAdapter = new MinimalAdapter(getContext(), mNewsList);
        mStoryRecyclerView.setAdapter(mListAdapter);
    }

    public void showCompactList() {
        mListAdapter = new CompactAdapter(getContext(), mNewsList);
        mStoryRecyclerView.setAdapter(mListAdapter);
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
