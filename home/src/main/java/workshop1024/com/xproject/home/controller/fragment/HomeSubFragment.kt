package workshop1024.com.xproject.home.controller.fragment

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import workshop1024.com.xproject.base.controller.fragment.LazyFragment
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.HomeSubListAdapter.SubListItemListener
import workshop1024.com.xproject.home.databinding.HomesubFragmentBinding
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.model.Injection

//参考：https://developer.android.com/topic/libraries/data-binding/binding-adapters
//@BindingMethods(value = [BindingMethod(type = RecyclerView::class, attribute = "itemDecoration", method = "addItemDecoration")])
/**
 * 抽屉导航HomeFragment的子Frament-HomeFragment的ViewPager的子Fragment-HomeSubFragment，处理布局和视图相关公共逻辑
 */
abstract class HomeSubFragment : LazyFragment(), SwipeRefreshLayout.OnRefreshListener, SubListItemListener, SubInfoDataSource.LoadSubInfoCallback {
    internal lateinit var mSubInfoRepository: SubInfoDataSource
    internal var mSubInfoList: List<SubInfo>? = null

    //Fragment是否在前台展示
    protected var mIsForeground: Boolean = false

    internal lateinit var mHomesubFragmentBinding: HomesubFragmentBinding
    internal lateinit var mHomeSubFragmentHanlders: HomeSubFragmentHanlders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSubInfoRepository = Injection.provideSubInfoRepository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mHomesubFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.homesub_fragment, container, false)
        mHomesubFragmentBinding.gridLayoutManager = GridLayoutManager(context, 2)
//        mHomesubFragmentBinding?.recyclerViewItemDecoration = RecyclerViewItemDecoration(6)
        mHomesubFragmentBinding.onRefreshListener = this

        mHomeSubFragmentHanlders = HomeSubFragmentHanlders()
        mHomesubFragmentBinding.homeSubFragmentHanlders = mHomeSubFragmentHanlders

        return mHomesubFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        mIsForeground = true
    }

    override fun onPause() {
        super.onPause()
        mIsForeground = false
    }

    internal abstract fun markAsRead()

    inner class HomeSubFragmentHanlders {
        val isRefreshing = ObservableBoolean()
    }
}
