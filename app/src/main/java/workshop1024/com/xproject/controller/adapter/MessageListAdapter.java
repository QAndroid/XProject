package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.message.Message;

/**
 * 消息列表是配资
 * TDDO 如何抽象出一个整体的HeaderView和FooterView、EmptyView的适配器
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {
    public static final int TYPEP_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;

    private List<Message> mMessageList;

    private View mHeaderView;
    private View mFooterView;

    public MessageListAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPEP_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPEP_HEADER) {
            return new MessageViewHolder(mHeaderView);
        }

        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MessageViewHolder(mFooterView);
        }

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelist_item_content,
                parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.mMessageTextView.setText(mMessageList.get(position - 1).getContent());
            return;
        } else if (getItemViewType(position) == TYPEP_HEADER) {
            return;
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mMessageList.size();
        } else if ((mHeaderView == null && mFooterView != null) || (mHeaderView != null &&
                mFooterView == null)) {
            return mMessageList.size() + 1;
        } else {
            return mMessageList.size() + 2;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView mMessageTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView || itemView == mFooterView) {
                return;
            }

            mMessageTextView = itemView.findViewById(R.id.messagelist_textview_content);
        }
    }
}
