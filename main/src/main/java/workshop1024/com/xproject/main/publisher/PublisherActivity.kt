package workshop1024.com.xproject.main.publisher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.controller.activity.XActivity
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.databinding.PublisherActivityBinding
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.other.view.dialog.TypeChoiceDialog
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.viewmodel.PublisherViewModel
import workshop1024.com.xproject.main.publisher.viewmodel.PublisherViewModelFactory

/**
 * 发布者列表页面
 */
class PublisherActivity : XActivity(), TypeChoiceDialog.TypeChoiceDialogListener {
    private var mTypeChoiceDialog: TypeChoiceDialog? = null
    private var mLanguageChoiceDialog: TypeChoiceDialog? = null

    lateinit var mPublisherActivityBinding: PublisherActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublisherActivityBinding = DataBindingUtil.setContentView(this, R.layout.publisher_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-mName-is-shown
        supportActionBar?.title = resources.getString(R.string.publisher_title_default)

        //使用范围函数run，传入对象this，返回参数lambda表达式结果
        //参考：https://www.kotlincn.net/docs/reference/scope-functions.html
        mPublisherActivityBinding.run {
            setSupportActionBar(publisherToolbarNavigator)

            publisherSwiperefreshlayoutPullrefresh.isEnabled = false
            publisherRecyclerviewList.addItemDecoration(RecyclerViewItemDecoration(6))

            //访问外部作用域的this，使用@label指代来源标签
            //参考：限定的this，https://www.kotlincn.net/docs/reference/this-expressions.html
            lifecycleOwner = this@PublisherActivity

            //Smart cast to 'PublisherViewModel' is impossible, because 'mPublisherActivityBinding.publisherviewmodel' is a complex expression
            publisherviewmodel = ViewModelProviders.of(this@PublisherActivity, PublisherViewModelFactory.
                    getInstance(application)).get(PublisherViewModel::class.java).apply {
                //匿名内部类：在JVM平台，如果对象时函数式Java接口（即具有单个抽象方法的Java接口）的实例，可以使用带接口类型前缀的lambda表达式创建它
                //参考：https://www.kotlincn.net/docs/reference/nested-classes.html
                mSnackMessage.observe(this@PublisherActivity, Observer<Event<String>> {
                    it.getContentIfNotHandled().let {
                        it?.let { Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show() }
                    }
                })

                //先临时创建"空数据的adapter"传入publisherviewmodel，数据返回了在更新
                publisherRecyclerviewList.adapter = PublisherListAdapter(emptyList(), this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.publisher_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val publisherViewModel = mPublisherActivityBinding.publisherviewmodel!!
        when (item?.itemId) {
            R.id.publisher_menu_search ->
                publisherViewModel.searchMenuSelected()
            R.id.publisher_menu_filter -> {
                publisherViewModel.mContentTypeList?.let {
                    mTypeChoiceDialog = TypeChoiceDialog.newInstance(R.string.publisher_dialog_title,
                            it, publisherViewModel.mSelectedTypeIndex).apply {
                        show(supportFragmentManager, "ChoiceTypeDialog")
                    }
                }

            }
            R.id.publisher_menu_language -> {
                publisherViewModel.mLanguageTypeList?.let {
                    mLanguageChoiceDialog = TypeChoiceDialog.newInstance(R.string.language_dialog_title,
                            it, publisherViewModel.mSelectedLanguageIndex).apply {
                        show(supportFragmentManager, "ChoiceLanguageDialog")
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onTypeChoiceDialogItemClick(dialog: DialogFragment, selectedIndex: Int, publisherType: PublisherType) {
        //Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type PublisherViewModel?
        val publisherViewModel = mPublisherActivityBinding.publisherviewmodel!!
        publisherViewModel.updateTitleText(publisherType.mName)
        if (mTypeChoiceDialog != null && dialog === mTypeChoiceDialog) {
            publisherViewModel.getPublishersByContentType(publisherType.mTypeId)
            publisherViewModel.mSelectedTypeIndex = selectedIndex
        } else if (mLanguageChoiceDialog != null && dialog === mLanguageChoiceDialog) {
            publisherViewModel.getPublishersByLanguageType(publisherType.mTypeId)
            publisherViewModel.mSelectedLanguageIndex = selectedIndex
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, PublisherActivity::class.java)
            context.startActivity(intent)
        }
    }
}