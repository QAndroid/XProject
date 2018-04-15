package workshop1024.com.xproject.controller.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter.SubListItemListener;
import workshop1024.com.xproject.model.sub.source.SubInfoDataSource;
import workshop1024.com.xproject.model.sub.source.SubInfoRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
public abstract class HomeSubFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SubInfoDataSource.LoadSubInfoCallback {
    //根视图
    private View mRootView;
    //下拉刷新
    SwipeRefreshLayout mSwipeRefreshLayout;
    //订阅的发布者列表
    RecyclerView mSubRecyclerView;

    SubInfoRepository mSubInfoRepository;

    SubListItemListener mSubListItemListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("XProject", "SubscribeFragment onAttach");
        if (context instanceof SubListItemListener) {
            mSubListItemListener = (SubListItemListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SubListItemListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject", "SubscribeFragment onCreate");
        mSubInfoRepository = SubInfoRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.homesub_fragment, container, false);

        mSwipeRefreshLayout = mRootView.findViewById(R.id.homesub_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSubRecyclerView = mRootView.findViewById(R.id.homesub_recyclerview_list);
        mSubRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mSubRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubListItemListener = null;
    }
}
