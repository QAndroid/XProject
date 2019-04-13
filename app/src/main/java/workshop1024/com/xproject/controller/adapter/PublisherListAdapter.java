package workshop1024.com.xproject.controller.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.PublishlistItemContentBinding;
import workshop1024.com.xproject.model.publisher.Publisher;

/**
 * 发布者列表选择适配器
 */
public class PublisherListAdapter extends RecyclerView.Adapter<PublisherListAdapter.PublisherViewHolder> {
    private List<Publisher> mPublisherList;
    private OnPublisherListSelectListener mOnPublisherListSelectListener;

    public PublisherListAdapter(List<Publisher> publisherList, OnPublisherListSelectListener
            onPublisherListSelectListener) {
        mPublisherList = publisherList;
        mOnPublisherListSelectListener = onPublisherListSelectListener;
    }

    @Override
    public PublisherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PublishlistItemContentBinding publishlistItemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.publishlist_item_content, parent, false);
        publishlistItemContentBinding.setPublisherHandlers(new PublisherHandlers());
        return new PublisherViewHolder(publishlistItemContentBinding);
    }

    @Override
    public void onBindViewHolder(final PublisherViewHolder holder, final int position) {
        holder.mPublishlistItemContentBinding.setPublisher(mPublisherList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPublisherList.size();
    }

    public interface OnPublisherListSelectListener {
        void onPublisherListItemSelect(Publisher selectPublisher, boolean isSelected);
    }

    public class PublisherViewHolder extends RecyclerView.ViewHolder {
        private final PublishlistItemContentBinding mPublishlistItemContentBinding;

        public PublisherViewHolder(PublishlistItemContentBinding publishlistItemContentBinding) {
            super(publishlistItemContentBinding.getRoot());
            mPublishlistItemContentBinding = publishlistItemContentBinding;
        }
    }

    public class PublisherHandlers {
        public boolean onTouchSelected(View v, MotionEvent event, Publisher publisher) {
            //CheckBox选中后，先不更改状态，待请求结果后在更改状态，参考 https://blog.csdn.net/qq_37822393/article/details/80195090
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.i("XProject", "public boolean onTouchSelected, publisher = " + publisher.getTypeId());
                if (mOnPublisherListSelectListener != null) {
                    mOnPublisherListSelectListener.onPublisherListItemSelect(publisher, !((CheckBox) v).isChecked());
                }
            }
            return true;
        }
    }
}