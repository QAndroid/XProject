package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.subinfo.SubInfo;

//FIXME 这几个Adapter是什么关系
public class SubscribeListAdapter extends HomeSubListAdapter {
    private Context mContext;
    private SubInfoListMenuListener mSubInfoListMenuListener;

    public SubscribeListAdapter(Context context, List<SubInfo> subInfoList, SubListItemListener
            subListItemListener, SubInfoListMenuListener subInfoListMenuListener) {
        super(subInfoList, subListItemListener);
        mContext = context;
        mSubInfoListMenuListener = subInfoListMenuListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesublist_item_empty,
                    parent, false);
            viewHolder = new EmptyViewHolder(view);
        }else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesublist_item_content,
                    parent, false);
            viewHolder = new SubscribeItemViewHolder(view);
        }

        return viewHolder;
    }

    public interface SubInfoListMenuListener {
        void onRenameMenuClick(SubInfo subscribe);

        void onUnscribeMenuClick(SubInfo subscribe);
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
                    mSubInfoListMenuListener.onRenameMenuClick(mSubInfo);
                    return true;
                case R.id.homepage_recyclerviewmenu_unsubscribe:
                    mSubInfoListMenuListener.onUnscribeMenuClick(mSubInfo);
                    return true;
                default:
                    return false;
            }
        }
    }
}
