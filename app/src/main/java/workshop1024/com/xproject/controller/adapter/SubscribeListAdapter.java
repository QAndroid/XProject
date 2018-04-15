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

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.sub.subscribe.Subscribe;

/**
 * 订阅的发布者列表适配器
 */
public class SubscribeListAdapter extends HomeSubListAdapter {
    private Context mContext;
    private List<Subscribe> mSubscribeList;
    private SubscribeListMenuListener mSubscribeListMenuListener;

    public SubscribeListAdapter(Context context, List<Subscribe> subscribeList, SubListItemListener
            subListItemListener, SubscribeListMenuListener subscribeListMenuListener) {
        super(subListItemListener);
        mContext = context;
        mSubscribeList = subscribeList;
        mSubscribeListMenuListener = subscribeListMenuListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesublist_item_content,
                    parent, false);
            viewHolder = new SubscribeItemViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            SubscribeItemViewHolder subscribeItemViewHolder = (SubscribeItemViewHolder) holder;
            Subscribe subscribe = mSubscribeList.get(position);
            subscribeItemViewHolder.mSubInfo = subscribe;
            if(!"".equals(subscribe.getCustomName())){
                subscribeItemViewHolder.mNameTextView.setText(subscribe.getCustomName());
            }else{
                subscribeItemViewHolder.mNameTextView.setText(subscribe.getName());
            }
            subscribeItemViewHolder.mNewsCountTextView.setText(subscribe.getUnreadCount());
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

    public interface SubscribeListMenuListener {
        void onRenameMenuClick(Subscribe subscribe);
        void onUnscribeMenuClick(Subscribe subscribe);
    }

    public class SubscribeItemViewHolder extends ItemViewHolder implements View.OnLongClickListener,
            PopupMenu.OnMenuItemClickListener {

        public SubscribeItemViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(this);
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
                    mSubscribeListMenuListener.onRenameMenuClick((Subscribe) mSubInfo);
                    return true;
                case R.id.homepage_recyclerviewmenu_unsubscribe:
                    mSubscribeListMenuListener.onUnscribeMenuClick((Subscribe) mSubInfo);
                    return true;
                default:
                    return false;
            }
        }
    }
}
