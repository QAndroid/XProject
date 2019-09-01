package workshop1024.com.xproject.introduce.controller.view.group

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import workshop1024.com.xproject.base.utils.UnitUtils
import workshop1024.com.xproject.introduce.R
import kotlin.math.abs

class CircleDotIndicator : LinearLayout {
    //TODO 是否可以将ViewPager的回调放在外部，指示器内部只处理点的渲染，当前渲染的那个点是否可以放在外面

    //相关联的ViewPager
    private var mViewpager: ViewPager? = null

    //圆点被选中时切换动画，用于状态变化切换时
    private lateinit var mSelectedAnimator: Animator
    //圆点未被选中时切换动画，用于状态变化切换时
    private lateinit var mUnSelectedAnimator: Animator
    //圆点被选中时静态动画，用于初始添加视图时
    private lateinit var mSelectedStaticAnimator: Animator
    //圆点未被选中时静态动画，用于初始添加视图时
    private lateinit var mUnSelectedStaticAnimator: Animator

    //指示器上一次被选中的位置
    private var mLastSelectedPosition: Int = -1

    init {
        //初始化默认横向，居中
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    constructor(context: Context) : super(context) {
        initViewShow(context)
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param attrs   属性集合
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViewShow(context)
    }

    private fun initViewShow(context: Context) {
        //创建选中和未选中状态切换，执行的动画
        mSelectedAnimator = createSelectedAnimator(context)
        mUnSelectedAnimator = createUnSelectedAnimator(context)

        //创建选中和未选中初始化状态动画
        mSelectedStaticAnimator = createSelectedAnimator(context)
        mSelectedStaticAnimator.duration = 0
        mUnSelectedStaticAnimator = createUnSelectedAnimator(context)
        mUnSelectedStaticAnimator.duration = 0
    }

    /**
     * 创建未被选中的动画对象，是选中动画的反向动画
     *
     * @param context 上下文
     * @return 动画对象
     */
    private fun createUnSelectedAnimator(context: Context): Animator {
        val animatorIn = AnimatorInflater.loadAnimator(context, R.animator.circledot_dot)
        animatorIn.interpolator = ReverseInterpolator()
        return animatorIn
    }

    /**
     * 创建被选中的动画对象
     *
     * @param context 上下文
     * @return 动画对象
     */
    private fun createSelectedAnimator(context: Context): Animator {
        return AnimatorInflater.loadAnimator(context, R.animator.circledot_dot)
    }

    /**
     * 设置圆点指示器相关联的ViewPagerr
     *
     * @param viewPager 相关关联的ViewPager
     */
    fun setViewPager(viewPager: ViewPager) {
        mViewpager = viewPager

        createCircleDotViews()
    }

    /**
     * 创建圆点视图
     */
    private fun createCircleDotViews() {
        //移除所有布局中的视图
        removeAllViews()

        //获取相关联ViewPager页面的数目
        val pageCount = mViewpager?.adapter?.count
        //获取相关联ViewPager当前页面索引
        val currentItem = mViewpager?.currentItem
        //添加指示器圆点
        for (page_i in 0 until pageCount!!) {
            if (currentItem == page_i) {
                addCircleDotToIndicator(R.drawable.circledot_background, mSelectedStaticAnimator)
            } else {
                addCircleDotToIndicator(R.drawable.circledot_background, mUnSelectedStaticAnimator)
            }
        }
    }

    /**
     * 向指示器中添加圆点视图
     *
     * @param backgroundDrawableId 指示器背景
     * @param animator             指示器圆点动画
     */
    private fun addCircleDotToIndicator(backgroundDrawableId: Int, animator: Animator) {
        //如果动画正在执行，则停止取消
        if (animator.isRunning) {
            animator.end()
            animator.cancel()
        }

        //创建圆点视图，并设置宽高，间距等属性
        val dotImageView = ImageView(context)
        dotImageView.setBackgroundResource(backgroundDrawableId)
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = UnitUtils.dpToPx(context, 5f)
        layoutParams.rightMargin = UnitUtils.dpToPx(context, 5f)
        addView(dotImageView, layoutParams)

        //重新开始圆点视图动画
        animator.setTarget(dotImageView)
        animator.start()
    }

    /**
     * 设置当前选中的圆点
     *
     * @param position 当前选中圆点的索引
     */
    fun setCurrentSelectedCircleDot(position: Int) {
        //取消正在进行的In和Out动画
        if (mUnSelectedAnimator.isRunning) {
            mUnSelectedAnimator.end()
            mUnSelectedAnimator.cancel()
        }

        //上次选中的圆点视图，执行In动画
        if (mLastSelectedPosition >= 0) {
            val currentCircleDot = getChildAt(mLastSelectedPosition)
            if (currentCircleDot != null) {
                mUnSelectedAnimator.setTarget(currentCircleDot)
                mUnSelectedAnimator.start()
            }
        }

        if (mSelectedAnimator.isRunning) {
            mSelectedAnimator.end()
            mSelectedAnimator.cancel()
        }

        //当前选中的圆点视图，执行Out动画
        val selectedIndicator = getChildAt(position)
        if (selectedIndicator != null) {
            mSelectedAnimator.setTarget(selectedIndicator)
            mSelectedAnimator.start()
        }

        //更新当前选中的圆点为上一次选中的圆点
        mLastSelectedPosition = position
    }

    private inner class ReverseInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float {
            return abs(1.0f - input)
        }
    }
}