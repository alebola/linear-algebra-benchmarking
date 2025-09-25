package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DenseMatrix {
    private final Map<Integer, Map<Integer, Long>> values;
    private final int n;

    public DenseMatrix(Map<Integer, Map<Integer, Long>> values, int size) {
        this.values = values;
        this.n = size;
    }

    public long get(int i, int j) {
        Map<Integer, Long> row = values.get(i);
        return row != null ? row.getOrDefault(j, 0L) : 0L;
    }

    public int size() { return n; }

    public Map<Integer, Map<Integer, Long>> getValues() { return values; }

    public void set(int i, int j, long value) {
        values.computeIfAbsent(i, k -> new HashMap<>()).put(j, value);
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DenseMatrix other)) return false;
        if (n != other.n) return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (get(i, j) != other.get(i, j)) return false;
        return true;
    }

    @Override public int hashCode() { return Objects.hash(n, values); }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) sb.append(get(i, j)).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}
