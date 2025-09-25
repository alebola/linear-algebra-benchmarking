package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class Coordinate {
    private int i;
    private int j;
    private double value;

    public Coordinate(int i, int j, double value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }

    public int getRow() {
        return i;
    }

    public int getCol() {
        return j;
    }

    public double getValue() {
        return value;
    }

    public void setRow(int i) {
        this.i = i;
    }

    public void setCol(int j) {
        this.j = j;
    }

    public void setValue(double value) {
        this.value = value;
    }
}


