package workshop1024.com.xproject.save.controller.fragment

import workshop1024.com.xproject.base.controller.fragment.TopFragment
import workshop1024.com.xproject.news.controller.fragment.NewsListFragment
import workshop1024.com.xproject.save.R

/**
 * 保存Fragment
 * //FIXME 和NewsListFragmet大量重复的代码，如何抽象，处理TopFragment和SubFragment的关系？？
 */
class SavedFragment : NewsListFragment(), TopFragment {

    override fun getNewsList() {
        mNewsRepository?.getSavedNewsList(this)
    }

    companion object {

        fun newInstance(navigationItemId: Int): SavedFragment {
            val savedFragment = SavedFragment()
            savedFragment.mNavigationItemId = navigationItemId
            return savedFragment
        }
    }
}
