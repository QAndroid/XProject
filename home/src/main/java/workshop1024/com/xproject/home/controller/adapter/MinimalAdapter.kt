package workshop1024.com.xproject.home.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.activity.NewsDetailActivity
import workshop1024.com.xproject.home.databinding.NewslistItemMinimalBinding
import workshop1024.com.xproject.home.model.news.News


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
            NewsDetailActivity.startActivity(mContext, news.newId!!)
        }
    }
}
