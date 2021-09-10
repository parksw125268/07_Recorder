package com.example.a07_recorder

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val recordButton : RecordButton by lazy {
        findViewById<RecordButton>(R.id.recordButton)
    }
    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var state = State.BEFORE_RECORDING
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission() //앱 시작하자마자 ㄱㄱ
        initViews()
    }

    override fun onRequestPermissionsResult( //generate override
        requestCode: Int, //<-201이들어옴
        permissions: Array<out String>, //<- RECORD_AUDIO 권한이 들어올 것임.
        grantResults: IntArray          //<- 권한 요청한것에 대한 결과
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)//
        var audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                                           grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
                                            //firstOrNull : list의 첫번째값을 반환하되 null이면 error
        if(!audioRecordPermissionGranted){
            finish()
        }
    }

    private fun requestAudioPermission(){
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)//권한 종류, 리퀘스트 코드.
    }
    private fun initViews(){
        recordButton.updateIconWithState(state)
    }
    companion object{ //자바의 상수와 같은 것
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}