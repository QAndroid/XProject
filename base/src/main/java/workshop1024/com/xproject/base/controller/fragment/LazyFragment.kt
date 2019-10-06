package workshop1024.com.xproject.base.controller.fragment

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment

/**
 * 懒加载Fragment
 */
abstract class LazyFragment : Fragment() {
    //视图是否初始化
    private var mIsViewInitiated: Boolean = false
    //视图是否对用户可见
    protected var mIsVisibleToUser: Boolean = false
    //数据是否初始化
    private var mIsDataInitiated: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //TODO 解决从信息订阅者信息页面返回重新请求
        Log.i("XProject", "LazyFragment setUserVisibleHint isVisibleToUser = ${isVisibleToUser}")
        mIsVisibleToUser = isVisibleToUser
        prepareLoadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("XProject", "LazyFragment onCreateView")

        mIsViewInitiated = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("XProject", "LazyFragment onActivityCreated")
        mIsViewInitiated = true
        prepareLoadData()
    }

    override fun onPause() {
        super.onPause()
        Log.i("XProject", "LazyFragment onPause")
        mIsVisibleToUser = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("XProject", "LazyFragment onDestroyView")
        mIsDataInitiated = false
    }

    /**
     * 当Fragmetn的可见状态变化的时候，判断是否需要加载数据
     */
    private fun prepareLoadData() {
        //当且仅当Fragment对用户可见，视图初始化完毕，并且还没有获取数据的时候，加载数据
        Log.i("XProject", "LazyFragment prepareLoadData mIsVisibleToUser =" + mIsVisibleToUser +
                ",mIsViewInitiated =" + mIsViewInitiated + ",mIsDataInitiated =" + mIsDataInitiated)
        //FIXME 上一个页面返回之后，不会再次加载数
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            mIsDataInitiated = true
            Log.i("XProject", "LazyFragment loadData")
            loadData()
        }
    }

    /**
     * 加载数据
     */
    protected abstract fun loadData()
}