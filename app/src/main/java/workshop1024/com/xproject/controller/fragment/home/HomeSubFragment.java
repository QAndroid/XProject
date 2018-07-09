package workshop1024.com.xproject.controller.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter.SubListItemListener;
import workshop1024.com.xproject.controller.fragment.LazyFragment;
import workshop1024.com.xproject.databinding.HomesubFragmentBinding;
import workshop1024.com.xproject.model.subinfo.SubInfo;
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource;
import workshop1024.com.xproject.model.subinfo.source.SubInfoRepository;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
public abstract class HomeSubFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener,
        SubListItemListener, SubInfoDataSource.LoadSubInfoCallback {
    SubInfoRepository mSubInfoRepository;
    List<SubInfo> mSubInfoList;

    //Fragment是否在前台展示
    protected boolean mIsForeground;

    HomesubFragmentBinding mHomesubFragmentBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubInfoRepository = SubInfoRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHomesubFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.homesub_fragment, container, false);

        mHomesubFragmentBinding.homesubSwiperefreshlayoutPullrefresh.setOnRefreshListener(this);

        mHomesubFragmentBinding.homesubRecyclerviewList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mHomesubFragmentBinding.homesubRecyclerviewList.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mHomesubFragmentBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsForeground = false;
    }

    abstract void markAsRead();
}
