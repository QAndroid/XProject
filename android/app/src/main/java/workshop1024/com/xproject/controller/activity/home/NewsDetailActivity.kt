package workshop1024.com.xproject.controller.activity.home

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.XActivity
import workshop1024.com.xproject.controller.adapter.TagListAdapter
import workshop1024.com.xproject.databinding.NewsdetailActivityBinding
import workshop1024.com.xproject.model.Injection
import workshop1024.com.xproject.model.news.NewsDetail
import workshop1024.com.xproject.model.news.source.NewsDataSource
import workshop1024.com.xproject.utils.UnitUtils
import workshop1024.com.xproject.utils.ViewUtils
import workshop1024.com.xproject.view.dialog.DisplaySettingsDialog
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration
import kotlin.math.hypot

class NewsDetailActivity : XActivity(), NewsDataSource.LoadNewsDetailCallBack, DisplaySettingsDialog.DisplaySettingsDialogListener {
    private var mDisplaySettingsDialog: DisplaySettingsDialog? = null

    private var mNewsId: String? = null
    private var mSelectedFontSize: Float = 0.0f
    private var mNewsRepository: NewsDataSource? = null
    private lateinit var mSharedPreferences: SharedPreferences

    private lateinit var mNewsdetailActivityBinding: NewsdetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNewsdetailActivityBinding = DataBindingUtil.setContentView(this, R.layout.newsdetail_activity)
        mNewsdetailActivityBinding.newsDetailHandlers = NewsDetailHandlers()

        setSupportActionBar(mNewsdetailActivityBinding.detailToolbarNavigator)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val fontSizeString = mSharedPreferences.getString(getString(R.string.settings_preference_fontsizes_key),
                getString(R.string.settings_preference_fontsizes_default)) as String
        mSelectedFontSize = fontSizeString.toFloat()
        mNewsdetailActivityBinding.newsdetailTextviewContent.textSize = UnitUtils.spToPx(this, mSelectedFontSize).toFloat()

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
        mNewsdetailActivityBinding.newsdetailRecyclerViewTags.layoutManager = flexboxLayoutManager
        mNewsdetailActivityBinding.newsdetailRecyclerViewTags.addItemDecoration(RecyclerViewItemDecoration(UnitUtils.dpToPx(this, 4f)))
    }

    override fun onStart() {
        super.onStart()

        mNewsId = intent.getStringExtra(NEWS_ID_KEY)
        mNewsRepository = Injection.provideNewsRepository()
        mNewsRepository?.getNewsDetailByNewsId(mNewsId!!, this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //拦截Home按钮事件，关闭当前Activity，返回上一个Activity-即MainActivity
        when (item?.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNewsDetailLoaded(newsDetail: NewsDetail) {
        if (mIsForeground) {
            mNewsdetailActivityBinding.newsdetailTextviewPublisher.text = newsDetail.publisher
            mNewsdetailActivityBinding.newsdetailTextviewPubdata.text = newsDetail.pubDate
            mNewsdetailActivityBinding.newsdetailTextviewContent.text = newsDetail.content

            val tagListAdapter = TagListAdapter(this, newsDetail.tagList!!)
            mNewsdetailActivityBinding.newsdetailRecyclerViewTags.adapter = tagListAdapter
        }
    }

    override fun onDataNotAvaiable() {

    }

    override fun onDisplaySettingDialogClick(dialogFragment: DialogFragment, textSize: Float) {
        mSelectedFontSize = textSize
        mNewsdetailActivityBinding.newsdetailTextviewContent.textSize = UnitUtils.spToPx(this, mSelectedFontSize).toFloat()

        val editor = mSharedPreferences.edit()
        editor?.putString(getString(R.string.settings_preference_fontsizes_key), textSize.toString())
        editor?.commit()
    }

    private fun sheetViewIn() {
        mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.animate().scaleX(1.2f).scaleY(1.2f).translationX(getTranslationX(mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction, mNewsdetailActivityBinding.newsdetailCardviewSheet)).translationY(getTranslationY(mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction, mNewsdetailActivityBinding.newsdetailCardviewSheet)).setInterpolator(AccelerateInterpolator()).setDuration(300).setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                mNewsdetailActivityBinding.newsdetailViewOverlay.visibility = View.VISIBLE
                mNewsdetailActivityBinding.newsdetailViewOverlay.animate().alpha(1f).setDuration(300).setInterpolator(AccelerateDecelerateInterpolator()).start()
            }

            override fun onAnimationEnd(animation: Animator) {
                mNewsdetailActivityBinding.newsdetailCardviewSheet.visibility = View.VISIBLE
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.visibility = View.INVISIBLE

                val animator = ViewAnimationUtils.createCircularReveal(mNewsdetailActivityBinding.newsdetailCardviewSheet,
                        getCenterX(mNewsdetailActivityBinding.newsdetailCardviewSheet), getCenterY(mNewsdetailActivityBinding.newsdetailCardviewSheet),
                        (mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.width / 2).toFloat(),
                        hypot(mNewsdetailActivityBinding.newsdetailCardviewSheet.width.toDouble(), mNewsdetailActivityBinding.newsdetailCardviewSheet.height.toDouble()).toFloat() / 2)
                animator.interpolator = DecelerateInterpolator()
                animator.duration = 300
                animator.start()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        }).start()
    }

    private fun sheetViewOut() {
        val animator = ViewAnimationUtils.createCircularReveal(mNewsdetailActivityBinding.newsdetailCardviewSheet,
                getCenterX(mNewsdetailActivityBinding.newsdetailCardviewSheet), getCenterY(mNewsdetailActivityBinding.newsdetailCardviewSheet),
                hypot(mNewsdetailActivityBinding.newsdetailCardviewSheet.width.toDouble(), mNewsdetailActivityBinding.newsdetailCardviewSheet.height.toDouble()).toFloat() / 2, (mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.width / 2).toFloat())
        animator.interpolator = DecelerateInterpolator()
        animator.duration = 300
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                mNewsdetailActivityBinding.newsdetailCardviewSheet.visibility = View.INVISIBLE
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.visibility = View.VISIBLE
                mNewsdetailActivityBinding.newsdetailFloatingactionbuttonAction.animate().scaleX(1f).scaleY(1f).translationX(0f).translationY(0f).setInterpolator(AccelerateInterpolator()).setDuration(300).setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        mNewsdetailActivityBinding.newsdetailViewOverlay.animate().alpha(0f).setDuration(500).setInterpolator(AccelerateDecelerateInterpolator()).start()
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        mNewsdetailActivityBinding.newsdetailViewOverlay.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                }).start()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        animator.start()
    }

    private fun getTranslationX(floatingActionButton: FloatingActionButton, sheetCardView: CardView): Float {
        val floatingActionButtonX = ViewUtils.getRelativeLeft(floatingActionButton) + floatingActionButton.width / 2
        val mSheetCardViewX = ViewUtils.getRelativeLeft(sheetCardView) + sheetCardView.width / 2
        return (mSheetCardViewX - floatingActionButtonX).toFloat()
    }

    private fun getTranslationY(floatingActionButton: FloatingActionButton, sheetCardView: CardView): Float {
        val floatingActionButtonY = ViewUtils.getRelativeTop(floatingActionButton) + floatingActionButton.height / 2
        val mSheetCardViewY = ViewUtils.getRelativeTop(sheetCardView) + sheetCardView.height / 2
        return (mSheetCardViewY - floatingActionButtonY).toFloat()
    }

    private fun getCenterX(sheetCardView: CardView): Int {
        return sheetCardView.width / 2 + sheetCardView.left
    }

    private fun getCenterY(sheetCardView: CardView): Int {
        return sheetCardView.height / 2 + sheetCardView.top
    }

    inner class NewsDetailHandlers {
        fun onClickFloatingActionButton(view: View) {
            sheetViewIn()
        }

        fun onClickViewOverlay(view: View) {
            sheetViewOut()
        }

        fun onClickSheetitem1(view: View) {
            if (mDisplaySettingsDialog == null) {
                mDisplaySettingsDialog = DisplaySettingsDialog.newInstance()
                mDisplaySettingsDialog?.mSelectTextSize = mSelectedFontSize
            }

            mDisplaySettingsDialog?.show(supportFragmentManager, "DisplaySettingsDialog")
            sheetViewOut()
        }

        fun onClickSheetitem2(view: View) {
            Toast.makeText(this@NewsDetailActivity, "mSheetItem2TextView Click", Toast.LENGTH_SHORT).show()
            sheetViewOut()
        }

        fun onClickSheetitem3(view: View) {
            val newsRepository = Injection.provideNewsRepository()
            newsRepository.saveNewsById(mNewsId!!)
            Toast.makeText(this@NewsDetailActivity, "mSheetItem3TextView Click", Toast.LENGTH_SHORT).show()
            sheetViewOut()
        }

        fun onClickSheetitem4(view: View) {
            Toast.makeText(this@NewsDetailActivity, "mSheetItem4TextView Click", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val NEWS_ID_KEY = "newsId"

        fun startActivity(context: Context, newsId: String) {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(NEWS_ID_KEY, newsId)
            context.startActivity(intent)
        }
    }
}