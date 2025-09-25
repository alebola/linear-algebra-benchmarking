package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DenseMatrixBuilder {
    private final Map<Integer, Map<Integer, Long>> values;
    private final int size;

    public DenseMatrixBuilder(int size) {
        this.size = size;
        this.values = new HashMap<>();
    }

    public void set(int row, int col, long value) {
        values.computeIfAbsent(row, k -> new HashMap<>()).put(col, value);
    }

    public Map<Integer, Map<Integer, Long>> getValues() {
        return values;
    }

    public static DenseMatrix convertToDenseMatrix(List<Coordinate> coordinates, int size) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(size);

        for (Coordinate coordinate : coordinates) {
            int row = (int) coordinate.getRow() - 1;
            int col = (int) coordinate.getCol() - 1;
            long value = (long) coordinate.getValue();
            builder.set(row, col, value);
        }

        return new DenseMatrix(builder.getValues(), size);
    }
}
