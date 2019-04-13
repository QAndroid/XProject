package workshop1024.com.xproject.view.group;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.CloundsViewBinding;

/**
 * 云朵视图
 */
public class CloundsView extends RelativeLayout {
    private CloundsViewBinding mCloundsViewBinding;

    public CloundsView(Context context) {
        super(context);
        initViewShow(context);
    }

    public CloundsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewShow(context);
    }

    private void initViewShow(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCloundsViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.clounds_view, this, false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound1, "translationX",
                -mCloundsViewBinding.cloundsviewImageviewClound1.getWidth(), getWidth() + mCloundsViewBinding.cloundsviewImageviewClound1.getWidth());
        translationXAnimator.setDuration(30000);
        translationXAnimator.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator.setRepeatCount(1000);
        translationXAnimator.start();

        ObjectAnimator translationXAnimator2 = ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound2, "translationX",
                -mCloundsViewBinding.cloundsviewImageviewClound2.getWidth(), getWidth() + mCloundsViewBinding.cloundsviewImageviewClound2.getWidth());
        translationXAnimator2.setDuration(25000);
        translationXAnimator2.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator2.setRepeatCount(1000);
        translationXAnimator2.start();

        ObjectAnimator translationXAnimator3 = ObjectAnimator.ofFloat(mCloundsViewBinding.cloundsviewImageviewClound3, "translationX",
                -mCloundsViewBinding.cloundsviewImageviewClound3.getWidth(), getWidth() + mCloundsViewBinding.cloundsviewImageviewClound3.getWidth());
        translationXAnimator3.setDuration(20000);
        translationXAnimator3.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator3.setRepeatCount(1000);
        translationXAnimator3.start();
    }
}
