# 123FastLink Web

一个纯前端的 123 云盘秒传 / 批量分享辅助页面。

当前版本基于 `index_fixed_light.html` 浅色现代 UI。

## 主要功能

- 123 云盘账号登录；
- 浏览网盘文件；
- 生成秒传链接；
- 保存秒传链接；
- 解析分享链接；
- 批量生成秒传 ZIP；
- 批量分享当前目录下的一级子文件夹；
- 导出账号全部分享链接 CSV。

## 本次接口修复

### 1. 123 主 API 改为 OpenList 同款域名

参考 OpenList 的 123 驱动：

```text
https://github.com/OpenListTeam/OpenList
```

当前页面中：

```js
const OFFICIAL_API = {
  login: 'https://login.123pan.com/api/user/sign_in',
  base: 'https://yun.123pan.com'
}
```

也就是说：

| 用途 | 域名 |
|---|---|
| 登录 | `https://login.123pan.com/api/user/sign_in` |
| 文件列表 / 文件操作 | `https://yun.123pan.com/b/api` |
| Origin / Referer | `https://yun.123pan.com/` |

### 2. 分享链接导出改为 123pan.cn

批量分享生成的链接现在使用：

```text
https://123pan.cn/s/xxxx
```

不再使用：

```text
https://www.123pan.com/s/xxxx
```

### 3. 修复“导出账号全部分享”接口

旧接口：

```text
/api/v1/share/list
```

会返回：

```text
Not Found
```

现在改为：

```text
https://api.123278.com/b/api/share/list
```

并带签名：

```js
signP('/b/api/share/list')
```

导出的 CSV 字段：

```text
名称,分享链接,提取码,带密码链接
```

## 使用方式

直接打开：

```text
index.html
```

或部署为静态页面。

如果浏览器拦截跨域，请使用原本可运行该页面的方式，或者部署到支持对应跨域请求的环境。

## 注意事项

- 这是纯前端页面，依赖浏览器环境和 123 云盘接口当前策略；
- 如果接口再次变更，需要重新抓包调整；
- 登录信息保存在浏览器本地 localStorage；
- 请勿在公共电脑上保存账号密码。
