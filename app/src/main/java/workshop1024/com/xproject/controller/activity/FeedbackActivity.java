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
import workshop1024.com.xproject.model.message.MessageGroup;
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

        List<MessageGroup> messageGroupList = new ArrayList<>();
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("001", "0101"));
        messageList.add(new Message("002", "0102"));
        messageList.add(new Message("003", "0103"));
        messageList.add(new Message("004", "0104"));
        messageList.add(new Message("005", "0105"));
        MessageGroup messageGroup = new MessageGroup("2018-01-01", messageList);
        messageGroupList.add(messageGroup);
        List<Message> messageList1 = new ArrayList<>();
        messageList1.add(new Message("001", "0201"));
        messageList1.add(new Message("002", "0202"));
        messageList1.add(new Message("003", "0203"));
        messageList1.add(new Message("004", "0204"));
        messageList1.add(new Message("005", "0205"));
        MessageGroup messageGroup1 = new MessageGroup("2018-02-02", messageList1);
        messageGroupList.add(messageGroup1);
        List<Message> messageList2 = new ArrayList<>();
        messageList2.add(new Message("001", "0301"));
        messageList2.add(new Message("002", "0302"));
        messageList2.add(new Message("003", "0303"));
        messageList2.add(new Message("004", "0304"));
        messageList2.add(new Message("005", "0305"));
        MessageGroup messageGroup2 = new MessageGroup("2018-03-03", messageList2);
        messageGroupList.add(messageGroup2);
        List<Message> messageList3 = new ArrayList<>();
        messageList3.add(new Message("001", "0401"));
        messageList3.add(new Message("002", "0402"));
        messageList3.add(new Message("003", "0403"));
        messageList3.add(new Message("004", "0404"));
        messageList3.add(new Message("005", "0405"));
        MessageGroup messageGroup3 = new MessageGroup("2018-04-04", messageList3);
        messageGroupList.add(messageGroup3);
        List<Message> messageList4 = new ArrayList<>();
        messageList4.add(new Message("001", "0501"));
        messageList4.add(new Message("002", "0502"));
        messageList4.add(new Message("003", "0503"));
        messageList4.add(new Message("004", "0504"));
        messageList4.add(new Message("005", "0505"));
        MessageGroup messageGroup4 = new MessageGroup("2018-05-05", messageList4);
        messageGroupList.add(messageGroup4);
        MessageListAdapter messageListAdapter = new MessageListAdapter(messageGroupList);

        mMessagesRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));
        mMessagesRecyclerView.setAdapter(messageListAdapter);
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.messagelist_header, mMessagesRecyclerView, false);
        View footerView = inflater.inflate(R.layout.messagelist_footer, mMessagesRecyclerView, false);
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
