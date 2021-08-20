package com.ruben.composition.share

import android.content.Context
import com.ruben.composition.R
import com.ruben.composition.share.PackageConstants.ICON_MORE
import com.ruben.composition.share.PackageConstants.ICON_NAME_COPY
import com.ruben.composition.share.PackageConstants.ICON_NAME_EMBED
import com.ruben.composition.share.PackageConstants.ICON_NAME_FB
import com.ruben.composition.share.PackageConstants.ICON_NAME_INSTA
import com.ruben.composition.share.PackageConstants.ICON_NAME_MORE
import com.ruben.composition.share.PackageConstants.ICON_NAME_TWITTER
import com.ruben.composition.share.PackageConstants.ICON_NAME_WHATSAPP

/**
 * Created by Ruben Quadros on 20/08/21
 **/
object PackageInfoUtil {

    fun getIconList(showEmbed: Boolean = false, context: Context): List<IconInfo> {
        val sharingIconList = arrayListOf<IconInfo>()

        fun addResource(iconDrawable: String, iconName: Int) {
            ApkInfoExtractor.getAppIconByPackageName(iconDrawable, context)?.let {
                sharingIconList.add(IconInfo(iconName, drawableResource = it))
            }
        }

        val installedApks = ApkInfoExtractor.getAllInstalledApkInfo(context)
        installedApks.contains(PackageInfo.WHATSAPP.packageName).let {
            addResource(PackageInfo.WHATSAPP.packageName, ICON_NAME_WHATSAPP)
        }

        context.let { ApkInfoExtractor.getAllInstalledApkInfo(it) }
            .asSequence()
            .filter { it in PackageInfo.values().map { it.packageName }.filter { it != PackageInfo.OTHERS.packageName && it != PackageInfo.WHATSAPP.packageName } }
            .map {
                when (it) {
                    PackageInfo.INSTAGRAM.packageName -> addResource(it, ICON_NAME_INSTA)
                    PackageInfo.FACEBOOK.packageName -> addResource(it, ICON_NAME_FB)
                    PackageInfo.TWITTER.packageName -> addResource(it, ICON_NAME_TWITTER)
                }
            }
            .toList()

        sharingIconList.add(IconInfo(ICON_NAME_COPY, R.drawable.ic_copylink))
        if (showEmbed) {
            sharingIconList.add(IconInfo(ICON_NAME_EMBED, R.drawable.ic_embed_icon))
        }
        sharingIconList.add(IconInfo(ICON_NAME_MORE, ICON_MORE))
        return sharingIconList
    }
}