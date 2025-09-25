package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;

import java.util.Arrays;
import java.util.List;

public class CompressedColumnMatrixBuilder {

    public CompressedColumnMatrix convertToCCS(List<Coordinate> coords, int rows, int cols) {
        int nnz = coords.size();
        int[] values = new int[nnz];
        int[] rowIndex = new int[nnz];
        int[] colPointer = new int[cols + 1];

        // 1) cuenta nnz por columna
        for (Coordinate c : coords) colPointer[c.getCol()]++;

        // 2) prefijo exclusivo
        for (int c = 1; c < colPointer.length; c++) colPointer[c] += colPointer[c - 1];

        for (int c = colPointer.length - 1; c > 0; c--) colPointer[c] = colPointer[c - 1];
        colPointer[0] = 0;

        // 3) cursor de inserción por columna
        int[] next = Arrays.copyOf(colPointer, colPointer.length);

        // 4) colocar
        for (Coordinate c : coords) {
            int col = c.getCol();
            int pos = next[col]++;
            values[pos]   = (int) c.getValue();
            rowIndex[pos] = c.getRow();
        }

        return new CompressedColumnMatrix(cols, values, colPointer, rowIndex);
    }
}
