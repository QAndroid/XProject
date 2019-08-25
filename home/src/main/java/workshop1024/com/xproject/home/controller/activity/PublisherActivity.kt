package workshop1024.com.xproject.home.controller.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import workshop1024.com.xproject.base.controller.activity.XActivity
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.home.R
import workshop1024.com.xproject.home.controller.adapter.PublisherListAdapter
import workshop1024.com.xproject.home.databinding.PublisherActivityBinding
import workshop1024.com.xproject.home.model.publisher.Publisher
import workshop1024.com.xproject.home.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.home.model.publishertype.PublisherType
import workshop1024.com.xproject.home.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.home.view.dialog.TypeChoiceDialog
import workshop1024.com.xproject.model.Injection

/**
 * 发布者列表页面
 */
class PublisherActivity : XActivity(), SwipeRefreshLayout.OnRefreshListener,
        PublisherDataSource.LoadPublishersCallback, PublisherTypeDataSource.LoadPublisherTypeCallback,
        TypeChoiceDialog.TypeChoiceDialogListener, PublisherListAdapter.OnPublisherListSelectListener {

    private var mTypeChoiceDialog: TypeChoiceDialog? = null
    private var mLanguageChoiceDialog: TypeChoiceDialog? = null
    private var mSelectedDialog: DialogFragment? = null

    private var mPublisherListAdapter: PublisherListAdapter? = null

    private var mPublisherDataSource: PublisherDataSource? = null
    private var mPublisherTypeDataSource: PublisherTypeDataSource? = null

    //可选择发布者内容类型
    private var mContentTypeList: ArrayList<PublisherType>? = null
    //可选择发布者语言类型
    private var mLanguageTypeList: ArrayList<PublisherType>? = null
    //选择的发布者索引
    private val mSelectedTypeIndex = 0
    //选择的语言索引
    private val mSelectedLanguageIndex = 0

    private var mPublisherActivityBinding: PublisherActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublisherActivityBinding = DataBindingUtil.setContentView(this, R.layout.publisher_activity)

        setSupportActionBar(mPublisherActivityBinding!!.publisherToolbarNavigator)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        mPublisherActivityBinding?.publisherSwiperefreshlayoutPullrefresh?.setOnRefreshListener(this)
        mPublisherActivityBinding?.publisherRecyclerviewList?.addItemDecoration(RecyclerViewItemDecoration(6))

        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        actionBar.title = resources.getString(R.string.publisher_title_default)
    }

    override fun onStart() {
        super.onStart()

        mPublisherTypeDataSource = Injection.providePublisherTypeRepository()
        mPublisherTypeDataSource?.getPublisherContentTypes(this)
        mPublisherTypeDataSource?.getPublisherLanguageTypes(this)
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

    override fun onRefresh() {
        mPublisherActivityBinding?.publisherSwiperefreshlayoutPullrefresh?.isRefreshing = true
        if (mSelectedDialog === mTypeChoiceDialog) {
            mPublisherDataSource?.getPublishersByContentType(mContentTypeList!![mSelectedTypeIndex].typeId, this)
        } else if (mSelectedDialog === mLanguageChoiceDialog) {
            mPublisherDataSource?.getPublishersByLanguageType(mLanguageTypeList!![mSelectedLanguageIndex].typeId, this)
        }
    }

    override fun onPublishersLoaded(publisherList: List<Publisher>) {
        //FIXME 每次都需要创建适配器吗？
        if (mIsForeground) {
            mPublisherActivityBinding!!.publisherSwiperefreshlayoutPullrefresh.isRefreshing = false
            mPublisherListAdapter = PublisherListAdapter(publisherList, this)
            mPublisherActivityBinding!!.publisherRecyclerviewList.adapter = mPublisherListAdapter
        }
    }

    override fun onDataNotAvailable() {

    }

    override fun onPublisherTypesLoaded(publisherTypeList: List<PublisherType>, type: String) {
        //TODO type equals很别扭
        if (type == "content") {
            mContentTypeList = publisherTypeList as ArrayList<PublisherType>
            //使用默认选中的发布者类型请求发布者信息
            mPublisherDataSource = Injection.providePublisherRepository()
            mPublisherActivityBinding?.publisherSwiperefreshlayoutPullrefresh?.isRefreshing = true
            mPublisherDataSource?.getPublishersByContentType(mContentTypeList!![mSelectedTypeIndex].typeId, this)
        } else {
            mLanguageTypeList = publisherTypeList as ArrayList<PublisherType>
        }
    }

    override fun onTypeChoiceDialogItemClick(dialog: DialogFragment, publisherType: PublisherType) {
        mSelectedDialog = dialog
        mPublisherActivityBinding?.publisherToolbarNavigator?.title = publisherType?.name

        mPublisherActivityBinding?.publisherSwiperefreshlayoutPullrefresh?.isRefreshing = true
        if (dialog === mTypeChoiceDialog) {
            mPublisherDataSource?.getPublishersByContentType(publisherType?.typeId!!, this)
        } else if (dialog === mLanguageChoiceDialog) {
            mPublisherDataSource?.getPublishersByLanguageType(publisherType?.typeId!!, this)
        }
    }

    override fun onPublisherListItemSelect(selectPublisher: Publisher, isSelected: Boolean) {
        if (isSelected) {
            //FIXME 订阅网络请求返回后，在提示并且选中
            mPublisherDataSource?.subscribePublisherById(selectPublisher?.publisherId!!)
            Snackbar.make(mPublisherActivityBinding!!.root, selectPublisher?.name + " selected", Snackbar.LENGTH_SHORT).show()
        } else {
            mPublisherDataSource!!.unSubscribePublisherById(selectPublisher?.publisherId!!)
            Snackbar.make(mPublisherActivityBinding!!.root, selectPublisher.name + " unselected", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, PublisherActivity::class.java)
            context.startActivity(intent)
        }
    }
}