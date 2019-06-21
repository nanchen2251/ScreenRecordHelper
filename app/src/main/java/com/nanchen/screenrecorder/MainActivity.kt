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
import com.nanchen.screenrecord.ScreenRecorder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var screenRecorder: ScreenRecorder? = null
    private val afdd: AssetFileDescriptor by lazy { assets.openFd("test.aac") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (screenRecorder == null) {
                    screenRecorder = ScreenRecorder(this, object : ScreenRecorder.OnVideoRecordListener {
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
                screenRecorder?.apply {
                    if (!isRecording) {
                        screenRecorder?.startRecord()
                    }
                }
            } else {
                Toast.makeText(this@MainActivity.applicationContext, "sorry,your phone does not support recording screen", Toast.LENGTH_LONG).show()
            }
        }

        btnStop.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                screenRecorder?.apply {
                    if (isRecording) {
                        if (mediaPlayer != null) {
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
            screenRecorder?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screenRecorder?.clearAll()
        }
        afdd.close()
        super.onDestroy()
    }
}
