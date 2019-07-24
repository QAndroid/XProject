package workshop1024.com.xproject.controller.fragment.home

import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.controller.fragment.home.news.FilterNewsFragment
import workshop1024.com.xproject.model.subinfo.SubInfo

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
        val filterNewsFragment = FilterNewsFragment.newInstance(subInfo.infoId)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainright_framelayout_fragments, filterNewsFragment)
                ?.addToBackStack("")?.commit()
        activity?.title = subInfo.name
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
