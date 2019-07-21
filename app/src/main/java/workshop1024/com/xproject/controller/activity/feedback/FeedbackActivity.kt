package workshop1024.com.xproject.controller.activity.feedback

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import workshop1024.com.xproject.R
import workshop1024.com.xproject.controller.activity.XActivity
import workshop1024.com.xproject.controller.adapter.MessageListAdapter
import workshop1024.com.xproject.databinding.FeedbackActivityBinding
import workshop1024.com.xproject.databinding.MessagelistFooterBinding
import workshop1024.com.xproject.databinding.MessagelistHeaderBinding
import workshop1024.com.xproject.model.Injection
import workshop1024.com.xproject.model.message.Message
import workshop1024.com.xproject.model.message.MessageGroup
import workshop1024.com.xproject.model.message.source.MessageDataSource
import workshop1024.com.xproject.view.dialog.AccountDialog
import workshop1024.com.xproject.view.dialog.SubmitMessageDialog
import workshop1024.com.xproject.view.recyclerview.RecyclerViewItemDecoration


class FeedbackActivity : XActivity(), View.OnClickListener, AccountDialog.AccountDialogListener, SubmitMessageDialog.SubmitMessageDialogListener,
        MessageDataSource.LoadMessagesCallback, SwipeRefreshLayout.OnRefreshListener {

    private var mMessageRepository: MessageDataSource? = null
    private lateinit var mFeedbackActivityBinding: FeedbackActivityBinding
    private lateinit var mMessagelistHeaderBinding: MessagelistHeaderBinding
    private lateinit var mMessagelistFooterBinding: MessagelistFooterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFeedbackActivityBinding = DataBindingUtil.setContentView(this, R.layout.feedback_activity)
        mMessagelistHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.messagelist_header, mFeedbackActivityBinding.feedbackRecyclerviewMessages,
                false)
        mMessagelistFooterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.messagelist_footer, mFeedbackActivityBinding.feedbackRecyclerviewMessages,
                false)

        setSupportActionBar(mFeedbackActivityBinding.feedbackToolbarNavigator)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Message Center"

        mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh.setOnRefreshListener(this)
        mFeedbackActivityBinding.feedbackRecyclerviewMessages.addItemDecoration(RecyclerViewItemDecoration(6))
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        refreshMessageGroupList()
    }

    private fun refreshMessageGroupList() {
        mMessageRepository = Injection.provideMessageRepository()
        mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh.isRefreshing = true
        mMessageRepository?.getMessages(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.feedback_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.feedback_menu_account -> {
                val accountDialog = AccountDialog.newInstance()
                accountDialog.show(supportFragmentManager, AccountDialog.TAG)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        if (v === mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit) {
            val submitMessageDialog = SubmitMessageDialog.newInstance()
            submitMessageDialog.show(supportFragmentManager, SubmitMessageDialog.TAG)

            mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit.visibility = View.GONE
        }
    }

    override fun okButtonClick(dialogFragment: DialogFragment, nameString: String, emailString: String) {
        mMessagelistHeaderBinding.feedbackTextviewHello?.text = StringBuffer("Hello!").append(nameString).toString()
    }

    override fun cancelButtonClick(dialogFragment: DialogFragment) {
        dialogFragment.dismiss()
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit?.visibility = View.VISIBLE
    }

    override fun submitButtonClick(dialogFragment: DialogFragment, messageConent: String) {
        dialogFragment.dismiss()
        mFeedbackActivityBinding.feedbackFloatingactionbuttonSubmit?.visibility = View.VISIBLE

        val message = Message("m99", messageConent)
        mMessageRepository?.submitMessage(message)

        refreshMessageGroupList()
    }

    override fun onMessagesLoaded(messageGroupList: List<MessageGroup>) {
        if (mIsForeground) {
            mFeedbackActivityBinding.feedbackSwiperefreshlayoutPullrefresh?.isRefreshing = false

            val messageListAdapter = MessageListAdapter(messageGroupList)
            mFeedbackActivityBinding.feedbackRecyclerviewMessages?.adapter = messageListAdapter
            messageListAdapter.setHeaderView(mMessagelistHeaderBinding.root)
            messageListAdapter.setFooterView(mMessagelistFooterBinding.root)
        }
    }

    override fun onDataNotAvailable() {

    }

    override fun onRefresh() {
        refreshMessageGroupList()
    }

    companion object {
        /**
         * 启动FeedbackActivity页面
         */
        fun startActivity(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }
}