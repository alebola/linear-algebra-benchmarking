package software.ulpgc.bigdata.algebra.matrices.longint;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.Operations;

public class Main {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Operations.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}

