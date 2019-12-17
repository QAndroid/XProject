package workshop1024.com.xproject.main.publisher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.controller.activity.XActivity
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.other.idlingResource.PublisherIdlingResouce
import workshop1024.com.xproject.main.databinding.PublisherActivityBinding
import workshop1024.com.xproject.main.model.Injection
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.other.view.dialog.TypeChoiceDialog

/**
 * 发布者列表页面
 */
class PublisherActivity : XActivity(), TypeChoiceDialog.TypeChoiceDialogListener,
        PublisherListAdapter.OnPublisherListSelectListener, PublisherContract.View {

    private var mTypeChoiceDialog: TypeChoiceDialog? = null
    private var mLanguageChoiceDialog: TypeChoiceDialog? = null
    private var mSelectedDialog: DialogFragment? = null

    private var mPublisherListAdapter: PublisherListAdapter? = null

    //可选择发布者内容类型
    private var mContentTypeList: ArrayList<PublisherType>? = null
    //可选择发布者语言类型
    private var mLanguageTypeList: ArrayList<PublisherType>? = null
    //选择的发布者索引
    private val mSelectedTypeIndex = 0
    //选择的语言索引
    private val mSelectedLanguageIndex = 0

    private lateinit var mPublisherActivityBinding: PublisherActivityBinding

    private lateinit var mPublisherPresenter: PublisherPresenter

    //发布者dlingResouce，检测请求发布者列表异步任务
    @VisibleForTesting
    var mPublisherIdlingResouce: PublisherIdlingResouce? = null
        get() {
            if (field == null) {
                field = PublisherIdlingResouce()
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublisherActivityBinding = DataBindingUtil.setContentView(this, R.layout.publisher_activity)

        setSupportActionBar(mPublisherActivityBinding.publisherToolbarNavigator)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isEnabled = false
        mPublisherActivityBinding.publisherRecyclerviewList.addItemDecoration(RecyclerViewItemDecoration(6))

        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-mName-is-shown
        actionBar.title = resources.getString(R.string.publisher_title_default)

        mPublisherPresenter = PublisherPresenter(Injection.providePublisherRepository(this), this)
    }

    override fun onStart() {
        super.onStart()
        mPublisherPresenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.publisher_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.publisher_menu_search -> Toast.makeText(this, "publisher_menu_search", Toast.LENGTH_SHORT).show()
            R.id.publisher_menu_filter -> {
                //FIXME 每次都要创建一个对象吗？
                mTypeChoiceDialog = TypeChoiceDialog.newInstance(R.string.publisher_dialog_title,
                        mContentTypeList!!, mSelectedTypeIndex)
                mTypeChoiceDialog!!.show(supportFragmentManager, "ChoiceTypeDialog")
            }
            R.id.publisher_menu_language -> {
                mLanguageChoiceDialog = TypeChoiceDialog.newInstance(R.string.language_dialog_title,
                        mLanguageTypeList!!, mSelectedLanguageIndex)
                mLanguageChoiceDialog!!.show(supportFragmentManager, "ChoiceLanguageDialog")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onTypeChoiceDialogItemClick(dialog: DialogFragment, publisherType: PublisherType) {
        mSelectedDialog = dialog
        mPublisherActivityBinding.publisherToolbarNavigator.title = publisherType.mName

        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = true
        if (dialog === mTypeChoiceDialog) {
            mPublisherPresenter.getPublishersByContentType(publisherType.mTypeId)
        } else if (dialog === mLanguageChoiceDialog) {
            mPublisherPresenter.getPublishersByLanguageType(publisherType.mTypeId)
        }
    }

    override fun onPublisherListItemSelect(selectPublisher: Publisher, isSelected: Boolean) {
        if (isSelected) {
            //FIXME 订阅网络请求返回后，在提示并且选中
            mPublisherPresenter.subscribePublisher(selectPublisher)
        } else {
            mPublisherPresenter.unSubscribePublisher(selectPublisher)
        }
    }

    override fun setLoadingIndicator(isLoaing: Boolean) {
        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = isLoaing
    }

    override fun refreshPublisherList(publisherList: List<Publisher>) {
        mPublisherListAdapter = PublisherListAdapter(publisherList, this)
        mPublisherActivityBinding.publisherRecyclerviewList.adapter = mPublisherListAdapter
    }

    override fun showSnackBar(mMessage: String) {
        Snackbar.make(mPublisherActivityBinding.root, mMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun setPublisherIdlingResouce(isIdling: Boolean) {
        mPublisherIdlingResouce?.setIdleState(isIdling)
    }

    override fun refreshPublisherTypeList(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mContentTypeList = contentTypeList as ArrayList<PublisherType>
        mLanguageTypeList = languageTyleList as ArrayList<PublisherType>
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, PublisherActivity::class.java)
            context.startActivity(intent)
        }
    }
}