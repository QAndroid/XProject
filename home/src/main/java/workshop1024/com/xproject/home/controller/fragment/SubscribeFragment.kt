package workshop1024.com.xproject.home.controller.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.SubscribeListAdapter
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.base.view.dialog.InputStringDialog
import workshop1024.com.xproject.home.model.Injection

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者列表
 */
class SubscribeFragment : HomeSubFragment(), SubscribeListAdapter.SubInfoListMenuListener, InputStringDialog.InputStringDialogListener {

    private var mRenameSubInfo: SubInfo? = null

    override fun onPause() {
        super.onPause()
        mSubInfoRepository.refreshByType(SubInfo.SUBSCRIBE_TYPE, false, false)
    }

    override fun onRefresh() {
        mSubInfoRepository.refreshByType(SubInfo.SUBSCRIBE_TYPE, true, true)
        super.onRefresh()
    }

    override fun refreshSubInfoList() {
        super.refreshSubInfoList()
        mSubInfoRepository.getSubInfoesByType(SubInfo.SUBSCRIBE_TYPE, this)
    }

    override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
        newSubscribeListAdapter(subInfoList)
        //FIXME 当从缓存中直接获取数据的时候，为什么XSnackbar.show()不可见
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch cacheorlocal " + subInfoList.size + " subscribes ...", Snackbar.LENGTH_SHORT).show()
        super.onCacheOrLocalSubInfosLoaded(subInfoList)
    }

    private fun newSubscribeListAdapter(subInfoList: List<SubInfo>) {
        mHomeSubListAdapter = SubscribeListAdapter(context!!, subInfoList,
                this, this)
    }

    override fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>) {
        newSubscribeListAdapter(subInfoList)
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch remote " + subInfoList.size + " subscribes ...", Snackbar.LENGTH_SHORT).show()
        super.onRemoteSubInfosLoaded(subInfoList)
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No subscribes refreshByType...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onInputStringDialogClick(dialog: DialogFragment, inputString: String) {
        mSubInfoRepository.reNameSubInfoById(mRenameSubInfo!!.mInfoId, inputString)
        //TODO 同步局部更新来刷新列表，而不是请求数据刷新！
        refreshSubInfoList()
    }


    override fun onRenameMenuClick(subscribe: SubInfo) {
        mRenameSubInfo = subscribe
        val renamePublisherDialog = InputStringDialog.newInstance(R.string.rename_dialog_title, R.string
                .rename_dialog_positive)
        renamePublisherDialog.mInputStringDialogListener = this
        renamePublisherDialog.show(fragmentManager!!, "renamePublisherDialog")
    }

    override fun onUnscribeMenuClick(subscribe: SubInfo) {
        mSubInfoRepository.unSubInfoById(subscribe.mInfoId)
        //TODO 同步局部更新来刷新列表，而不是请求数据刷新！
        refreshSubInfoList()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //由于替换的Fragment容器的id，在main组件中的不居中，无法直接访问
        //故使用跨组件通信方案—公共组件Service接口，实跨home组件和main组件之间的通信
        ServiceFactory.getInstance()?.mainService?.showSubscribeNewsFragment(activity, subInfo.mInfoId, subInfo.mName)
    }

    companion object {
        fun newInstance(): SubscribeFragment {
            return SubscribeFragment()
        }
    }
}
