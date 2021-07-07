package com.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class FieldView extends androidx.appcompat.widget.AppCompatImageView{
    private Shape shape;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.shape = Shape.NONE;
        LocalPvpActivity activity = (LocalPvpActivity) context;
        setImageResource(R.drawable.blank);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getShape().isNone())
                {
                    shape = activity.getCurrentShape();
                    if (getShape() == Shape.CIRCLE) {
                        setImageResource(R.drawable.circle);
                    } else {
                        setImageResource(R.drawable.cross);
                    }
                    activity.afterMoveAction();
                }
            }
        });
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        if (getShape() == Shape.CIRCLE) {
            setImageResource(R.drawable.circle);
        } else if (getShape() == Shape.CROSS) {
            setImageResource(R.drawable.cross);
        } else {
            setImageResource(R.drawable.blank);
        }
    }
}
