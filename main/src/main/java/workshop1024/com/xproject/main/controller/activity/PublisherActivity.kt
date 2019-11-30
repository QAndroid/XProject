package workshop1024.com.xproject.main.controller.activity

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
import workshop1024.com.xproject.main.controller.adapter.PublisherListAdapter
import workshop1024.com.xproject.main.controller.idlingResource.PublisherIdlingResouce
import workshop1024.com.xproject.main.databinding.PublisherActivityBinding
import workshop1024.com.xproject.main.model.Injection
import workshop1024.com.xproject.main.model.publisher.Publisher
import workshop1024.com.xproject.main.model.publisher.PublisherType
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.main.view.dialog.TypeChoiceDialog

/**
 * 发布者列表页面
 */
class PublisherActivity : XActivity(), PublisherDataSource.LoadPublisherAndPublisherTypeCallback, TypeChoiceDialog.TypeChoiceDialogListener, PublisherListAdapter.OnPublisherListSelectListener {

    private var mTypeChoiceDialog: TypeChoiceDialog? = null
    private var mLanguageChoiceDialog: TypeChoiceDialog? = null
    private var mSelectedDialog: DialogFragment? = null

    private var mPublisherListAdapter: PublisherListAdapter? = null

    private lateinit var mPublisherRepository: PublisherDataSource

    //可选择发布者内容类型
    private var mContentTypeList: ArrayList<PublisherType>? = null
    //可选择发布者语言类型
    private var mLanguageTypeList: ArrayList<PublisherType>? = null
    //选择的发布者索引
    private val mSelectedTypeIndex = 0
    //选择的语言索引
    private val mSelectedLanguageIndex = 0

    private lateinit var mPublisherActivityBinding: PublisherActivityBinding

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

        mPublisherRepository = Injection.providePublisherRepository(this)
    }

    override fun onStart() {
        super.onStart()
        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = true
        //开始请求发布者列表，IdlingResouce设置为false，等待异步执行完毕
        mPublisherIdlingResouce?.setIdleState(false)
        mPublisherRepository.getPublishersAndPublisherTypes(this)
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

    override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
        //FIXME 每次都需要创建适配器吗？
        refreshPublisherList(publisherList)
        Snackbar.make(mPublisherActivityBinding.root, "Fetch cacheorlocal " + publisherList.size + " publishers ...", Snackbar.LENGTH_SHORT).show()
        if (!mPublisherRepository.getIsRequestRemote()) {
            mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = false
        }
        mPublisherIdlingResouce?.setIdleState(true)
    }

    override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
        refreshPublisherList(publisherList)
        Snackbar.make(mPublisherActivityBinding.root, "Fetch remote " + publisherList.size + " publishers ...", Snackbar.LENGTH_SHORT).show()
        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = false
        //请求发布者类表完毕，IdlingResouce设置为true，执行后面异步指令
        mPublisherIdlingResouce?.setIdleState(true)
    }


    override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        refreshPublisherTypeList(contentTypeList, languageTyleList)
    }

    override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        refreshPublisherTypeList(contentTypeList, languageTyleList)
    }

    private fun refreshPublisherTypeList(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mContentTypeList = contentTypeList as ArrayList<PublisherType>
        mLanguageTypeList = languageTyleList as ArrayList<PublisherType>
    }

    private fun refreshPublisherList(publisherList: List<Publisher>) {
        mPublisherListAdapter = PublisherListAdapter(publisherList, this)
        mPublisherActivityBinding.publisherRecyclerviewList.adapter = mPublisherListAdapter
    }

    override fun onDataNotAvailable() {

    }

    override fun onTypeChoiceDialogItemClick(dialog: DialogFragment, publisherType: PublisherType) {
        mSelectedDialog = dialog
        mPublisherActivityBinding.publisherToolbarNavigator.title = publisherType.mName

        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.isRefreshing = true
        if (dialog === mTypeChoiceDialog) {
            mPublisherRepository.getPublishersByContentType(publisherType.mTypeId, this)
        } else if (dialog === mLanguageChoiceDialog) {
            mPublisherRepository.getPublishersByLanguageType(publisherType.mTypeId, this)
        }
    }

    override fun onPublisherListItemSelect(selectPublisher: Publisher, isSelected: Boolean) {
        if (isSelected) {
            //FIXME 订阅网络请求返回后，在提示并且选中
            mPublisherRepository.subscribePublisherById(selectPublisher.mPublisherId)
            Snackbar.make(mPublisherActivityBinding.root, selectPublisher.mName + " selected", Snackbar.LENGTH_SHORT).show()
        } else {
            mPublisherRepository.unSubscribePublisherById(selectPublisher.mPublisherId)
            Snackbar.make(mPublisherActivityBinding.root, selectPublisher.mName + " unselected", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, PublisherActivity::class.java)
            context.startActivity(intent)
        }
    }
}