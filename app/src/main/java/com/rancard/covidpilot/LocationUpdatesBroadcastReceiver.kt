package com.rancard.covidpilot

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.LocationResult
import com.rancard.covidpilot.data.LocationRepository
import com.rancard.covidpilot.data.db.MyLocationEntity
import java.util.Date
import java.util.concurrent.Executors

private const val TAG = "LUBroadcastReceiver"

/**
 * Receiver for handling location updates.
 *
 * For apps targeting API level O and above
 * {@link android.app.PendingIntent#getBroadcast(Context, int, Intent, int)} should be used when
 * requesting location updates in the background. Due to limits on background services,
 * {@link android.app.PendingIntent#getService(Context, int, Intent, int)} should NOT be used.
 *
 *  Note: Apps running on "O" devices (regardless of targetSdkVersion) may receive updates
 *  less frequently than the interval specified in the
 *  {@link com.google.android.gms.location.LocationRequest} when the app is no longer in the
 *  foreground.
 */

class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent){
        Log.d(TAG, "onReceive() context: $context, intent:$intent")

        if(intent.action == ACTION_PROCESS_UPDATES) {
            LocationResult.extractResult(intent)?.let { locationResult ->
                val locations = locationResult.locations.map {location ->
                    MyLocationEntity(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        foreground = isAppInForeGround(context),
                        date = Date(location.time)
                    )
                }
                if (locations.isNotEmpty()) {
                    LocationRepository.getInstance(context, Executors.newSingleThreadExecutor())
                        .addLocations(locations)

                }
            }
        }
    }


    // Note: This function's implementation is only for debugging purposes. If you are going to do
    // this in a production app, you should instead track the state of all your activities in a
    // process via android.app.Application.ActivityLifecycleCallbacks's
    // unregisterActivityLifecycleCallbacks(). For more information, check out the link:
    // https://developer.android.com/reference/android/app/Application.html#unregisterActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks
    private fun isAppInForeGround(context: Context): Boolean{
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcess = activityManager.runningAppProcesses ?: return false

        appProcess.forEach { appProcess ->
            if(appProcess.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    appProcess.processName == context.packageName){
                return true
            }
        }
        return false
    }

    companion object {
        const val ACTION_PROCESS_UPDATES =
            "com.rancard.covidpilot.action.PROCESS_UPDATES"
    }
}