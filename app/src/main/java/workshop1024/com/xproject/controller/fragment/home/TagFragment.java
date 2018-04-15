package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;

import java.util.List;

import workshop1024.com.xproject.controller.adapter.TagListAdapter;
import workshop1024.com.xproject.model.sub.tag.Tag;
import workshop1024.com.xproject.model.sub.tag.source.TagDataSource;
import workshop1024.com.xproject.model.sub.tag.source.TagRepository;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
public class TagFragment extends HomeSubFragment implements TagDataSource.LoadTagsCallback {
    private TagListAdapter mTagListAdapter;

    private TagRepository mTagRepository;

    public static TagFragment newInstance() {
        TagFragment tagFragment = new TagFragment();
        return tagFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagRepository = TagRepository.getInstance();
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
    public void onRefresh() {
        refreshTagList();
    }

    @Override
    public void onTagsLoaded(List<Tag> tagList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mTagListAdapter = new TagListAdapter(tagList, mSubListItemListener);
        mSubRecyclerView.setAdapter(mTagListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
