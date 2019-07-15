package workshop1024.com.xproject.controller.fragment.save

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.fragment.TopFragment
import workshop1024.com.xproject.controller.fragment.home.news.NewsListFragment

/**
 * 保存Fragment
 * //FIXME 和NewsListFragmet大量重复的代码，如何抽象，处理TopFragment和SubFragment的关系？？
 */
class SavedFragment : NewsListFragment(), TopFragment {

    override fun getNewsList() {
        mNewsRepository?.getSavedNewsList(this)
    }

    companion object {

        fun newInstance(): SavedFragment {
            val savedFragment = SavedFragment()
            savedFragment.mNavigationItemId = R.id.leftnavigator_menu_saved
            return savedFragment
        }
    }
}
