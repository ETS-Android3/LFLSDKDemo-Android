# 乐福乐alldls_Android接入文档1.0.4.2

## 1. SDK接入

#### 1.1 申请AppId

请向相关人员申请 appId

## 2. 引用SDK

```
maven {
  credentials {
      username 'rvxtfz'
      password 'QBWaa4Fi8O'
      	}
      url 'https://packages.aliyun.com/maven/repository/2046311-release-HZhbV0/'
   	}
maven { url 'https://repo1.maven.org/maven2/' }

implementation 'com.alldls.lflsdk:lflsdk:1.0.4.2'
///!!!重要说明！！！需要额外引用最新版融合SDK
```

### 3.乐福乐SDK初始化

请在Application中调用初始化方法

```java
LflSDK.init(Application application, String appId);
```

## 4.乐福乐SDK有两种方式,选择其中一种即可

#### 4.1 直接跳转到SDK内部乐福乐Activity

```
LflSDK.show(Context context, String appId, String userId, EventListener eventListener);
```

#### 4.2 引用LflTasksLayout

```
LflLayout lflTasksLayout= (LflLayout) findViewById(R.id.LflTasksLayout);

lflTasksLayout.onLoadLayout(Activity activity, String appId, String userId,EventListener eventListener);

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
LflSDK.addListener(new LflCustomTaskListener() {
            @Override
            public void onCallCustomTask(Context context, CustomTaskType customTaskType) {
                if (customTaskType == CustomTaskType.SHARE) {
                    //调用媒体端分享逻辑 
                } else if (customTaskType == CustomTaskType.INVITE) {
                    //调用媒体端邀请逻辑 
                } else if (customTaskType == CustomTaskType.TAKE_PHOTO) {
                    //调用媒体端拍照逻辑 
                } else if (customTaskType == CustomTaskType.CHECK_LOGIN) { //调用媒体端检测登录逻辑
                    if (true) {
                        LflSDK.triggerSuccess(customTaskType);
                    } else {

                        LflSDK.triggerFail(customTaskType);
                    }
                } else if (customTaskType == CustomTaskType.LOGIN) { //调用媒体端登录逻辑
                }
            }
        });
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
    
    INVITE("邀请", 7),
    
    CHECK_LOGIN("登录检测", 8),
    
    LOGIN("登录", 9);
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