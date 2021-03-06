package workshop1024.com.xproject.introduce.introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment

//TODO Nullable注解的作用？
/**
 * 介绍Fragment
 */
class IntroduceFragment : Fragment() {
    private var mLayoutId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mLayoutId = arguments!!.getInt(LAYOUT_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(mLayoutId,container, false)
    }

    companion object {
        //@VisibleForTesting：只有提醒作用，次成员的公开是为了测试使用，其它地方别访问
        @VisibleForTesting
        const val LAYOUT_ID = "layoutId"

        fun newInstance(layoutId: Int): IntroduceFragment {
            val fragment = IntroduceFragment()
            val args = Bundle()
            args.putInt(LAYOUT_ID, layoutId)
            fragment.arguments = args
            return fragment
        }
    }
}