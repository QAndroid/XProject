package workshop1024.com.xproject.controller.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.home.NewsDetailActivity;
import workshop1024.com.xproject.databinding.FilterlistItemContentBinding;
import workshop1024.com.xproject.model.filter.Filter;
import workshop1024.com.xproject.model.news.News;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterViewHolder> {
    private List<Filter> mFilterList;
    private OnFilterListDeleteListener mOnFilterListDeleteListener;

    public FilterListAdapter(List<Filter> filterList, OnFilterListDeleteListener onFilterListDeleteListener) {
        mFilterList = filterList;
        mOnFilterListDeleteListener = onFilterListDeleteListener;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FilterlistItemContentBinding filterlistItemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.filterlist_item_content, parent, false);
        filterlistItemContentBinding.setFilterHandlers(new FilterHandlers());
        return new FilterViewHolder(filterlistItemContentBinding);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.mFilterlistItemContentBinding.setFilter(mFilterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public interface OnFilterListDeleteListener {
        void filterListItemDelete(Filter filter);
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        private final FilterlistItemContentBinding mFilterlistItemContentBinding;

        public FilterViewHolder(FilterlistItemContentBinding filterlistItemContentBinding) {
            super(filterlistItemContentBinding.getRoot());
            mFilterlistItemContentBinding = filterlistItemContentBinding;
        }
    }

    public class FilterHandlers {
        public void onClickDelete(View view, Filter filter) {
            if (mOnFilterListDeleteListener != null) {
                mOnFilterListDeleteListener.filterListItemDelete(filter);
            }
        }
    }
}
