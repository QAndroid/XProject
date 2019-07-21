package workshop1024.com.xproject.controller.fragment.home.news

import android.os.Bundle

import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.fragment.SubFragment

class SubscribeNewsFragment : NewsListFragment(), SubFragment {
    private var mSubscribeName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            mSubscribeName = arguments?.getString(SUBSCRIBE_NAME)
    }

    override fun getNewsList() {
        mSubscribeName?.let { mNewsRepository?.getNewsListBySubscribe(it, this) }
    }

    companion object {
        private const val SUBSCRIBE_NAME = "subscribe_Name"

        fun newInstance(subscribeName: String): SubscribeNewsFragment {
            val subscribeNewsFragment = SubscribeNewsFragment()
            subscribeNewsFragment.mNavigationItemId = R.id.leftnavigator_menu_home
            val args = Bundle()
            args.putString(SUBSCRIBE_NAME, subscribeName)
            subscribeNewsFragment.arguments = args
            return subscribeNewsFragment
        }
    }
}
