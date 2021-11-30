package com.jacoblip.andriod.newsports.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.TermsViewModel
import com.jacoblip.andriod.newsports.databinding.ActivityWebBinding
import com.jacoblip.andriod.newsports.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding
    lateinit var webView: WebView
    lateinit var prefs: SharedPreferences
    val viewModel: TermsViewModel by viewModels()
    var API_KEY = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webview
        prefs = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        val webSettings: WebSettings = webView.settings
        webSettings.setJavaScriptEnabled(true)

        getapiExtra()

        webView.loadUrl("https://cruisonlinegames.com/terms/")

        //     webView.addJavascriptInterface(object :JavascriptInterface(){},"javaInterface")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val queue = Volley.newRequestQueue(applicationContext)
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        // Display the first 500 characters of the response string.
                        val text = response.substring(0,4)

                        if(text=="true")
                            viewModel.setAccess()

                        Log.i("web", text)
                    },
                    { var text = "That didn't work!" })

// Add the request to the RequestQueue.
                queue.add(stringRequest)
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if(url!=null&& url=="https://cruisonlinegames.com/terms/index.php?checkbox=check&submit=submit") {
                    webView.visibility = View.GONE
                }else{
                    webView.visibility = View.VISIBLE
                }
                super.onPageStarted(view, url, favicon)
            }


        }

        setUpObservers()
    }

    private fun getapiExtra(){
        val extras = intent.extras

        if (extras != null) {
            API_KEY = extras.getString("apiKey").toString()
        }
    }

    private fun setUpObservers(){
        viewModel.canAccess.observe(this,{
            if(it){
                prefs.edit().putBoolean("acceptedTerms",true).apply()
                val intent = Intent(this@WebActivity, MainActivity::class.java)
                intent.putExtra("apiKey",API_KEY)
                startActivity(intent)
                this@WebActivity.finish()
            }
        })
    }
}