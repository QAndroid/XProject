package workshop1024.com.xproject.controller.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.PublisherListAdapter;
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.publishertype.PublisherType;
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeDataSource;
import workshop1024.com.xproject.view.dialog.TypeChoiceDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

/**
 * 发布者列表页面
 */
public class PublisherActivity extends XActivity implements SwipeRefreshLayout.OnRefreshListener,
        PublisherDataSource.LoadPublishersCallback, PublisherTypeDataSource.LoadPublisherTypeCallback,
        TypeChoiceDialog.TypeChoiceDialogListener, PublisherListAdapter.OnPublisherListSelectListener {

    private TypeChoiceDialog mTypeChoiceDialog;
    private TypeChoiceDialog mLanguageChoiceDialog;
    private CoordinatorLayout mRootView;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mPublisherSwipeRefreshLayout;
    private RecyclerView mPublisherRecyclerView;

    private DialogFragment mSelectedDialog;

    private PublisherListAdapter mPublisherListAdapter;

    private PublisherDataSource mPublisherDataSource;
    private PublisherTypeDataSource mPublisherTypeDataSource;

    //可选择发布者内容类型
    private ArrayList<PublisherType> mContentTypeList;
    //可选择发布者语言类型
    private ArrayList<PublisherType> mLanguageTypeList;
    //选择的发布者索引
    private int mSelectedTypeIndex = 0;
    //选择的语言索引
    private int mSelectedLanguageIndex = 0;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PublisherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_activity);

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

        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        actionBar.setTitle(getResources().getString(R.string.publisher_title_default));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPublisherTypeDataSource = Injection.providePublisherTypeRepository();
        mPublisherSwipeRefreshLayout.setRefreshing(true);
        mPublisherTypeDataSource.getPublisherContentTypes(this);
        mPublisherTypeDataSource.getPublisherLanguageTypes(this);
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
                mTypeChoiceDialog = TypeChoiceDialog.newInstance(R.string.publisher_dialog_title,
                        mContentTypeList, mSelectedTypeIndex);
                mTypeChoiceDialog.show(getSupportFragmentManager(), "ChoiceTypeDialog");
                break;
            case R.id.publisher_menu_language:
                mLanguageChoiceDialog = TypeChoiceDialog.newInstance(R.string.language_dialog_title,
                        mLanguageTypeList, mSelectedLanguageIndex);
                mLanguageChoiceDialog.show(getSupportFragmentManager(), "ChoiceLanguageDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mPublisherSwipeRefreshLayout.setRefreshing(true);
        if (mSelectedDialog == mTypeChoiceDialog) {
            mPublisherDataSource.getPublishersByType(mContentTypeList.get(mSelectedTypeIndex).getTypeId(), this);
        } else if (mSelectedDialog == mLanguageChoiceDialog) {
            mPublisherDataSource.getPublishersByLanguage(mLanguageTypeList.get(mSelectedLanguageIndex).getTypeId(), this);
        }
    }

    @Override
    public void onTypeChoiceDialogItemClick(DialogFragment dialog, PublisherType publisherType) {
        mSelectedDialog = dialog;
        mToolbar.setTitle(publisherType.getName());

        mPublisherSwipeRefreshLayout.setRefreshing(true);

        if (dialog == mTypeChoiceDialog) {
            mPublisherDataSource.getPublishersByType(publisherType.getTypeId(), this);
        } else if (dialog == mLanguageChoiceDialog) {
            mPublisherDataSource.getPublishersByLanguage(publisherType.getTypeId(), this);
        }
    }

    @Override
    public void publisherListItemSelect(Publisher selectPublisher, boolean isSelected) {
        if (isSelected) {
            Snackbar.make(mRootView, selectPublisher.getName() + " selected", Snackbar.LENGTH_SHORT).show();
            mPublisherDataSource.subscribePublisherById(selectPublisher.getPublisherId());
        } else {
            Snackbar.make(mRootView, selectPublisher.getName() + " unselected", Snackbar.LENGTH_SHORT).show();
            mPublisherDataSource.unSubscribePublisherById(selectPublisher.getPublisherId());
        }
    }

    @Override
    public void onPublishersLoaded(List<Publisher> publisherList) {
        //FIXME 每次都需要创建适配器吗？
        if (mIsForeground) {
            mPublisherSwipeRefreshLayout.setRefreshing(false);
            mPublisherListAdapter = new PublisherListAdapter(publisherList, this);
            mPublisherRecyclerView.setAdapter(mPublisherListAdapter);
        }
    }

    @Override
    public void onPublisherTypesLoaded(List<PublisherType> publisherTypeList, String type) {
        if (type.equals("content")) {
            mContentTypeList = (ArrayList<PublisherType>) publisherTypeList;
            //使用默认选中的发布者类型请求发布者信息
            mPublisherDataSource = Injection.providePublisherRepository();
            mPublisherSwipeRefreshLayout.setRefreshing(true);
            mPublisherDataSource.getPublishersByType(mContentTypeList.get(mSelectedTypeIndex).getTypeId(), this);
        } else {
            mLanguageTypeList = (ArrayList<PublisherType>) publisherTypeList;
        }
    }

    @Override
    public void onDataNotAvailable() {

    }
}
