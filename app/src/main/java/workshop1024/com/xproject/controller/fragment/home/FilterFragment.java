package workshop1024.com.xproject.controller.fragment.home;

import java.util.List;

import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
import workshop1024.com.xproject.model.sub.SubInfo;

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment，按照过滤关键字分类展示
 */
public class FilterFragment extends HomeSubFragment {

    public static FilterFragment newInstance() {
        FilterFragment filterFragment = new FilterFragment();
        return filterFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFilterList();
    }

    @Override
    public void onRefresh() {
        refreshFilterList();
    }

    private void refreshFilterList() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSubInfoRepository.getFilterSubInfos(this);
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
