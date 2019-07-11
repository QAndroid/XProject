package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.home.NewsDetailActivity;
import workshop1024.com.xproject.databinding.NewslistItemMinimalBinding;
import workshop1024.com.xproject.model.news.News;

/**
 * Created by chengxiang.peng on 2018/1/24.
 */
public class MinimalAdapter extends RecyclerView.Adapter<MinimalAdapter.MinimalViewHolder> {
    private Context mContext;
    private List<? extends News> mNewsList;

    public MinimalAdapter(Context context, List<? extends News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public MinimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewslistItemMinimalBinding newslistItemMinimalBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.newslist_item_minimal, parent, false);
        newslistItemMinimalBinding.setMinimalHandlers(new MinimalHandlers());
        return new MinimalViewHolder(newslistItemMinimalBinding);
    }

    @Override
    public void onBindViewHolder(MinimalViewHolder holder, int position) {
        holder.mNewslistItemMinimalBinding.setNews(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class MinimalViewHolder extends RecyclerView.ViewHolder {
        private final NewslistItemMinimalBinding mNewslistItemMinimalBinding;

        private MinimalViewHolder(NewslistItemMinimalBinding newslistItemMinimalBinding) {
            super(newslistItemMinimalBinding.getRoot());
            mNewslistItemMinimalBinding = newslistItemMinimalBinding;
        }
    }

    public class MinimalHandlers {
        public void onClickItem(View view, News news) {
            NewsDetailActivity.startActivity(mContext, news.getNewId());
        }
    }
}
