package com.jacoblip.andriod.newsports.ui.splash

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jacoblip.andriod.newsports.BuildConfig
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.databinding.ActivityAboutBinding
import com.jacoblip.andriod.newsports.databinding.ActivitySplashBinding
import com.jacoblip.andriod.newsports.utilities.facebookLink
import com.jacoblip.andriod.newsports.utilities.twitterLink
import com.jacoblip.andriod.newsports.utilities.whatsappContact

class AboutActivity : AppCompatActivity() {

    lateinit var binding:ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.companyName.text = getString(R.string.sample_company_name) + " v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"

        binding.twitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(twitterLink)
            startActivity(intent)
        }

        binding.facebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(facebookLink)
            startActivity(intent)
        }
        binding.whatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$whatsappContact")
            startActivity(intent)
        }
    }
}