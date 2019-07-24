package workshop1024.com.xproject.controller.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.FilterlistItemContentBinding
import workshop1024.com.xproject.model.filter.Filter

class FilterListAdapter(private val mFilterList: List<Filter>, private val mOnFilterListDeleteListener: OnFilterListDeleteListener?) : RecyclerView.Adapter<FilterListAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val filterlistItemContentBinding = DataBindingUtil.inflate<FilterlistItemContentBinding>(LayoutInflater.from(parent.context),
                R.layout.filterlist_item_content, parent, false)
        filterlistItemContentBinding.filterHandlers = FilterHandlers()
        return FilterViewHolder(filterlistItemContentBinding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.mFilterlistItemContentBinding.filter = mFilterList[position]
    }

    override fun getItemCount(): Int {
        return mFilterList.size
    }

    interface OnFilterListDeleteListener {
        fun filterListItemDelete(filter: Filter)
    }

    inner class FilterViewHolder(internal val mFilterlistItemContentBinding: FilterlistItemContentBinding) : RecyclerView.ViewHolder(mFilterlistItemContentBinding.root)

    inner class FilterHandlers {
        fun onClickDelete(view: View, filter: Filter) {
            mOnFilterListDeleteListener?.filterListItemDelete(filter)
        }
    }
}
