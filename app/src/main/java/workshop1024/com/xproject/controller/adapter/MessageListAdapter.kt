package workshop1024.com.xproject.controller.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.MessagelistItemChildBinding
import workshop1024.com.xproject.databinding.MessagelistItemGroupBinding
import workshop1024.com.xproject.model.message.Message
import workshop1024.com.xproject.model.message.MessageGroup

/**
 * 消息列表是配资
 * TDDO 如何抽象出一个整体的HeaderView和FooterView、EmptyView的适配器
 */
class MessageListAdapter(private val mMessageGroupList: List<MessageGroup>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mHeaderView: View? = null
    private var mFooterView: View? = null

    val messageGroupItemCount: Int
        get() {
            var itemCount = 0
            for (messageGroup in mMessageGroupList) {
                itemCount++
                for (message in messageGroup.messageList) {
                    itemCount++
                }
            }
            return itemCount
        }

    fun setHeaderView(headerView: View) {
        mHeaderView = headerView
        notifyItemInserted(0)
    }

    fun setFooterView(footerView: View) {
        mFooterView = footerView
        notifyItemInserted(messageGroupItemCount - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val layoutInflater = LayoutInflater.from(parent.context)

        if (mHeaderView != null && viewType == TYPEP_HEADER) {
            viewHolder = HeaderViewHolder(mHeaderView!!)
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            viewHolder = FooterViewHolder(mFooterView!!)
        } else if (viewType == TYPE_GROUP) {
            val messagelistItemGroupBinding = DataBindingUtil.inflate<MessagelistItemGroupBinding>(layoutInflater, R.layout.messagelist_item_group,
                    parent, false)
            viewHolder = GroupViewHolder(messagelistItemGroupBinding)
        } else if (viewType == TYPE_CHILD) {
            val messagelistItemChildBinding = DataBindingUtil.inflate<MessagelistItemChildBinding>(layoutInflater, R.layout.messagelist_item_child,
                    parent, false)
            viewHolder = ChildViewHolder(messagelistItemChildBinding)
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GroupViewHolder) {
            holder.mMessagelistItemGroupBinding.messagelistTextviewGroupdata.text = getItemTextByPosition(position)
        } else if (holder is ChildViewHolder) {
            holder.mMessagelistItemChildBinding.messagelistTextviewChildcontent.text = getItemTextByPosition(position)
        }
    }

    private fun getItemTextByPosition(position: Int): String? {
        var position = position
        if (mHeaderView != null) {
            position = position - 1
        }

        var itemText: String? = null

        for (messageGroup in mMessageGroupList) {
            if (position == 0) {
                itemText = messageGroup.publishData
            }
            position--
            for (message in messageGroup.messageList) {
                if (position == 0) {
                    itemText = message.content
                }
                position--
            }
        }

        return itemText
    }

    override fun getItemViewType(position: Int): Int {
        val viewType: Int

        if (mHeaderView == null && mFooterView == null) {
            viewType = getMessageTypeByPosition(position)
        } else if (mHeaderView != null && mFooterView == null) {
            if (position == 0) {
                viewType = TYPEP_HEADER
            } else {
                viewType = getMessageTypeByPosition(position - 1)
            }
        } else if (mHeaderView == null && mFooterView != null) {
            if (position == itemCount - 1) {
                viewType = TYPE_FOOTER
            } else {
                viewType = getMessageTypeByPosition(position)
            }
        } else {
            if (position == 0) {
                viewType = TYPEP_HEADER
            } else if (position == itemCount - 1) {
                viewType = TYPE_FOOTER
            } else {
                viewType = getMessageTypeByPosition(position - 1)
            }
        }

        return viewType
    }

    private fun getMessageTypeByPosition(position: Int): Int {
        var position = position
        var viewType = 0

        for (messageGroup in mMessageGroupList) {
            if (position == 0) {
                viewType = TYPE_GROUP
                return viewType
            }
            position--
            for (message in messageGroup.messageList) {
                if (position == 0) {
                    viewType = TYPE_CHILD
                    return viewType
                }
                position--
            }
        }

        return viewType
    }

    override fun getItemCount(): Int {
        val itemCount: Int
        if (mHeaderView != null && mFooterView != null) {
            itemCount = messageGroupItemCount + 2
        } else if (mHeaderView == null && mFooterView == null) {
            itemCount = messageGroupItemCount
        } else {
            itemCount = messageGroupItemCount + 1
        }

        return itemCount
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class GroupViewHolder(internal val mMessagelistItemGroupBinding: MessagelistItemGroupBinding) : RecyclerView.ViewHolder(mMessagelistItemGroupBinding.root)

    inner class ChildViewHolder(internal val mMessagelistItemChildBinding: MessagelistItemChildBinding) : RecyclerView.ViewHolder(mMessagelistItemChildBinding.root)

    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val TYPEP_HEADER = 0
        const val TYPE_FOOTER = 1
        const val TYPE_GROUP = 2
        const val TYPE_CHILD = 3
    }
}
