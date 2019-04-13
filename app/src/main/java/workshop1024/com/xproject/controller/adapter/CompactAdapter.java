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
import workshop1024.com.xproject.databinding.NewslistItemCompactBinding;
import workshop1024.com.xproject.model.news.News;

/**
 * Created by chengxiang.peng on 2018/1/24.
 */
public class CompactAdapter extends RecyclerView.Adapter<CompactAdapter.CompactViewHolder> {
    private Context mContext;
    private List<News> mNewsList;

    public CompactAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public CompactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewslistItemCompactBinding newslistItemCompactBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.newslist_item_compact, parent, false);
        newslistItemCompactBinding.setCompactHandlers(new CompactHandlers());
        return new CompactViewHolder(newslistItemCompactBinding);
    }

    @Override
    public void onBindViewHolder(CompactViewHolder holder, int position) {
        holder.mNewslistItemCompactBinding.setNews(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class CompactViewHolder extends RecyclerView.ViewHolder {
        private final NewslistItemCompactBinding mNewslistItemCompactBinding;

        private CompactViewHolder(NewslistItemCompactBinding newslistItemCompactBinding) {
            super(newslistItemCompactBinding.getRoot());
            mNewslistItemCompactBinding = newslistItemCompactBinding;
        }
    }

    public class CompactHandlers {
        public void onClickItem(View view, News news) {
            NewsDetailActivity.startActivity(mContext, news.getNewId());
        }
    }
}
