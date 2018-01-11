package workshop1024.com.xproject.fragment;

import android.support.v4.app.Fragment;

/**
 * XProject Fragment基类，抽象Fragment一些公共的属性和方法，用于Fragment堆栈变化的时候，更新导航列表当前选项
 */
public class XFragment extends Fragment {
    //该Fragment对应的导航列表的选项的id
    private int mNavigationItemId;
    //该Fragment对应的Toolbar标题显示的文案
    private int mTitleString;

    public int getNavigationItemId() {
        return mNavigationItemId;
    }

    public void setNavigationItemId(int navigationItemId) {
        mNavigationItemId = navigationItemId;
    }


}
