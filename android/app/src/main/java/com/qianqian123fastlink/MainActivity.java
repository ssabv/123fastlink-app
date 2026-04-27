package com.qianqian123fastlink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ValueCallback<Uri> filePathCallback;
    private ValueCallback<Uri[]> filePathCallbacks;
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(false);

        // 关键：让 file input 可以工作
        ws.setAllowContentAccess(true);

        // 加载本地 HTML
        webView.loadUrl("file:///android_asset/index.html");

        // 防止跳转到外部浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如果是文件选择回调，不处理
                if (url.startsWith("blob:") || url.startsWith("data:")) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        // 关键：设置 WebChromeClient 以支持 file input
        webView.setWebChromeClient(new WebChromeClient() {
            // Android < 5.0 单文件
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                filePathCallback = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(createFileChooserIntent(), FILE_CHOOSER_REQUEST_CODE);
            }

            // Android 5.0+ 单文件 (不过时)
            @SuppressLint("NewApi")
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                MainActivity.this.filePathCallbacks = filePathCallback;
                Intent intent = createFileChooserIntent();
                // 可根据 fileChooserParams.getAcceptTypes() 过滤文件类型
                startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE);
                return true;
            }
        });
    }

    // 创建文件选择 Intent
    private Intent createFileChooserIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // 支持多选
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        // 允许访问内部存储和外部存储
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        return intent;
    }

    // 处理文件选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (filePathCallback != null) {
                    // Android < 5.0
                    Uri result = data == null ? null : data.getData();
                    if (result != null) {
                        // 申请持久化权限
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContentResolver().takePersistableUriPermission(result, takeFlags);
                    }
                    filePathCallback.onReceiveValue(result);
                    filePathCallback = null;
                } else if (filePathCallbacks != null) {
                    // Android 5.0+
                    Uri[] results = null;
                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            final int takeFlags = data.getFlags()
                                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                            results = new Uri[]{uri};
                        }
                    }
                    filePathCallbacks.onReceiveValue(results);
                    filePathCallbacks = null;
                }
            } else {
                // 用户取消
                if (filePathCallback != null) {
                    filePathCallback.onReceiveValue(null);
                    filePathCallback = null;
                }
                if (filePathCallbacks != null) {
                    filePathCallbacks.onReceiveValue(null);
                    filePathCallbacks = null;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
