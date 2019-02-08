package com.toolslab.cowork.app.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import javax.inject.Inject

class PermissionHelper @Inject constructor() {

    private val permissionRequestCode = 10
    private val permission = Manifest.permission.ACCESS_COARSE_LOCATION

    fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), permissionRequestCode)
    }

    fun isLocationPermissionEnabled(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_GRANTED
    }

    fun isLocationPermissionEnabled(requestCode: Int,
                                    permissions: Array<out String>,
                                    grantResults: IntArray): Boolean {
        return requestCode == permissionRequestCode &&
                permissions.size == 1 &&
                permissions[0] == permission &&
                grantResults[0] == PERMISSION_GRANTED
    }

}
