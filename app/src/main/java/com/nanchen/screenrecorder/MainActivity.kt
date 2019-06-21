package com.nanchen.screenrecorder

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.PathUtils
import com.nanchen.screenrecordhelper.ScreenRecordHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var screenRecordHelper: ScreenRecordHelper? = null
    private val afdd: AssetFileDescriptor by lazy { assets.openFd("test.aac") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (screenRecordHelper == null) {
                    screenRecordHelper = ScreenRecordHelper(this, object : ScreenRecordHelper.OnVideoRecordListener {
                        override fun onBeforeRecord() {
                        }

                        override fun onStartRecord() {
                            play()
                        }

                        override fun onCancelRecord() {
                            releasePlayer()
                        }

                        override fun onEndRecord() {
                            releasePlayer()
                        }

                    }, PathUtils.getExternalStoragePath() + "/nanchen")
                }
                screenRecordHelper?.apply {
                    if (!isRecording) {
                        // 如果你想录制音频（一定会有环境音量），你可以打开下面这个限制,并且使用不带参数的 stopRecord()
//                        recordAudio = true
                        startRecord()
                    }
                }
            } else {
                Toast.makeText(this@MainActivity.applicationContext, "sorry,your phone does not support recording screen", Toast.LENGTH_LONG).show()
            }
        }

        btnStop.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                screenRecordHelper?.apply {
                    if (isRecording) {
                        if (mediaPlayer != null) {
                            // 如果选择带参数的 stop 方法，则录制音频无效
                            stopRecord(mediaPlayer!!.duration.toLong(), 15 * 1000, afdd)
                        } else {
                            stopRecord()
                        }
                    }
                }
            }
        }
    }

    private fun play() {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.apply {
                this.reset()
                this.setDataSource(afdd.fileDescriptor, afdd.startOffset, afdd.length)
                this.isLooping = true
                this.prepare()
                this.start()
            }
        } catch (e: Exception) {
            Log.d("nanchen2251", "播放音乐失败")
        } finally {

        }
    }

    // 音频播放
    private var mediaPlayer: MediaPlayer? = null

    private fun releasePlayer() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && data != null) {
            screenRecordHelper?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screenRecordHelper?.clearAll()
        }
        afdd.close()
        super.onDestroy()
    }
}
