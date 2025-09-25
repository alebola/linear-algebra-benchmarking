package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.stream.IntStream;

public class CompressedRowMatrix extends SparseMatrix {
    public final int size;
    public final int[] values;
    public final int[] rowPointer;
    public final int[] columns;

    private final int[] columnIndex;

    public CompressedRowMatrix(int size, int[] values, int[] rowPointer, int[] columnIndex) {
        this.size = size;
        this.values = values;
        this.rowPointer = rowPointer;
        this.columnIndex = columnIndex;
        this.columns = calculateColumns(rowPointer, columnIndex);
    }

    private static int[] calculateColumns(int[] rowPointer, int[] columnIndex) {
        return IntStream.range(0, rowPointer.length - 1)
                .flatMap(r -> IntStream.range(rowPointer[r], rowPointer[r + 1]))
                .map(idx -> columnIndex[idx])
                .toArray();
    }
}
