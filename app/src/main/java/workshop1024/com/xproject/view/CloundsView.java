package workshop1024.com.xproject.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import workshop1024.com.xproject.R;

/**
 * 云朵视图
 */
public class CloundsView extends RelativeLayout {
    private ImageView mClound1ImageView;
    private ImageView mClound2ImageView;
    private ImageView mClound3ImageView;

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
        layoutInflater.inflate(R.layout.clounds_view, this);

        mClound1ImageView = findViewById(R.id.cloundsview_imageview_clound1);
        mClound2ImageView = findViewById(R.id.cloundsview_imageview_clound2);
        mClound3ImageView = findViewById(R.id.cloundsview_imageview_clound3);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(mClound1ImageView, "translationX",
                -mClound1ImageView.getWidth(), getWidth() + mClound1ImageView.getWidth());
        translationXAnimator.setDuration(30000);
        translationXAnimator.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator.setRepeatCount(1000);
        translationXAnimator.start();

        ObjectAnimator translationXAnimator2 = ObjectAnimator.ofFloat(mClound2ImageView, "translationX",
                -mClound2ImageView.getWidth(), getWidth() + mClound2ImageView.getWidth());
        translationXAnimator2.setDuration(25000);
        translationXAnimator2.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator2.setRepeatCount(1000);
        translationXAnimator2.start();

        ObjectAnimator translationXAnimator3 = ObjectAnimator.ofFloat(mClound3ImageView, "translationX",
                -mClound3ImageView.getWidth(), getWidth() + mClound3ImageView.getWidth());
        translationXAnimator3.setDuration(20000);
        translationXAnimator3.setRepeatMode(ValueAnimator.RESTART);
        translationXAnimator3.setRepeatCount(1000);
        translationXAnimator3.start();
    }
}
