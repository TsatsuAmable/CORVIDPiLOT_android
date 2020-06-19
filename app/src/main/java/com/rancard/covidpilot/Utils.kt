package com.rancard.covidpilot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Helper functions to simplify permission checks/requests
 */
fun Context.hasPermission(permission: String): Boolean {
    //Background permissinos didn't exist prior to Q, so it's approved by default.
    if(permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
            android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q){
        return true
    }
    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_DENIED
}

fun Fragment.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    snackBar: Snackbar
){
    val provideRationale = shouldShowRequestPermissionRationale(permission)

    if (provideRationale){
        snackBar.show()
    }else{
        requestPermissions(arrayOf(permission), requestCode)
    }
}