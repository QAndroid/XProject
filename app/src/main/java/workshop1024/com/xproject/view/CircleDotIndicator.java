package workshop1024.com.xproject.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.utils.UnitUtils;

/**
 * 圆点指示器
 */
public class CircleDotIndicator extends LinearLayout {
    //TODO 是否可以将ViewPager的回调放在外部，指示器内部只处理点的渲染，当前渲染的那个点是否可以放在外面

    //相关联的ViewPager
    private ViewPager mViewpager;

    //圆点被选中时切换动画，用于状态变化切换时
    private Animator mSelectedAnimator;
    //圆点未被选中时切换动画，用于状态变化切换时
    private Animator mUnSelectedAnimator;
    //圆点被选中时静态动画，用于初始添加视图时
    private Animator mSelectedStaticAnimator;
    //圆点未被选中时静态动画，用于初始添加视图时
    private Animator mUnSelectedStaticAnimator;

    //指示器上一次被选中的位置
    private int mLastSelectedPosition = -1;

    {
        //初始化默认横向，居中
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public CircleDotIndicator(Context context) {
        super(context);
        initViewShow(context);
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param attrs   属性集合
     */
    public CircleDotIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewShow(context);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleDotIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewShow(context);
    }

    /**
     * 设置圆点指示器相关联的ViewPagerr
     *
     * @param viewPager 相关关联的ViewPager
     */
    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;

        createCircleDotViews();
    }

    /**
     * 初始化视图
     *
     * @param context 上下文对象
     * @param attrs   属性集合对象
     */
    private void initViewShow(Context context) {
        //创建选中和未选中状态切换，执行的动画
        mSelectedAnimator = createSelectedAnimator(context);
        mUnSelectedAnimator = createUnSelectedAnimator(context);

        //创建选中和未选中初始化状态动画
        mSelectedStaticAnimator = createSelectedAnimator(context);
        mSelectedStaticAnimator.setDuration(0);
        mUnSelectedStaticAnimator = createUnSelectedAnimator(context);
        mUnSelectedStaticAnimator.setDuration(0);
    }

    /**
     * 创建被选中的动画对象
     *
     * @param context 上下文
     * @return 动画对象
     */
    private Animator createSelectedAnimator(Context context) {
        return AnimatorInflater.loadAnimator(context, R.animator.circledot_scaleandalpha);
    }

    /**
     * 创建未被选中的动画对象，是选中动画的反向动画
     *
     * @param context 上下文
     * @return 动画对象
     */
    private Animator createUnSelectedAnimator(Context context) {
        Animator animatorIn = AnimatorInflater.loadAnimator(context, R.animator.circledot_scaleandalpha);
        animatorIn.setInterpolator(new ReverseInterpolator());
        return animatorIn;
    }

    /**
     * 创建圆点视图
     */
    private void createCircleDotViews() {
        //移除所有布局中的视图
        removeAllViews();

        //获取相关联ViewPager页面的数目
        int pageCount = mViewpager.getAdapter().getCount();
        //获取相关联ViewPager当前页面索引
        int currentItem = mViewpager.getCurrentItem();
        //添加指示器圆点
        for (int page_i = 0; page_i < pageCount; page_i++) {
            if (currentItem == page_i) {
                addCircleDotToIndicator(R.drawable.circledot_background, mSelectedStaticAnimator);
                mLastSelectedPosition = currentItem;
            } else {
                addCircleDotToIndicator(R.drawable.circledot_background, mUnSelectedStaticAnimator);
            }
        }
    }

    /**
     * 向指示器中添加圆点视图
     *
     * @param backgroundDrawableId 指示器背景
     * @param animator             指示器圆点动画
     */
    private void addCircleDotToIndicator(@DrawableRes int backgroundDrawableId, Animator animator) {
        //如果动画正在执行，则停止取消
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        //创建圆点视图，并设置宽高，间距等属性
        ImageView dotImageView = new ImageView(getContext());
        dotImageView.setBackgroundResource(backgroundDrawableId);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        layoutParams.leftMargin = UnitUtils.dip2px(getContext(), 5);
        layoutParams.rightMargin = UnitUtils.dip2px(getContext(), 5);
        addView(dotImageView, layoutParams);

        //重新开始圆点视图动画
        animator.setTarget(dotImageView);
        animator.start();
    }

    /**
     * 设置当前选中的圆点
     *
     * @param position 当前选中圆点的索引
     */
    public void setCurrentSelectedCircleDot(int position) {
        //取消正在进行的In和Out动画
        if (mUnSelectedAnimator.isRunning()) {
            mUnSelectedAnimator.end();
            mUnSelectedAnimator.cancel();
        }
        //上次选中的圆点视图，执行In动画
        if (mLastSelectedPosition >= 0) {
            View currentCircleDot = getChildAt(mLastSelectedPosition);
            if (currentCircleDot != null) {
                mUnSelectedAnimator.setTarget(currentCircleDot);
                mUnSelectedAnimator.start();
            }
        }

        if (mSelectedAnimator.isRunning()) {
            mSelectedAnimator.end();
            mSelectedAnimator.cancel();
        }
        //当前选中的圆点视图，执行Out动画
        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            mSelectedAnimator.setTarget(selectedIndicator);
            mSelectedAnimator.start();
        }

        //更新当前选中的圆点为上一次选中的圆点
        mLastSelectedPosition = position;
    }


    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}
