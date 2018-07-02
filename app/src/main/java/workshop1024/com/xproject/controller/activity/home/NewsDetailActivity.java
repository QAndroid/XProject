package workshop1024.com.xproject.controller.activity.home;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.TagListAdapter;
import workshop1024.com.xproject.model.news.NewsDetail;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.utils.UnitUtils;
import workshop1024.com.xproject.utils.ViewUtils;
import workshop1024.com.xproject.view.dialog.DisplaySettingsDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class NewsDetailActivity extends XActivity implements View.OnClickListener,
        NewsDataSource.LoadNewsDetailCallBack, DisplaySettingsDialog.DisplaySettingsDialogListener {
    public static final String NEWS_ID_KEY = "newsId";

    private Toolbar mToolbar;
    private TextView mPublisherTextView;
    private TextView mPubDataTextView;
    private TextView mContentTextView;
    private RecyclerView mTagRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CardView mSheetCardView;
    private TextView mSheetItem1TextView;
    private TextView mSheetItem2TextView;
    private TextView mSheetItem3TextView;
    private TextView mSheetItem4TextView;
    private View mOverlayView;
    private DisplaySettingsDialog mDisplaySettingsDialog;

    private String mNewsId;
    private Float mSelectedFontSize;
    private NewsRepository mNewsRepository;
    private SharedPreferences mSharedPreferences;

    public static void startActivity(Context context, String newsId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetail_activity);

        mToolbar = findViewById(R.id.detail_toolbar_navigator);
        mPublisherTextView = findViewById(R.id.newsdetail_textview_publisher);
        mPubDataTextView = findViewById(R.id.newsdetail_textview_pubdata);
        mContentTextView = findViewById(R.id.newsdetail_textview_content);
        mTagRecyclerView = findViewById(R.id.newsdetail_recyclerView_tags);
        mFloatingActionButton = findViewById(R.id.newsdetail_floatingactionbutton_action);
        mSheetCardView = findViewById(R.id.newsdetail_cardview_sheet);
        mSheetItem1TextView = findViewById(R.id.newsdetail_textview_sheetitem1);
        mSheetItem2TextView = findViewById(R.id.newsdetail_textview_sheetitem2);
        mSheetItem3TextView = findViewById(R.id.newsdetail_textview_sheetitem3);
        mSheetItem4TextView = findViewById(R.id.newsdetail_textview_sheetitem4);
        mOverlayView = findViewById(R.id.newsdetail_view_overlay);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizeString = mSharedPreferences.getString(getString(R.string.settings_preference_fontsizes_key),
                getString(R.string.settings_preference_fontsizes_default));
        mSelectedFontSize = Float.valueOf(fontSizeString);
        Log.i("XProject", "onCreate fintSize = " + fontSizeString);
        mContentTextView.setTextSize(UnitUtils.spToPx(this, mSelectedFontSize));

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mTagRecyclerView.setLayoutManager(flexboxLayoutManager);
        mTagRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(UnitUtils.dpToPx(this, 4)));

        mFloatingActionButton.setOnClickListener(this);
        mSheetItem1TextView.setOnClickListener(this);
        mSheetItem2TextView.setOnClickListener(this);
        mSheetItem3TextView.setOnClickListener(this);
        mSheetItem4TextView.setOnClickListener(this);
        mOverlayView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewsId = getIntent().getStringExtra(NEWS_ID_KEY);
        mNewsRepository = NewsRepository.getInstance();
        mNewsRepository.getNewsDetailByNewsId(mNewsId, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //拦截Home按钮事件，关闭当前Activity，返回上一个Activity-即MainActivity
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mFloatingActionButton)) {
            sheetViewIn();
        } else if (v.equals(mOverlayView)) {
            sheetViewOut();
        } else if (v.equals(mSheetItem1TextView)) {
            if (mDisplaySettingsDialog == null) {
                mDisplaySettingsDialog = DisplaySettingsDialog.newInstance();
                mDisplaySettingsDialog.setSelectTextSize(mSelectedFontSize);
            }
            mDisplaySettingsDialog.show(getSupportFragmentManager(), "DisplaySettingsDialog");
            sheetViewOut();
        } else if (v.equals(mSheetItem2TextView)) {
            Toast.makeText(this, "mSheetItem2TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        } else if (v.equals(mSheetItem3TextView)) {
            NewsRepository newsRepository = NewsRepository.getInstance();
            newsRepository.saveNewsById(mNewsId);
            Toast.makeText(this, "mSheetItem3TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        } else if (v.equals(mSheetItem4TextView)) {
            Toast.makeText(this, "mSheetItem4TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        }
    }

    @Override
    public void onNewsDetailLoaded(NewsDetail newsDetail) {
        if (mIsForeground) {
            mPublisherTextView.setText(newsDetail.getPublisher());
            mPubDataTextView.setText(newsDetail.getPubDate());
            mContentTextView.setText(newsDetail.getContent());

            TagListAdapter tagListAdapter = new TagListAdapter(this, newsDetail.getTagList());
            mTagRecyclerView.setAdapter(tagListAdapter);
        }
    }

    @Override
    public void onDataNotAvaiable() {

    }

    @Override
    public void onDisplaySettingDialogClick(DialogFragment dialogFragment, float textSize) {
        mSelectedFontSize = textSize;
        mContentTextView.setTextSize(UnitUtils.spToPx(this, mSelectedFontSize));

        Log.i("XProject", "onDisplaySettingDialogClick fintSize = " + textSize);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(getString(R.string.settings_preference_fontsizes_key), String.valueOf(textSize));
        editor.commit();
    }

    private void sheetViewIn() {
        mFloatingActionButton.animate().scaleX(1.2f).scaleY(1.2f).translationX(getTranslationX(mFloatingActionButton, mSheetCardView)).
                translationY(getTranslationY(mFloatingActionButton, mSheetCardView)).setInterpolator(new AccelerateInterpolator()).
                setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mOverlayView.setVisibility(View.VISIBLE);
                mOverlayView.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSheetCardView.setVisibility(View.VISIBLE);
                mFloatingActionButton.setVisibility(View.INVISIBLE);

                Animator animator = ViewAnimationUtils.createCircularReveal(mSheetCardView, getCenterX(mSheetCardView),
                        getCenterY(mSheetCardView), mFloatingActionButton.getWidth() / 2,
                        (float) Math.hypot(mSheetCardView.getWidth(), mSheetCardView.getHeight()) / 2);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.setDuration(300);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void sheetViewOut() {
        Animator animator = ViewAnimationUtils.createCircularReveal(mSheetCardView, getCenterX(mSheetCardView),
                getCenterY(mSheetCardView), (float) Math.hypot(mSheetCardView.getWidth(),
                        mSheetCardView.getHeight()) / 2, mFloatingActionButton.getWidth() / 2);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSheetCardView.setVisibility(View.INVISIBLE);
                mFloatingActionButton.setVisibility(View.VISIBLE);
                mFloatingActionButton.animate().scaleX(1f).scaleY(1f).translationX(0).translationY(0).setInterpolator(new AccelerateInterpolator()).
                        setDuration(300).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mOverlayView.animate().alpha(0).setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mOverlayView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private float getTranslationX(FloatingActionButton floatingActionButton, CardView sheetCardView) {
        int floatingActionButtonX = ViewUtils.getRelativeLeft(floatingActionButton) + floatingActionButton.getWidth() / 2;
        int mSheetCardViewX = ViewUtils.getRelativeLeft(sheetCardView) + sheetCardView.getWidth() / 2;
        return mSheetCardViewX - floatingActionButtonX;
    }

    private float getTranslationY(FloatingActionButton floatingActionButton, CardView sheetCardView) {
        int floatingActionButtonY = ViewUtils.getRelativeTop(floatingActionButton) + floatingActionButton.getHeight() / 2;
        int mSheetCardViewY = ViewUtils.getRelativeTop(sheetCardView) + sheetCardView.getHeight() / 2;
        return mSheetCardViewY - floatingActionButtonY;
    }

    private int getCenterX(CardView sheetCardView) {
        return sheetCardView.getWidth() / 2 + sheetCardView.getLeft();
    }

    private int getCenterY(CardView sheetCardView) {
        return sheetCardView.getHeight() / 2 + sheetCardView.getTop();
    }
}
