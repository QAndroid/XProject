package workshop1024.com.xproject.home.controller.fragment.news

import android.os.Bundle
import workshop1024.com.xproject.news.controller.fragment.NewsListFragment

import workshop1024.com.xproject.base.controller.fragment.SubFragment

class SubscribeNewsFragment : NewsListFragment(), SubFragment {
    private var mSubscribeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSubscribeId = arguments?.getString(SUBSCRIBE_ID)
    }

    override fun getNewsList() {
        mSubscribeId?.let { mNewsRepository?.getNewsListBySubscribe(it, this) }
    }

    companion object {
        private const val SUBSCRIBE_ID = "subscribe_Id"

        fun newInstance(subscribeId: String, navigationItemId: Int): SubscribeNewsFragment {
            val subscribeNewsFragment = SubscribeNewsFragment()
            subscribeNewsFragment.mNavigationItemId = navigationItemId
            val args = Bundle()
            args.putString(SUBSCRIBE_ID, subscribeId)
            subscribeNewsFragment.arguments = args
            return subscribeNewsFragment
        }
    }
}
