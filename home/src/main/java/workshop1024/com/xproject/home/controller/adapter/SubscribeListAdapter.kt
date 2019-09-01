package workshop1024.com.xproject.home.controller.adapter

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.databinding.HomesublistItemContentBinding
import workshop1024.com.xproject.home.model.subinfo.SubInfo

//FIXME 这几个Adapter是什么关系
class SubscribeListAdapter(private val mContext: Context, mSubInfoList: List<SubInfo>, mSubListItemListener: SubListItemListener
                           , private val mSubInfoListMenuListener: SubInfoListMenuListener) : HomeSubListAdapter(mSubInfoList, mSubListItemListener) {
    private lateinit var mSubscribeItemViewHolder: SubscribeItemViewHolder
    private lateinit var mSelectedSubInfo: SubInfo

    override fun getItemViewHolder(homesublistItemContentBinding: HomesublistItemContentBinding): RecyclerView.ViewHolder {
        homesublistItemContentBinding.subscribeHandlers = SubscribeHandlers()
        mSubscribeItemViewHolder = SubscribeItemViewHolder(homesublistItemContentBinding)
        return mSubscribeItemViewHolder
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
                    mSubInfoListMenuListener.onRenameMenuClick(mSelectedSubInfo)
                    true
                }
                R.id.homepage_recyclerviewmenu_unsubscribe -> {
                    mSubInfoListMenuListener.onUnscribeMenuClick(mSelectedSubInfo)
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