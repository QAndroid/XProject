package workshop1024.com.xproject.controller.activity.home;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.TagListAdapter;
import workshop1024.com.xproject.databinding.NewsdetailActivityBinding;
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.news.NewsDetail;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.utils.UnitUtils;
import workshop1024.com.xproject.utils.ViewUtils;
import workshop1024.com.xproject.view.dialog.DisplaySettingsDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class NewsDetailActivity extends XActivity implements NewsDataSource.LoadNewsDetailCallBack,
        DisplaySettingsDialog.DisplaySettingsDialogListener {
    public static final String NEWS_ID_KEY = "newsId";

    private DisplaySettingsDialog mDisplaySettingsDialog;

    private String mNewsId;
    private Float mSelectedFontSize;
    private NewsDataSource mNewsRepository;
    private SharedPreferences mSharedPreferences;

    private NewsdetailActivityBinding mNewsdetailActivityBinding;

    public static void startActivity(Context context, String newsId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsdetailActivityBinding = DataBindingUtil.setContentView(this, R.layout.newsdetail_activity);
        mNewsdetailActivityBinding.setNewsDetailHandlers(new NewsDetailHandlers());

        setSupportActionBar(mNewsdetailActivityBinding.detailToolbarNavigator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizeString = mSharedPreferences.getString(getString(R.string.settings_preference_fontsizes_key),
                getString(R.string.settings_preference_fontsizes_default));
        mSelectedFontSize = Float.valueOf(fontSizeString);
        Log.i("XProject", "onCreate fintSize = " + fontSizeString);
        mNewsdetailActivityBinding.newsdetailTextviewContent.setTextSize(UnitUtils.spToPx(this, mSelectedFontSize));

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mNewsdetailActivityBinding.newsdetailRecyclerViewTags.setLayoutManager(flexboxLayoutManager);
        mNewsdetailActivityBinding.newsdetailRecyclerViewTags.addItemDecoration(new RecyclerViewItemDecoration(UnitUtils.dpToPx(this, 4)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewsId = getIntent().getStringExtra(NEWS_ID_KEY);
        mNewsRepository = Injection.provideNewsRepository();
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
    public void onNewsDetailLoaded(NewsDetail newsDetail) {
        if (mIsForeground) {
            mNewsdetailActivityBinding.newsdetailTextviewPublisher.setText(newsDetail.getPublisher());
            mNewsdetailActivityBinding.newsdetailTextviewPubdata.setText(newsDetail.getPubDate());
            mNewsdetailActivityBinding.newsdetailTextviewContent.setText(newsDetail.getContent());

            TagListAdapter tagListAdapter = new TagListAdapter(this, newsDetail.getTagList());
            mNewsdetailActivityBinding.newsdetailRecyclerViewTags.setAdapter(tagListAdapter);
        }
    }

    @Override
    public void onDataNotAvaiable() {

    }

    @Override
    public void onDisplaySettingDialogClick(DialogFragment dialogFragment, float textSize) {
        mSelectedFontSize = textSize;
        mNewsdetailActivityBinding.newsdetailTextviewContent.setTextSize(UnitUtils.spToPx(this, mSelectedFontSize));

        Log.i("XProject", "onDisplaySettingDialogClick fintSize = " + textSize);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(getString(R.string.settings_preference_fontsizes_key), String.valueOf(textSize));
        editor.commit();
    }

    private void sheetViewIn() {
        mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.animate().scaleX(1.2f).scaleY(1.2f).
                translationX(getTranslationX(mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction, mNewsdetailActivityBinding.newsdetailCardviewSheet)).
                translationY(getTranslationY(mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction, mNewsdetailActivityBinding.newsdetailCardviewSheet)).
                setInterpolator(new AccelerateInterpolator()).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mNewsdetailActivityBinding.newsdetailViewOverlay.setVisibility(View.VISIBLE);
                mNewsdetailActivityBinding.newsdetailViewOverlay.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mNewsdetailActivityBinding.newsdetailCardviewSheet.setVisibility(View.VISIBLE);
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.setVisibility(View.INVISIBLE);

                Animator animator = ViewAnimationUtils.createCircularReveal(mNewsdetailActivityBinding.newsdetailCardviewSheet,
                        getCenterX(mNewsdetailActivityBinding.newsdetailCardviewSheet), getCenterY(mNewsdetailActivityBinding.newsdetailCardviewSheet),
                        mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.getWidth() / 2,
                        (float) Math.hypot(mNewsdetailActivityBinding.newsdetailCardviewSheet.getWidth(),
                                mNewsdetailActivityBinding.newsdetailCardviewSheet.getHeight()) / 2);
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
        Animator animator = ViewAnimationUtils.createCircularReveal(mNewsdetailActivityBinding.newsdetailCardviewSheet,
                getCenterX(mNewsdetailActivityBinding.newsdetailCardviewSheet), getCenterY(mNewsdetailActivityBinding.newsdetailCardviewSheet),
                (float) Math.hypot(mNewsdetailActivityBinding.newsdetailCardviewSheet.getWidth(), mNewsdetailActivityBinding.
                        newsdetailCardviewSheet.getHeight()) / 2, mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.getWidth() / 2);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mNewsdetailActivityBinding.newsdetailCardviewSheet.setVisibility(View.INVISIBLE);
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.setVisibility(View.VISIBLE);
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.animate().scaleX(1f).scaleY(1f).translationX(0).
                        translationY(0).setInterpolator(new AccelerateInterpolator()).setDuration(300).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mNewsdetailActivityBinding.newsdetailViewOverlay.animate().alpha(0).setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mNewsdetailActivityBinding.newsdetailViewOverlay.setVisibility(View.INVISIBLE);
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

    public class NewsDetailHandlers {
        public void onClickFloatingActionButton(View view) {
            sheetViewIn();
        }

        public void onClickViewOverlay(View view) {
            sheetViewIn();
        }

        public void onClickSheetitem1(View view) {
            if (mDisplaySettingsDialog == null) {
                mDisplaySettingsDialog = DisplaySettingsDialog.newInstance();
                mDisplaySettingsDialog.setSelectTextSize(mSelectedFontSize);
            }
            mDisplaySettingsDialog.show(getSupportFragmentManager(), "DisplaySettingsDialog");
            sheetViewOut();
        }

        public void onClickSheetitem2(View view) {
            Toast.makeText(NewsDetailActivity.this, "mSheetItem2TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        }

        public void onClickSheetitem3(View view) {
            NewsDataSource newsRepository = Injection.provideNewsRepository();
            newsRepository.saveNewsById(mNewsId);
            Toast.makeText(NewsDetailActivity.this, "mSheetItem3TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        }

        public void onClickSheetitem4(View view) {
            Toast.makeText(NewsDetailActivity.this, "mSheetItem4TextView Click", Toast.LENGTH_SHORT).show();
            sheetViewOut();
        }
    }
}
