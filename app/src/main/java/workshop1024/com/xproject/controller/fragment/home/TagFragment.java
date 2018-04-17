package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
import workshop1024.com.xproject.model.subinfo.SubInfo;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
public class TagFragment extends HomeSubFragment {

    public static TagFragment newInstance() {
        TagFragment tagFragment = new TagFragment();
        return tagFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("XProject", "TagFragment setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("XProject", "TagFragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject", "TagFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("XProject", "TagFragment onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("XProject", "TagFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("XProject", "TagFragment onStart");
    }

    @Override
    public void onResume() {
        Log.i("XProject", "TagFragment onResume");
        super.onResume();
    }

    @Override
    public void onRefresh() {
        Log.i("XProject", "TagFragment onRefresh");
        refreshTagList();
    }

    @Override
    public void onPause() {
        Log.i("XProject", "TagFragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("XProject", "TagFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("XProject", "TagFragment onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("XProject", "TagFragment onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("XProject", "TagFragment onDetach");
        super.onDetach();
    }

    @Override
    protected void loadData() {
        Log.i("XProject", "TagFragment loadData");
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
