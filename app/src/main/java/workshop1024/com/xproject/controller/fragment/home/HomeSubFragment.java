package workshop1024.com.xproject.controller.fragment.home;

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter.SubListItemListener;
import workshop1024.com.xproject.controller.fragment.LazyFragment;
import workshop1024.com.xproject.databinding.HomesubFragmentBinding;
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.subinfo.SubInfo;
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

@BindingMethods({
        @BindingMethod(type = RecyclerView.class, attribute = "itemDecoration", method = "addItemDecoration")
})

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
public abstract class HomeSubFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener,
        SubListItemListener, SubInfoDataSource.LoadSubInfoCallback {
    SubInfoDataSource mSubInfoRepository;
    List<SubInfo> mSubInfoList;

    //Fragment是否在前台展示
    protected boolean mIsForeground;

    HomesubFragmentBinding mHomesubFragmentBinding;
    HomeSubFragmentHanlders mHomeSubFragmentHanlders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubInfoRepository = Injection.INSTANCE.provideSubInfoRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHomesubFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.homesub_fragment, container, false);
        mHomesubFragmentBinding.setGridLayoutManager(new GridLayoutManager(getContext(), 2));
        mHomesubFragmentBinding.setRecyclerViewItemDecoration(new RecyclerViewItemDecoration(6));
        mHomesubFragmentBinding.setOnRefreshListener(this);

        mHomeSubFragmentHanlders = new HomeSubFragmentHanlders();
        mHomesubFragmentBinding.setHomeSubFragmentHanlders(mHomeSubFragmentHanlders);

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

    public class HomeSubFragmentHanlders {
        public final ObservableBoolean isRefreshing = new ObservableBoolean();
    }
}
