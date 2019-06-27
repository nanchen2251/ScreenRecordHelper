# ScreenRecordHelper
🔥screen record helper https://github.com/nanchen2251/ScreenRecordHelper

[中文使用说明](https://github.com/nanchen2251/ScreenRecordHelper/blob/master/README_zh.md)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/nanchen2251/ScreenRecordHelper/blob/master/LICENSE)<p>
## feature
1. screen record
2. audio record
3. support stripping environment volume
4. support saving to album
5. support for custom duration
6. support for custom path and name
7. resist the test of DAU 1 million users
## Screenshots

## How to use it
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
    implementation 'com.github.nanchen2251:ScreenRecordHelper:1.0.2'
}
```

#### Step 3. Just use it in your project
```kotlin
// start screen record
if (screenRecordHelper == null) {
    screenRecordHelper = ScreenRecordHelper(this, null, PathUtils.getExternalStoragePath() + "/nanchen")
}
screenRecordHelper?.apply {
    if (!isRecording) {
        // if you want to record the audio,you can set the recordAudio as true
        screenRecordHelper?.startRecord()
    }
}

// You must rewrite the onActivityResult
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && data != null) {
        screenRecordHelper?.onActivityResult(requestCode, resultCode, data)
    }
}
    
// just stop screen record
screenRecordHelper?.apply {
    if (isRecording) {
        stopRecord()     
    }
}
```
#### Step 4. if you want to mix the audio into your video,you just should do
```kotlin
// parameter1 -> The last video length you want
// parameter2 -> the audio's duration
// parameter2 -> assets resource
stopRecord(duration, audioDuration, afdd)
```

#### Step 5. If you still don't understand, please refer to the demo

### About the author
    nanchen<br>
    Chengdu,China<br>
    [其它开源](https://github.com/nanchen2251/)<br>
    [个人博客](https://nanchen2251.github.io/)<br>
    [简书](http://www.jianshu.com/u/f690947ed5a6)<br>
    [博客园](http://www.cnblogs.com/liushilin/)<br>
    交流群：118116509<br>
    欢迎投稿(关注)我的唯一公众号，公众号搜索 nanchen 或者扫描下方二维码：<br>
    ![](https://github.com/nanchen2251/Blogs/blob/master/images/nanchen12.jpg)
    
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
