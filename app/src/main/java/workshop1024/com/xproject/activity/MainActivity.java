package workshop1024.com.xproject.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.databinding.MainActivityBinding;
import workshop1024.com.xproject.fragment.HomeFragment;
import workshop1024.com.xproject.fragment.SavedFragment;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MainActivityBinding mBinding;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        setSupportActionBar(mBinding.layoutRight.toolbarNavigator);

        //创建ActionBar左边的up action，点击开关左侧抽屉导航
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerlayoutNavigator, mBinding
                .layoutRight.toolbarNavigator, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerlayoutNavigator.addDrawerListener(toggle);
        toggle.syncState();

        mBinding.navigationviewLeft.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        //默认展示HomeFragment
        replaceHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //创建ActionBar右边Handle actions
        getMenuInflater().inflate(R.menu.main_toolbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_search) {
            Toast.makeText(this, "toolbar_action_search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_add) {
            Toast.makeText(this, "toolbar_action_add", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_refresh) {
            Toast.makeText(this, "toolbar_action_refresh", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_marked) {
            Toast.makeText(this, "toolbar_action_marked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_rate) {
            Toast.makeText(this, "toolbar_action_rate", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_product) {
            Toast.makeText(this, "toolbar_action_product", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.toolbar_action_about) {
            Toast.makeText(this, "toolbar_action_about", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.leftnavigator_menu_home) {
            replaceHomeFragment();
        } else if (id == R.id.leftnavigator_menu_saved) {
            SavedFragment savedFragment = SavedFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.framelayout_fragments, savedFragment).commit();
        } else if (id == R.id.leftnavigator_menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.leftnavigator_menu_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        }

        mBinding.drawerlayoutNavigator.closeDrawer(GravityCompat.START);
        return true;
    }


    private void replaceHomeFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.framelayout_fragments, homeFragment).commit();
    }
}
