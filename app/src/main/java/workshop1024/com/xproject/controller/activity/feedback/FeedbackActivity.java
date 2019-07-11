package workshop1024.com.xproject.controller.activity.feedback;

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
import android.view.View;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.MessageListAdapter;
import workshop1024.com.xproject.databinding.FeedbackActivityBinding;
import workshop1024.com.xproject.databinding.MessagelistFooterBinding;
import workshop1024.com.xproject.databinding.MessagelistHeaderBinding;
import workshop1024.com.xproject.model.Injection;
import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.model.message.MessageGroup;
import workshop1024.com.xproject.model.message.source.MessageDataSource;
import workshop1024.com.xproject.view.dialog.AccountDialog;
import workshop1024.com.xproject.view.dialog.SubmitMessageDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class FeedbackActivity extends XActivity implements View.OnClickListener, AccountDialog.
        AccountDialogListener, SubmitMessageDialog.SubmitMessageDialogListener, MessageDataSource.
        LoadMessagesCallback, SwipeRefreshLayout.OnRefreshListener {

    private MessageDataSource mMessageRepository;

    private FeedbackActivityBinding mFeedbackActivityBinding;
    private MessagelistHeaderBinding mMessagelistHeaderBinding;
    private MessagelistFooterBinding mMessagelistFooterBinding;

    /**
     * 启动FeedbackActivity页面
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedbackActivityBinding = DataBindingUtil.setContentView(this, R.layout.feedback_activity);
        mMessagelistHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.messagelist_header,
                mFeedbackActivityBinding.feedbackRecyclerviewMessages, false);
        mMessagelistFooterBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.messagelist_footer,
                mFeedbackActivityBinding.feedbackRecyclerviewMessages, false);

        setSupportActionBar(mFeedbackActivityBinding.feedbackToolbarNavigator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Message Center");

        mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh.setOnRefreshListener(this);
        mFeedbackActivityBinding.feedbackRecyclerviewMessages.addItemDecoration(new RecyclerViewItemDecoration(6));
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshMessageGroupList();
    }

    private void refreshMessageGroupList() {
        mMessageRepository = Injection.INSTANCE.provideMessageRepository();
        mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh.setRefreshing(true);
        mMessageRepository.getMessages(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feedback_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feedback_menu_account:
                AccountDialog accountDialog = AccountDialog.newInstance();
                accountDialog.show(getSupportFragmentManager(), AccountDialog.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit) {
            SubmitMessageDialog submitMessageDialog = SubmitMessageDialog.newInstance();
            submitMessageDialog.show(getSupportFragmentManager(), SubmitMessageDialog.TAG);

            mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancelButtonClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    public void okButtonClick(DialogFragment dialogFragment, String nameString, String emailString) {
        mMessagelistHeaderBinding.feedbackTextviewHello.setText(new StringBuffer("Hello!").append(nameString).toString());
    }

    @Override
    public void submitButtonClick(DialogFragment dialogFragment, String messageConent) {
        dialogFragment.dismiss();
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.setVisibility(View.VISIBLE);

        Message message = new Message("m999", messageConent);
        mMessageRepository.submitMessage(message);

        refreshMessageGroupList();
    }

    @Override
    public void onMessagesLoaded(List<? extends MessageGroup> messageGroupList) {
        if (mIsForeground) {
            mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh.setRefreshing(false);

            MessageListAdapter messageListAdapter = new MessageListAdapter(messageGroupList);
            mFeedbackActivityBinding.feedbackRecyclerviewMessages.setAdapter(messageListAdapter);
            messageListAdapter.setHeaderView(mMessagelistHeaderBinding.getRoot());
            messageListAdapter.setFooterView(mMessagelistFooterBinding.getRoot());
        }
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onRefresh() {
        refreshMessageGroupList();
    }
}
