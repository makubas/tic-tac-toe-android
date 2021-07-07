package com.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.core.util.Pair;

@SuppressLint("ViewConstructor")
public class FinishLine extends FrameLayout {
    private Paint paint = new Paint();
    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;

    public FinishLine(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }

    public FinishLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    public FinishLine(Context context, Pair<Integer, Integer> startPoint, Pair<Integer, Integer> endPoint) {
        super(context);
        setWillNotDraw(false);
        System.out.println("constructor called...");
        startX = startPoint.first;
        startY = startPoint.second;
        endX = endPoint.first;
        endY = endPoint.second;
        init();
    }
    
    private void init() {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("drawing...");
        canvas.drawLine(startX, startY, endX, endY, paint);
    }
}
