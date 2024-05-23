package com.example.face_recognition

import android.annotation.SuppressLint
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.transition.TransitionManager
import android.util.SizeF
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.face_recognition.databinding.ActivityMainBinding
import com.example.recognition.recognition.Camera
import com.example.recognition.recognition.detection.FaceAnalyzerListener

@SuppressLint("ParcelCreator")
abstract class MainActivity() : AppCompatActivity(), FaceAnalyzerListener, Parcelable {
    private lateinit var binding : ActivityMainBinding
    private val camera = Camera(this)

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setProgressText("시작하기를 눌러주세요.")
            camera.initCamera(cameraLayout, this@MainActivity)

            startDetectButton.setOnClickListener {
                it.isVisible = false
                camera.startFaceDetect()
                setProgressText("얼굴을 보여주세요.")
            }
        }
    }


    override fun detect() {

    }

    override fun stopDetect() {
        camera.stopFaceDetect()
        reset()
    }

    override fun notDetect() {

    }

    override fun detectProgress(progress: Float, message: String) {
        setProgressText(message)
    }

    override fun faceSize(rectF: RectF, sizeF: SizeF, pointF: PointF) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun reset() {
        binding.startDetectButton.isVisible = true
    }

    private fun setProgressText(text:String) {
        TransitionManager.beginDelayedTransition(binding.root)
        binding.progressTextView.text = text
    }

}