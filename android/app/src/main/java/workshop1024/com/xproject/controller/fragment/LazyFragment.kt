package workshop1024.com.xproject.controller.fragment

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
    private var mIsVisibleToUser: Boolean = false
    //数据是否初始化
    private var mIsDataInitiated: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        prepareLoadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mIsViewInitiated = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mIsViewInitiated = true
        prepareLoadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsViewInitiated = false
        mIsDataInitiated = false
    }

    /**
     * 当Fragmetn的可见状态变化的时候，判断是否需要加载数据
     */
    private fun prepareLoadData() {
        //当且仅当Fragment对用户可见，视图初始化完毕，并且还没有获取数据的时候，加载数据
        Log.i("XProject", "prepareLoadData mIsVisibleToUser =" + mIsVisibleToUser +
                ",mIsViewInitiated =" + mIsViewInitiated + ",mIsDataInitiated =" + mIsDataInitiated)
        //FIXME 上一个页面返回之后，不会再次加载数
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            mIsDataInitiated = true
            loadData()
        }
    }

    /**
     * 加载数据
     */
    protected abstract fun loadData()
}