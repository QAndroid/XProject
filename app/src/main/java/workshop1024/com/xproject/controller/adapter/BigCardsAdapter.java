package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.NewsDetailActivity;
import workshop1024.com.xproject.model.news.News;

public class BigCardsAdapter extends RecyclerView.Adapter<BigCardsAdapter.NewsViewHolder> {
    private Context mContext;
    private List<News> mNewsList;

    public BigCardsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslist_item_bigcards, parent,
                false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        News showNews = mNewsList.get(position);
        holder.mNews = showNews;
        holder.mTitleTextView.setText(showNews.getTitle());
        holder.mAuthorTextView.setText(showNews.getPublisher());
        holder.mTimeTextView.setText(showNews.getPubDate());

        if (showNews.isIsReaded()) {
            //FIXME 有必要每次都创造对象吗？
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.0F);
            holder.mBannerImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        } else {
            holder.mBannerImageView.setColorFilter(null);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mBannerImageView;
        public TextView mTitleTextView;
        public TextView mAuthorTextView;
        public TextView mTimeTextView;

        public News mNews;

        public NewsViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mBannerImageView = view.findViewById(R.id.compact_imageview_banner);
            mTitleTextView = view.findViewById(R.id.bigcards_textview_title);
            mAuthorTextView = view.findViewById(R.id.compact_textview_author);
            mTimeTextView = view.findViewById(R.id.bigcards_textview_time);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("newsId", mNews.getNewId());
            mContext.startActivity(intent);
        }
    }
}