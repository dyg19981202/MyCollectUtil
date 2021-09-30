#### AndroidStudio 4.0版本 Gradle离线操作(offline mode)

解决办法:

```xml
gradlew build     在线模式

gradlew build --offline    离线
```

#### @FormUrlEncoded

```xml
我需要将POST数据提交的请求方式设置为表单的 @FormUrlEncoded，其对应的参数注解可以是 @FieldMap 或者 @Field，这是简单的键值对表单请求方式对应的注解组合。
```

https://blog.csdn.net/weixin_41633457/article/details/105226728

#### buildConfigField属性

```xml
如果使用该关键字在build.gradle 则是使其自动生成配置文件,使用方式 如下所示:
buildConfigField  "String","BASE_URL","\"${active.baseUrl}\""
该值均记录在: config.gradle 中
```

https://www.jianshu.com/p/a614aca9d191

==获取配置信息失败，暂未找到原因==

==GitHubToken: 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==

==One语音17560920629Token:OMyVacoDhXezTMaO8seoKdqbsowHwfo8TRmccekW1c0=.dXNlcl9pZD04JmV4cGlyZV90aW1lPTE2MzI1ODQ1MzMmcmFuZG9tPTU3MzU0MTAw==



#### 创建新Model

```xml
创建后，需要到对应的 build.gradle中添加 
implementation project(path:':im-ease')
```

#### Gradle

```xml
AndroidManifest.xml
<meta-data
        android:name="EASEMOB_APPKEY"
        android:value="${EASEMOB_APPKEY}" />

build.gradle
manifestPlaceholders = [EASEMOB_APPKEY:active.easeAppKey]

config.gradle
release = [
	easeAppKey        : "1118210902051105#demo",
```



#### 定时任务(ScheduledExecutorService)

https://blog.csdn.net/ma969070578/article/details/82863477



#### 创建Model时 遇到的问题

```xml
如果在 `A(libray)的build.gradle` 中 已经引用了 `B(libray)` ，那么 如果再从 `B中的build引入A` 就会出现 `Circular dependency between the following tasks.` 错误
```

#### 即构(解析)
https://zhuanlan.zhihu.com/p/121107307


#### Kotlin 内联函数使用
```xml
    参考类 BaseRepository
    代码示例如下:
        private inline fun <reified T> api(): T = RetrofitUtil.getInstance().create(T::class.java)
```

```xml
api\BaseObserver
http\BaseObserver

上麦接口:/api/room/applyPit
下麦接口:/api/room/returnPit
许愿池接口:/api/tree/index
```
#### 需要添加 分页功能


#### 添加 ToastUtil 错误
```xml
    Build was configured to prefer settings repositories over project repositories but repository ...
    https://blog.csdn.net/hpp_1225/article/details/119888981
```

#### Retrofit异步请求多个接口
```java
    val a = lifecycleScope.async { getDataA() }
    val b = lifecycleScope.async { getDataB() }
```
https://blog.csdn.net/yu540135101/article/details/113246177

#### 常用的正则表达式
https://www.cnblogs.com/xuqiang7/p/11082729.html