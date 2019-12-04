package workshop1024.com.xproject.main.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import workshop1024.com.xproject.main.R
import workshop1024.com.xproject.main.databinding.AboutActivityBinding

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