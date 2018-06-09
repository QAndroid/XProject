package workshop1024.com.xproject.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.controller.adapter.MessageListAdapter;
import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.view.dialog.AccountDialog;
import workshop1024.com.xproject.view.dialog.SubmitMessageDialog;
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener, AccountDialog.
        AccountDialogListener, SubmitMessageDialog.SubmitMessageDialogListener {
    private Toolbar mToolbar;
    private RecyclerView mMessagesRecyclerView;
    private FloatingActionButton mSubmitFloatingActionButton;

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
        mMessagesRecyclerView = findViewById(R.id.feedback_recyclerview_messages);
        mSubmitFloatingActionButton = findViewById(R.id.feedback_floatingactionbutton_submit);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Message Center");

        mSubmitFloatingActionButton.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMessagesRecyclerView.setLayoutManager(linearLayoutManager);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("001", "001"));
        messageList.add(new Message("002", "002"));
        messageList.add(new Message("003", "003"));
        messageList.add(new Message("004", "004"));
        messageList.add(new Message("004", "005"));
        messageList.add(new Message("004", "006"));
        messageList.add(new Message("004", "007"));
        messageList.add(new Message("004", "008"));
        messageList.add(new Message("004", "009"));
        messageList.add(new Message("004", "010"));
        messageList.add(new Message("004", "011"));
        messageList.add(new Message("004", "012"));
        messageList.add(new Message("004", "013"));
        messageList.add(new Message("004", "014"));
        messageList.add(new Message("004", "015"));
        messageList.add(new Message("004", "016"));
        messageList.add(new Message("004", "017"));
        messageList.add(new Message("004", "018"));
        MessageListAdapter messageListAdapter = new MessageListAdapter(messageList);
        mMessagesRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));
        mMessagesRecyclerView.setAdapter(messageListAdapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout.messagelist_header, mMessagesRecyclerView, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.messagelist_footer, mMessagesRecyclerView, false);
        messageListAdapter.setHeaderView(headerView);
        messageListAdapter.setFooterView(footerView);
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
        Toast.makeText(this, "cancelButtonClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void okButtonClick(DialogFragment dialogFragment, String nameString, String emailString) {

    }

    @Override
    public void submitButtonClick(DialogFragment dialogFragment, String messageConent) {
        dialogFragment.dismiss();
        mSubmitFloatingActionButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, "submitButtonClick", Toast.LENGTH_SHORT).show();
    }

}
