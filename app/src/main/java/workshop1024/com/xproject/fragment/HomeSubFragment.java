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
import workshop1024.com.xproject.view.GrassView;

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
    public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        //列表项空视图类型
        private static final int VIEW_TYPE_EMPTY = 0;
        //列表项内容视图类型
        private static final int VIEW_TYPE_ITEM = 1;

        private List<String> mStoreTitleList;

        public EmptyRecyclerViewAdapter(List<String> storeTitleList) {
            mStoreTitleList = storeTitleList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            if (viewType == VIEW_TYPE_EMPTY) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
                viewHolder = new EmptyViewHolder(view);
            } else if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
                viewHolder = new ItemViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == VIEW_TYPE_EMPTY) {
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            } else if (viewType == VIEW_TYPE_ITEM) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.mNameTextView.setText(mStoreTitleList.get(position));
                itemViewHolder.mUnReadTextView.setText(" " + position);
            }
        }

        @Override
        public int getItemCount() {
            if (mStoreTitleList.size() == 0) {
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

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            //处理了，EmptyView只显示一行的问题
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == VIEW_TYPE_EMPTY ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView mNameTextView;
            private TextView mUnReadTextView;

            public ItemViewHolder(View view) {
                super(view);
                mNameTextView = itemView.findViewById(R.id.item_stories_textview_name);
                mUnReadTextView = itemView.findViewById(R.id.item_stories_textview_unread);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }

        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            private GrassView mGrassView;

            public EmptyViewHolder(View itemView) {
                super(itemView);
                mGrassView = itemView.findViewById(R.id.empty_grassview);
            }
        }
    }
}
