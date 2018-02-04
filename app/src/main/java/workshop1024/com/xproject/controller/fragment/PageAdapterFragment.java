package workshop1024.com.xproject.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter;
import workshop1024.com.xproject.controller.adapter.SubscribeListAdapter.SubscribeListItemClickListener;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;

/**
 * 抽屉导航HomeFragment的子Frament，是HomeFragment ViewPager的子Fragment
 */
public class PageAdapterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //内容列表
    private RecyclerView mRecyclerViewContentList;

    //列表内容适配器
    private SubscribeListAdapter mSubscribeListAdapter;

    private SubscribeListItemClickListener mSubscribeListItemClickListener;

    private List<String> mStoreTitleList = new ArrayList<String>() {{
        add("Title1");
        add("Title2");
        add("Title3");
        add("Title4");
    }};

    public static PageAdapterFragment newInstance() {
        PageAdapterFragment fragment = new PageAdapterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.page_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.page_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mRecyclerViewContentList = mRootView.findViewById(R.id.page_recyclerview_contentlist);
        mRecyclerViewContentList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mSubscribeListAdapter = new SubscribeListAdapter(getContext(), mStoreTitleList,
                mSubscribeListItemClickListener);
        mRecyclerViewContentList.setAdapter(mSubscribeListAdapter);
        mRecyclerViewContentList.addItemDecoration(new RecyclerViewItemDecoration(6));

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SubscribeListItemClickListener) {
            mSubscribeListItemClickListener = (SubscribeListItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SubscribeListItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubscribeListItemClickListener = null;
    }

    @Override
    public void onRefresh() {
        Snackbar.make(mRootView, "Refreshing....", Snackbar.LENGTH_INDEFINITE).show();

        mStoreTitleList.add("Title...");
        mStoreTitleList.add("Title...");

        if (mStoreTitleList.size() >= 12) {
            mStoreTitleList.clear();
            mSubscribeListAdapter.notifyDataSetChanged();
            mSwipeRefreshLayoutPull.setRefreshing(false);
            Snackbar.make(mRootView, "No News...", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mRootView, "Have News Availible...", Snackbar.LENGTH_INDEFINITE).setAction("REFRESH", new View
                    .OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSwipeRefreshLayoutPull.setRefreshing(false);
                    mSubscribeListAdapter.notifyDataSetChanged();
                }
            }).show();
        }
    }
}
