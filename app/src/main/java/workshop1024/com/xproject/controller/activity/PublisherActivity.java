package workshop1024.com.xproject.controller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.Publisher;
import workshop1024.com.xproject.view.RecyclerViewItemDecoration;
import workshop1024.com.xproject.view.SingleChoiceDialog;

/**
 * 发布者列表页面
 */
public class PublisherActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private Toolbar mToolbar;
    private List<Publisher> mPublisherList = new ArrayList<Publisher>() {{
        add(new Publisher("/imag1", "The Verge1", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge2", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge3", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge4", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge5", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge6", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge7", "970603 subscribers"));
        add(new Publisher("/imag1", "The Verge48", "970603 subscribers"));
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("XProject", "onCreate");
        setContentView(R.layout.activity_publisher);

        mToolbar = findViewById(R.id.publisher_toolbar_navigator);
        RecyclerView publisherRecyclerView = findViewById(R.id.publisher_recyclerview_list);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        publisherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        publisherRecyclerView.setAdapter(new PublisherAdapter(mPublisherList));
        publisherRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(6));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("XProject", "onCreateOptionsMenu");
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
                SingleChoiceDialog publisherChoiceDialog = SingleChoiceDialog.newInstance(R.string
                        .publisher_dialog_title, R.array.publisher_dialog_publishers);
                publisherChoiceDialog.show(getSupportFragmentManager(), "ChoicePublisherDialog");
                break;
            case R.id.publisher_menu_language:
                SingleChoiceDialog languageChoiceDialog = SingleChoiceDialog.newInstance(R.string
                        .language_dialog_title, R.array.language_dialog_languages);
                languageChoiceDialog.show(getSupportFragmentManager(), "ChoicePublisherDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //FIXME 如果区分两个不同对话框
        Toast.makeText(this, "select item " + i + " .", Toast.LENGTH_SHORT).show();
    }

    public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.PublisherViewHolder> {
        private List<Publisher> mPublisherList;

        public PublisherAdapter(List<Publisher> publisherList) {
            mPublisherList = publisherList;
        }

        @Override
        public PublisherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publishlist_item_content, parent,
                    false);
            return new PublisherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PublisherViewHolder holder, int position) {
            Publisher publisher = mPublisherList.get(position);

            holder.mNameTextView.setText(publisher.getName());
            holder.mSubscribeNumTextView.setText(publisher.getSubscribeNum());
        }


        @Override
        public int getItemCount() {
            return mPublisherList.size();
        }

        public class PublisherViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mIconImageView;
            public final TextView mNameTextView;
            private final TextView mSubscribeNumTextView;
            private final CheckBox mSelectedCheckBox;

            public PublisherViewHolder(View view) {
                super(view);

                mIconImageView = view.findViewById(R.id.publisherlist_imageview_icon);
                mNameTextView = view.findViewById(R.id.publisherlist_textview_name);
                mSubscribeNumTextView = view.findViewById(R.id.publisherlist_textview_subscribenum);
                mSelectedCheckBox = view.findViewById(R.id.publisherlist_checkbox_selected);
            }
        }
    }
}
