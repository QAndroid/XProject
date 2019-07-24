package workshop1024.com.xproject.controller.activity.home

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import workshop1024.com.xproject.R
import workshop1024.com.xproject.databinding.AboutActivityBinding

class AboutActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aboutActivityBinding = DataBindingUtil.setContentView<AboutActivityBinding>(this, R.layout.about_activity)
        setSupportActionBar(aboutActivityBinding.aboutToolbarNavigator)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        title = "About Paperboy"
    }
}