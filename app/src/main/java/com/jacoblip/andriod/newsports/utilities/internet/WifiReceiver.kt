package com.jacoblip.andriod.newsports.utilities.internet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.jacoblip.andriod.newsports.utilities.Util


class WifiReceiver() : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent!!.action) {
            val noConnectivity: Boolean = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (noConnectivity) {
                //Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
                Util.hasInternet.postValue(false)

            } else {
                //Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
                Util.hasInternet.postValue(true)
            }
        }
    }
}