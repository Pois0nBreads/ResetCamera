# ResetCamera
de.robv.android.xposed:api:82

通过对传入参数的修改 Camera.open(int) 和对系统判断前后摄像头标识 CameraCharacteristics.get(CameraCharacteristics) 的Hook来实现前后相机的重新定义

本软件适用于后置摄像头半死不活（比如索尼丢drm密匙绿屏）又需要用摄像头进行扫码时使用。

因为各大手机厂商的相机APP基本是调用内置库文件来启用的，所以本软件不适用大部分系统的自带相机（部分奇葩系统相机 玄学支持）

#如果可以，请赏我杯饮料喝 谢谢 #https://pois0nbreads.github.io/Breads/
