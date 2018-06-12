package workshop1024.com.xproject.controller.activity.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.activity.XActivity;
import workshop1024.com.xproject.controller.adapter.MessageListAdapter;
import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.model.message.MessageGroup;
import workshop1024.com.xproject.model.message.source.MessageDataSource;
import workshop1024.com.xproject.model.message.source.MessageRepository;
import workshop1024.com.xproject.view.dialog.AccountDialog;
import workshop1024.com.xproject.view.dialog.SubmitMessageDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class FeedbackActivity extends XActivity implements View.OnClickListener, AccountDialog.
        AccountDialogListener, SubmitMessageDialog.SubmitMessageDialogListener, MessageDataSource.
        LoadMessagesCallback, SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private SwipeRefreshLayout mMessageSwipeRefreshLayout;
    private RecyclerView mMessagesRecyclerView;
    private View mHeaderView;
    private View mFooterView;
    private TextView mHelloTextView;
    private FloatingActionButton mSubmitFloatingActionButton;

    private MessageRepository mMessageRepository;

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
        setContentView(R.layout.feedback_activity);

        mToolbar = findViewById(R.id.feedback_toolbar_navigator);
        mMessageSwipeRefreshLayout = findViewById(R.id.feedback_swiperefreshlayout_pullrefresh);
        mMessagesRecyclerView = findViewById(R.id.feedback_recyclerview_messages);
        mSubmitFloatingActionButton = findViewById(R.id.feedback_floatingactionbutton_submit);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Message Center");

        mMessageSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMessagesRecyclerView.setLayoutManager(linearLayoutManager);
        mMessagesRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));
        LayoutInflater inflater = LayoutInflater.from(this);

        mHeaderView = inflater.inflate(R.layout.messagelist_header, mMessagesRecyclerView, false);
        mFooterView = inflater.inflate(R.layout.messagelist_footer, mMessagesRecyclerView, false);
        mHelloTextView = mHeaderView.findViewById(R.id.feedback_textview_hello);

        mSubmitFloatingActionButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshMessageGroupList();
    }

    private void refreshMessageGroupList() {
        mMessageRepository = MessageRepository.getInstance();
        mMessageSwipeRefreshLayout.setRefreshing(true);
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
        if (v == mSubmitFloatingActionButton) {
            SubmitMessageDialog submitMessageDialog = SubmitMessageDialog.newInstance();
            submitMessageDialog.show(getSupportFragmentManager(), SubmitMessageDialog.TAG);

            mSubmitFloatingActionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancelButtonClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
        mSubmitFloatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void okButtonClick(DialogFragment dialogFragment, String nameString, String emailString) {
        mHelloTextView.setText(new StringBuffer("Hello!").append(nameString).toString());
    }

    @Override
    public void submitButtonClick(DialogFragment dialogFragment, String messageConent) {
        dialogFragment.dismiss();
        mSubmitFloatingActionButton.setVisibility(View.VISIBLE);

        Message message = new Message("m999", messageConent);
        mMessageRepository.submitMessage(message);

        refreshMessageGroupList();
    }

    @Override
    public void onMessagesLoaded(List<MessageGroup> messageGroupList) {
        if (mIsForeground) {
            mMessageSwipeRefreshLayout.setRefreshing(false);

            MessageListAdapter messageListAdapter = new MessageListAdapter(messageGroupList);
            mMessagesRecyclerView.setAdapter(messageListAdapter);
            messageListAdapter.setHeaderView(mHeaderView);
            messageListAdapter.setFooterView(mFooterView);
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
