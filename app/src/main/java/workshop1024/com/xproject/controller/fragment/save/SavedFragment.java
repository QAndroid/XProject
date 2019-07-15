package workshop1024.com.xproject.controller.fragment.save;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.fragment.TopFragment;
import workshop1024.com.xproject.controller.fragment.home.news.NewsListFragment;

/**
 * 保存Fragment
 * //FIXME 和NewsListFragmet大量重复的代码，如何抽象，处理TopFragment和SubFragment的关系？？
 */
public class SavedFragment extends NewsListFragment implements TopFragment {

    public static SavedFragment newInstance() {
        SavedFragment savedFragment = new SavedFragment();
        savedFragment.setMNavigationItemId(R.id.leftnavigator_menu_saved);
        return savedFragment;
    }

    @Override
    public void getNewsList() {
        mNewsRepository.getSavedNewsList(this);
    }
}
