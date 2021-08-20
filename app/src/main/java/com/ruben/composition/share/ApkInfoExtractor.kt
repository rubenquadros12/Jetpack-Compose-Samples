package com.ruben.composition.share

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable

/**
 * Created by Ruben Quadros on 20/08/21
 **/
object ApkInfoExtractor {

    fun getAllInstalledApkInfo(context: Context): List<String> {

        fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
            return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
        }

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED

        val apkList:List<String> = context.packageManager.queryIntentActivities(intent, 0)
            .asSequence()
            .filter { !isSystemPackage(it) }
            .map { it.activityInfo.applicationInfo.packageName }
            .filter {
                it in PackageInfo.values().map{ it.packageName }.filter{ it!=PackageInfo.OTHERS.packageName }
            }
            .toList()

        return apkList.sortedBy{PackageInfo.getPackageInfoFromPackageName(it).orderWeight}
    }

    fun getAppIconByPackageName(apkTempPackageName: String, context: Context): Drawable? {
        var drawable: Drawable? = null

        try {
            drawable = context.packageManager.getApplicationIcon(apkTempPackageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return drawable
    }
}