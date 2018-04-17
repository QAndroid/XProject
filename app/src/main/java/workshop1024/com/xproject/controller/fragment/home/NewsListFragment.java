package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.BigCardsAdapter;
import workshop1024.com.xproject.controller.adapter.CompactAdapter;
import workshop1024.com.xproject.controller.adapter.MinimalAdapter;
import workshop1024.com.xproject.controller.fragment.SubFragment;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航Home Fragment的PageFragment列表选项点击后展示的子列表Fragment
 */
public class NewsListFragment extends SubFragment implements SwipeRefreshLayout.OnRefreshListener,
        NewsDataSource.LoadNewsListCallback {
    private static final String SEARCH_ID = "search_Id";

    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;

    private RecyclerView.Adapter mListAdapter;

    private String mSearchId;
    private NewsRepository mNewsRepository;
    private List<News> mNewsList;

    public NewsListFragment() {
    }

    public static NewsListFragment newInstance(String searchId) {
        NewsListFragment fragment = new NewsListFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        Bundle args = new Bundle();
        args.putString(SEARCH_ID, searchId);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchId = getArguments().getString(SEARCH_ID);
        }
        mNewsRepository = NewsRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homelist_fragment, container, false);

        mSwipeRefreshLayoutPull = view.findViewById(R.id.stroy_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = view.findViewById(R.id.story_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStoryRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return view;
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
        mSwipeRefreshLayoutPull.setRefreshing(true);
        mNewsRepository.getNewsListByFilter(mSearchId, this);
    }

    @Override
    public void onNewsLoaded(List<News> newsList) {
        mNewsList = newsList;
        mSwipeRefreshLayoutPull.setRefreshing(false);
        showBigCardsList();
    }

    @Override
    public void onDataNotAvaiable() {

    }

    public void showBigCardsList() {
        mListAdapter = new BigCardsAdapter(getContext(),mNewsList);
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
}
