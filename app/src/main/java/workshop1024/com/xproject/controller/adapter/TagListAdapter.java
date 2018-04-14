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

/**
 * Created by chengxiang.peng on 2018/3/11.
 */
public class TagListAdapter extends RecyclerView.Adapter {
    //列表项空视图类型
    private static final int VIEW_TYPE_EMPTY = 0;
    //列表项内容视图类型
    private static final int VIEW_TYPE_ITEM = 1;

    private Context mContext;
    private List<Tag> mTagList;

    public TagListAdapter(Context context, List<Tag> tagList, TopicListAdapter.TagListItemListener mTagListItemListener) {
        mContext = context;
        mTagList = tagList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribelist_item_empty, parent,
                    false);
            viewHolder = new EmptyViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglist_item_content, parent,
                    false);
            viewHolder = new TagViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
        } else if (viewType == VIEW_TYPE_ITEM) {
            TagViewHolder tagViewHolder = (TagViewHolder) holder;
            Tag tag = mTagList.get(position);
            tagViewHolder.mTag = tag;
            tagViewHolder.mNameTextView.setText(tag.getName());
            tagViewHolder.mUnReadTextView.setText(tag.getUnReadedCount());
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

    public class TagViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mUnReadTextView;

        private Tag mTag;

        public TagViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.tagitem_textview_name);
            mUnReadTextView = itemView.findViewById(R.id.tagitem_textview_newscount);
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
