package workshop1024.com.xproject.home.controller.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import workshop1024.com.xproject.base.controller.fragment.LazyFragment
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter
import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter.SubListItemListener
import workshop1024.com.xproject.home.databinding.HomesubFragmentBinding
import workshop1024.com.xproject.home.model.Injection
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import java.util.ArrayList

//参考：https://developer.android.com/topic/libraries/data-binding/binding-adapters
//@BindingMethods(value = [BindingMethod(type = RecyclerView::class, attribute = "itemDecoration", method = "addItemDecoration")])
/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
abstract class HomeSubFragment : LazyFragment(), SwipeRefreshLayout.OnRefreshListener, SubListItemListener, SubInfoDataSource.LoadSubInfoCallback {
    internal lateinit var mHomeSubListAdapter: HomeSubListAdapter

    internal lateinit var mSubInfoRepository: SubInfoDataSource
    internal var mSubInfoList: List<SubInfo>? = null

    internal lateinit var mHomesubFragmentBinding: HomesubFragmentBinding
    internal lateinit var mHomeSubFragmentHanlders: HomeSubFragmentHanlders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("XProject", "HomeSubFragment onCreate")
        mSubInfoRepository = Injection.provideSubInfoRepository(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("XProject", "HomeSubFragment onCreateView")
        mHomesubFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.homesub_fragment, container, false)
        mHomesubFragmentBinding.gridLayoutManager = GridLayoutManager(context, 2)
//        mHomesubFragmentBinding?.recyclerViewItemDecoration = RecyclerViewItemDecoration(6)
        mHomesubFragmentBinding.onRefreshListener = this

        mHomeSubFragmentHanlders = HomeSubFragmentHanlders()
        mHomesubFragmentBinding.homeSubFragmentHanlders = mHomeSubFragmentHanlders

        return mHomesubFragmentBinding.root
    }

    override fun onRefresh() {
        refreshSubInfoList()
    }

    override fun loadData() {
        refreshSubInfoList()
    }

    open fun refreshSubInfoList() {
        mHomeSubFragmentHanlders.isRefreshing.set(true)
    }

    override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
        setAdapterAndSubInfoList(subInfoList)
    }

    override fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>) {
        setAdapterAndSubInfoList(subInfoList)
        mHomeSubFragmentHanlders.isRefreshing.set(false)
    }

    private fun setAdapterAndSubInfoList(subInfoList: List<SubInfo>) {
        mHomesubFragmentBinding.homesubRecyclerviewList.adapter = mHomeSubListAdapter
        mSubInfoList = subInfoList
    }

    public fun markAsRead() {
        val subInfoIds = ArrayList<String>()
        for ((infoId) in mSubInfoList!!) {
            subInfoIds.add(infoId)
        }

        mSubInfoRepository.markedSubInfoesAsRead(subInfoIds)

        refreshSubInfoList()
    }


    inner class HomeSubFragmentHanlders {
        val isRefreshing = ObservableBoolean()
    }
}
