package workshop1024.com.xproject.controller.adapter;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by chengxiang.peng on 2018/1/24.
 */
public class CompactAdapter extends RecyclerView.Adapter<CompactAdapter.NewsViewHolder> {
    private Context mContext;
    private List<News> mNewsList;

    public CompactAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslist_item_compact, parent,
                false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.mTitleTextView.setText(mNewsList.get(position).getTitle());
        holder.mAuthorTextView.setText(mNewsList.get(position).getPublisher());
        holder.mTimeTextView.setText(mNewsList.get(position).getPubDate());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mBannerImageView;
        public final TextView mTitleTextView;
        public final TextView mAuthorTextView;
        public final TextView mTimeTextView;

        public NewsViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mBannerImageView = view.findViewById(R.id.compact_imageview_banner);
            mTitleTextView = view.findViewById(R.id.compact_textview_title);
            mAuthorTextView = view.findViewById(R.id.compact_textview_author);
            mTimeTextView = view.findViewById(R.id.compact_textview_time);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            mContext.startActivity(intent);
        }
    }
}
