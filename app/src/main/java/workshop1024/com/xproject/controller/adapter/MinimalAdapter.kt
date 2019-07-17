package workshop1024.com.xproject.controller.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.home.NewsDetailActivity
import workshop1024.com.xproject.databinding.NewslistItemMinimalBinding
import workshop1024.com.xproject.model.news.News

/**
 * Created by chengxiang.peng on 2018/1/24.
 */
class MinimalAdapter(private val mContext: Context, private val mNewsList: List<News>) : RecyclerView.Adapter<MinimalAdapter.MinimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinimalViewHolder {
        val newslistItemMinimalBinding = DataBindingUtil.inflate<NewslistItemMinimalBinding>(LayoutInflater.from(parent.context),
                R.layout.newslist_item_minimal, parent, false)
        newslistItemMinimalBinding.minimalHandlers = MinimalHandlers()
        return MinimalViewHolder(newslistItemMinimalBinding)
    }

    override fun onBindViewHolder(holder: MinimalViewHolder, position: Int) {
        holder.mNewslistItemMinimalBinding.news = mNewsList[position]
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    inner class MinimalViewHolder(internal val mNewslistItemMinimalBinding: NewslistItemMinimalBinding) : RecyclerView.ViewHolder(mNewslistItemMinimalBinding.root)

    inner class MinimalHandlers {
        fun onClickItem(view: View, news: News) {
            NewsDetailActivity.startActivity(mContext, news.newId)
        }
    }
}
