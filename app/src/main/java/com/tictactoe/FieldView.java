package com.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class FieldView extends View{
    private Shape shape;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.shape = Shape.NONE;
        MainGameActivity activity = (MainGameActivity) context;
        setBackgroundColor(Color.GRAY);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getShape().isNone())
                {
                    shape = activity.getCurrentShape();
                    if (getShape() == Shape.CIRCLE) {
                        setBackgroundColor(Color.RED);
                    } else {
                        setBackgroundColor(Color.BLUE);
                    }
                    activity.afterMoveAction();
                }
            }
        });
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        if (getShape() == Shape.CIRCLE) {
            setBackgroundColor(Color.RED);
        } else if (getShape() == Shape.CROSS) {
            setBackgroundColor(Color.BLUE);
        } else {
            setBackgroundColor(Color.GRAY);
        }
    }
}
