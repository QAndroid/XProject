package workshop1024.com.xproject.view.popupwindow;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.home.FilterActivity;
import workshop1024.com.xproject.controller.activity.home.PublisherActivity;
import workshop1024.com.xproject.databinding.BottommenuViewBinding;

public class BottomMenu extends PopupWindow {
    private Context mContext;
    private BottommenuViewBinding mBottommenuViewBinding;

    public BottomMenu(Context context) {
        super(context);
        mContext = context;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBottommenuViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottommenu_view,
                null, false);
        mBottommenuViewBinding.setBottomMenuHandlers(new BottomMenuHandlers());
        setContentView(mBottommenuViewBinding.getRoot());

        setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT);
        setHeight(ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable backgoundDrawable = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(backgoundDrawable);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottommenu_in);
        mBottommenuViewBinding.bottommenuLinearlayoutActions.startAnimation(inAnimation);
    }

    public void dismiss1() {
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottommenu_out);
        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBottommenuViewBinding.bottommenuLinearlayoutActions.startAnimation(outAnimation);
    }

    public class BottomMenuHandlers {
        public void onClickAddPublisher(View view) {
            PublisherActivity.startActivity(mContext);
            dismiss1();
        }

        public void onClickAddTopic(View view) {
            FilterActivity.startActivity(mContext);
            dismiss1();
        }

        public void onClickHolder(View view) {
            dismiss1();
        }
    }
}
