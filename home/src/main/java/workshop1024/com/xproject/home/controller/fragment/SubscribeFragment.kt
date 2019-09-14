package workshop1024.com.xproject.home.controller.fragment

import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import java.util.ArrayList

import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.SubscribeListAdapter
import workshop1024.com.xproject.home.controller.fragment.news.SubscribeNewsFragment
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.base.view.dialog.InputStringDialog

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者列表
 */
class SubscribeFragment : HomeSubFragment(), SubscribeListAdapter.SubInfoListMenuListener, InputStringDialog.InputStringDialogListener {
    private var mSubscribeListAdapter: SubscribeListAdapter? = null

    private var mRenameSubInfo: SubInfo? = null

    override fun onRefresh() {
        refreshSubscribedList()
    }

    override fun loadData() {
        refreshSubscribedList()
    }


    private fun refreshSubscribedList() {
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch more subscribe ...", Snackbar.LENGTH_SHORT).show()
        mHomeSubFragmentHanlders.isRefreshing.set(true)
        mSubInfoRepository.getSubscribeSubInfos(this)
    }

    override fun onSubInfosLoaded(subInfoList: List<SubInfo>) {
        if (mIsForeground) {
            mSubInfoList = subInfoList

            mHomeSubFragmentHanlders.isRefreshing.set(false)
            mSubscribeListAdapter = SubscribeListAdapter(context!!, subInfoList,
                    this, this)
            mHomesubFragmentBinding.homesubRecyclerviewList.adapter = mSubscribeListAdapter
            Snackbar.make(mHomesubFragmentBinding.root, "Fetch " + subInfoList.size + " subscribes ...", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No subscribes refresh...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onInputStringDialogClick(dialog: DialogFragment, inputString: String) {
        mSubInfoRepository.reNameSubscribeSubInfoById(mRenameSubInfo!!.infoId, inputString)
        refreshSubscribedList()
    }


    override fun onRenameMenuClick(subscribe: SubInfo) {
        mRenameSubInfo = subscribe
        val renamePublisherDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive)
        renamePublisherDialog.mInputStringDialogListener = this
        renamePublisherDialog.show(fragmentManager!!, "renamePublisherDialog")
    }

    override fun onUnscribeMenuClick(subscribe: SubInfo) {
        mSubInfoRepository.unSubscribeSubInfoById(subscribe.infoId)
        refreshSubscribedList()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //由于替换的Fragment容器的id，在main组件中的不居中，无法直接访问
        //故使用跨组件通信方案—公共组件Service接口，实跨home组件和main组件之间的通信
        ServiceFactory.getInstance()?.mainService?.showSubscribeNewsFragment(activity, subInfo.infoId, subInfo.name)
    }

    public override fun markAsRead() {
        val subInfoIds = ArrayList<String>()
        for ((infoId) in mSubInfoList!!) {
            subInfoIds.add(infoId)
        }

        mSubInfoRepository.markedSubscribeSubInfoesAsRead(subInfoIds)

        refreshSubscribedList()
    }

    companion object {

        fun newInstance(): SubscribeFragment {
            return SubscribeFragment()
        }
    }
}
