package workshop1024.com.xproject.home.controller.fragment

import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.service.ServiceFactory

import java.util.ArrayList

import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.home.controller.fragment.news.TagNewsFragment
import workshop1024.com.xproject.home.model.subinfo.SubInfo

/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-SubscribeFragment，显示已订阅者发布者新闻的Tag分列表
 */
class TagFragment : HomeSubFragment() {

    override fun onRefresh() {
        refreshTagList()
    }

    override fun loadData() {
        refreshTagList()
    }

    private fun refreshTagList() {
        Snackbar.make(mHomesubFragmentBinding.root, "Fetch more tag ...", Snackbar.LENGTH_SHORT).show()
        mHomeSubFragmentHanlders.isRefreshing.set(true)
        mSubInfoRepository.getTagSubInfos(this)
    }


    override fun onSubInfosLoaded(subInfoList: List<SubInfo>) {
        if (mIsForeground) {
            mSubInfoList = subInfoList
            mHomeSubFragmentHanlders.isRefreshing.set(false)
            val homeSubListAdapter = HomeSubListAdapter(subInfoList, this)
            mHomesubFragmentBinding.homesubRecyclerviewList.adapter = homeSubListAdapter
            Snackbar.make(mHomesubFragmentBinding.root, "Fetch " + subInfoList.size + " tags ...", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDataNotAvailable() {
        Snackbar.make(mHomesubFragmentBinding.root, "No tags refresh...", Snackbar.LENGTH_SHORT).show()
    }

    override fun onSubListItemClick(subInfo: SubInfo) {
        //替换的布局在main组件中
        ServiceFactory.getInstance()?.mainService?.showTagNewsFragment(activity, subInfo.infoId, subInfo.name)
    }

    public override fun markAsRead() {
        val subInfoIds = ArrayList<String>()
        for ((infoId) in mSubInfoList!!) {
            subInfoIds.add(infoId)
        }
        mSubInfoRepository.markedTagSubInfoesAsRead(subInfoIds)

        refreshTagList()
    }

    companion object {

        fun newInstance(): TagFragment {
            return TagFragment()
        }
    }
}
