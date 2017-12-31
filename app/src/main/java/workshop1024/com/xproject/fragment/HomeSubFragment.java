package workshop1024.com.xproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;

/**
 * 主页Fragment中ViewPager子Fragment
 */
public class HomeSubFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //内容列表
    private RecyclerView mRecyclerViewContentList;

    //列表内容适配器
    private EmptyRecyclerViewAdapter mEmptyRecyclerViewAdapter;

    private List<String> mStoreTitleList = new ArrayList<String>() {{
        add("Title1");
        add("Title2");
        add("Title3");
        add("Title4");
    }};

    public HomeSubFragment() {
    }

    public static HomeSubFragment newInstance() {
        HomeSubFragment fragment = new HomeSubFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_sub, container, false);

        mSwipeRefreshLayoutPull = view.findViewById(R.id.homesub_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mRecyclerViewContentList = view.findViewById(R.id.homesub_recyclerview_contentlist);
        mRecyclerViewContentList.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        mEmptyRecyclerViewAdapter = new EmptyRecyclerViewAdapter(mStoreTitleList);
        mRecyclerViewContentList.setAdapter(mEmptyRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onRefresh() {
        mStoreTitleList.add("Title...");
        mStoreTitleList.add("Title...");

        if (mStoreTitleList.size() >= 12) {
            mStoreTitleList.clear();
        }
        mSwipeRefreshLayoutPull.setRefreshing(false);
        mEmptyRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 支持空实体的列表适配器
     */
    public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<EmptyRecyclerViewAdapter.ViewHolder> {
        //列表项空视图类型
        private static final int VIEW_TYPE_EMPTY = 0;
        //列表项内容视图类型
        private static final int VIEW_TYPE_ITEM = 1;

        private List<String> mStoreTitleList;

        public EmptyRecyclerViewAdapter(List<String> storeTitleList) {
            mStoreTitleList = storeTitleList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;

            if (viewType == VIEW_TYPE_EMPTY) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            } else if (viewType == VIEW_TYPE_ITEM) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case VIEW_TYPE_ITEM:
                    holder.mNameTextView.setText(mStoreTitleList.get(position));
                    holder.mUnReadTextView.setText(" " + position);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (mStoreTitleList == null) {
                return 0;
            } else if (mStoreTitleList.size() == 0) {
                return 1;
            } else {
                return mStoreTitleList.size();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mStoreTitleList.size() == 0) {
                return VIEW_TYPE_EMPTY;
            } else {
                return VIEW_TYPE_ITEM;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mNameTextView;
            private TextView mUnReadTextView;

            public ViewHolder(View view) {
                super(view);
                mNameTextView = itemView.findViewById(R.id.item_stories_textview_name);
                mUnReadTextView = itemView.findViewById(R.id.item_stories_textview_unread);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }
    }
}
