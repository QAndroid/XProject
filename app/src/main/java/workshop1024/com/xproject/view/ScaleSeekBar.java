package workshop1024.com.xproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import workshop1024.com.xproject.R;
import workshop1024.com.xproject.utils.UnitUtils;

public class ScaleSeekBar extends View {
    //字体大小刻度数组，注意该字符串必须是Float字符串
    private String[] mSizes;
    //当前字体大小
    private int mSelectedIndex;

    //大圆点绘制半径
    private int mCirclePointMaxRadius;
    //小圆点绘制半径
    private int mCirclePointMinRadius;
    //圆点的数量
    private int mCirclePointNum;
    //圆点坐标
    private float[][] mPointPositions;

    //计算点的个数
    private int mCaculateNum;
    //计算点坐标
    private float[][] mCaculatePositions;

    private Context mContext;
    private Paint mSelectedPaint;
    private Paint mUnSelectedPaint;

    public ScaleSeekBar(Context context) {
        super(context);
        mContext = context;
    }

    public ScaleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScaleSeekBar,
                0, 0);
        try {
            int sizesResourceId = typedArray.getResourceId(R.styleable.ScaleSeekBar_sizeArray, 0);
            if (sizesResourceId != 0) {
                mSizes = getResources().getStringArray(sizesResourceId);
            }
            mSelectedIndex = typedArray.getInt(R.styleable.ScaleSeekBar_selectedIndex, 1);
        } finally {
            typedArray.recycle();
        }

        //初始化绘制属性
        mSelectedPaint = new Paint();
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStrokeWidth(UnitUtils.dpToPx(mContext, 6));
        mSelectedPaint.setColor(Color.parseColor("#01A3AE"));

        mUnSelectedPaint = new Paint();
        mUnSelectedPaint.setAntiAlias(true);
        mUnSelectedPaint.setStrokeWidth(UnitUtils.dpToPx(mContext, 6));
        mUnSelectedPaint.setColor(Color.parseColor("#C3C3C3"));

        //计算绘制相关数据
        mCirclePointNum = mSizes.length;
        mCirclePointMaxRadius = UnitUtils.dpToPx(mContext, 12);
        mCirclePointMinRadius = UnitUtils.dpToPx(mContext, 6);
        mCaculateNum = mCirclePointNum - 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("XProject", "onDraw()");
        //FIXME 如果将数据计算放在其他生命周期？
        if (mPointPositions == null) {
            float circlePointSpace = (getWidth() - mCirclePointMaxRadius * 2) / (mCirclePointNum - 1);
            mPointPositions = new float[mCirclePointNum][2];
            for (int point_i = 0; point_i < mCirclePointNum; point_i++) {
                mPointPositions[point_i][0] = mCirclePointMaxRadius + point_i * circlePointSpace;
                mPointPositions[point_i][1] = getHeight() / 2;
            }
        }

        if (mCaculatePositions == null) {
            float circlePointSpace1 = (getWidth() - mCirclePointMaxRadius * 2) / (mCirclePointNum - 1);
            mCaculatePositions = new float[mCaculateNum][2];
            for (int caculate_i = 0; caculate_i < mCaculateNum; caculate_i++) {
                mCaculatePositions[caculate_i][0] = mCirclePointMaxRadius + (caculate_i * 2 + 1) * (circlePointSpace1 / 2);
                mCaculatePositions[caculate_i][1] = getHeight() / 2;
            }
        }

        //绘制线条
        canvas.drawLine(mPointPositions[0][0], mPointPositions[0][1], mPointPositions[mSelectedIndex][0],
                mPointPositions[mSelectedIndex][1], mSelectedPaint);
        canvas.drawLine(mPointPositions[mSelectedIndex][0], mPointPositions[mSelectedIndex][1],
                mPointPositions[mCirclePointNum - 1][0], mPointPositions[mCirclePointNum - 1][1], mUnSelectedPaint);

        //绘制点
        for (int point_i = 0; point_i < mCirclePointNum; point_i++) {
            if (point_i < mSelectedIndex) {
                canvas.drawCircle(mPointPositions[point_i][0], mPointPositions[point_i][1], mCirclePointMinRadius,
                        mSelectedPaint);
            } else if (point_i == mSelectedIndex) {
                canvas.drawCircle(mPointPositions[point_i][0], mPointPositions[point_i][1], mCirclePointMaxRadius,
                        mSelectedPaint);
            } else {
                canvas.drawCircle(mPointPositions[point_i][0], mPointPositions[point_i][1], mCirclePointMinRadius,
                        mUnSelectedPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //解决wrap_content无效问题，参考：https://www.jianshu.com/p/1a1491f059fc
        int width = getMyMeasureSize(widthMeasureSpec);
        int height = getMyMeasureSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.i("XProject", "X = " + event.getX() + "，Y = " + event.getY());
                int newSize = caculateSelectedSize(event);

                if (newSize != mSelectedIndex) {
                    mSelectedIndex = newSize;
                    invalidate();
                }
                break;
        }

        //为什么没有ACTION_MOVE和ACTION_UP，参考：https://stackoverflow.com/questions/5933426/android-cant-see-action-move-up-in-ontouchevent-of-a-relativelayout
        return true;
    }

    private int caculateSelectedSize(MotionEvent event) {
        Log.i("XProject", "caculateSelectedSize()");
        int resultdSize = mSelectedIndex;

        float currentX = event.getX();
        if (currentX <= mCaculatePositions[0][0]) {
            resultdSize = 0;
            Log.i("XProject", "currentX <= mPointPositions[0][0],resultdSize = " + resultdSize);
        } else if (currentX >= mCaculatePositions[mCaculateNum - 1][0]) {
            resultdSize = mCirclePointNum - 1;
            Log.i("XProject", "currentX >= mPointPositions[mCirclePointNum - 1][0],resultdSize = " + resultdSize);
        } else {
            for (int caculate_i = 0; caculate_i < mCaculateNum; caculate_i++) {
                if (currentX >= mCaculatePositions[caculate_i][0] && currentX < mCaculatePositions[caculate_i + 1][0]) {
                    resultdSize = caculate_i + 1;
                    Log.i("XProject", "currentX >= mPointPositions[caculate_i][0] && currentX < mPointPositions[caculate_i][0],resultdSize = " + resultdSize);
                    break;
                }
            }
        }

        return resultdSize;
    }

    public int getMyMeasureSize(int measureSpec) {
        int resultSize = 100;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                resultSize = specSize;
                break;
            case MeasureSpec.AT_MOST:
                resultSize = Math.min(resultSize, specSize);
                break;
        }
        return resultSize;
    }

    public float getSelectedTextSize() {
        return Float.valueOf(mSizes[mSelectedIndex]);
    }

    public void setSelectedTextSize(float textSize) {
        for (int size_i = 0; size_i < mSizes.length; size_i++) {
            if (Float.valueOf(mSizes[size_i]) == textSize) {
                mSelectedIndex = size_i;
            }
        }
    }
}
