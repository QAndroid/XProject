package workshop1024.com.xproject.controller.fragment.home;


import android.content.Context;
import android.os.Bundle;
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
import workshop1024.com.xproject.controller.adapter.TagListAdapter.TagListItemListener;
import workshop1024.com.xproject.model.tag.Tag;
import workshop1024.com.xproject.model.tag.source.TagDataSource;
import workshop1024.com.xproject.model.tag.source.TagRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
public class TagFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        TagDataSource.LoadTagsCallback {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //订阅的发布者新闻Tag列表
    private RecyclerView mTagRecyclerView;

    private TagListAdapter mTagListAdapter;

    private TagRepository mTagRepository;
    private TagListItemListener mTagListItemListener;

    public static TagFragment newInstance() {
        TagFragment tagFragment = new TagFragment();
        return tagFragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagRepository = TagRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.tag_fragment, container, false);

        mSwipeRefreshLayout = mRootView.findViewById(R.id.tag_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
        mSwipeRefreshLayout.setRefreshing(true);
        mTagRepository.getTags(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTagListItemListener = null;
    }

    @Override
    public void onRefresh() {
        refreshTagList();
    }

    @Override
    public void onTagsLoaded(List<Tag> tagList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mTagListAdapter = new TagListAdapter(getContext(), tagList, mTagListItemListener);
        mTagRecyclerView.setAdapter(mTagListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
