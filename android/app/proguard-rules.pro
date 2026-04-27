# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep the WebView
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void onPageFinished(android.webkit.WebView, java.lang.String);
}
