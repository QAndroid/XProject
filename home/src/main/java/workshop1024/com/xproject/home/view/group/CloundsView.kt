package workshop1024.com.xproject.home.view.group

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.databinding.CloundsViewBinding

/**
 * 云朵视图
 */
class CloundsView : RelativeLayout {
    private lateinit var mCloundsViewBinding: CloundsViewBinding

    constructor(context: Context) : super(context) {
        initViewShow(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewShow(context)
    }

    private fun initViewShow(context: Context) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mCloundsViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.clounds_view, this, false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound1, "translationX", -mCloundsViewBinding.cloundsviewImageviewClound1.width.toFloat(),
                (width + mCloundsViewBinding.cloundsviewImageviewClound1.width).toFloat()).apply {
            duration = 30000
            repeatMode = ValueAnimator.RESTART
            repeatCount = 1000
            start()
        }


        ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound2, "translationX", -mCloundsViewBinding.cloundsviewImageviewClound2.width.toFloat(),
                (width + mCloundsViewBinding.cloundsviewImageviewClound2.width).toFloat()).apply {
            duration = 25000
            repeatMode = ValueAnimator.RESTART
            repeatCount = 1000
            start()
        }

        ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound3, "translationX", -mCloundsViewBinding.cloundsviewImageviewClound3.width.toFloat(),
                (width + mCloundsViewBinding.cloundsviewImageviewClound3.width).toFloat()).apply {
            duration = 20000
            repeatMode = ValueAnimator.RESTART
            repeatCount = 1000
            start()
        }
    }

}