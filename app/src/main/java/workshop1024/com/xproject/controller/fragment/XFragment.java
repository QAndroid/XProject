package workshop1024.com.xproject.controller.fragment;

import android.support.v4.app.Fragment;

/**
 * XProject Fragment基类，抽象Fragment一些公共的属性和方法，用于Fragment堆栈变化的时候，更新导航列表当前选项
 */
public class XFragment extends Fragment {
    //该Fragment对应的导航列表的选项的id
    private int mNavigationItemId;

    //Fragment是否在前台展示
    protected boolean mIsForeground;

    @Override
    public void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsForeground = false;
    }

    public int getNavigationItemId() {
        return mNavigationItemId;
    }

    public void setNavigationItemId(int navigationItemId) {
        mNavigationItemId = navigationItemId;
    }
}
