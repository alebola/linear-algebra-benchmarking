package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TiledMatrixMultiplier {
    private final DenseMatrix a, b, result;
    private final int tileSize;

    public TiledMatrixMultiplier(DenseMatrix a, DenseMatrix b, int tileSize) {
        int n = a.size();
        if (n != b.size()) throw new IllegalArgumentException("Matrices must have the same size");
        if (tileSize <= 0) throw new IllegalArgumentException("Tile size must be > 0");
        if (n % tileSize != 0) throw new IllegalArgumentException("Tile size must divide matrix size");

        this.a = a; this.b = b; this.tileSize = tileSize;
        this.result = new DenseMatrix(new HashMap<>(), n);
    }

    public DenseMatrix multiply() {
        int n = a.size();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < n; i += tileSize) {
            for (int j = 0; j < n; j += tileSize) {
                final int si = i, sj = j;
                pool.execute(() -> multiplyTiles(si, sj));
            }
        }
        pool.shutdown();
        try { pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        return result;
    }

    private void multiplyTiles(int startRow, int startCol) {
        int n = a.size();
        int endRow = Math.min(startRow + tileSize, n);
        int endCol = Math.min(startCol + tileSize, n);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                long sum = 0;
                for (int k = 0; k < n; k++) sum += a.get(i, k) * b.get(k, j);
                synchronized (result) { result.set(i, j, sum); }
            }
        }
    }
}
