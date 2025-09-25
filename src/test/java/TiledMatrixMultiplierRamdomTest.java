import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.TiledMatrixMultiplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class MatrixBenchmark {

    public static void main(String[] args) {
        int[] matrixSizes = {100, 200, 300, 400, 500, 600}; // Puedes ajustar los tamaños de las matrices

        for (int size : matrixSizes) {
            DenseMatrix matrixA = generateRandomMatrix(size, size);
            DenseMatrix matrixB = generateRandomMatrix(size, size);

            TiledMatrixMultiplier multiplier = new TiledMatrixMultiplier(matrixA, matrixB, 4);

            long startTime = System.currentTimeMillis();
            DenseMatrix resultMatrix = multiplier.multiply();
            long endTime = System.currentTimeMillis();

            long executionTime = endTime - startTime;
            System.out.println("Matrix Size: " + size + "x" + size + " - Execution Time: " + executionTime + " ms");
        }
    }

    private static DenseMatrix generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        Map<Integer, Map<Integer, Long>> matrixData = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            Map<Integer, Long> row = new HashMap<>();
            for (int j = 0; j < cols; j++) {
                row.put(j, (long) random.nextInt(100)); // Puedes ajustar el rango de valores aleatorios
            }
            matrixData.put(i, row);
        }

        return new DenseMatrix(matrixData, rows);
    }
}

