package workshop1024.com.xproject.controller.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.MessagelistItemChildBinding;
import workshop1024.com.xproject.databinding.MessagelistItemGroupBinding;
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
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (mHeaderView != null && viewType == TYPEP_HEADER) {
            viewHolder = new HeaderViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            viewHolder = new FooterViewHolder(mFooterView);
        } else if (viewType == TYPE_GROUP) {
            MessagelistItemGroupBinding messagelistItemGroupBinding = DataBindingUtil.inflate(layoutInflater, R.layout.messagelist_item_group,
                    parent, false);
            viewHolder = new GroupViewHolder(messagelistItemGroupBinding);
        } else if (viewType == TYPE_CHILD) {
            MessagelistItemChildBinding messagelistItemChildBinding = DataBindingUtil.inflate(layoutInflater, R.layout.messagelist_item_child,
                    parent, false);
            viewHolder = new ChildViewHolder(messagelistItemChildBinding);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupViewHolder) {
            ((GroupViewHolder) holder).mMessagelistItemGroupBinding.messagelistTextviewGroupdata.setText(
                    getItemTextByPosition(position));
        } else if (holder instanceof ChildViewHolder) {
            ((ChildViewHolder) holder).mMessagelistItemChildBinding.messagelistTextviewChildcontent.setText(
                    getItemTextByPosition(position));
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
        int viewType;

        if (mHeaderView == null && mFooterView == null) {
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
        int itemCount;
        if (mHeaderView != null && mFooterView != null) {
            itemCount = getMessageGroupItemCount() + 2;
        } else if (mHeaderView == null && mFooterView == null) {
            itemCount = getMessageGroupItemCount();
        } else {
            itemCount = getMessageGroupItemCount() + 1;
        }

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
        private final MessagelistItemGroupBinding mMessagelistItemGroupBinding;

        public GroupViewHolder(MessagelistItemGroupBinding messagelistItemGroupBinding) {
            super(messagelistItemGroupBinding.getRoot());
            mMessagelistItemGroupBinding = messagelistItemGroupBinding;
        }
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private final MessagelistItemChildBinding mMessagelistItemChildBinding;

        public ChildViewHolder(MessagelistItemChildBinding messagelistItemChildBinding) {
            super(messagelistItemChildBinding.getRoot());
            mMessagelistItemChildBinding = messagelistItemChildBinding;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
