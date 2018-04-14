package workshop1024.com.xproject.controller.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import workshop1024.com.xproject.controller.adapter.PublisherListAdapter;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.publisher.source.PublisherRepository;
import workshop1024.com.xproject.model.publisher.source.local.PublisherDatabase;
import workshop1024.com.xproject.model.publisher.source.local.PublisherLocalDataSource;
import workshop1024.com.xproject.model.publisher.source.remote.PublisherRemoteDataSource;
import workshop1024.com.xproject.utils.ExecutorUtils;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;
import workshop1024.com.xproject.view.dialog.SingleChoiceDialog;

/**
 * 发布者列表页面
 */
public class PublisherActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        PublisherDataSource.LoadPublishersCallback, SingleChoiceDialog.SingleChoiceDialogListener,
        PublisherListAdapter.OnPublisherListSelectListener {

    private CoordinatorLayout mRootView;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mPublisherSwipeRefreshLayout;
    private RecyclerView mPublisherRecyclerView;

    private SingleChoiceDialog mTypeChoiceDialog;
    private SingleChoiceDialog mLanguageChoiceDialog;
    private DialogFragment mSelectedDialog;

    private PublisherListAdapter mPublisherListAdapter;
    private PublisherRepository mPublisherRepository;

    //可选择的发布者
    private String[] mSelectTypeStrings;
    //可选择的语言
    private String[] mSelectLanguageStrings;
    //选择的发布者索引
    private int mSelectedTypeIndex = 0;
    //选择的语言索引
    private int mSelectedLanguageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        mRootView = findViewById(R.id.publisher_coordinatorlayout_rootview);
        mToolbar = findViewById(R.id.publisher_toolbar_navigator);
        mPublisherSwipeRefreshLayout = findViewById(R.id.publisher_swiperefreshlayout_pullrefresh);
        mPublisherRecyclerView = findViewById(R.id.publisher_recyclerview_list);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPublisherSwipeRefreshLayout.setOnRefreshListener(this);
        mPublisherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPublisherRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));

        //获取选择的发布者和语言字符串
        Resources resources = getResources();
        mSelectTypeStrings = resources.getStringArray(R.array.publisher_dialog_publishers);
        mSelectLanguageStrings = resources.getStringArray(R.array.language_dialog_languages);

        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        actionBar.setTitle(mSelectTypeStrings[mSelectedTypeIndex]);

        //使用默认选中的发布者类型请求发布者信息
        mPublisherRepository = PublisherRepository.getInstance(PublisherRemoteDataSource.getInstance(),
                PublisherLocalDataSource.getInstance(PublisherDatabase.getInstance(this).publisherDao(), new
                        ExecutorUtils()));
//        mPublisherRepository.refreshLimitedPublishers();
        mPublisherSwipeRefreshLayout.setRefreshing(true);
        mPublisherRepository.getPublishersByType(mSelectTypeStrings[mSelectedTypeIndex], this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.publisher_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publisher_menu_search:
                Toast.makeText(this, "publisher_menu_search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.publisher_menu_filter:
                //FIXME 每次都要创建一个对象吗？
                mTypeChoiceDialog = SingleChoiceDialog.newInstance(R.string.publisher_dialog_title,
                        mSelectTypeStrings, mSelectedTypeIndex);
                mTypeChoiceDialog.show(getSupportFragmentManager(), "ChoiceTypeDialog");
                break;
            case R.id.publisher_menu_language:
                mLanguageChoiceDialog = SingleChoiceDialog.newInstance(R.string.language_dialog_title,
                        mSelectLanguageStrings, mSelectedLanguageIndex);
                mLanguageChoiceDialog.show(getSupportFragmentManager(), "ChoiceLanguageDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mPublisherSwipeRefreshLayout.setRefreshing(true);
        mPublisherRepository.refreshPublishers(mSelectedDialog.getTag());
        if (mSelectedDialog == mTypeChoiceDialog) {
            mPublisherRepository.getPublishersByType(mSelectTypeStrings[mSelectedTypeIndex], this);
        } else if (mSelectedDialog == mLanguageChoiceDialog) {
            mPublisherRepository.getPublishersByLanguage(mSelectLanguageStrings[mSelectedLanguageIndex], this);
        }
    }

    @Override
    public void onSingleChoiceDialogItemClick(DialogFragment dialog, String selectItem) {
        mSelectedDialog = dialog;
        mToolbar.setTitle(selectItem);

        mPublisherSwipeRefreshLayout.setRefreshing(true);

        if (dialog == mTypeChoiceDialog) {
            mPublisherRepository.getPublishersByType(selectItem, this);
        } else if (dialog == mLanguageChoiceDialog) {
            mPublisherRepository.getPublishersByLanguage(selectItem, this);
        }
    }

    @Override
    public void publisherListItemSelect(Publisher selectPublisher, boolean isSelected) {
        if (isSelected) {
            Snackbar.make(mRootView, selectPublisher.getName() + " selected", Snackbar.LENGTH_SHORT).show();
            mPublisherRepository.subscribePublisherById(selectPublisher.getPublisherId());
        } else {
            Snackbar.make(mRootView, selectPublisher.getName() + " unselected", Snackbar.LENGTH_SHORT).show();
            mPublisherRepository.unSubscribePublisherById(selectPublisher.getPublisherId());
        }
    }

    @Override
    public void onPublishersLoaded(List<Publisher> publisherList) {
        //FIXME 每次都需要创建适配器吗？
        mPublisherSwipeRefreshLayout.setRefreshing(false);
        mPublisherListAdapter = new PublisherListAdapter(publisherList, this);
        mPublisherRecyclerView.setAdapter(mPublisherListAdapter);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
