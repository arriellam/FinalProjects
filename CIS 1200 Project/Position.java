package org.cis1200.othello;

public class Position implements Comparable {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public int compareTo(Object other) {
        Position p = (Position) other;
        if (x != p.x) {
            return x - p.x;
        } else {
            return y - p.y; // same x; break ties with y.
        }
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
