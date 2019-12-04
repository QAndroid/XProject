package workshop1024.com.xproject.main.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.filter.data.Filter
import workshop1024.com.xproject.main.databinding.FilterlistItemContentBinding

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
