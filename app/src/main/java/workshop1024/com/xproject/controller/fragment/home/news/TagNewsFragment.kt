package workshop1024.com.xproject.controller.fragment.home.news

import android.os.Bundle

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.fragment.SubFragment

class TagNewsFragment : NewsListFragment(), SubFragment {
    private var mTagName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTagName = arguments!!.getString(TAG_NAME)
        }
    }

    override fun getNewsList() {
        mNewsRepository.getNewsListByTag(mTagName!!, this)
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
