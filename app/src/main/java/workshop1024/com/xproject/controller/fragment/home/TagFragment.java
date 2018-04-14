package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.TagListAdapter;
import workshop1024.com.xproject.controller.adapter.TopicListAdapter.TagListItemListener;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.model.news.source.local.NewsDatabase;
import workshop1024.com.xproject.model.news.source.local.NewsLocalDataSource;
import workshop1024.com.xproject.model.news.source.remote.NewsRemoteDataSource;
import workshop1024.com.xproject.model.publisher.source.PublisherRepository;
import workshop1024.com.xproject.model.publisher.source.local.PublisherDatabase;
import workshop1024.com.xproject.model.publisher.source.local.PublisherLocalDataSource;
import workshop1024.com.xproject.model.publisher.source.remote.PublisherRemoteDataSource;
import workshop1024.com.xproject.model.tag.Tag;
import workshop1024.com.xproject.model.tag.source.TagDataSource;
import workshop1024.com.xproject.model.tag.source.TagRepository;
import workshop1024.com.xproject.utils.ExecutorUtils;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment-TagFragment，按Tag分类显示已订阅发布者信息
 */
public class TagFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TagDataSource.LoadTagCallback {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //订阅的标题列表
    private RecyclerView mTagRecyclerView;
    //订阅标题列表表项点击监听器
    private TagListItemListener mTagListItemListener;

    private TagListAdapter mTagListAdapter;

    private TagRepository mTagRepository;

    /**
     * 构造方法
     *
     * @return 被订阅标题Fragment实例
     */
    public static TagFragment newInstance() {
        TagFragment fragment = new TagFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TagListItemListener) {
            mTagListItemListener = (TagListItemListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TagListItemListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagRepository = TagRepository.getInstance(PublisherRepository.getInstance(PublisherRemoteDataSource.getInstance(),
                PublisherLocalDataSource.getInstance(PublisherDatabase.getInstance(getActivity()).publisherDao(), new ExecutorUtils())),
                NewsRepository.getInstance(NewsRemoteDataSource.getInstance(), NewsLocalDataSource.getInstance(NewsDatabase.getInstance(
                        getActivity()).newsDao(), new ExecutorUtils())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.tag_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.tag_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mTagRecyclerView = mRootView.findViewById(R.id.tag_recyclerview_taglist);
        mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mTagRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshTagList();
    }

    private void refreshTagList() {
        mSwipeRefreshLayoutPull.setRefreshing(true);
        mTagRepository.getTag(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onTagInfoLoaded(List<Tag> tagList) {
        mSwipeRefreshLayoutPull.setRefreshing(false);
        mTagListAdapter = new TagListAdapter(getContext(), tagList, mTagListItemListener);
        mTagRecyclerView.setAdapter(mTagListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
