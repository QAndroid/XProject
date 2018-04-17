package workshop1024.com.xproject.controller.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.model.news.NewsDetail;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;

public class NewsDetailActivity extends AppCompatActivity implements NewsDataSource.LoadNewsDetailCallBack {
    private Toolbar mToolbar;
    private TextView mPublisherTextView;
    private TextView mPubDataTextView;
    private TextView mContentTextView;

    private String mNewsId;
    private NewsRepository mNewsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mToolbar = findViewById(R.id.detail_toolbar_navigator);
        mPublisherTextView = findViewById(R.id.newsdetail_textview_publisher);
        mPubDataTextView = findViewById(R.id.newsdetail_textview_pubdata);
        mContentTextView = findViewById(R.id.newsdetail_textview_content);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewsId = getIntent().getStringExtra("newsId");
        mNewsRepository = NewsRepository.getInstance();
        mNewsRepository.getNewsDetailByNewsId(mNewsId, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //拦截Home按钮事件，关闭当前Activity，返回上一个Activity-即MainActivity
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNewsDetailLoaded(NewsDetail newsDetail) {
        mPublisherTextView.setText(newsDetail.getPublisher());
        mPubDataTextView.setText(newsDetail.getPubDate());
        mContentTextView.setText(newsDetail.getContent());
    }

    @Override
    public void onDataNotAvaiable() {

    }
}
