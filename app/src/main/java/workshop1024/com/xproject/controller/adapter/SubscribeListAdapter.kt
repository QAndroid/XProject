package workshop1024.com.xproject.controller.adapter

import android.content.Context
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.HomesublistItemContentBinding
import workshop1024.com.xproject.model.subinfo.SubInfo

//FIXME 这几个Adapter是什么关系
class SubscribeListAdapter(private val mContext: Context, private val mSubInfoList: List<SubInfo>, private val mSubListItemListener: SubListItemListener
                           , private val mSubInfoListMenuListener: SubInfoListMenuListener) : HomeSubListAdapter(mSubInfoList, mSubListItemListener) {
    private var mSubscribeItemViewHolder: SubscribeItemViewHolder? = null
    private var mSelectedSubInfo: SubInfo? = null

    override fun getItemViewHolder(homesublistItemContentBinding: HomesublistItemContentBinding): RecyclerView.ViewHolder {
        homesublistItemContentBinding.subscribeHandlers = SubscribeHandlers()
        mSubscribeItemViewHolder = SubscribeItemViewHolder(homesublistItemContentBinding)
        return mSubscribeItemViewHolder!!
    }

    interface SubInfoListMenuListener {
        fun onRenameMenuClick(subscribe: SubInfo)

        fun onUnscribeMenuClick(subscribe: SubInfo)
    }

    inner class SubscribeItemViewHolder(homesublistItemContentBinding: HomesublistItemContentBinding) : ItemViewHolder(homesublistItemContentBinding),
            PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.homepage_recyclerviewmenu_rename -> {
                    mSubInfoListMenuListener.onRenameMenuClick(mSelectedSubInfo!!)
                    true
                }
                R.id.homepage_recyclerviewmenu_unsubscribe -> {
                    mSubInfoListMenuListener.onUnscribeMenuClick(mSelectedSubInfo!!)
                    true
                }
                else -> false
            }
        }
    }

    inner class SubscribeHandlers {
        fun onLongClickItem(view: View, subInfo: SubInfo): Boolean {
            mSelectedSubInfo = subInfo

            val popupMenu = PopupMenu(mContext, view)
            popupMenu.setOnMenuItemClickListener(mSubscribeItemViewHolder)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.homepage_recyclerview_menu, popupMenu.menu)
            popupMenu.show()

            return false
        }
    }
}