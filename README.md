# 乐福乐Android接入文档

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

implementation 'com.alldls.lflsdk:lflsdk:1.0.6'
// 资源包可选，请联系相关人员申请

///!!!重要说明！！！需要引用最新版融合SDK
```

### 3.乐福乐SDK初始化（二选一）

```java
LflSDK.init(Application application, String appId, InitListener initListener);//重要！！如果您没有引用了资源包请使用此方法初始化
```

```java
public interface InitListener {
    /***
     * 初始化成功（重要！！请务必在初始化成功后再调用乐福乐页面的显示方法）
     */
    void initSuccess();
    /***
     * 初始化失败
     */
    void initFail();
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

//注意！此接入方式一定要在onBackPressed中调用
@Override
public void onBackPressed() {
    if (lflTasksLayout.getWebView().canGoBack()) {
       lflTasksLayout.getWebView().goBack();
    } else {
       super.onBackPressed();
    }
}
//注意！此接入方式一定要在onDestroy中调用
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
LflSDK.addListener(new LflCustomTaskListener() {
        @Override
        public void onCallCustomTask(Context context, int customTaskType) {
            if (customTaskType == CustomTaskType.SHARE) {
                //调用媒体端分享逻辑 
            } else if (customTaskType == CustomTaskType.INVITE) {
                //调用媒体端邀请逻辑 
            } else if (customTaskType == CustomTaskType.TAKE_PHOTO) {
                //调用媒体端拍照逻辑 
            } else if (customTaskType == CustomTaskType.CHECK_LOGIN) { 
                //调用媒体端检测登录逻辑
                if (...已登录...) {
                    LflSDK.triggerSuccess(customTaskType);
                } else {
                    LflSDK.triggerFail(customTaskType);
                }
            } else if (customTaskType == CustomTaskType.LOGIN) { 
                //调用媒体端登录逻辑
            }
        }
});
```

#### 5.2 当用户操作了自定义任务需要调用如下代码通知乐福乐SDK

```java
//任务完成
LflSDK.triggerSuccess(int customTaskType);

//任务失败
LflSDK.triggerFail(int customTaskType);
```

CustomTaskType类

```java
public interface CustomTaskType {

    int TAKE_PHOTO = 5; //拍照

    int SHARE = 6; //分享

    int INVITE = 7; //邀请

    int CHECK_LOGIN = 8; //检测登录

    int LOGIN = 9; //去登录

    int REWARD_VIDEO_COMPLETE = 100; //激励视频播放完成通知
}
```

LflCustomTaskListener类：

```java
public interface LflCustomTaskListener {
    /**
     * 调用自定义任务(当用户点击页面的自定义任务时会响应此回调)
     *
     * @param customTaskType
     */
    void onCallCustomTask(Context context, int customTaskType);
}
```
