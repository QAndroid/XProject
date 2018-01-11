package workshop1024.com.xproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
 * 主页Fragment
 */
public class HomeFragment extends TopFragment implements SwipeRefreshLayout.OnRefreshListener {
    //根视图
    private View mRootView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayoutPull;
    //内容列表
    private RecyclerView mRecyclerViewContentList;

    //列表内容适配器
    private EmptyRecyclerViewAdapter mEmptyRecyclerViewAdapter;

    private ContentListItemClickListener mContentListItemClickListener;

    private List<String> mStoreTitleList = new ArrayList<String>() {{
        add("Title1");
        add("Title2");
        add("Title3");
        add("Title4");
    }};


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_home);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.home_fragment, container, false);

        mSwipeRefreshLayoutPull = mRootView.findViewById(R.id.home_swiperefreshlayout_pullrefresh);
        mSwipeRefreshLayoutPull.setOnRefreshListener(this);

        mRecyclerViewContentList = mRootView.findViewById(R.id.home_recyclerview_contentlist);
        mRecyclerViewContentList.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        mEmptyRecyclerViewAdapter = new EmptyRecyclerViewAdapter(mStoreTitleList, mContentListItemClickListener);
        mRecyclerViewContentList.setAdapter(mEmptyRecyclerViewAdapter);

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContentListItemClickListener) {
            mContentListItemClickListener = (ContentListItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContentListItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContentListItemClickListener = null;
    }

    @Override
    public void onRefresh() {
        Snackbar.make(mRootView, "Refreshing....", Snackbar.LENGTH_INDEFINITE).show();

        mStoreTitleList.add("Title...");
        mStoreTitleList.add("Title...");

        if (mStoreTitleList.size() >= 12) {
            mStoreTitleList.clear();
            mSwipeRefreshLayoutPull.setRefreshing(false);
            mEmptyRecyclerViewAdapter.notifyDataSetChanged();
            Snackbar.make(mRootView, "No News...", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mRootView, "Have News Availible...", Snackbar.LENGTH_INDEFINITE).setAction("REFRESH", new View
                    .OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSwipeRefreshLayoutPull.setRefreshing(false);
                    mEmptyRecyclerViewAdapter.notifyDataSetChanged();
                }
            }).show();
        }
    }

    /**
     * 内容列表点击接口
     */
    public interface ContentListItemClickListener {
        void onContentListItemClick(String item);
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
        private ContentListItemClickListener mContentListItemClickListener;

        public EmptyRecyclerViewAdapter(List<String> storeTitleList, ContentListItemClickListener
                contentListItemClickListener) {
            mStoreTitleList = storeTitleList;
            mContentListItemClickListener = contentListItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            if (viewType == VIEW_TYPE_EMPTY) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
                viewHolder = new EmptyRecyclerViewAdapter.EmptyViewHolder(view);
            } else if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
                viewHolder = new EmptyRecyclerViewAdapter.ItemViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == VIEW_TYPE_EMPTY) {
                EmptyRecyclerViewAdapter.EmptyViewHolder emptyViewHolder = (EmptyRecyclerViewAdapter.EmptyViewHolder)
                        holder;
            } else if (viewType == VIEW_TYPE_ITEM) {
                final EmptyRecyclerViewAdapter.ItemViewHolder itemViewHolder = (EmptyRecyclerViewAdapter
                        .ItemViewHolder) holder;
                itemViewHolder.mStoreTitleString = mStoreTitleList.get(position);
                itemViewHolder.mNameTextView.setText(mStoreTitleList.get(position));
                itemViewHolder.mUnReadTextView.setText(" " + position);
                itemViewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mContentListItemClickListener)
                            mContentListItemClickListener.onContentListItemClick(itemViewHolder.mStoreTitleString);
                    }
                });
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
            private View mRootView;
            private TextView mNameTextView;
            private TextView mUnReadTextView;
            private String mStoreTitleString;

            public ItemViewHolder(View view) {
                super(view);
                mRootView = view;
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
