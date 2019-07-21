package workshop1024.com.xproject.controller.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.HomesublistItemContentBinding
import workshop1024.com.xproject.model.subinfo.SubInfo

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment中列表适配器，处理公共的视图渲染逻辑
 */
open class HomeSubListAdapter(private val mSubInfoList: List<SubInfo>, private val mSubListItemListener: SubListItemListener?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_EMPTY) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.homesublist_item_empty,
                    parent, false)
            viewHolder = EmptyViewHolder(view)
        } else if (viewType == VIEW_TYPE_ITEM) {
            val homesublistItemContentBinding = DataBindingUtil.inflate<HomesublistItemContentBinding>(LayoutInflater.from(parent.context),
                    R.layout.homesublist_item_content, parent, false)
            homesublistItemContentBinding.homeSubHandlers = HomeSubHandlers()
            viewHolder = getItemViewHolder(homesublistItemContentBinding)
        }

        return viewHolder!!
    }

    open fun getItemViewHolder(homesublistItemContentBinding: HomesublistItemContentBinding): RecyclerView.ViewHolder {
        return ItemViewHolder(homesublistItemContentBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == VIEW_TYPE_ITEM) {
            val itemViewHolder = holder as ItemViewHolder
            itemViewHolder.mHomesublistItemContentBinding.subInfo = mSubInfoList[position]
        }
    }

    override fun getItemCount(): Int {
        return if (mSubInfoList.isEmpty()) {
            1
        } else {
            mSubInfoList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mSubInfoList.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        //处理了，EmptyView只显示一行的问题
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == VIEW_TYPE_EMPTY) manager.spanCount else 1
                }
            }
        }
    }

    interface SubListItemListener {
        fun onSubListItemClick(subInfo: SubInfo)
    }

    open inner class ItemViewHolder(val mHomesublistItemContentBinding: HomesublistItemContentBinding) : RecyclerView.ViewHolder(mHomesublistItemContentBinding.root)

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class HomeSubHandlers {
        fun onClickItem(view: View, subInfo: SubInfo) {
            mSubListItemListener?.onSubListItemClick(subInfo)
        }
    }

    companion object {
        //列表项空视图类型
        internal const val VIEW_TYPE_EMPTY = 0
        //列表项内容视图类型
        internal const val VIEW_TYPE_ITEM = 1
    }
}
