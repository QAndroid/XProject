package workshop1024.com.xproject.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.view.GrassView;
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
    private EmptyRecyclerViewAdapter mEmptyRecyclerViewAdapter;

    private ContentListItemClickListener mContentListItemClickListener;

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
        mEmptyRecyclerViewAdapter = new EmptyRecyclerViewAdapter(mStoreTitleList, mContentListItemClickListener);
        mRecyclerViewContentList.setAdapter(mEmptyRecyclerViewAdapter);
        mRecyclerViewContentList.addItemDecoration(new RecyclerViewItemDecoration(6));

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
            mEmptyRecyclerViewAdapter.notifyDataSetChanged();
            mSwipeRefreshLayoutPull.setRefreshing(false);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homelist_item_empty, parent,
                        false);
                viewHolder = new EmptyRecyclerViewAdapter.EmptyViewHolder(view);
            } else if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homelist_item_content, parent,
                        false);
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

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
                .OnLongClickListener, PopupMenu.OnMenuItemClickListener {
            private TextView mNameTextView;
            private TextView mUnReadTextView;
            private String mStoreTitleString;

            public ItemViewHolder(View view) {
                super(view);
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);

                mNameTextView = itemView.findViewById(R.id.item_stories_textview_name);
                mUnReadTextView = itemView.findViewById(R.id.item_stories_textview_unread);
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public void onClick(View view) {
                if (null != mContentListItemClickListener) {
                    mContentListItemClickListener.onContentListItemClick(mStoreTitleString);
                }
            }

            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.setOnMenuItemClickListener(this);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.homepage_recyclerview_menu, popupMenu.getMenu());
                popupMenu.show();

                return false;
            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage_recyclerviewmenu_rename:
                        Toast.makeText(getContext(), "homepage_recyclerviewmenu_rename", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.homepage_recyclerviewmenu_unsubscribe:
                        Toast.makeText(getContext(), "homepage_recyclerviewmenu_unsubscribe", Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    default:
                        return false;
                }
            }
        }

        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            private GrassView mGrassView;

            public EmptyViewHolder(View itemView) {
                super(itemView);
                mGrassView = itemView.findViewById(R.id.homelist_grassview);
            }
        }
    }
}
