package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.BigCardsAdapter;
import workshop1024.com.xproject.controller.adapter.CompactAdapter;
import workshop1024.com.xproject.controller.adapter.MinimalAdapter;
import workshop1024.com.xproject.controller.fragment.SubFragment;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航Home Fragment的PageFragment列表选项点击后展示的子列表Fragment
 */
public class HomeListFragment extends SubFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    private RecyclerView mStoryRecyclerView;

    private RecyclerView.Adapter mListAdapter;
    private BigCardsAdapter mBigCardsAdapter;
    private CompactAdapter mCompactAdapter;
    private MinimalAdapter mMinimalAdapter;

    private List<News> mNewsList = new ArrayList<News>() {{
        add(new News("news001", "publisher001", "/imag1",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), false));
        add(new News("news002", "publisher001", "/imag2",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), true));
        add(new News("news003", "publisher002", "/imag3",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), false));
        add(new News("news004", "publisher002", "/imag4",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), true));
        add(new News("news005", "publisher003", "/imag5",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), false));
    }};

    public HomeListFragment() {
    }

    public static HomeListFragment newInstance() {
        HomeListFragment fragment = new HomeListFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homelist_fragment, container, false);

        mSwipeRefreshLayoutPull = view.findViewById(R.id.stroy_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mStoryRecyclerView = view.findViewById(R.id.story_recyclerview_list);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        showBigCardsList();

        mStoryRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return view;
    }

    @Override
    public void onRefresh() {
        mNewsList.add(new News("news006", "publisher001", "/imag1",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), false));
        mNewsList.add(new News("news007", "publisher001", "/imag1",
                "title1title1title1title1title1title1title1", "author1",
                "time1", Arrays.asList("aaa", "bbb"), false));
        mListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutPull.setRefreshing(false);
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
}
