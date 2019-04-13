package workshop1024.com.xproject.view.group;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.GrassViewBinding;

/**
 * 草视图
 */
public class GrassView extends LinearLayout {
    private GrassViewBinding mGrassViewBinding;

    public GrassView(Context context) {
        super(context);
        initViewShow(context);
    }

    public GrassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewShow(context);
    }

    private void initViewShow(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGrassViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.grass_view, this, false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass, "translationX",
                -mGrassViewBinding.grassviewImageviewGrass.getWidth(), getWidth() + mGrassViewBinding.grassviewImageviewGrass.getWidth());
        translationXAnimator.setDuration(10000);
        translationXAnimator.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator.setRepeatCount(1000);

        ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass, "translationY", 0, -50);
        translationYAnimator.setDuration(1000);
        translationYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        translationYAnimator.setRepeatCount(1000);
        translationYAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewGrass, "rotation", 0, 3600);
        rotationAnimator.setDuration(10000);
        rotationAnimator.setRepeatCount(1000);
        rotationAnimator.setRepeatMode(ValueAnimator.RESTART);

        AnimatorSet grassAnimatorSet = new AnimatorSet();
        grassAnimatorSet.playTogether(translationXAnimator, translationYAnimator, rotationAnimator);
        grassAnimatorSet.start();

        ObjectAnimator translationXAnimator2 = ObjectAnimator.ofFloat(mGrassViewBinding.grassviewImageviewShadow, "translationX",
                -mGrassViewBinding.grassviewImageviewShadow.getWidth(), getWidth() + mGrassViewBinding.grassviewImageviewShadow.getWidth());
        translationXAnimator2.setDuration(10000);
        translationXAnimator2.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator2.setRepeatCount(1000);

        translationXAnimator2.start();
    }

}
