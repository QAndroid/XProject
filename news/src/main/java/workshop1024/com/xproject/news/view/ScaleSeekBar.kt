package workshop1024.com.xproject.news.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.View.MeasureSpec.*
import workshop1024.com.xproject.base.utils.UnitUtils
import workshop1024.com.xproject.news.R
import kotlin.math.min

class ScaleSeekBar : View {
    private var mContext: Context

    private lateinit var mSelectedPaint: Paint
    private lateinit var mUnSelectedPaint: Paint

    //字体大小刻度数组，注意该字符串必须是Float字符串
    private var mSizes: Array<String>? = null
    //当前字体大小
    private var mSelectedIndex: Int = 0
    private var mCirclePointNum: Int = 0
    //大圆点绘制半径
    private var mCirclePointMaxRadius: Int = 0
    //小圆点绘制半径
    private var mCirclePointMinRadius: Int = 0
    //计算点的个数
    private var mCaculateNum: Int = 0

    //圆点坐标
    private var mPointPositions: Array<FloatArray>? = null
    //计算点的个数
    private var mCaculatePositions: Array<FloatArray>? = null

    var selectedTextSize: Float
        get() = java.lang.Float.valueOf(mSizes!![mSelectedIndex])!!
        set(textSize) {
            for (size_i in mSizes!!.indices) {
                if (java.lang.Float.valueOf(mSizes!![size_i]) == textSize) {
                    mSelectedIndex = size_i
                }
            }
        }

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ScaleSeekBar, 0, 0)
        try {
            val sizesResourceId = typedArray.getResourceId(R.styleable.ScaleSeekBar_sizeArray, 0)
            if (sizesResourceId != 0) {
                mSizes = resources.getStringArray(sizesResourceId)
            }
            mSelectedIndex = typedArray.getInt(R.styleable.ScaleSeekBar_selectedIndex, 1)
        } finally {
            typedArray.recycle()
        }

        //初始化绘制属性
        mSelectedPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = UnitUtils.dpToPx(mContext, 6f).toFloat()
            color = Color.parseColor("#01A3AE")
        }


        mUnSelectedPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = UnitUtils.dpToPx(mContext, 6f).toFloat()
            color = Color.parseColor("#C3C3C3")
        }

        //计算绘制相关数据
        mCirclePointNum = mSizes?.size ?: 0
        mCirclePointMaxRadius = UnitUtils.dpToPx(mContext, 12f)
        mCirclePointMinRadius = UnitUtils.dpToPx(mContext, 6f)
        mCaculateNum = mCirclePointNum - 1
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("XProject", "onDraw()")
        if (mPointPositions == null) {
            val circlePointSpace = ((width - mCirclePointMaxRadius * 2) / (mCirclePointNum - 1)).toFloat()
            mPointPositions = Array(mCirclePointNum) { FloatArray(2) }
            for (point_i in 0 until mCirclePointNum) {
                mPointPositions!![point_i][0] = mCirclePointMaxRadius + point_i * circlePointSpace
                mPointPositions!![point_i][1] = (height / 2).toFloat()
            }
        }

        if (mCaculatePositions == null) {
            val circlePointSpace1 = ((width - mCirclePointMaxRadius * 2) / (mCirclePointNum - 1)).toFloat()
            mCaculatePositions = Array(mCaculateNum) { FloatArray(2) }
            for (caculate_i in 0 until mCaculateNum) {
                mCaculatePositions!![caculate_i][0] = mCirclePointMaxRadius + (caculate_i * 2 + 1) * (circlePointSpace1 / 2)
                mCaculatePositions!![caculate_i][1] = (height / 2).toFloat()
            }
        }

        //绘制线条
        canvas?.drawLine(mPointPositions!![0][0], mPointPositions!![0][1], mPointPositions!![mSelectedIndex][0],
                mPointPositions!![mSelectedIndex][1], mSelectedPaint)
        canvas?.drawLine(mPointPositions!![mSelectedIndex][0], mPointPositions!![mSelectedIndex][1],
                mPointPositions!![mCirclePointNum - 1][0], mPointPositions!![mCirclePointNum - 1][1], mUnSelectedPaint)

        //绘制点
        for (point_i in 0 until mCirclePointNum) {
            when {
                point_i < mSelectedIndex -> {
                    canvas?.drawCircle(mPointPositions!![point_i][0], mPointPositions!![point_i][1], mCirclePointMinRadius.toFloat(), mSelectedPaint)
                }
                point_i == mSelectedIndex -> {
                    canvas?.drawCircle(mPointPositions!![point_i][0], mPointPositions!![point_i][1], mCirclePointMaxRadius.toFloat(), mSelectedPaint)
                }
                else -> {
                    canvas?.drawCircle(mPointPositions!![point_i][0], mPointPositions!![point_i][1], mCirclePointMaxRadius.toFloat(), mUnSelectedPaint)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //解决wrap_content无效问题，参考：https://www.jianshu.com/p/1a1491f059fc
        val width = getMyMeasureSize(widthMeasureSpec)
        val height = getMyMeasureSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN, ACTION_MOVE -> {
                Log.i("XProject", "X = " + event.x + "，Y = " + event.y)
                val newSize = caculateSelectedSize(event)
                if (newSize != mSelectedIndex) {
                    mSelectedIndex = newSize
                    invalidate()
                }
            }
        }

        //为什么没有ACTION_MOVE和ACTION_UP，参考：https://stackoverflow.com/questions/5933426/android-cant-see-action-move-up-in-ontouchevent-of-a-relativelayout
        return true
    }

    fun getMyMeasureSize(measureSpec: Int): Int {
        var resultSize = 100
        val specMode = getMode(measureSpec)
        val specSize = getSize(measureSpec)
        when (specMode) {
            EXACTLY, UNSPECIFIED -> resultSize = specSize
            AT_MOST -> resultSize = min(resultSize, specSize)
        }
        return resultSize
    }

    private fun caculateSelectedSize(event: MotionEvent?): Int {
        Log.i("XProject", "caculateSelectedSize()")
        var resultdSize = mSelectedIndex

        val currentX = event?.x
        when {
            currentX?.compareTo(mCaculatePositions!![0][0])!! <= 0 -> {
                resultdSize = 0
                Log.i("XProject", "currentX <= mPointPositions[0][0],resultdSize = $resultdSize")
            }
            currentX.compareTo(mCaculatePositions!![mCaculateNum - 1][0]) >= 0 -> {
                resultdSize = mCirclePointNum - 1
                Log.i("XProject", "currentX >= mPointPositions[mCirclePointNum - 1][0],resultdSize = $resultdSize")
            }
            else -> {
                for (caculate_i in 0 until mCaculateNum) {
                    if (currentX >= mCaculatePositions!![caculate_i][0] && currentX < mCaculatePositions!![caculate_i + 1][0]) {
                        resultdSize = caculate_i + 1
                        Log.i("XProject", "currentX >= mPointPositions[caculate_i][0] && currentX < mPointPositions[caculate_i][0],resultdSize = $resultdSize")
                        break
                    }
                }
            }
        }

        return resultdSize
    }
}