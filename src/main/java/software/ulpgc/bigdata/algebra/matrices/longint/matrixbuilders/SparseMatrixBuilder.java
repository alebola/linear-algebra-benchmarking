package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.SparseMatrix;

import java.util.ArrayList;
import java.util.List;

public class SparseMatrixBuilder {
    private final int size;
    private final List<Coordinate> coordinates;

    public SparseMatrixBuilder(int size) {
        this.size = size;
        this.coordinates = new ArrayList<>();
    }

    public void set(int i, int j, long value) {
        coordinates.add(new Coordinate(i, j, value));
    }

    public SparseMatrix toMatrix() {
        SparseMatrix m = new SparseMatrix();
        for (Coordinate c : coordinates) m.set(c.getRow(), c.getCol(), c.getValue());
        return m;
    }
}
