# 123秒传 App

> 123云盘 秒传链接生成与转存的独立工具 App

## 在线访问

🌐 **网页版**：https://xjxjj.ccwu.cc/ （部署于 Cloudflare Pages）

📱 **安卓 App**：下载下方 APK 文件安装

## 功能

- ✅ **生成秒传**：选择本地文件 → 计算 Hash → 生成秒传链接
- ✅ **保存秒传**：粘贴秒传链接 → 一键秒传保存到123云盘
- ✅ **复制分享**：生成的链接一键复制
- ✅ **支持大文件**：分片计算 Hash，支持超大文件
- ✅ **网盘浏览**：登录后可浏览云盘文件
- ✅ **批量生成**：批量生成目录下所有文件秒传
- ✅ **分享解析**：解析分享链接并生成秒传

---

## 分支说明

| 分支 | 说明 |
|------|------|
| `main` | 安卓 App 版本（需要打包 APK） |
| `web` | 网页版（可直接部署到 Cloudflare Pages） |

网页版在线访问：https://xjxjj.ccwu.cc/

---

## 快速开始

### 方法一：网页直接使用（推荐 👍）

直接访问：https://xjxjj.ccwu.cc/

或本地使用：
1. 下载 `index.html`
2. 用浏览器打开
3. 登录即可

### 方法二：打包成安卓 App

#### 在线打包（1分钟）

1. 打开 **https://appr.idea.run/**
2. 填 App Name，选择上传 HTML
3. 下载 APK 安装

#### Android Studio 打包

参考下方详细教程...

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
