package workshop1024.com.xproject.home.controller.fragment

import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import java.util.ArrayList

import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.home.controller.fragment.news.FilterNewsFragment
import workshop1024.com.xproject.home.model.subinfo.SubInfo

/**
 * 抽屉导航HomeFragment的子Frament HomeFragment的ViewPager的子Fragment，按照过滤关键字分类展示
 */
class FilterFragment : HomeSubFragment() {

    override fun onRefresh() {
        refreshFilterList()
    }

    override fun loadData() {
        refreshFilterList()
    }

    /**
     * 刷新过滤器列表
     */
    private fun refreshFilterList() {
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch more filters ...", Snackbar.LENGTH_SHORT).show()
        mHomeSubFragmentHanlders.isRefreshing.set(true)
        mSubInfoRepository.getFilterSubInfos(this)
    }

    override fun onSubInfosLoaded(subInfoList: List<SubInfo>) {
        if (mIsForeground) {
            mSubInfoList = subInfoList
            mHomeSubFragmentHanlders.isRefreshing.set(false)
            val homeSubListAdapter = HomeSubListAdapter(subInfoList, this)
            mHomesubFragmentBinding.homesubRecyclerviewList.adapter = homeSubListAdapter
            Snackbar.make(mHomesubFragmentBinding.root, "Fetch " + subInfoList.size + " filters ...", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No filters refresh...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //替换的mainright_framelayout_fragments在main组件
        ServiceFactory.getInstance()?.mainService?.showFilterNewsFragment(activity, subInfo.infoId, subInfo.name)
    }

    public override fun markAsRead() {
        val subInfoIds = ArrayList<String>()
        for ((infoId) in mSubInfoList!!) {
            subInfoIds.add(infoId)
        }

        mSubInfoRepository.markeFilterSubInfoesAsRead(subInfoIds)

        refreshFilterList()
    }

    companion object {

        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }
}
