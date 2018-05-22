package workshop1024.com.xproject.controller.fragment.save;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

/**
 * 保存Fragment
 * //FIXME 和NewsListFragmet大量重复的代码，如何抽象，处理TopFragment和SubFragment的关系？？
 */
public class SavedFragment extends TopFragment implements SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback {
    private View mRootView;
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;
    private RecyclerView.Adapter mListAdapter;

    private NewsRepository mNewsRepository;

    private List<News> mNewsList;

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_saved);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsRepository = NewsRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.saved_fragment, container, false);
        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.savelist_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = mRootView.findViewById(R.id.savelist_recyclerview_list);
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

    private void refreshNewsList() {
        Snackbar.make(mRootView, "Fetch more saved newses ...", Snackbar.LENGTH_SHORT).show();
        mSwipeRefreshLayoutPull.setRefreshing(true);
        mNewsRepository.getSavedNewsList(this);
    }

    @Override
    public void onNewsLoaded(List<News> newsList) {
        mNewsList = newsList;
        mSwipeRefreshLayoutPull.setRefreshing(false);
        showBigCardsList();
        Snackbar.make(mRootView, "Fetch " + newsList.size() + " saved newses ...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDataNotAvaiable() {

    }

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
