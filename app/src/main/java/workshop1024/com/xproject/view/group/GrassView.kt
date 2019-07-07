package workshop1024.com.xproject.view.group

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.animation.ValueAnimator
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.GrassViewBinding

class GrassView : LinearLayout {
    private lateinit var mGrassViewBinding: GrassViewBinding

    constructor(context: Context) : super(context) {
        initViewShow()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewShow()
    }

    private fun initViewShow() {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mGrassViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.grass_view, this, false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val translationXAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass, "translationX",
                -mGrassViewBinding.grassviewImageviewGrass.width.toFloat(), (width + mGrassViewBinding.grassviewImageviewGrass.width).toFloat())
                .apply {
                    duration = 10000

                    repeatMode = ValueAnimator.RESTART
                    repeatCount = 1000
                }

        val translationYAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass, "translationY",
                0f, -50f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1000
            repeatMode = ValueAnimator.REVERSE
        }

        val rotationAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass,
                "rotation", 0f, 3600f).apply {
            duration = 10000
            repeatCount = 1000
            repeatMode = ValueAnimator.RESTART
        }

        AnimatorSet().apply {
            playTogether(translationXAnimator, translationYAnimator, rotationAnimator)
            start()
        }

        ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewShadow, "translationX", -mGrassViewBinding.grassviewImageviewShadow.width
                .toFloat(), (width + mGrassViewBinding.grassviewImageviewShadow.width).toFloat())
                .apply {
                    duration = 10000
                    repeatMode = ValueAnimator.RESTART
                    repeatCount = 1000

                    start()
                }
    }
}