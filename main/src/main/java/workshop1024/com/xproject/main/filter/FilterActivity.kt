package workshop1024.com.xproject.main.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import workshop1024.com.xproject.base.controller.activity.XActivity
import workshop1024.com.xproject.base.view.dialog.InputStringDialog
import workshop1024.com.xproject.base.view.recyclerview.RecyclerViewItemDecoration
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.databinding.FilterActivityBinding
import workshop1024.com.xproject.main.model.Injection
import workshop1024.com.xproject.main.filter.data.Filter
import workshop1024.com.xproject.main.filter.data.source.FilterDataSource
import java.util.*

class FilterActivity : XActivity(), InputStringDialog.InputStringDialogListener,
        FilterDataSource.LoadFiltersCallback, FilterListAdapter.OnFilterListDeleteListener {

    private lateinit var mFilterRepository: FilterDataSource
    private var mFilterListAdapter: FilterListAdapter? = null

    private lateinit var mFilterActivityBinding: FilterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFilterActivityBinding = DataBindingUtil.setContentView(this, R.layout.filter_activity)

        setSupportActionBar(mFilterActivityBinding.filterToolbarNavigator)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Manage Filter"

        mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.isEnabled = false
        mFilterActivityBinding.filterRecyclerviewList.addItemDecoration(RecyclerViewItemDecoration(6))

        mFilterRepository = Injection.provideFilterRepository(this)
    }

    override fun onStart() {
        super.onStart()
        refreshFilter()
    }

    private fun refreshFilter() {
        mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.isRefreshing = true
        mFilterRepository.getFilters(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.filter_menu_add -> {
                Toast.makeText(this, "filter_menu_add", Toast.LENGTH_SHORT).show()
                val addFilterDialog = InputStringDialog.newInstance(R.string.addfilter_dialog_title,
                        R.string.addfilter_dialog_positive)
                addFilterDialog.mInputStringDialogListener = this
                addFilterDialog.show(supportFragmentManager, "addFilterDialog")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInputStringDialogClick(dialog: DialogFragment, inputString: String) {
        //TODO 在构造函数中自己生成唯一id
        val filter = Filter(UUID.randomUUID().toString(), inputString)
        mFilterRepository.addFilter(filter)
        refreshFilter()
    }

    override fun onRemoteFiltersLoaded(filterList: List<Filter>) {
        refreshFilterList(filterList)
        Snackbar.make(mFilterActivityBinding.root, "Fetch remote " + filterList.size + " filters ...", Snackbar.LENGTH_SHORT).show()
        mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.isRefreshing = false;
    }

    override fun onCacheOrLocalFiltersLoaded(filterList: List<Filter>) {
        refreshFilterList(filterList)
        Snackbar.make(mFilterActivityBinding.root, "Fetch cacheorlocal " + filterList.size + " filters ...", Snackbar.LENGTH_SHORT).show()
        if (!mFilterRepository.getIsRequestRemote()) {
            mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.isRefreshing = false;
        }
    }

    private fun refreshFilterList(filterList: List<Filter>) {
        mFilterListAdapter = FilterListAdapter(filterList, this)
        mFilterActivityBinding.filterRecyclerviewList.adapter = mFilterListAdapter
    }

    override fun onDataNotAvailable() {

    }

    override fun filterListItemDelete(filter: Filter) {
        mFilterRepository.deleteFilterById(filter.mFilterId)
        refreshFilter()
    }

    companion object {

        /**
         * 启动FilterActivity页面
         *
         * @param context
         */
        fun startActivity(context: Context) {
            val intent = Intent(context, FilterActivity::class.java)
            context.startActivity(intent)
        }
    }
}