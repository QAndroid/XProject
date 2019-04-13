package workshop1024.com.xproject.controller.activity.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.PublisherListAdapter;
import workshop1024.com.xproject.databinding.PublisherActivityBinding;
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

    private PublisherActivityBinding mPublisherActivityBinding;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PublisherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPublisherActivityBinding = DataBindingUtil.setContentView(this, R.layout.publisher_activity);

        setSupportActionBar(mPublisherActivityBinding.publisherToolbarNavigator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.setOnRefreshListener(this);
        mPublisherActivityBinding.publisherRecyclerviewList.addItemDecoration(new RecyclerViewItemDecoration(6));

        //显示默认选中的发布者
        //mToolbar.setTitle()在此处不生效，参考：https://stackoverflow
        // .com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        actionBar.setTitle(getResources().getString(R.string.publisher_title_default));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPublisherTypeDataSource = Injection.providePublisherTypeRepository();
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
        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.setRefreshing(true);
        if (mSelectedDialog == mTypeChoiceDialog) {
            mPublisherDataSource.getPublishersByContentType(mContentTypeList.get(mSelectedTypeIndex).getTypeId(), this);
        } else if (mSelectedDialog == mLanguageChoiceDialog) {
            mPublisherDataSource.getPublishersByLanguageType(mLanguageTypeList.get(mSelectedLanguageIndex).getTypeId(), this);
        }
    }

    @Override
    public void onTypeChoiceDialogItemClick(DialogFragment dialog, PublisherType publisherType) {
        mSelectedDialog = dialog;
        mPublisherActivityBinding.publisherToolbarNavigator.setTitle(publisherType.getName());

        mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.setRefreshing(true);
        if (dialog == mTypeChoiceDialog) {
            mPublisherDataSource.getPublishersByContentType(publisherType.getTypeId(), this);
        } else if (dialog == mLanguageChoiceDialog) {
            mPublisherDataSource.getPublishersByLanguageType(publisherType.getTypeId(), this);
        }
    }

    @Override
    public void onPublisherListItemSelect(Publisher selectPublisher, boolean isSelected) {
        if (isSelected) {
            //FIXME 订阅网络请求返回后，在提示并且选中
            mPublisherDataSource.subscribePublisherById(selectPublisher.getPublisherId());
            Snackbar.make(mPublisherActivityBinding.getRoot(), selectPublisher.getName() + " selected", Snackbar.LENGTH_SHORT).show();
        } else {
            mPublisherDataSource.unSubscribePublisherById(selectPublisher.getPublisherId());
            Snackbar.make(mPublisherActivityBinding.getRoot(), selectPublisher.getName() + " unselected", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPublishersLoaded(List<Publisher> publisherList) {
        //FIXME 每次都需要创建适配器吗？
        if (mIsForeground) {
            mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.setRefreshing(false);
            mPublisherListAdapter = new PublisherListAdapter(publisherList, this);
            mPublisherActivityBinding.publisherRecyclerviewList.setAdapter(mPublisherListAdapter);
        }
    }

    @Override
    public void onPublisherTypesLoaded(List<PublisherType> publisherTypeList, String type) {
        //TODO type equals很别扭
        if (type.equals("content")) {
            mContentTypeList = (ArrayList<PublisherType>) publisherTypeList;
            //使用默认选中的发布者类型请求发布者信息
            mPublisherDataSource = Injection.providePublisherRepository();
            mPublisherActivityBinding.publisherSwiperefreshlayoutPullrefresh.setRefreshing(true);
            mPublisherDataSource.getPublishersByContentType(mContentTypeList.get(mSelectedTypeIndex).getTypeId(), this);
        } else {
            mLanguageTypeList = (ArrayList<PublisherType>) publisherTypeList;
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
