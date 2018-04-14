package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter.SubscribeListItemListener;
import workshop1024.com.xproject.model.subscribe.Subscribe;
import workshop1024.com.xproject.model.subscribe.source.SubscribeDataSource;
import workshop1024.com.xproject.model.subscribe.source.SubscribeRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者列表
 */
public class SubscribeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SubscribeDataSource.LoadSubscribesCallback,SubscribeListAdapter.SubscribeListMenuListener,
        InputStringDialog.InputStringDialogListener {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //订阅的发布者列表
    private RecyclerView mSubscribeRecyclerView;

    //列表内容适配器
    private SubscribeListAdapter mSubscribeListAdapter;

    private SubscribeRepository mSubscribeRepository;
    private Subscribe mRenameSubscribe;

    //订阅列表表项点击监听器
    private SubscribeListItemListener mSubscribeListItemListener;

    public static SubscribeFragment newInstance() {
        SubscribeFragment fragment = new SubscribeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("XProject","SubscribeFragment onAttach");
        if (context instanceof SubscribeListItemListener) {
            mSubscribeListItemListener = (SubscribeListItemListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SubscribeListItemListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject","SubscribeFragment onCreate");
        mSubscribeRepository =SubscribeRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("XProject","SubscribeFragment onCreateView");
        mRootView = inflater.inflate(R.layout.subscribe_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.subscribe_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mSubscribeRecyclerView = mRootView.findViewById(R.id.subscribe_recyclerview_subscribelist);
        mSubscribeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mSubscribeRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
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
        mSwipeRefreshLayoutPull.setRefreshing(true);
        mSubscribeRepository.getSubscribes(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("XProject","SubscribeFragment onDetach");
        mSubscribeListItemListener = null;
    }

    @Override
    public void onRefresh() {
        Log.i("XProject","SubscribeFragment onRefresh");
        refreshSubscribedList();
    }

    @Override
    public void onPublishersLoaded(List<Subscribe> subscribeList) {
        mSwipeRefreshLayoutPull.setRefreshing(false);
        mSubscribeListAdapter = new SubscribeListAdapter(getContext(), subscribeList,
                mSubscribeListItemListener, this);
        mSubscribeRecyclerView.setAdapter(mSubscribeListAdapter);
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
        mSubscribeRepository.unSubscribeById(subscribe.getSubscribeId());
        refreshSubscribedList();
    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mSubscribeRepository.reNameSubscribeById(mRenameSubscribe.getSubscribeId(), inputString);
        refreshSubscribedList();
    }


}
