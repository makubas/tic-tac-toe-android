package com.tictactoe;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

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
        String name = getPackageName();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                FieldView iterationView = findViewById(getResources().getIdentifier("field_" + x + "_" + y, "id", name));
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

    }

    private Shape checkForResult() {
        // Rows
        for (int x = 0; x < 3; x++) {
            if (this.shapesArray[x][0].isCross && this.shapesArray[x][1].isCross && this.shapesArray[x][2].isCross) {
                return Shape.CROSS;
            } else if (this.shapesArray[x][0].isCircle && this.shapesArray[x][1].isCircle && this.shapesArray[x][2].isCircle) {
                return Shape.CIRCLE;
            }
        }

        // Columns
        for (int y = 0; y < 3; y++) {
            if (this.shapesArray[0][y].isCross && this.shapesArray[1][y].isCross && this.shapesArray[2][y].isCross) {
                return Shape.CROSS;
            } else if (this.shapesArray[0][y].isCircle && this.shapesArray[1][y].isCircle && this.shapesArray[2][y].isCircle) {
                return Shape.CIRCLE;
            }
        }

        // Bias NW
        if (this.shapesArray[0][0].isCross && this.shapesArray[1][1].isCross && this.shapesArray[2][2].isCross) {
            return Shape.CROSS;
        } else if (this.shapesArray[0][0].isCircle && this.shapesArray[1][1].isCircle && this.shapesArray[2][2].isCircle) {
            return Shape.CIRCLE;
        }

        // Bias SE
        if (this.shapesArray[2][0].isCross && this.shapesArray[1][1].isCross && this.shapesArray[0][2].isCross) {
            return Shape.CROSS;
        } else if (this.shapesArray[2][0].isCircle && this.shapesArray[1][1].isCircle && this.shapesArray[0][2].isCircle) {
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