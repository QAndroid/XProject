package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
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

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.view.GrassView;

/**
 * 订阅的发布者列表适配器
 */
public class SubscribeListAdapter extends RecyclerView.Adapter {
    //列表项空视图类型
    private static final int VIEW_TYPE_EMPTY = 0;
    //列表项内容视图类型
    private static final int VIEW_TYPE_ITEM = 1;

    private Context mContext;
    private List<String> mStoreTitleList;
    private SubscribeListItemClickListener mSubscribeListItemClickListener;

    public SubscribeListAdapter(Context context, List<String> storeTitleList, SubscribeListItemClickListener
            subscribeListItemClickListener) {
        mContext = context;
        mStoreTitleList = storeTitleList;
        mSubscribeListItemClickListener = subscribeListItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homelist_item_empty, parent,
                    false);
            viewHolder = new SubscribeListAdapter.EmptyViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homelist_item_content, parent,
                    false);
            viewHolder = new SubscribeListAdapter.ItemViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            SubscribeListAdapter.EmptyViewHolder emptyViewHolder = (SubscribeListAdapter.EmptyViewHolder)
                    holder;
        } else if (viewType == VIEW_TYPE_ITEM) {
            final SubscribeListAdapter.ItemViewHolder itemViewHolder = (SubscribeListAdapter.ItemViewHolder) holder;
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

    /**
     * 内容列表点击接口
     */
    public interface SubscribeListItemClickListener {
        void onContentListItemClick(String item);
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
            if (null != mSubscribeListItemClickListener) {
                mSubscribeListItemClickListener.onContentListItemClick(mStoreTitleString);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            PopupMenu popupMenu = new PopupMenu(mContext, view);
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
                    Toast.makeText(mContext, "homepage_recyclerviewmenu_rename", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.homepage_recyclerviewmenu_unsubscribe:
                    Toast.makeText(mContext, "homepage_recyclerviewmenu_unsubscribe", Toast.LENGTH_SHORT)
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
