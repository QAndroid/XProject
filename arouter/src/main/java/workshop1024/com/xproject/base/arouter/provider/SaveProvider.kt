package workshop1024.com.xproject.base.arouter.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface SaveProvider : IProvider {
    fun newSavedFragmentInstance(navigationItemId: Int): Fragment
}