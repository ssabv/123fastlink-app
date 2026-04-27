# 123秒传 App

> 123云盘 秒传链接生成与转存的独立工具 App

## 功能

- ✅ **生成秒传**：选择本地文件 → 计算 Hash → 生成秒传链接
- ✅ **保存秒传**：粘贴秒传链接 → 一键秒传保存到123云盘
- ✅ **复制分享**：生成的链接一键复制
- ✅ **支持大文件**：分片计算 Hash，支持超大文件

---

## 方法一：网页直接使用（最快，1分钟）

不想打包 App？直接用手机浏览器打开 HTML 文件即可：

1. 把 `index.html` 传到手机（通过微信/QQ/百度网盘/蓝牙）
2. 用浏览器打开它
3. 登录即可使用所有功能

**推荐浏览器**：Chrome、Safari、Edge、夸克（**微信内置浏览器**不支持文件选择）

---

## 方法二：打包成安卓 App（5-15分钟，无需写代码）

### 工具一：在线打包（适合完全新手，推荐 👍）

1. 打开 **https://webintoapp.com/**（免费）
2. 点击 **"Create App"**
3. App Name 填 `123秒传`，Website URL 填 `https://临时填一下.com`（后面改）
4. 选择 **"Upload ZIP"** 上传一个 zip 包
5. 把 `index.html` 压缩成 zip 上传
6. 完成后下载 APK 安装

**更简单的方法** —— 使用 Appr.idea 的"网页转App"功能：
1. 打开 **https://appr.idea.run/**（免费）
2. 填 App Name，选择上传 HTML
3. 直接下载 APK

### 工具二：安卓工作室打包（需要电脑）

#### 准备工作（只做一次）
1. 下载 **Android Studio**：https://developer.android.com/studio
2. 安装好，记住安装路径

#### 步骤

**第一步：创建项目**

打开 Android Studio，选择：
- **"Empty Views Activity"**（不是 Compose）
- Language: **Java**（或 Kotlin 都行）
- Minimum SDK: **API 24**（Android 7.0，兼容性好）

Project Name 填 `123FastLink`，点击 Create。

---

**第二步：准备文件**

1. 在左侧项目面板，展开 `app` → `src` → `main`
2. 在 `main` 文件夹上右键 → **New** → **Directory** → 填 `assets` → 回车
3. 把本仓库的 `index.html` 拖进 `assets` 文件夹

> 如果拖拽不行，右键 assets → Reveal in Finder/Explorer → 手动复制文件进去

---

**第三步：写 WebView 代码**

展开 `app` → `src` → `main` → `java` → `com.example.qianqian123fastlink`（你的包名）

打开 `MainActivity.java`，清空内容，替换为：

```java
package com.example.qianqian123fastlink;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webview);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 加载本地 HTML 文件
        webView.loadUrl("file:///android_asset/index.html");

        // 防止跳转到外部浏览器
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        WebView wv = findViewById(R.id.webview);
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
```

---

**第四步：配置网络权限**

展开 `app` → `src` → `main` → `AndroidManifest.xml`

在 `<manifest ...>` 标签**内**添加（放在 package 那行下面）：

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

在 `<application ...>` 标签里添加：

```xml
android:usesCleartextTraffic="true"
```

---

**第五步：调整布局（让 WebView 全屏）**

打开 `res` → `layout` → `activity_main.xml`

清空内容，替换为：

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <WebView
        android:id="@+id/webview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</FrameLayout>
```

---

**第六步：设置 App 图标（可选）**

在 `res` → `mipmap-hdpi`（等）等文件夹里放一个 512x512 的 PNG 图标（PNG用`ic_launcher.png`命名）

---

**第七步：编译 APK**

1. 顶部菜单 → **Build** → **Generate Signed Bundle / APK...**
2. 选择 **APK** → Next
3. 点击 **Create new** 创建签名（填密码，记住所填内容）
4. 选择 **release** → Finish
5. 等待编译完成，在下方提示栏点 **locate** 找到 APK 文件
6. 把 APK 传到手机安装

---

## 方法三：使用 Capacitor（适合有基础的人）

如果你有 Node.js 环境：

```bash
# 1. 创建项目
npm create vite@latest 123fastlink -- --template vanilla
cd 123fastlink

# 2. 把 index.html 放到项目目录

# 3. 安装 Capacitor
npm install @capacitor/core @capacitor/cli @capacitor/android
npx cap init "123秒传" com.qianqian123fastlink --web-dir=.
npx cap add android

# 4. 同步并构建
npx cap sync android

# 5. 用 Android Studio 打开
npx cap open android
```

---

## 项目文件

```
123fastlink-app/
├── index.html      ← 核心应用（单文件，可独立运行）
├── SPEC.md         ← 产品规格说明
└── README.md       ← 本文件
```

---

## ⚠️ 注意事项

1. **账号安全**：建议使用小号，不要在 App 里输入重要账号
2. **API 稳定性**：123云盘 API 为非官方接口，可能随版本更新失效
3. **文件大小**：秒传依赖云端已有文件，全新文件无法秒传
4. **仅供学习**：本工具仅供个人学习交流使用

---

## 更新日志

- **v1.0.0** 初始版本，包含登录、生成秒传、保存秒传、复制链接功能
