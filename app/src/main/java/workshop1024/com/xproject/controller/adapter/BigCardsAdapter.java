package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.home.NewsDetailActivity;
import workshop1024.com.xproject.databinding.NewslistItemBigcardsBinding;
import workshop1024.com.xproject.model.news.News;

public class BigCardsAdapter extends RecyclerView.Adapter<BigCardsAdapter.BigCardsViewHolder> {
    private Context mContext;
    private List<? extends News> mNewsList;

    public BigCardsAdapter(Context context, List<? extends News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public BigCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewslistItemBigcardsBinding newslistItemBigcardsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.newslist_item_bigcards, parent, false);
        newslistItemBigcardsBinding.setBigCardsHandlers(new BigCardsHandlers());
        return new BigCardsViewHolder(newslistItemBigcardsBinding);
    }

    @Override
    public void onBindViewHolder(final BigCardsViewHolder holder, int position) {
        News showNews = mNewsList.get(position);
        holder.mNewslistItemBigcardsBinding.setNews(showNews);

        //FIXME 如何使用Databinding来处理这块逻辑？
        if (showNews.isIsReaded()) {
            //FIXME 有必要每次都创造对象吗？
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.0F);
            holder.mNewslistItemBigcardsBinding.compactImageviewBanner.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        } else {
            holder.mNewslistItemBigcardsBinding.compactImageviewBanner.setColorFilter(null);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class BigCardsViewHolder extends RecyclerView.ViewHolder {

        private final NewslistItemBigcardsBinding mNewslistItemBigcardsBinding;

        public BigCardsViewHolder(NewslistItemBigcardsBinding newslistItemBigcardsBinding) {
            super(newslistItemBigcardsBinding.getRoot());
            mNewslistItemBigcardsBinding = newslistItemBigcardsBinding;
        }
    }

    public class BigCardsHandlers {
        public void onClickItem(View view, News news) {
            NewsDetailActivity.startActivity(mContext, news.getNewId());
        }
    }
}