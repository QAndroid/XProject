package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
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
        Log.i("XProject", "FilterFragment onRefresh");
        refreshFilterList();
    }

    @Override
    protected void loadData() {
        Log.i("XProject", "FilterFragment loadData");
        refreshFilterList();
    }

    /**
     * 刷新过滤器列表
     */
    private void refreshFilterList() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSubInfoRepository.getFilterSubInfos(this);
    }

    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        mSwipeRefreshLayout.setRefreshing(false);
        HomeSubListAdapter homeSubListAdapter = new HomeSubListAdapter(subInfoList, this);
        mSubRecyclerView.setAdapter(homeSubListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        NewsListFragment newsListFragment = NewsListFragment.newInstance("Filter",subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, newsListFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }
}
