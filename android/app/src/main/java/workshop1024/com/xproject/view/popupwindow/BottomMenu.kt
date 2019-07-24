package workshop1024.com.xproject.view.popupwindow

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupWindow
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.home.FilterActivity
import workshop1024.com.xproject.controller.activity.home.PublisherActivity
import workshop1024.com.xproject.databinding.BottommenuViewBinding

class BottomMenu(private val mContext: Context) : PopupWindow(mContext) {
    private var mBottommenuViewBinding: BottommenuViewBinding

    init {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBottommenuViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottommenu_view, null, false)
        mBottommenuViewBinding.bottomMenuHandlers = BottomMenuHandlers()
        contentView = mBottommenuViewBinding.root

        width = ConstraintLayout.LayoutParams.MATCH_PARENT
        height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val backgoundDrawable = ColorDrawable(0x50000000)
        setBackgroundDrawable(backgoundDrawable)
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)

        val inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottommenu_in)
        mBottommenuViewBinding.bottommenuLinearlayoutActions.startAnimation(inAnimation)
    }

    inner class BottomMenuHandlers {
        fun onClickAddPublisher(view: View) {
            PublisherActivity.startActivity(mContext)
            dismiss1()
        }

        fun onClickAddTopic(view: View) {
            FilterActivity.startActivity(mContext)
            dismiss1()
        }

        fun onClickHolder(view: View) {
            dismiss1()
        }
    }

    fun dismiss1() {
        val outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottommenu_out)
        outAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                dismiss()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        mBottommenuViewBinding.bottommenuLinearlayoutActions.startAnimation(outAnimation)
    }
}


