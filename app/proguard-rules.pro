# Proguard rules for Grandmaster Chess WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
