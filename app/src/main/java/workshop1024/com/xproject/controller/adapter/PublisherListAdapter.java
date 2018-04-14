package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publishlist_item_content, parent,
                false);
        return new PublisherViewHolder(view, mOnPublisherListSelectListener);
    }

    @Override
    public void onBindViewHolder(final PublisherViewHolder holder, int position) {
        Publisher publisher = mPublisherList.get(position);

        holder.mNameTextView.setText(publisher.getName());
        holder.mSubscribeNumTextView.setText(publisher.getSubscribeNum());
        holder.mSelectedCheckBox.setChecked(publisher.isIsSubscribed());
        holder.mPublisher = publisher;
    }

    @Override
    public int getItemCount() {
        return mPublisherList.size();
    }

    public interface OnPublisherListSelectListener {
        void publisherListItemSelect(Publisher selectPublisher, boolean isSelected);
    }

    public class PublisherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mIconImageView;
        public final TextView mNameTextView;
        private final TextView mSubscribeNumTextView;
        private final CheckBox mSelectedCheckBox;
        private Publisher mPublisher;
        private OnPublisherListSelectListener mOnPublisherListSelectListener;

        public PublisherViewHolder(View view, OnPublisherListSelectListener onPublisherListSelectListener) {
            super(view);
            mOnPublisherListSelectListener = onPublisherListSelectListener;

            mIconImageView = view.findViewById(R.id.publisherlist_imageview_icon);
            mNameTextView = view.findViewById(R.id.publisherlist_textview_name);
            mSubscribeNumTextView = view.findViewById(R.id.publisherlist_textview_subscribenum);
            mSelectedCheckBox = view.findViewById(R.id.publisherlist_checkbox_selected);

            mSelectedCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnPublisherListSelectListener.publisherListItemSelect(mPublisher, mSelectedCheckBox.isChecked());
        }
    }
}
