package workshop1024.com.xproject.base.arouter.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface HomeProvider : IProvider {
    fun newFilterNewsFragmentInstance(subscribeId: String, navigationItemId: Int): Fragment
}