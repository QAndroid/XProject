package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.model.message.MessageGroup;

/**
 * 消息列表是配资
 * TDDO 如何抽象出一个整体的HeaderView和FooterView、EmptyView的适配器
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    public static final int TYPEP_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_GROUP = 2;
    public static final int TYPE_CHILD = 3;

    private List<MessageGroup> mMessageGroupList;

    private View mHeaderView;
    private View mFooterView;

    public MessageListAdapter(List<MessageGroup> messageGroupList) {
        mMessageGroupList = messageGroupList;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getMessageGroupItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("XProject", "onCreateViewHolder()");
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (mHeaderView != null && viewType == TYPEP_HEADER) {
            Log.i("XProject", "mHeaderView != null && viewType == TYPEP_HEADER");
            viewHolder = new HeaderViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            viewHolder = new FooterViewHolder(mFooterView);
            Log.i("XProject", "mFooterView != null && viewType == TYPE_FOOTER");
        } else if (viewType == TYPE_GROUP) {
            Log.i("XProject", "viewType == TYPE_GROUP");
            View groupView = layoutInflater.inflate(R.layout.messagelist_item_group, parent, false);
            viewHolder = new GroupViewHolder(groupView);
        } else if (viewType == TYPE_CHILD) {
            Log.i("XProject", "viewType == TYPE_CHILD");
            View childView = layoutInflater.inflate(R.layout.messagelist_item_child, parent, false);
            viewHolder = new ChildViewHolder(childView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("XProject", "onBindViewHolder()");
        if (holder instanceof GroupViewHolder) {
            Log.i("XProject", "holder instanceof GroupViewHolder.position = " + position + ",text = " + getItemTextByPosition(position));
            ((GroupViewHolder) holder).mDataTextView.setText(getItemTextByPosition(position));
        } else if (holder instanceof ChildViewHolder) {
            Log.i("XProject", "holder instanceof ChildViewHolder = " + position + ",text = " + getItemTextByPosition(position));
            ((ChildViewHolder) holder).mContentTextView.setText(getItemTextByPosition(position));
        }
    }

    private String getItemTextByPosition(int position) {
        if (mHeaderView != null) {
            position = position - 1;
        }

        String itemText = null;

        for (MessageGroup messageGroup : mMessageGroupList) {
            if (position == 0) {
                itemText = messageGroup.getPublishData();
            }
            position--;
            for (Message message : messageGroup.getMessageList()) {
                if (position == 0) {
                    itemText = message.getContent();
                }
                position--;
            }
        }

        return itemText;
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("XProject", "getItemViewType()");
        int viewType;

        if (mHeaderView == null && mFooterView == null) {
            Log.i("XProject", "mHeaderView == null && mFooterView == null");
            viewType = getMessageTypeByPosition(position);
        } else if (mHeaderView != null && mFooterView == null) {
            if (position == 0) {
                viewType = TYPEP_HEADER;
            } else {
                viewType = getMessageTypeByPosition(position - 1);
            }
        } else if (mHeaderView == null && mFooterView != null) {
            if (position == getItemCount() - 1) {
                viewType = TYPE_FOOTER;
            } else {
                viewType = getMessageTypeByPosition(position);
            }
        } else {
            if (position == 0) {
                viewType = TYPEP_HEADER;
            } else if (position == getItemCount() - 1) {
                viewType = TYPE_FOOTER;
            } else {
                viewType = getMessageTypeByPosition(position - 1);
            }
        }

        Log.i("XProject", "position = " + position + ",viewType = " + viewType);
        return viewType;
    }

    private int getMessageTypeByPosition(int position) {
        int viewType = 0;

        for (MessageGroup messageGroup : mMessageGroupList) {
            if (position == 0) {
                viewType = TYPE_GROUP;
                return viewType;
            }
            position--;
            for (Message message : messageGroup.getMessageList()) {
                if (position == 0) {
                    viewType = TYPE_CHILD;
                    return viewType;
                }
                position--;
            }
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        Log.i("XProject", "getItemCount()");
        int itemCount;
        if (mHeaderView != null && mFooterView != null) {
            Log.i("XProject", "mHeaderView != null && mFooterView != null");
            itemCount = getMessageGroupItemCount() + 2;
        } else if (mHeaderView == null && mFooterView == null) {
            Log.i("XProject", "mHeaderView == null && mFooterView == null");
            itemCount = getMessageGroupItemCount();
        } else {
            itemCount = getMessageGroupItemCount() + 1;
        }

        Log.i("XProject", "itemCount = " + itemCount);
        return itemCount;
    }

    public int getMessageGroupItemCount() {
        int itemCount = 0;
        for (MessageGroup messageGroup : mMessageGroupList) {
            itemCount++;
            for (Message message : messageGroup.getMessageList()) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mDataTextView;

        public GroupViewHolder(View itemView) {
            super(itemView);
            mDataTextView = itemView.findViewById(R.id.messagelist_textview_groupdata);
        }
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView mContentTextView;

        public ChildViewHolder(View itemView) {
            super(itemView);
            mContentTextView = itemView.findViewById(R.id.messagelist_textview_childcontent);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
