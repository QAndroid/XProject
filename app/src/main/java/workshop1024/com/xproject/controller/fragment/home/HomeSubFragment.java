package workshop1024.com.xproject.controller.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter.SubListItemListener;
import workshop1024.com.xproject.controller.fragment.LazyFragment;
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource;
import workshop1024.com.xproject.model.subinfo.source.SubInfoRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
public abstract class HomeSubFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener,
        SubListItemListener,SubInfoDataSource.LoadSubInfoCallback {
    //根视图
    private View mRootView;
    //下拉刷新
    SwipeRefreshLayout mSwipeRefreshLayout;
    //订阅的发布者列表
    RecyclerView mSubRecyclerView;

    SubInfoRepository mSubInfoRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
