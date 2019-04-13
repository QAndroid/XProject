package workshop1024.com.xproject.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载Fragment
 */
public abstract class LazyFragment extends Fragment {
    //视图是否初始化
    private boolean mIsViewInitiated;
    //视图是否对用户可见
    private boolean mIsVisibleToUser;
    //数据是否初始化
    private boolean mIsDataInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        prepareLoadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsViewInitiated = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsViewInitiated = true;
        prepareLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewInitiated = false;
        mIsDataInitiated = false;
    }

    /**
     * 当Fragmetn的可见状态变化的时候，判断是否需要加载数据
     */
    private void prepareLoadData() {
        //当且仅当Fragment对用户可见，视图初始化完毕，并且还没有获取数据的时候，加载数据
        Log.i("XProject", "prepareLoadData mIsVisibleToUser =" + mIsVisibleToUser +
                ",mIsViewInitiated =" + mIsViewInitiated + ",mIsDataInitiated =" + mIsDataInitiated);
        //FIXME 上一个页面返回之后，不会再次加载数
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            mIsDataInitiated = true;
            loadData();
        }
    }


    /**
     * 加载数据
     */
    protected abstract void loadData();
}
