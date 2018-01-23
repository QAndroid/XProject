package workshop1024.com.xproject.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.PublisherActivity;

public class BottomMenu extends PopupWindow implements View.OnClickListener {
    private Context mContext;

    private View mHolderView;
    private LinearLayout mActionsLinearLayout;
    private TextView mAddPublisherTextView;
    private TextView mAddTopicTextView;

    public BottomMenu(Context context) {
        super(context);
        mContext = context;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.bottommenu_view, null);
        setContentView(view);

        mHolderView = view.findViewById(R.id.bottommenu_space_holder);
        mActionsLinearLayout = view.findViewById(R.id.bottommenu_linearlayout_actions);
        mAddPublisherTextView = view.findViewById(R.id.bottommenu_textview_addpublisher);
        mAddTopicTextView = view.findViewById(R.id.bottommenu_textview_addtopic);

        mHolderView.setOnClickListener(this);
        mAddPublisherTextView.setOnClickListener(this);
        mAddTopicTextView.setOnClickListener(this);

        setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT);
        setHeight(ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable backgoundDrawable = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(backgoundDrawable);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottommenu_in);
        mActionsLinearLayout.startAnimation(inAnimation);
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
        mActionsLinearLayout.startAnimation(outAnimation);
    }

    @Override
    public void onClick(View view) {
        if (view == mAddPublisherTextView) {
            Intent intent = new Intent(mContext, PublisherActivity.class);
            mContext.startActivity(intent);
        } else if (view == mAddTopicTextView) {

        }

        dismiss1();
    }
}
