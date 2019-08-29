package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle

import workshop1024.com.xproject.base.controller.fragment.SubFragment
import workshop1024.com.xproject.home.R

class TagNewsFragment : NewsListFragment(), SubFragment {
    private var mTagName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTagName = arguments!!.getString(TAG_NAME)
    }

    override fun getNewsList() {
        mTagName?.let { mNewsRepository?.getNewsListByTag(it, this) }
    }

    companion object {
        private const val TAG_NAME = "tag_Name"

        fun newInstance(subscribeName: String): TagNewsFragment {
            val tagNewsFragment = TagNewsFragment()
            tagNewsFragment.mNavigationItemId = R.id.leftnavigator_menu_home
            val args = Bundle()
            args.putString(TAG_NAME, subscribeName)
            tagNewsFragment.arguments = args
            return tagNewsFragment
        }
    }
}
