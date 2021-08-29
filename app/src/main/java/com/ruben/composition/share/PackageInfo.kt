package com.ruben.composition.share

/**
 * Created by Ruben Quadros on 20/08/21
 **/
enum class PackageInfo
constructor(val packageName: String, val referrer: String, val orderWeight: Int) {
    TWITTER("com.twitter.android", "twitterShare", 4),
    WHATSAPP("com.whatsapp", "whatsappShare", 1),
    FACEBOOK("com.facebook.katana", "facebookShare", 2),
    INSTAGRAM("com.instagram.android", "instagramShare", 3),
    SHAREIT("com.lenovo.anyshare.gps", "shareItShare", 5),
    XENDER("cn.xender", "xenderShare", 6),
    OTHERS("", "otherShare", 7),
    PLAYSTORE_INSTALL("com.android.vending", "", 8),
    ADHOC_INSTALL("com.google.android.packageinstaller", "", 9),
    TRUECALLER("com.truecaller", "", 10),
    CHROME("com.android.chrome","",11);

    companion object {
        const val OTHERS_MEDIUM_NAME = "others"

        fun getPackageInfoFromPackageName(packageName: String?): PackageInfo {
            return when (packageName) {
                "com.twitter.android" -> TWITTER
                "com.whatsapp" -> WHATSAPP
                "com.facebook.katana" -> FACEBOOK
                "com.instagram.android" -> INSTAGRAM
                "com.lenovo.anyshare.gps" -> SHAREIT
                "cn.xender" -> XENDER
                else -> OTHERS
            }
        }

        fun getMediumNameForTracking(packageInfo: PackageInfo?): String {
            return when(packageInfo) {
                WHATSAPP -> "whatsapp"
                INSTAGRAM -> "instagram"
                FACEBOOK -> "facebook"
                else -> OTHERS_MEDIUM_NAME
            }
        }
    }
}