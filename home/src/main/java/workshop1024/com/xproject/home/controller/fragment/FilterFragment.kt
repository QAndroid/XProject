package workshop1024.com.xproject.home.controller.fragment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.home.model.Injection
import workshop1024.com.xproject.home.model.subinfo.SubInfo

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment，按照过滤关键字分类展示
 */
class FilterFragment : HomeSubFragment() {
    override fun onPause() {
        super.onPause()
        mSubInfoRepository.refreshByType(SubInfo.FILTER_TYPE, false, false)
    }

    override fun onRefresh() {
        mSubInfoRepository.refreshByType(SubInfo.FILTER_TYPE,true,false)
        super.onRefresh()
    }

    override fun refreshSubInfoList() {
        super.refreshSubInfoList()
        mSubInfoRepository.getSubInfoesByType(SubInfo.FILTER_TYPE, this)
    }

    override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
        newHomeSubListAdapter(subInfoList)
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch cacheorlocal " + subInfoList.size + " filters ...", Snackbar.LENGTH_SHORT).show()

        super.onCacheOrLocalSubInfosLoaded(subInfoList)
    }

    override fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>) {
        newHomeSubListAdapter(subInfoList)
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch remote " + subInfoList.size + " filters ...", Snackbar.LENGTH_SHORT).show()

        super.onRemoteSubInfosLoaded(subInfoList)
    }

    private fun newHomeSubListAdapter(subInfoList: List<SubInfo>) {
        mHomeSubListAdapter = HomeSubListAdapter(subInfoList, this)
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No filters refreshByType...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //替换的mainright_framelayout_fragments在main组件
        ServiceFactory.getInstance()?.mainService?.showFilterNewsFragment(activity, subInfo.mInfoId, subInfo.mName)
    }

    companion object {

        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }
}
