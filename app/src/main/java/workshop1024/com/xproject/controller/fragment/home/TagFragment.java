package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
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
    public void onRefresh() {
        Log.i("XProject", "TagFragment onRefresh");
        refreshTagList();
    }

    @Override
    protected void loadData() {
        Log.i("XProject", "TagFragment loadData");
        refreshTagList();
    }

    private void refreshTagList() {
        Snackbar.make(mRootView, "Fetch more tag ...", Snackbar.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(true);
        mSubInfoRepository.getTagSubInfos(this);
    }


    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        mSwipeRefreshLayout.setRefreshing(false);
        HomeSubListAdapter homeSubListAdapter = new HomeSubListAdapter(subInfoList, this);
        mSubRecyclerView.setAdapter(homeSubListAdapter);
        Snackbar.make(mRootView, "Fetch " + subInfoList.size() + " tags ...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDataNotAvailable() {
        Snackbar.make(mRootView, "No tags refresh...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        NewsListFragment newsListFragment = NewsListFragment.newInstance("Tag",subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, newsListFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }
}
