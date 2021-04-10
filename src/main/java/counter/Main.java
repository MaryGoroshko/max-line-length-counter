package counter;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {

    public static void main(String[] args) {
        try {
            Options opt = new OptionsBuilder()
                    .include(BenchmarkCounter.class.getSimpleName())
                    .build();
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

    /*
    output:
    # Run complete. Total time: 00:00:12
Benchmark                                           (inFile)  Mode  Cnt  Score   Error  Units
BenchmarkCounter.firstSolution   src/main/resources/text.txt  avgt    5  0.241 ± 0.057  ms/op
BenchmarkCounter.secondSolution  src/main/resources/text.txt  avgt    5  0.342 ± 0.428  ms/op
     */
}
