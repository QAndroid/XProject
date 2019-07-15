package workshop1024.com.xproject.controller.fragment.home

import android.support.design.widget.Snackbar

import java.util.ArrayList

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.controller.fragment.home.news.TagNewsFragment
import workshop1024.com.xproject.model.subinfo.SubInfo

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
        val newsListFragment = TagNewsFragment.newInstance(subInfo.infoId)
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainright_framelayout_fragments, newsListFragment)
                .addToBackStack("").commit()
        activity!!.title = subInfo.name
    }

    public override fun markAsRead() {
        val subInfoIds = ArrayList<String>()
        for ((infoId) in mSubInfoList) {
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
