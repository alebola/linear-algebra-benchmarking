package software.ulpgc.bigdata.algebra.matrices.longint.operators;

import org.openjdk.jmh.annotations.*;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.*;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class Operations {

    // --- Data loaded once per trial ---
    private List<Coordinate> coordinates;
    private int rows;
    private int cols;
    private int size;

    private CompressedRowMatrix crs;
    private CompressedColumnMatrix ccs;
    private DenseMatrix dense;

    @Setup(Level.Trial)
    public void setUp() throws Exception {
        // Load from resources (no absolute paths)
        String resourceName = "olm5000.mtx";
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourceName);
        Objects.requireNonNull(is, "Resource not found: " + resourceName);

        var br = new BufferedReader(new InputStreamReader(is));

        CoordinateMatrixBuilder coordBuilder = new CoordinateMatrixBuilder();
        coordinates = coordBuilder.buildMatrix(br);

        rows = calculateRowsFromCoordinates(coordinates);
        cols = calculateColsFromCoordinates(coordinates);
        size = Math.max(rows, cols);

        CompressedRowMatrixBuilder crsBuilder = new CompressedRowMatrixBuilder();
        CompressedColumnMatrixBuilder ccsBuilder = new CompressedColumnMatrixBuilder();
        crs = crsBuilder.convertToCRS(coordinates, rows, cols);
        ccs = ccsBuilder.convertToCCS(coordinates, rows, cols);

        dense = DenseMatrixBuilder.convertToDenseMatrix(coordinates, size);
    }


    public static SparseMatrix multiply(CompressedRowMatrix a, CompressedColumnMatrix b) {
        if (a.size != b.size) {
            throw new IllegalArgumentException("CRS and CCS must have same size (square matrices).");
        }
        int n = a.size;
        SparseMatrixBuilder builder = new SparseMatrixBuilder(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int ii = a.rowPointer[i];
                int iEnd = a.rowPointer[i + 1];
                int jj = b.colPointer[j];
                int jEnd = b.colPointer[j + 1];
                long s = 0;

                while (ii < iEnd && jj < jEnd) {
                    int aa = a.columns[ii];
                    int bb = b.rows[jj];
                    if (aa == bb) {
                        s += (long) a.values[ii] * b.values[jj];
                        ii++;
                        jj++;
                    } else if (aa < bb) {
                        ii++;
                    } else {
                        jj++;
                    }
                }
                if (s != 0) builder.set(i, j, s);
            }
        }
        return builder.toMatrix();
    }

    public static SparseMatrix multiplyDense(DenseMatrix a) {
        int n = a.size();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += a.get(i, k) * a.get(k, j);
                }
                if (sum != 0) builder.set(i, j, sum);
            }
        }
        return builder.toMatrix();
    }


    @Benchmark
    public SparseMatrix benchmarkMultiplySparse() {
        // Uses prebuilt CRS/CCS
        return multiply(crs, ccs);
    }

    @Benchmark
    public SparseMatrix benchmarkMultiplyDense() {
        return multiplyDense(dense);
    }


    private static int calculateRowsFromCoordinates(List<Coordinate> coords) {
        int maxRow = 0;
        for (Coordinate c : coords) maxRow = Math.max(maxRow, c.getRow());
        return maxRow + 1;
    }

    private static int calculateColsFromCoordinates(List<Coordinate> coords) {
        int maxCol = 0;
        for (Coordinate c : coords) maxCol = Math.max(maxCol, c.getCol());
        return maxCol + 1;
    }
}
