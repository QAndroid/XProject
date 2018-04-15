package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
import workshop1024.com.xproject.model.sub.subscribe.Subscribe;
import workshop1024.com.xproject.model.sub.subscribe.source.SubscribeDataSource;
import workshop1024.com.xproject.model.sub.subscribe.source.SubscribeRepository;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者列表
 */
public class SubscribeFragment extends HomeSubFragment implements SubscribeDataSource.LoadSubscribesCallback,
        SubscribeListAdapter.SubscribeListMenuListener, InputStringDialog.InputStringDialogListener {
    //列表内容适配器
    private SubscribeListAdapter mSubscribeListAdapter;

    private SubscribeRepository mSubscribeRepository;
    private Subscribe mRenameSubscribe;

    public static SubscribeFragment newInstance() {
        SubscribeFragment subscribeFragment = new SubscribeFragment();
        return subscribeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject","SubscribeFragment onCreate");
        mSubscribeRepository =SubscribeRepository.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("XProject","SubscribeFragment onStart");
        refreshSubscribedList();
    }

    /**
     * 刷新订阅的发布者列表信息
     */
    private void refreshSubscribedList() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSubscribeRepository.getSubscribes(this);
    }

    @Override
    public void onRefresh() {
        Log.i("XProject","SubscribeFragment onRefresh");
        refreshSubscribedList();
    }

    @Override
    public void onPublishersLoaded(List<Subscribe> subscribeList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mSubscribeListAdapter = new SubscribeListAdapter(getContext(), subscribeList,
                mSubListItemListener, this);
        mSubRecyclerView.setAdapter(mSubscribeListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onRenameMenuClick(Subscribe subscribe) {
        mRenameSubscribe = subscribe;
        InputStringDialog inputStringDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive);
        inputStringDialog.setInputStringDialogListener(this);
        inputStringDialog.show(getFragmentManager(), "inputStringDialog");
    }

    @Override
    public void onUnscribeMenuClick(Subscribe subscribe) {
        mSubscribeRepository.unSubscribeById(subscribe.getInfoId());
        refreshSubscribedList();
    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mSubscribeRepository.reNameSubscribeById(mRenameSubscribe.getInfoId(), inputString);
        refreshSubscribedList();
    }


}
