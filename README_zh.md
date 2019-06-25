# ScreenRecordHelper
🔥screen record helper https://github.com/nanchen2251/ScreenRecordHelper


## 特点
1. 屏幕录制（Android 5.0+）
2. 支持剥离环境音量（即录制好的视频只有媒体音乐）
3. 支持自动更新到相册
4. 支持自定义视频时长
5. 支持自定义文件路径和名称
6. 抗住了百万级日活 APP 考验
## 屏幕截图

## 怎么使用
#### Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}       
```
#### Step 2. Add the dependency
```groovy
dependencies {
    implementation 'com.github.nanchen2251:ScreenRecordHelper:1.0.1'
}
```

#### Step 3. 直接在你的项目中使用
```kotlin
// 开始录屏，第二个参数是回调，不清楚请直接查看demo
if (screenRecordHelper == null) {
    screenRecordHelper = ScreenRecordHelper(this, null, PathUtils.getExternalStoragePath() + "/nanchen")
}
screenRecordHelper?.apply {
    if (!isRecording) {
        screenRecordHelper?.startRecord()
    }
}
// 你必须重写 onActivityResult 方法，否则会发现申请录屏后没有回调
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && data != null) {
        screenRecordHelper?.onActivityResult(requestCode, resultCode, data)
    }
}    
// 直接停止录屏，不做音频剪辑处理
screenRecordHelper?.apply {
    if (isRecording) {
        stopRecord()     
    }
}
```
#### Step 4. if you want to mix the audio into your video,you just should do
```kotlin
// parameter1 -> 你想要的视频时长
// parameter2 -> 你合成的音频时长，一般情况下这个是你知道的
// parameter2 -> assets文件夹下的 aac 文件
stopRecord(duration, audioDuration, afdd)
```

#### Step 5. 如果你还不清楚，请移步到 Demo 查看

### 关于作者
    南尘<br>
    四川成都<br>
    [其它开源](https://github.com/nanchen2251/)<br>
    [个人博客](https://nanchen2251.github.io/)<br>
    [简书](http://www.jianshu.com/u/f690947ed5a6)<br>
    [博客园](http://www.cnblogs.com/liushilin/)<br>
    交流群：118116509<br>
    欢迎投稿(关注)我的唯一公众号，公众号搜索 nanchen 或者扫描下方二维码：<br>
    ![](https://github.com/nanchen2251/Blogs/blob/master/images/nanchen12.jpg)
​    
## Licenses
```
 Copyright 2019 nanchen(刘世麟)

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
