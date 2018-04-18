package workshop1024.com.xproject.controller.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.FilterListAdapter;
import workshop1024.com.xproject.model.filter.Filter;
import workshop1024.com.xproject.model.filter.source.FilterDataSource;
import workshop1024.com.xproject.model.filter.source.FilterRepository;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;
import workshop1024.com.xproject.view.dialog.InputStringDialog;

public class FilterActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        InputStringDialog.InputStringDialogListener, FilterDataSource.LoadFiltersCallback,
        FilterListAdapter.OnFilterListDeleteListener {
    private CoordinatorLayout mRootView;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mFilterSwipeRefreshLayout;
    private RecyclerView mFilterRecyclerView;

    private FilterRepository mFilterRepository;
    private FilterListAdapter mFilterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mRootView = findViewById(R.id.filter_coordinatorlayout_rootview);
        mToolbar = findViewById(R.id.filter_toolbar_navigator);
        mFilterSwipeRefreshLayout = findViewById(R.id.filter_swiperefreshlayout_pullrefresh);
        mFilterRecyclerView = findViewById(R.id.filter_recyclerview_list);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage Filter");

        mFilterSwipeRefreshLayout.setOnRefreshListener(this);
        mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFilterRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));
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
        mFilterSwipeRefreshLayout.setRefreshing(false);
        mFilterListAdapter = new FilterListAdapter(filterList, this);
        mFilterRecyclerView.setAdapter(mFilterListAdapter);
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
        mFilterSwipeRefreshLayout.setRefreshing(true);
        mFilterRepository.getFilters(this);
    }
    }
