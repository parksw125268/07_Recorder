package com.example.a07_recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    private val recordButton : RecordButton by lazy {
        findViewById<RecordButton>(R.id.recordButton)
    }
    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private val recordingFilePath :String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    private var recorder:MediaRecorder?= null
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

    private fun startRecording(){
        recorder = MediaRecorder().run { //apply말고 한번 run의 개념 이해를 위해 run으로 시도해봄.
            setAudioSource(MediaRecorder.AudioSource.MIC) //마이크
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)//컨테이너 역할. 인코딩(압축)된 파일들을 차곡차곡 정리해둠
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)//AMR_NB방식으로 인코딩(버전에 상관없이 가능해서 AMR_NB방식을 체택)
            setOutputFile(recordingFilePath)//저장할 경로
            prepare() //세팅 다 끝내고 얘를 해줘야 녹음할 수 있는 상태가 됨.
            this //run은 맨마지막 값을 반환하므로 this를 써줌.
        }
        recorder?.start() //실제 녹음 시작.
    }

    private fun stopRecording(){
        recorder?.run {
            stop()//녹음 멈춤.
            release()//메모리 해제
        }
        recorder = null
    }
    companion object{ //자바의 상수와 같은 것
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}