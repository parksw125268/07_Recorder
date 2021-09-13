package com.example.a07_recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SoundVisualizerView (
    context : Context,
    attrs : AttributeSet? = null
): View( context, attrs ){

                                    //ANTI_ALIAS_FLAG: 계단화 방지 라고해서 곡선을 잘 그릴수 있도록 도와줌
    val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_500)//색상지정
        strokeWidth  =  LINE_WIDTH //라인 굵기
        strokeCap    =  Paint.Cap.ROUND // 라인의 양 끄트머리를 둥글게
    }
    //== 그릴 사이즈 지정 ==
    var drawingWidth: Int = 0
    var drawingHeight: Int = 0
    var drawingAmplitudes : List<Int> = emptyList()//내가 그릴것을 list에 넣어서

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { // 디바이스에 맞게 사이즈를 가져다줌.
        super.onSizeChanged(w, h, oldw, oldh)                          // old 필요없음 .
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {//그리기
        super.onDraw(canvas)

        canvas ?: return //null이면 return
        val centerY = drawingHeight/2f //그려질 사이즈의 절반.
        var offsetX = drawingWidth.toFloat() //그릴 위치 (처음에 오른쪽 끝에서 나와서 왼쪽으로 가면서 그려지도록 할 예정

        drawingAmplitudes.forEach{ amplitude ->
            val lineLength = amplitude  / MAX_AMPLITUDE * drawingHeight * 0.8F//그릴려는 높이(drawingHeight) 데비 몇퍼센트 인가 //0.8은 좀더 작게
            offsetX -= LINE_SPACE //그릴 위치를 왼쪽으로 이동시킴
            //왼쪽으로 이동하다가 View를 넘어간다면
            if (offsetX < 0) return@forEach

            //todo 본격적으로 ampitude를 그리자!
        }

    }

    companion object{
        private const val LINE_WIDTH = 10F //오디오 녹음시 세로 라인의 굵기
        private const val LINE_SPACE = 15F //라인과 라인 사이의 간격
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
    }

}