package workshop1024.com.xproject.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.filter.Filter;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterViewHolder> {
    private List<Filter> mFilterList;
    private OnFilterListDeleteListener mOnFilterListDeleteListener;

    public FilterListAdapter(List<Filter> filterList, OnFilterListDeleteListener onFilterListDeleteListener) {
        mFilterList = filterList;
        mOnFilterListDeleteListener = onFilterListDeleteListener;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filterlist_item_content, parent,
                false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        Filter filter = mFilterList.get(position);

        holder.mNameTextView.setText(filter.getFilterName());
        holder.mFilter = filter;
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public interface OnFilterListDeleteListener {
        void filterListItemDelete(Filter filter);
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private ImageButton mDeleteButton;
        private Filter mFilter;

        public FilterViewHolder(View view) {
            super(view);
            mNameTextView = view.findViewById(R.id.filterlist_textview_name);
            mDeleteButton = view.findViewById(R.id.filterlist_imagebutton_delete);

            mDeleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnFilterListDeleteListener != null) {
                mOnFilterListDeleteListener.filterListItemDelete(mFilter);
            }
        }
    }
}
