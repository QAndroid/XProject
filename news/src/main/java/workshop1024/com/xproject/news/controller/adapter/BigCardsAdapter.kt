package workshop1024.com.xproject.news.controller.adapter

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.news.R
import workshop1024.com.xproject.news.controller.activity.NewsDetailActivity
import workshop1024.com.xproject.news.databinding.NewslistItemBigcardsBinding
import workshop1024.com.xproject.news.model.news.News

class BigCardsAdapter(private val mContext: Context, private val mNewsList: List<News>) : RecyclerView.Adapter<BigCardsAdapter.BigCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigCardsViewHolder {
        val newslistItemBigcardsBinding = DataBindingUtil.inflate<NewslistItemBigcardsBinding>(LayoutInflater.from(parent.context),
                R.layout.newslist_item_bigcards, parent, false)
        newslistItemBigcardsBinding.bigCardsHandlers = BigCardsHandlers()
        return BigCardsViewHolder(newslistItemBigcardsBinding)
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    override fun onBindViewHolder(holder: BigCardsViewHolder, position: Int) {
        val showNews = mNewsList.get(position)
        holder.mNewslistItemBigcardsBinding.news = showNews

        //FIXME 如何使用Databinding来处理这块逻辑？
        if (showNews.mIsReaded) {
            //FIXME 有必要每次都创造对象吗？
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0.0f)
            holder.mNewslistItemBigcardsBinding.compactImageviewBanner.colorFilter = ColorMatrixColorFilter(colorMatrix)
        } else {
            holder.mNewslistItemBigcardsBinding.compactImageviewBanner.colorFilter = null
        }
    }

    inner class BigCardsViewHolder(internal val mNewslistItemBigcardsBinding: NewslistItemBigcardsBinding) :
            RecyclerView.ViewHolder(mNewslistItemBigcardsBinding.root)

    inner class BigCardsHandlers {
        fun onClickItem(view: View, news: News) {
            NewsDetailActivity.startActivity(mContext, news.mNewsId)
        }
    }
}