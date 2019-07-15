package workshop1024.com.xproject.controller.fragment.home;

import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
import workshop1024.com.xproject.controller.fragment.home.news.FilterNewsFragment;
import workshop1024.com.xproject.model.subinfo.SubInfo;

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment，按照过滤关键字分类展示
 */
public class FilterFragment extends HomeSubFragment {

    public static FilterFragment newInstance() {
        FilterFragment filterFragment = new FilterFragment();
        return filterFragment;
    }

    @Override
    public void onRefresh() {
        refreshFilterList();
    }

    @Override
    protected void loadData() {
        refreshFilterList();
    }

    /**
     * 刷新过滤器列表
     */
    private void refreshFilterList() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch more filters ...", Snackbar.LENGTH_SHORT).show();
        mHomeSubFragmentHanlders.isRefreshing.set(true);
        mSubInfoRepository.getFilterSubInfos(this);
    }

    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        if (mIsForeground) {
            mSubInfoList = subInfoList;
            mHomeSubFragmentHanlders.isRefreshing.set(false);
            HomeSubListAdapter homeSubListAdapter = new HomeSubListAdapter(subInfoList, this);
            mHomesubFragmentBinding.homesubRecyclerviewList.setAdapter(homeSubListAdapter);
            Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch " + subInfoList.size() + " filters ...", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "No filters refresh...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        FilterNewsFragment filterNewsFragment = FilterNewsFragment.Companion.newInstance(subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, filterNewsFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }

    @Override
    public void markAsRead() {
        List<String> subInfoIds = new ArrayList<>();
        for (SubInfo subInfo : mSubInfoList) {
            subInfoIds.add(subInfo.getInfoId());
        }

        mSubInfoRepository.markeFilterSubInfoesAsRead(subInfoIds);

        refreshFilterList();
    }
}
