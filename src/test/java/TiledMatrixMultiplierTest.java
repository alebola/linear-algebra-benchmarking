import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.TiledMatrixMultiplier;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TiledMatrixMultiplierTest {

    @Test
    public void testTiledMatrixMultiplication() {
        // Definir las matrices de prueba
        DenseMatrix matrixA = createDenseMatrix(new long[][] {
                {2, 1, 5, 3},
                {0, 7, 1, 6},
                {9, 2, 4, 4},
                {3, 6, 7, 2}
        });
        DenseMatrix matrixB = createDenseMatrix(new long[][] {
                {6, 1, 2, 3},
                {4, 5, 6, 5},
                {1, 9, 8, -8},
                {4, 0, -8, 5}
        });

        // Matriz resultado esperada
        DenseMatrix expectedMatrix = createDenseMatrix(new long[][] {
                {33, 52, 26, -14},
                {53, 44, 2, 57},
                {82, 55, 30, 25},
                {57, 96, 82, -7}
        });

        // Realizar la multiplicación de matrices en bloques
        TiledMatrixMultiplier multiplier = new TiledMatrixMultiplier(matrixA, matrixB, 4);
        DenseMatrix resultMatrix = multiplier.multiply();

        // Verificar que el resultado coincide con el esperado
        System.out.println("Expected Matrix: " + expectedMatrix);
        System.out.println("Result Matrix: " + resultMatrix);
        assertEquals(expectedMatrix, resultMatrix);

    }

    private DenseMatrix createDenseMatrix(long[][] matrixData) {
        Map<Integer, Map<Integer, Long>> values = new HashMap<>();
        for (int i = 0; i < matrixData.length; i++) {
            for (int j = 0; j < matrixData[i].length; j++) {
                values.computeIfAbsent(i, k -> new HashMap<>()).put(j, matrixData[i][j]);
            }
        }
        return new DenseMatrix(values, matrixData.length);
    }
}
