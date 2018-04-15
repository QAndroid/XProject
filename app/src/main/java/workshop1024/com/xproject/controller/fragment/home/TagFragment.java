package workshop1024.com.xproject.controller.fragment.home;

import java.util.List;

import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
import workshop1024.com.xproject.model.sub.SubInfo;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
public class TagFragment extends HomeSubFragment {

    public static TagFragment newInstance() {
        TagFragment tagFragment = new TagFragment();
        return tagFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshTagList();
    }

    @Override
    public void onRefresh() {
        refreshTagList();
    }

    private void refreshTagList() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSubInfoRepository.getTagSubInfos(this);
    }


    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        mSwipeRefreshLayout.setRefreshing(false);
        HomeSubListAdapter homeSubListAdapter = new HomeSubListAdapter(subInfoList, mSubListItemListener);
        mSubRecyclerView.setAdapter(homeSubListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
