# 乐福乐alldls_Android接入文档1.0.4

## 1. SDK接入

#### 1.1 申请AppId

请向相关人员申请 appId

## 2. 引用SDK

```groovy
maven {
  credentials {
      username 'rvxtfz'
      password 'QBWaa4Fi8O'
      	}
      url 'https://packages.aliyun.com/maven/repository/2046311-release-HZhbV0/'
   	}
maven { url 'https://repo1.maven.org/maven2/' }

implementation 'com.alldls.lflsdk:lflsdk:1.0.4'
// 资源包可选，请联系相关人员申请

///!!!重要说明！！！需要引用最新版融合SDK
```

### 3.乐福乐SDK初始化（二选一）

```java

LflSDK.init(Application application, String appId);//重要！！如果您引用了资源包请使用此方法初始化


LflSDK.init(Application application, String appId, InitListener initListener);//重要！！如果您没有引用了资源包请使用此方法初始化

```

```java
public interface InitListener {
    /***
     * 初始化成功（重要！！请务必在初始化成功后再调用乐福乐页面的显示方法）
     */
    void initSuccess();
}
```

## 4.乐福乐SDK有两种方式,选择其中一种即可

#### 4.1 直接跳转到SDK内部乐福乐Activity

```java
LflSDK.show(Context context, String userId, EventListener eventListener);
```

#### 4.2 引用LflTasksLayout

```java
LflLayout lflTasksLayout= (LflLayout) findViewById(R.id.LflTasksLayout);

lflTasksLayout.onLoadLayout(Activity activity, String userId,EventListener eventListener);

注意！此接入方式一定要在onBackPressed中调用
@Override
public void onBackPressed() {
if (lflTasksLayout.getWebView().canGoBack()) {
   lflTasksLayout.getWebView().goBack();
} else {
   super.onBackPressed();
  }
}
注意！此接入方式一定要在onDestroy中调用
  lflLayout.onDestroy();
```

EventListener类：

```java
public interface EventListener {
    /***
     * 乐福乐页面关闭
     */
    void onPageClose();

}
```

## 5 自定义任务回调说明

#### 5.1 在适当位置添加自定义任务回调

```java
LflSDK.addListener(LflCustomTaskListener listener)
```

#### 5.2 当用户操作了自定义任务需要调用如下代码通知乐福乐SDK

```java
//任务完成
LflSDK.triggerSuccess(CustomTaskType customTask);

//任务失败
LflSDK.triggerFail(CustomTaskType customTask);
```

CustomTaskType类

```java
public enum CustomTaskType {

    TAKE_PHOTO("拍照", 5),

    SHARE("分享", 6),

    INVITE("邀请", 7);
}
```

LflCustomTaskListener类：

```java
public interface LflCustomTaskListener {
    /**
     * 调用自定义任务(当用户点击页面的自定义任务时会响应此回调)
     *
     * @param customTask
     */
    void onCallCustomTask(CustomTaskType customTsk);
}
```