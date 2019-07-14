package workshop1024.com.xproject.controller.activity.home

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.XActivity
import workshop1024.com.xproject.controller.adapter.FilterListAdapter
import workshop1024.com.xproject.databinding.FilterActivityBinding
import workshop1024.com.xproject.model.filter.Filter
import workshop1024.com.xproject.model.filter.source.FilterDataSource
import workshop1024.com.xproject.model.filter.source.FilterRepository
import workshop1024.com.xproject.view.dialog.InputStringDialog
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration

class FilterActivity : XActivity(), SwipeRefreshLayout.OnRefreshListener, InputStringDialog.InputStringDialogListener,
        FilterDataSource.LoadFiltersCallback, FilterListAdapter.OnFilterListDeleteListener {

    private var mFilterRepository: FilterRepository? = null
    private var mFilterListAdapter: FilterListAdapter? = null

    private var mFilterActivityBinding: FilterActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFilterActivityBinding = DataBindingUtil.setContentView(this, R.layout.filter_activity)

        setSupportActionBar(mFilterActivityBinding?.filterToolbarNavigator)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Manage Filter"

        mFilterActivityBinding?.filterSwiperefreshlayoutPullrefresh?.setOnRefreshListener(this)
        mFilterActivityBinding?.filterRecyclerviewList?.addItemDecoration(RecyclerViewItemDecoration(6))
    }

    override fun onStart() {
        super.onStart()
        refreshFilterList()
    }

    private fun refreshFilterList() {
        mFilterRepository = FilterRepository.getInstance()
        mFilterActivityBinding?.filterSwiperefreshlayoutPullrefresh?.isRefreshing = true
        mFilterRepository?.getFilters(this)

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
                addFilterDialog.setInputStringDialogListener(this)
                addFilterDialog.show(supportFragmentManager, "addFilterDialog")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        refreshFilterList()
    }

    override fun onInputStringDialogClick(dialog: DialogFragment?, inputString: String?) {
        mFilterRepository?.addFilterByName(inputString!!)
        refreshFilterList()
    }

    override fun onPublishersLoaded(filterList: List<Filter>) {
        if (mIsForeground) {
            mFilterActivityBinding?.filterSwiperefreshlayoutPullrefresh?.isRefreshing = false;
            mFilterListAdapter = FilterListAdapter(filterList, this)
            mFilterActivityBinding?.filterRecyclerviewList?.adapter = mFilterListAdapter
        }
    }

    override fun onDataNotAvailable() {

    }

    override fun filterListItemDelete(filter: Filter?) {
        mFilterRepository?.deleteFilterById(filter?.filterId!!)
        refreshFilterList()
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