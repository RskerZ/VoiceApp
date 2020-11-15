package com.example.voiceko

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View


class CircleProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs){
    private var percentage: Float = 0F
    private var strProgress: String = ""
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        var width = MeasureSpec.getSize(widthMeasureSpec)
//        var height = MeasureSpec.getSize(heightMeasureSpec)
//        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        if (widthMode == MeasureSpec.AT_MOST){
//            width = paddingLeft + paddingRight + FormatUtil.dp2px(context,200)
//        }
//        if (heightMode == MeasureSpec.AT_MOST){
//            height = paddingTop + paddingBottom + FormatUtil.dp2px(context,200)
//        }
//        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true //消除鋸齒

        val bottomColor = Color.parseColor("#C9C9C9") //設置顏色

        paint.color = bottomColor //設置底部顏色

        paint.strokeJoin = Paint.Join.ROUND //設置畫筆畫出的形狀

        paint.strokeCap = Paint.Cap.ROUND //使線的尾端具有圓角

        paint.style = Paint.Style.STROKE //使圓為空心

        paint.strokeWidth = dp2px(10F) //設置外圍線的粗度

        val mRecF = RectF(
            (10 + dp2px(20F)), (10 + dp2px(20F))
            , (width - 10 - dp2px(20F)), (height-80).toFloat()
        ) //畫一個扇形

        canvas!!.drawArc(mRecF, 360F, 360F, false, paint) //將扇形畫至畫布上
        paint.color = Color.GREEN;//把顏色換成綠色
        paint.strokeWidth = dp2px(30F) //設置外圍線的粗度
        canvas.drawArc(mRecF, 90F, percentage, false, paint);//畫進度

        paint.reset()

        paint.textSize = dp2px(24F)
        paint.color = Color.BLACK
        paint.strokeWidth = dp2px(10F)
        //获取文字边缘
        canvas.drawText(strProgress, (width/2-75).toFloat(), (height/2).toFloat(),paint)
    }
    private fun dp2px(dp: Float): Float {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        return dp * metrics.density
    }
    //設定百分比跟進度條
    fun setPercentage(value: Float){
        strProgress = "${(value * 100).toInt()}%"
        val valueAnimator = ValueAnimator.ofFloat(percentage, value * 360)
        percentage = value * 360
        valueAnimator.addUpdateListener { vA: ValueAnimator ->
            percentage = vA.animatedValue as Float
            postInvalidate()
        }
        valueAnimator.duration = 500 //設置動畫時間

        valueAnimator.start()
    }
}