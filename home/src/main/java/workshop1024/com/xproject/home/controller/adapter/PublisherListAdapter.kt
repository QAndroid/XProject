package workshop1024.com.xproject.home.controller.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.databinding.PublishlistItemContentBinding
import workshop1024.com.xproject.home.model.publisher.Publisher

/**
 * 发布者列表选择适配器
 */
class PublisherListAdapter(private val mPublisherList: List<Publisher>, private val mOnPublisherListSelectListener: OnPublisherListSelectListener?)
    : RecyclerView.Adapter<PublisherListAdapter.PublisherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublisherViewHolder {
        val publishlistItemContentBinding = DataBindingUtil.inflate<PublishlistItemContentBinding>(LayoutInflater.from(parent.context),
                R.layout.publishlist_item_content, parent, false)
        publishlistItemContentBinding.publisherHandlers = PublisherHandlers()
        return PublisherViewHolder(publishlistItemContentBinding)
    }

    override fun onBindViewHolder(holder: PublisherViewHolder, position: Int) {
        holder.mPublishlistItemContentBinding.publisher = mPublisherList[position]
    }

    override fun getItemCount(): Int {
        return mPublisherList.size
    }

    interface OnPublisherListSelectListener {
        fun onPublisherListItemSelect(selectPublisher: Publisher, isSelected: Boolean)
    }

    inner class PublisherViewHolder(internal val mPublishlistItemContentBinding: PublishlistItemContentBinding) : RecyclerView.ViewHolder(mPublishlistItemContentBinding.root)

    inner class PublisherHandlers {
        fun onTouchSelected(v: View, event: MotionEvent, publisher: Publisher): Boolean {
            //CheckBox选中后，先不更改状态，待请求结果后在更改状态，参考 https://blog.csdn.net/qq_37822393/article/details/80195090
            if (event.action == MotionEvent.ACTION_UP) {
                Log.i("XProject", "public boolean onTouchSelected, publisher = " + publisher.typeId)
                mOnPublisherListSelectListener?.onPublisherListItemSelect(publisher, !(v as CheckBox).isChecked)
            }
            return true
        }
    }
}