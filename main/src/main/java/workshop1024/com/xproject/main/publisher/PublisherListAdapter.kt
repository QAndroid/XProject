package workshop1024.com.xproject.main.publisher

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.databinding.PublishlistItemContentBinding
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.viewmodel.PublisherViewModel

/**
 * 发布者列表选择适配器
 */
class PublisherListAdapter(private var mPublisherList: List<Publisher>, private val mPublisherViewModel: PublisherViewModel)
    : RecyclerView.Adapter<PublisherListAdapter.PublisherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublisherViewHolder {
        val publishlistItemContentBinding = DataBindingUtil.inflate<PublishlistItemContentBinding>(LayoutInflater.from(parent.context),
                R.layout.publishlist_item_content, parent, false)
        publishlistItemContentBinding.publisherHandlers = PublisherHandlers()
        return PublisherViewHolder(publishlistItemContentBinding)
    }

    override fun onBindViewHolder(holder: PublisherViewHolder, position: Int) {
        holder.mPublishlistItemContentBinding.publisher = mPublisherList[position]

        //判断发布列表是否滑动到底部
        if (position == mPublisherList.size / 2) {
            holder.mIsInTheMiddle = true
        } else {
            holder.mIsInTheMiddle = false
        }
    }

    override fun getItemCount(): Int {
        return mPublisherList.size
    }

    fun refreshPublisherList(publisherList: List<Publisher>) {
        mPublisherList = publisherList
        notifyDataSetChanged()
    }

    interface OnPublisherListSelectListener {
        fun onPublisherListItemSelect(selectPublisher: Publisher, isSelected: Boolean)
    }

    inner class PublisherViewHolder(internal val mPublishlistItemContentBinding: PublishlistItemContentBinding) : RecyclerView.ViewHolder(mPublishlistItemContentBinding.root) {
        //是否在列表的尾部
        internal var mIsInTheMiddle = false
    }

    inner class PublisherHandlers {
        fun onTouchSelected(v: View, event: MotionEvent, publisher: Publisher): Boolean {
            //CheckBox选中后，先不更改状态，待请求结果后在更改状态，参考 https://blog.csdn.net/qq_37822393/article/details/80195090
            if (event.action == MotionEvent.ACTION_UP) {
                Log.i("XProject", "public boolean onTouchSelected, publisher = " + publisher.mType)
                mPublisherViewModel.onPublisherListItemSelect(publisher, !(v as CheckBox).isChecked)
            }
            return true
        }
    }
}