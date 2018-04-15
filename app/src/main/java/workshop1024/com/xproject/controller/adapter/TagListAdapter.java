package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.sub.tag.Tag;

public class TagListAdapter extends HomeSubListAdapter {
    private List<Tag> mTagList;

    public TagListAdapter(List<Tag> tagList , SubListItemListener subListItemListener) {
        super(subListItemListener);
        mTagList = tagList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesublist_item_content,
                    parent, false);
            viewHolder = new TagItemViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            TagItemViewHolder tagItemViewHolder = (TagItemViewHolder) holder;
            Tag tag = mTagList.get(position);
            tagItemViewHolder.mSubInfo = tag;
            tagItemViewHolder.mNameTextView.setText(tag.getName());
            tagItemViewHolder.mNewsCountTextView.setText(tag.getUnreadCount());
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

    public class TagItemViewHolder extends ItemViewHolder{
        public TagItemViewHolder(View view) {
            super(view);
        }
    }
}
