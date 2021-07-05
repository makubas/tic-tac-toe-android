package com.tictactoe;

public enum Shape {
    CIRCLE(false, true, false),
    CROSS(false, false, true),
    NONE(true, false, false);

    boolean isCircle;
    boolean isCross;
    boolean isNone;

    private Shape(boolean isNone, boolean isCircle, boolean isCross) {
       this.isNone = isNone;
       this.isCircle = isCircle;
       this.isCross = isCross;
    }

    public boolean isNone() {
        return isNone;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public boolean isCross() {
        return isCross;
    }
}
