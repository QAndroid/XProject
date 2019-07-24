package workshop1024.com.xproject.controller.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.home.NewsDetailActivity
import workshop1024.com.xproject.databinding.NewslistItemCompactBinding
import workshop1024.com.xproject.model.news.News

/**
 * Created by chengxiang.peng on 2018/1/24.
 */
class CompactAdapter(private val mContext: Context, private val mNewsList: List<News>) : RecyclerView.Adapter<CompactAdapter.CompactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompactViewHolder {
        val newslistItemCompactBinding = DataBindingUtil.inflate<NewslistItemCompactBinding>(LayoutInflater.from(parent.context),
                R.layout.newslist_item_compact, parent, false)
        newslistItemCompactBinding.compactHandlers = CompactHandlers()
        return CompactViewHolder(newslistItemCompactBinding)
    }

    override fun getItemCount(): Int {
       return mNewsList.size
    }

    override fun onBindViewHolder(holder: CompactViewHolder, position: Int) {
        holder.mNewslistItemCompactBinding.news = mNewsList[position]
    }

    inner class CompactViewHolder(internal val mNewslistItemCompactBinding: NewslistItemCompactBinding)
        : RecyclerView.ViewHolder(mNewslistItemCompactBinding.root)

    inner class CompactHandlers {
        fun onClickItem(view: View, news: News) {
            NewsDetailActivity.startActivity(mContext, news.newId!!)
        }
    }
}
