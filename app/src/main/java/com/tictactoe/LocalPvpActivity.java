package com.tictactoe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.util.Pair;

import java.util.concurrent.TimeUnit;

public class LocalPvpActivity extends AppCompatActivity {
    private static final Shape startingShape = Shape.CROSS;
    private Shape currentShape;
    private int moveCounter = 0;
    private int crossWins = 0;
    private int circleWins = 0;
    private final Shape[][] shapesArray = new Shape[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_pvp_activity);
        this.currentShape = startingShape;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
        this.resetGrid();
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    private void resetGrid() {
        setContentView(R.layout.local_pvp_activity);
        String name = getPackageName();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                FieldView iterationView = findViewById(getResources().getIdentifier("field_" + x + "_" + y, "id", name));
                if (iterationView == null) {
                    System.out.println(x);
                    System.out.println(y);
                }
                iterationView.setShape(Shape.NONE);
                this.shapesArray[x - 1][y - 1] = Shape.NONE;
            }
        }

        this.currentShape = startingShape;
        this.moveCounter = 0;
    }

    public void afterMoveAction() {
        if (this.currentShape == Shape.CIRCLE) {
            this.currentShape = Shape.CROSS;
        } else {
            this.currentShape = Shape.CIRCLE;
        }
        this.moveCounter++;
        updateCurrentFields();
        switch (checkForResult()) {
            case CROSS:
                System.out.println("Crosses won!");
                this.crossWins++;
                this.resetGrid();
                break;
            case CIRCLE:
                System.out.println("Circles won!");
                this.circleWins++;
                this.resetGrid();
                break;
            case NONE:
                if (this.moveCounter == 9) {
                    System.out.println("Draw!");
                    this.resetGrid();
                }
                break;
        }
    }

    private void updateCurrentFields() {
        String name = getPackageName();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                FieldView iterationView = findViewById(getResources().getIdentifier("field_" + x + "_" + y, "id", name));
                this.shapesArray[x - 1][y - 1] = iterationView.getShape();
            }
        }
    }

    private void drawFinishingLine(Pair<Integer, Integer> startPoint, Pair<Integer, Integer> endPoint) {
        System.out.println("TEST1");
        String name = getPackageName();
        FieldView startView = findViewById(getResources().getIdentifier("field_" + (startPoint.first + 1) + "_" + (startPoint.second + 1), "id", name));
        FieldView endView = findViewById(getResources().getIdentifier("field_" + (endPoint.first + 1) + "_" +(endPoint.second + 1), "id", name));
        System.out.println("TEST2");

        int[] startLocation = new int[2];
        int[] endLocation = new int[2];
        System.out.println(startView);
        startView.getLocationOnScreen(startLocation);
        endView.getLocationOnScreen(endLocation);
        System.out.println("TEST3");

        Pair<Integer, Integer> startPair = new Pair<>(startLocation[0], startLocation[1]);
        Pair<Integer, Integer> endPair = new Pair<>(endLocation[0], endLocation[1]);
        System.out.println(startPair.first);
        System.out.println(startPair.second);
        System.out.println(endPair.first);
        System.out.println(endPair.second);

        setContentView(R.layout.local_pvp_activity);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.main);
        ConstraintSet set = new ConstraintSet();

        FinishLine finishLine = new FinishLine(this, startPair, endPair);
        finishLine.setId(View.generateViewId());
        layout.addView(finishLine, 0);

        set.clone(layout);
        set.connect(finishLine.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60);
        set.applyTo(layout);


        //FinishLine finishLine = new FinishLine(this);
        //finishLine.setLayoutParams(new FrameLayout.LayoutParams(2000, 2000));
        //setContentView(finishLine);

        System.out.println("TEST4");
        sleep(2000);
    }

    private Shape checkForResult() {
        // Rows
        for (int x = 0; x < 3; x++) {
            if (this.shapesArray[x][0].isCross && this.shapesArray[x][1].isCross && this.shapesArray[x][2].isCross) {
                this.drawFinishingLine(new Pair<>(x, 0), new Pair<>(x, 2));
                return Shape.CROSS;
            } else if (this.shapesArray[x][0].isCircle && this.shapesArray[x][1].isCircle && this.shapesArray[x][2].isCircle) {
                this.drawFinishingLine(new Pair<>(x, 0), new Pair<>(x, 2));
                return Shape.CIRCLE;
            }
        }

        // Columns
        for (int y = 0; y < 3; y++) {
            if (this.shapesArray[0][y].isCross && this.shapesArray[1][y].isCross && this.shapesArray[2][y].isCross) {
                this.drawFinishingLine(new Pair<>(0, y), new Pair<>(2, y));
                return Shape.CROSS;
            } else if (this.shapesArray[0][y].isCircle && this.shapesArray[1][y].isCircle && this.shapesArray[2][y].isCircle) {
                this.drawFinishingLine(new Pair<>(0, y), new Pair<>(2, y));
                return Shape.CIRCLE;
            }
        }

        // Bias NW
        if (this.shapesArray[0][0].isCross && this.shapesArray[1][1].isCross && this.shapesArray[2][2].isCross) {
            this.drawFinishingLine(new Pair<>(0, 0), new Pair<>(2, 2));
            return Shape.CROSS;
        } else if (this.shapesArray[0][0].isCircle && this.shapesArray[1][1].isCircle && this.shapesArray[2][2].isCircle) {
            this.drawFinishingLine(new Pair<>(0, 0), new Pair<>(2, 2));
            return Shape.CIRCLE;
        }

        // Bias NE
        if (this.shapesArray[2][0].isCross && this.shapesArray[1][1].isCross && this.shapesArray[0][2].isCross) {
            this.drawFinishingLine(new Pair<>(2, 0), new Pair<>(0, 2));
            return Shape.CROSS;
        } else if (this.shapesArray[2][0].isCircle && this.shapesArray[1][1].isCircle && this.shapesArray[0][2].isCircle) {
            this.drawFinishingLine(new Pair<>(2, 0), new Pair<>(0, 2));
            return Shape.CIRCLE;
        }

        return Shape.NONE;
    }

    public int getCircleWins() {
        return circleWins;
    }

    public int getCrossWins() {
        return crossWins;
    }

    private void sleep(int seconds) {
        try
        {
            Thread.sleep(seconds);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    // Fullscreen functionality

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}