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

#### 带有标题的Adapter
```java
public class MainAdapter extends RecyclerView.Adapter {

    private List<ModuleInfo> topicList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (mOnItemClickListener != null) {
            myViewHolder.list.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    int position1 = myViewHolder.getLayoutPosition();
                    v.setTag(topicList.get(position1));
                    mOnItemClickListener.onItemClick(v, position1);
                }
            });
        }
        ModuleInfo moduleInfo = topicList.get(position);
        if (moduleInfo.getTitleName() != null) {
            myViewHolder.title.setVisibility(View.VISIBLE);
            myViewHolder.titleName.setText(moduleInfo.getTitleName());
        } else {
            myViewHolder.title.setVisibility(View.GONE);
        }

        myViewHolder.name.setText(moduleInfo.getModule());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    /**
     * 新增模块信息
     *
     * @param moduleInfo module info
     */
    public void addModuleInfo(ModuleInfo moduleInfo) {
        topicList.add(moduleInfo);
        notifyDataSetChanged();
    }

    public void clear() {
        topicList.clear();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout list;
        TextView name;
        TextView titleName;
        RelativeLayout title;

        MyViewHolder(View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.list);
            name = itemView.findViewById(R.id.name);
            titleName = itemView.findViewById(R.id.title_name);
            title = itemView.findViewById(R.id.title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
````

#### CardView会覆盖其他组件问题

```xml
需要将 CardView 的 cardEvelent = 0
```


#### 项目点击run 运行完后 无法弹出 程序界面
```xml
<activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
 主 Activity 下 需添加 `android:exported="true"`
```

#### 设置状态栏字体颜色及更改虚拟导航栏背景颜色
```xml
    均记录在  baseretrofit-utils-STATUS_BAR_HEIGHT.kt 中
```

#### 在基类中设置需要使用的方法 如下所示
```java
    /**
     * 需要设置状态栏颜色的activity
     */
    abstract fun getActivity(): Activity?

    /**
     * 需要给状态栏字体设置的颜色
     * true 黑色
     * false 白色
     */
    abstract fun setStatusFontColor(): Boolean

    /**
     * 需要设置的组件id
     */
    abstract fun viewId(): View?

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 设置与状态栏高度相同的边距
     */
    private fun setMarginToStatusBar(){
        if(viewId() != null){
            //计算状态栏高度
            val statusBarHeight = getStatusBarHeight(this)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            viewId()?.layoutParams?.height = statusBarHeight
            viewId()?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    /**
     * 在基类中给相应的activity设置状态栏字体
     */
    getActivity()?.let { setAndroidNativeLightStatusBar(it,setStatusFontColor()) }

    /**
     * 子类中重写该方法
     * *返回给基类需要的activity 需要用 this 替代*
     */
     override fun getActivity(): Activity = this

     override fun setStatusFontColor(): Boolean = true

```


#### TabLayout 相关问题
```xml
    可以通过
    app:tabIndicator="@drawable/shape_tab_indicator" //指定 tab 指示器的样式
    app:tabIndicatorFullWidth="false" //设置指示器不铺满
    app:tabIndicatorHeight="3dp"  //指定高度
```

#### 瀑布流
```java
            /**
             * 计算ImageView宽高 实现瀑布流效果
             */
            val width = itemDynamicShopIv.layoutParams.width / 3
            val height = (200 + Math.random() * 400).toInt()

            itemDynamicShopIv.layoutParams.height = height

```

#### Android Gradle plugin requires Java 11 to run. You are currently using Java 1.8.

```xml
https://juejin.cn/post/6948967511400382501
```

