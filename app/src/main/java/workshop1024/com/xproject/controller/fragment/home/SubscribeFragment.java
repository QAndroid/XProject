package workshop1024.com.xproject.controller.fragment.home;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
import workshop1024.com.xproject.controller.fragment.home.news.SubscribeNewsFragment;
import workshop1024.com.xproject.model.subinfo.SubInfo;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者列表
 */
public class SubscribeFragment extends HomeSubFragment implements SubscribeListAdapter.SubInfoListMenuListener,
        InputStringDialog.InputStringDialogListener {
    private SubscribeListAdapter mSubscribeListAdapter;

    private SubInfo mRenameSubInfo;

    public static SubscribeFragment newInstance() {
        SubscribeFragment subscribeFragment = new SubscribeFragment();
        return subscribeFragment;
    }

    @Override
    public void onRefresh() {
        refreshSubscribedList();
    }

    @Override
    protected void loadData() {
        refreshSubscribedList();
    }


    private void refreshSubscribedList() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch more subscribe ...", Snackbar.LENGTH_SHORT).show();
        mHomesubFragmentBinding.homesubSwiperefreshlayoutPullrefresh.setRefreshing(true);
        mSubInfoRepository.getSubscribeSubInfos(this);
    }

    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        if (mIsForeground) {
            mSubInfoList = subInfoList;
            mHomesubFragmentBinding.homesubSwiperefreshlayoutPullrefresh.setRefreshing(false);
            mSubscribeListAdapter = new SubscribeListAdapter(getContext(), subInfoList,
                    this, this);
            mHomesubFragmentBinding.homesubRecyclerviewList.setAdapter(mSubscribeListAdapter);
            Snackbar.make(mHomesubFragmentBinding.getRoot(), "Fetch " + subInfoList.size() + " subscribes ...", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.getRoot(), "No subscribes refresh...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mSubInfoRepository.reNameSubscribeSubInfoById(mRenameSubInfo.getInfoId(), inputString);
        refreshSubscribedList();
    }


    @Override
    public void onRenameMenuClick(SubInfo subscribe) {
        mRenameSubInfo = subscribe;
        InputStringDialog renamePublisherDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive);
        renamePublisherDialog.setInputStringDialogListener(this);
        renamePublisherDialog.show(getFragmentManager(), "renamePublisherDialog");
    }

    @Override
    public void onUnscribeMenuClick(SubInfo subscribe) {
        mSubInfoRepository.unSubscribeSubInfoById(subscribe.getInfoId());
        refreshSubscribedList();
    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        SubscribeNewsFragment subscribeNewsFragment = SubscribeNewsFragment.newInstance(subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, subscribeNewsFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }

    @Override
    public void markAsRead() {
        List<String> subInfoIds = new ArrayList<>();
        for (SubInfo subInfo : mSubInfoList) {
            subInfoIds.add(subInfo.getInfoId());
        }

        mSubInfoRepository.markedSubscribeSubInfoesAsRead(subInfoIds);

        refreshSubscribedList();
    }
}
