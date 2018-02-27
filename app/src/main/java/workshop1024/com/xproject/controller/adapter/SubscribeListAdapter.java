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

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.publisher.Publisher;
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
    private List<Publisher> mSubscribeList;
    private SubscribeListItemListener mSubscribeListItemListener;
    private SubscribeListMenuListener mSubscribeListMenuListener;

    public SubscribeListAdapter(Context context, List<Publisher> subscribeList, SubscribeListItemListener
            subscribeListItemListener, SubscribeListMenuListener subscribeListMenuListener) {
        mContext = context;
        mSubscribeList = subscribeList;
        mSubscribeListItemListener = subscribeListItemListener;
        mSubscribeListMenuListener = subscribeListMenuListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribelist_item_empty, parent,
                    false);
            viewHolder = new SubscribeListAdapter.EmptyViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribelist_item_content, parent,
                    false);
            viewHolder = new SubscribeViewHolder(view);
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
            SubscribeViewHolder subscribeViewHolder = (SubscribeViewHolder) holder;
            Publisher subscribedPublisher = mSubscribeList.get(position);
            subscribeViewHolder.mPublisher = subscribedPublisher;
            subscribeViewHolder.mNameTextView.setText(subscribedPublisher.getName());
            subscribeViewHolder.mNewsCountTextView.setText(subscribedPublisher.getNewsCount());
        }
    }

    @Override
    public int getItemCount() {
        if (mSubscribeList.size() == 0) {
            return 1;
        } else {
            return mSubscribeList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSubscribeList.size() == 0) {
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
     * 订阅的发布者列表表项接口
     */
    public interface SubscribeListItemListener {
        /**
         * 订阅的发布者列表项点击监听
         *
         * @param publisher 点击的发布者名称
         */
        void onSubscribeListItemClick(Publisher publisher);
    }

    /**
     * 订阅的发布者列表菜单接口
     */
    public interface SubscribeListMenuListener {
        /**
         * 订阅的发布者重命名菜单点击监听
         *
         * @param mPublisher
         */
        void onRenameMenuClick(Publisher mPublisher);

        /**
         * 订阅的发布者取消订阅菜单点击监听
         *
         * @param mPublisher
         */
        void onUnscribeMenuClick(Publisher mPublisher);
    }

    public class SubscribeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        private TextView mNameTextView;
        private TextView mNewsCountTextView;
        private Publisher mPublisher;

        public SubscribeViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            mNameTextView = itemView.findViewById(R.id.subscribeitem_textview_name);
            mNewsCountTextView = itemView.findViewById(R.id.subscribeitem_textview_newscount);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
            if (null != mSubscribeListItemListener) {
                mSubscribeListItemListener.onSubscribeListItemClick(mPublisher);
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
                    mSubscribeListMenuListener.onRenameMenuClick(mPublisher);
                    return true;
                case R.id.homepage_recyclerviewmenu_unsubscribe:
                    mSubscribeListMenuListener.onUnscribeMenuClick(mPublisher);
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
