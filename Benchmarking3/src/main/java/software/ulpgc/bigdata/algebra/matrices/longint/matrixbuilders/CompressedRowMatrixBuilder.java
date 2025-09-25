package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.util.Arrays;
import java.util.List;

public class CompressedRowMatrixBuilder {

    public CompressedRowMatrix convertToCRS(List<Coordinate> coords, int rows, int cols) {
        int nnz = coords.size();
        int[] values = new int[nnz];
        int[] columnIndex = new int[nnz];
        int[] rowPointer = new int[rows + 1];

        // 1) cuenta nnz por fila
        for (Coordinate c : coords) rowPointer[c.getRow()]++;

        // 2) prefijo exclusivo
        for (int r = 1; r < rowPointer.length; r++) rowPointer[r] += rowPointer[r - 1];
        for (int r = rowPointer.length - 1; r > 0; r--) rowPointer[r] = rowPointer[r - 1];
        rowPointer[0] = 0;

        // 3) cursor por fila
        int[] next = Arrays.copyOf(rowPointer, rowPointer.length);

        // 4) colocar
        for (Coordinate c : coords) {
            int row = c.getRow();
            int pos = next[row]++;
            values[pos]      = (int) c.getValue();
            columnIndex[pos] = c.getCol();
        }

        return new CompressedRowMatrix(rows, values, rowPointer, columnIndex);
    }
}
