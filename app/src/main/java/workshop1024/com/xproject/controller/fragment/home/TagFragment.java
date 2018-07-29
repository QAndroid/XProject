package workshop1024.com.xproject.controller.fragment.home;

import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter;
import workshop1024.com.xproject.controller.fragment.home.news.TagNewsFragment;
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
        refreshTagList();
    }

    @Override
    protected void loadData() {
        refreshTagList();
    }

    private void refreshTagList() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch more tag ...", Snackbar.LENGTH_SHORT).show();
        mHomeSubFragmentHanlders.isRefreshing.set(true);
        mSubInfoRepository.getTagSubInfos(this);
    }


    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        if (mIsForeground) {
            mSubInfoList = subInfoList;
            mHomeSubFragmentHanlders.isRefreshing.set(false);
            HomeSubListAdapter homeSubListAdapter = new HomeSubListAdapter(subInfoList, this);
            mHomesubFragmentBinding.homesubRecyclerviewList.setAdapter(homeSubListAdapter);
            Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch " + subInfoList.size() + " tags ...", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "No tags refresh...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        TagNewsFragment newsListFragment = TagNewsFragment.newInstance(subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, newsListFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }

    @Override
    public void markAsRead() {
        List<String> subInfoIds = new ArrayList<>();
        for (SubInfo subInfo : mSubInfoList) {
            subInfoIds.add(subInfo.getInfoId());
        }
        mSubInfoRepository.markedTagSubInfoesAsRead(subInfoIds);

        refreshTagList();
    }
}
