package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("XProject", "SubscribeFragment setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("XProject", "SubscribeFragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject", "SubscribeFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("XProject", "SubscribeFragment onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("XProject", "SubscribeFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("XProject", "SubscribeFragment onStart");
    }

    @Override
    public void onResume() {
        Log.i("XProject", "SubscribeFragment onResume");
        super.onResume();
    }

    @Override
    public void onRefresh() {
        Log.i("XProject", "SubscribeFragment onRefresh");
        refreshSubscribedList();
    }

    @Override
    public void onPause() {
        Log.i("XProject", "SubscribeFragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("XProject", "SubscribeFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("XProject", "SubscribeFragment onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("XProject", "SubscribeFragment onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("XProject", "SubscribeFragment onDetach");
        super.onDetach();
    }

    @Override
    protected void loadData() {
        Log.i("XProject", "SubscribeFragment loadData");
        refreshSubscribedList();
    }


    private void refreshSubscribedList() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSubInfoRepository.getSubscribeSubInfos(this);
    }

    @Override
    public void onSubInfosLoaded(List<SubInfo> subInfoList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mSubscribeListAdapter = new SubscribeListAdapter(getContext(), subInfoList,
                this, this);
        mSubRecyclerView.setAdapter(mSubscribeListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mSubInfoRepository.reNameSubscribeSubInfoById(mRenameSubInfo.getInfoId(), inputString);
        refreshSubscribedList();
    }


    @Override
    public void onRenameMenuClick(SubInfo subscribe) {
        mRenameSubInfo = subscribe;
        InputStringDialog inputStringDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive);
        inputStringDialog.setInputStringDialogListener(this);
        inputStringDialog.show(getFragmentManager(), "inputStringDialog");
    }

    @Override
    public void onUnscribeMenuClick(SubInfo subscribe) {
        mSubInfoRepository.unSubscribeSubInfoById(subscribe.getInfoId());
        refreshSubscribedList();
    }

    @Override
    public void onSubListItemClick(SubInfo subInfo) {
        NewsListFragment newsListFragment = NewsListFragment.newInstance("Subscribe",subInfo.getInfoId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainright_framelayout_fragments, newsListFragment)
                .addToBackStack("").commit();
        getActivity().setTitle(subInfo.getName());
    }
}
