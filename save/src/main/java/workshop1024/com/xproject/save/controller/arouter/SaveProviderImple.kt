package workshop1024.com.xproject.save.controller.arouter

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import workshop1024.com.xproject.base.arouter.provider.SaveProvider
import workshop1024.com.xproject.save.controller.fragment.SavedNewsesFragment

@Route(path = "/save/SaveProvider")
class SaveProviderImple : SaveProvider {
    override fun init(context: Context?) {

    }

    override fun newSavedFragmentInstance(navigationItemId: Int): Fragment {
        return SavedNewsesFragment.newInstance(navigationItemId)
    }
}