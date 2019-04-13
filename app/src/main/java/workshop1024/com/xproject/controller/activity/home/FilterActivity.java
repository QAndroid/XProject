package workshop1024.com.xproject.controller.activity.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.FilterListAdapter;
import workshop1024.com.xproject.databinding.FilterActivityBinding;
import workshop1024.com.xproject.model.filter.Filter;
import workshop1024.com.xproject.model.filter.source.FilterDataSource;
import workshop1024.com.xproject.model.filter.source.FilterRepository;
import workshop1024.com.xproject.view.dialog.InputStringDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class FilterActivity extends XActivity implements SwipeRefreshLayout.OnRefreshListener,
        InputStringDialog.InputStringDialogListener, FilterDataSource.LoadFiltersCallback,
        FilterListAdapter.OnFilterListDeleteListener {

    private FilterRepository mFilterRepository;
    private FilterListAdapter mFilterListAdapter;

    private FilterActivityBinding mFilterActivityBinding;

    /**
     * 启动FilterActivity页面
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FilterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterActivityBinding = DataBindingUtil.setContentView(this, R.layout.filter_activity);

        setSupportActionBar(mFilterActivityBinding.filterToolbarNavigator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage Filter");

        mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.setOnRefreshListener(this);
        mFilterActivityBinding.filterRecyclerviewList.addItemDecoration(new RecyclerViewItemDecoration(6));
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshFilterList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_menu_add:
                Toast.makeText(this, "filter_menu_add", Toast.LENGTH_SHORT).show();
                InputStringDialog addFilterDialog = InputStringDialog.newInstance(R.string.addfilter_dialog_title, R.string
                        .addfilter_dialog_positive);
                addFilterDialog.setInputStringDialogListener(this);
                addFilterDialog.show(getSupportFragmentManager(), "addFilterDialog");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        refreshFilterList();
    }

    @Override
    public void onInputStringDialogClick(DialogFragment dialog, String inputString) {
        mFilterRepository.addFilterByName(inputString);
        refreshFilterList();
    }

    @Override
    public void onPublishersLoaded(List<Filter> filterList) {
        if (mIsForeground) {
            mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.setRefreshing(false);
            mFilterListAdapter = new FilterListAdapter(filterList, this);
            mFilterActivityBinding.filterRecyclerviewList.setAdapter(mFilterListAdapter);
        }
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void filterListItemDelete(Filter filter) {
        mFilterRepository.deleteFilterById(filter.getFilterId());
        refreshFilterList();
    }

    private void refreshFilterList() {
        mFilterRepository = FilterRepository.getInstance();
        mFilterActivityBinding.filterSwiperefreshlayoutPullrefresh.setRefreshing(true);
        mFilterRepository.getFilters(this);
    }
}
