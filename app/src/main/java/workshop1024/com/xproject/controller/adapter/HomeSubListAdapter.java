package workshop1024.com.xproject.controller.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.HomesublistItemContentBinding;
import workshop1024.com.xproject.model.subinfo.SubInfo;

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment中列表适配器，处理公共的视图渲染逻辑
 */
public class HomeSubListAdapter extends RecyclerView.Adapter {
    //列表项空视图类型
    static final int VIEW_TYPE_EMPTY = 0;
    //列表项内容视图类型
    static final int VIEW_TYPE_ITEM = 1;

    private List<SubInfo> mSubInfoList;
    private SubListItemListener mSubListItemListener;

    public HomeSubListAdapter(List<SubInfo> subInfoList, SubListItemListener subListItemListener) {
        mSubInfoList = subInfoList;
        mSubListItemListener = subListItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesublist_item_empty,
                    parent, false);
            viewHolder = new EmptyViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            HomesublistItemContentBinding homesublistItemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.homesublist_item_content, parent, false);
            viewHolder = getItemViewHolder(homesublistItemContentBinding);
        }

        return viewHolder;
    }

    public RecyclerView.ViewHolder getItemViewHolder(HomesublistItemContentBinding homesublistItemContentBinding) {
        homesublistItemContentBinding.setHomeSubHandlers(new HomeSubHandlers());
        return new ItemViewHolder(homesublistItemContentBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mHomesublistItemContentBinding.setSubInfo(mSubInfoList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mSubInfoList.size() == 0) {
            return 1;
        } else {
            return mSubInfoList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSubInfoList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //处理了，EmptyView只显示一行的问题
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_TYPE_EMPTY ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public interface SubListItemListener {
        void onSubListItemClick(SubInfo subInfo);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected final HomesublistItemContentBinding mHomesublistItemContentBinding;

        public ItemViewHolder(HomesublistItemContentBinding homesublistItemContentBinding) {
            super(homesublistItemContentBinding.getRoot());
            mHomesublistItemContentBinding = homesublistItemContentBinding;
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HomeSubHandlers {
        public void onClickItem(View view, SubInfo subInfo) {
            if (mSubListItemListener != null) {
                mSubListItemListener.onSubListItemClick(subInfo);
            }
        }
    }
}
