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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("XProject", "FilterFragment setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("XProject", "FilterFragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject", "FilterFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("XProject", "FilterFragment onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("XProject", "FilterFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("XProject", "FilterFragment onStart");
    }

    @Override
    public void onResume() {
        Log.i("XProject", "FilterFragment onResume");
        super.onResume();
    }

    @Override
    public void onRefresh() {
        Log.i("XProject", "FilterFragment onRefresh");
        refreshFilterList();
    }

    @Override
    public void onPause() {
        Log.i("XProject", "FilterFragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("XProject", "FilterFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("XProject", "FilterFragment onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("XProject", "FilterFragment onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("XProject", "FilterFragment onDetach");
        super.onDetach();
    }

    @Override
    protected void loadData() {
        Log.i("XProject", "FilterFragment loadData");
        refreshFilterList();
    }


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
}
