package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoordinateMatrixBuilder {

    public List<Coordinate> buildMatrix(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return buildMatrix(br);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<Coordinate> buildMatrix(BufferedReader br) throws IOException {
        List<Coordinate> coordinates = new ArrayList<>();
        String line;
        boolean headerPassed = false;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("%")) continue;

            if (!headerPassed) {
                headerPassed = true;
                continue;
            }
            String[] parts = line.split("\\s+");
            if (parts.length >= 3) {
                int row = Integer.parseInt(parts[0]) - 1; // 0-based
                int col = Integer.parseInt(parts[1]) - 1; // 0-based
                double val = Double.parseDouble(parts[2]);
                coordinates.add(new Coordinate(row, col, val));
            }
        }
        return coordinates;
    }
}
