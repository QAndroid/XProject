package workshop1024.com.xproject.home.controller.fragment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.home.model.Injection
import workshop1024.com.xproject.home.model.subinfo.SubInfo

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
class TagFragment : HomeSubFragment() {

    override fun onPause() {
        super.onPause()
        mSubInfoRepository.refreshByType(SubInfo.TAG_TYPE, false, false)
    }

    override fun onRefresh() {
        mSubInfoRepository.refreshByType(SubInfo.TAG_TYPE, true, false)
        super.onRefresh()
    }

    override fun refreshSubInfoList() {
        super.refreshSubInfoList()
        mSubInfoRepository.getSubInfoesByType(SubInfo.TAG_TYPE, this)
    }

    override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
        newHomeSubListAdapter(subInfoList)
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch cacheorlocal " + subInfoList.size + " tags ...", Snackbar.LENGTH_SHORT).show()

        super.onCacheOrLocalSubInfosLoaded(subInfoList)
    }

    override fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>) {
        newHomeSubListAdapter(subInfoList)
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch remote " + subInfoList.size + " tags ...", Snackbar.LENGTH_SHORT).show()
        super.onRemoteSubInfosLoaded(subInfoList)
    }

    private fun newHomeSubListAdapter(subInfoList: List<SubInfo>) {
        mHomeSubListAdapter = HomeSubListAdapter(subInfoList, this)
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No tags refreshByType...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //替换的布局在main组件中
        ServiceFactory.getInstance()?.mainService?.showTagNewsFragment(activity, subInfo.mInfoId, subInfo.mName)
    }

    companion object {

        fun newInstance(): TagFragment {
            return TagFragment()
        }
    }
}
