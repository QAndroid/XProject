package workshop1024.com.xproject.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter.SubscribeListItemListener;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.publisher.source.PublisherRepository;
import workshop1024.com.xproject.model.publisher.source.local.PublisherLocalDataSource;
import workshop1024.com.xproject.model.publisher.source.remote.PublisherRemoteDataSource;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment，用于展示被订阅的发布者
 */
public class SubscribeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PublisherDataSource
        .LoadPublishersCallback, SubscribeListAdapter.SubscribeListMenuListener, InputStringDialog
        .InputStringDialogListener {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //订阅的发布者列表
    private RecyclerView mRecyclerViewSubscribeList;

    //列表内容适配器
    private SubscribeListAdapter mSubscribeListAdapter;

    private PublisherRepository mPublisherRepository;
    private Publisher mRenamePublisher;

    private SubscribeListItemListener mSubscribeListItemListener;

    public static SubscribeFragment newInstance() {
        SubscribeFragment fragment = new SubscribeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SubscribeListAdapter.SubscribeListItemListener) {
            mSubscribeListItemListener = (SubscribeListItemListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SubscribeListItemListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.subscribe_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.subscribe_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mRecyclerViewSubscribeList = mRootView.findViewById(R.id.subscribe_recyclerview_subscribelist);
        mRecyclerViewSubscribeList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerViewSubscribeList.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPublisherRepository = PublisherRepository.getInstance(PublisherRemoteDataSource.getInstance(),
                PublisherLocalDataSource.getInstance());

        refreshSubscribedList();
    }

    /**
     * 刷新订阅的发布者列表信息
     */
    private void refreshSubscribedList() {
        mPublisherRepository.refreshPublishers();
        mPublisherRepository.getSubscribedPublishers(this);
        mSwipeRefreshLayoutPull.setRefreshing(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubscribeListItemListener = null;
    }

    @Override
    public void onRefresh() {
        refreshSubscribedList();
    }

    @Override
    public void onPublishersLoaded(List<Publisher> publisherList) {
        mSwipeRefreshLayoutPull.setRefreshing(false);
        mSubscribeListAdapter = new SubscribeListAdapter(getContext(), publisherList, mSubscribeListItemListener, this);
        mRecyclerViewSubscribeList.setAdapter(mSubscribeListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onRenameMenuClick(Publisher publisher) {
        mRenamePublisher = publisher;
        InputStringDialog inputStringDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive);
        inputStringDialog.setInputStringDialogListener(this);
        inputStringDialog.show(getFragmentManager(), "inputStringDialog");
    }

    @Override
    public void onUnscribeMenuClick(Publisher publisher) {
        mPublisherRepository.unSubscribePublisher(publisher);
        refreshSubscribedList();
    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mPublisherRepository.reNamePublisher(mRenamePublisher, inputString);
        refreshSubscribedList();
    }
}
