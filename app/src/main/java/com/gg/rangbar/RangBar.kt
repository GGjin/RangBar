package com.gg.progressbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Creator : GG
 * Time    : 2017/12/4
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class RangBar : View {

    private lateinit var mNormalBitmap: Bitmap
    private lateinit var mForceBitmap: Bitmap

    private var mGradeNumber: Int = 0

    private var mCurrentNumber: Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context?.obtainStyledAttributes(attrs, R.styleable.RangBar)
        val normalId = array?.getResourceId(R.styleable.RangBar_normalBar, R.drawable.star_normal)
        mNormalBitmap = BitmapFactory.decodeResource(resources, normalId!!)
        val forceId = array.getResourceId(R.styleable.RangBar_focusBar, R.drawable.star_selected)
        mForceBitmap = BitmapFactory.decodeResource(resources, forceId)

        mGradeNumber = array.getInt(R.styleable.RangBar_gradeNumber, 0)

        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = mNormalBitmap.width * mGradeNumber
        val height = mNormalBitmap.height
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until mGradeNumber) {
            if (i < mCurrentNumber)
                canvas?.drawBitmap(mForceBitmap, i * mForceBitmap.width.toFloat(), paddingTop.toFloat(), null)
            else
                canvas?.drawBitmap(mNormalBitmap, i * mNormalBitmap.width.toFloat(), paddingTop.toFloat(), null)
        }
//        repeat(mCurrentNumber - 1) { i ->
//            run {
//                if (i < mCurrentNumber)
//                    canvas?.drawBitmap(mForceBitmap, i * mNormalBitmap.width, paddingTop, null)
//            }
//        }

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val position = (x / mNormalBitmap.width).toInt()


                if (position == mCurrentNumber - 1) {
                    return true
                }
                mCurrentNumber = position + 1
                invalidate()
            }
        }
        return true
    }


}