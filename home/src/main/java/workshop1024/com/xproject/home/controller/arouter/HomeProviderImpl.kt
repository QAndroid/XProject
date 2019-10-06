package workshop1024.com.xproject.home.controller.arouter

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import workshop1024.com.xproject.base.arouter.provider.HomeProvider
import workshop1024.com.xproject.home.controller.fragment.news.FilterNewsesFragment

@Route(path = "/home/HomeProvider")
class HomeProviderImpl : HomeProvider {
    override fun init(context: Context?) {

    }

    override fun newFilterNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment {
        return FilterNewsesFragment.newInstance(subscribeId, navigationItemId)
    }
}