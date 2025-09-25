package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.stream.IntStream;

public class CompressedColumnMatrix extends SparseMatrix {
    public final int size;
    public final int[] values;
    public final int[] colPointer;
    public final int[] rows;

    private final int[] rowIndex;

    public CompressedColumnMatrix(int size, int[] values, int[] colPointer, int[] rowIndex) {
        this.size = size;
        this.values = values;
        this.colPointer = colPointer;
        this.rowIndex = rowIndex;
        this.rows = calculateRows(colPointer, rowIndex);
    }

    private static int[] calculateRows(int[] columnPointers, int[] rowIndex) {
        return IntStream.range(0, columnPointers.length - 1)
                .flatMap(c -> IntStream.range(columnPointers[c], columnPointers[c + 1]))
                .map(idx -> rowIndex[idx])
                .toArray();
    }
}
