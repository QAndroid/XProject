package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.tag.Tag;
import workshop1024.com.xproject.view.GrassView;

public class TagListAdapter extends RecyclerView.Adapter {
    //列表项空视图类型
    private static final int VIEW_TYPE_EMPTY = 0;
    //列表项内容视图类型
    private static final int VIEW_TYPE_ITEM = 1;

    private Context mContext;
    private List<Tag> mTagList;
    private TagListItemListener mTagListItemListener;

    public TagListAdapter(Context context, List<Tag> tagList, TagListItemListener tagListItemListener) {
        mContext = context;
        mTagList = tagList;
        mTagListItemListener = tagListItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribelist_item_empty, parent,
                    false);
            viewHolder = new TagListAdapter.EmptyViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglist_item_content, parent,
                    false);
            viewHolder = new TagListAdapter.TagViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            SubscribeListAdapter.EmptyViewHolder emptyViewHolder = (SubscribeListAdapter.EmptyViewHolder)
                    holder;
        } else if (viewType == VIEW_TYPE_ITEM) {
            TagListAdapter.TagViewHolder tagViewHolder = (TagViewHolder) holder;
            Tag tag = mTagList.get(position);
            tagViewHolder.mTag = tag;
            tagViewHolder.mNameTextView.setText(tag.getName());
            tagViewHolder.mNewsCountTextView.setText(tag.getUnreadCount());
        }
    }

    @Override
    public int getItemCount() {
        if (mTagList.size() == 0) {
            return 1;
        } else {
            return mTagList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTagList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    /**
     * 订阅的发布这新闻Tag列表表项接口
     */
    public interface TagListItemListener {
        /**
         * 订阅的发布者列表项点击监听
         *
         * @param subscribe 点击的发布者名称
         */
        void onTagListItemClick(Tag subscribe);
    }

    public class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mNewsCountTextView;
        private Tag mTag;

        public TagViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mNameTextView = itemView.findViewById(R.id.tagitem_textview_name);
            mNewsCountTextView = itemView.findViewById(R.id.tagitem_textview_newscount);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
            if (null != mTagListItemListener) {
                mTagListItemListener.onTagListItemClick(mTag);
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
