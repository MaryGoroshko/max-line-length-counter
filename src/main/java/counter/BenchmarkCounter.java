package counter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, jvmArgs = {"-Xms256m", "-Xmx256m"})
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Timeout(time = 1, timeUnit = TimeUnit.MINUTES)
public class BenchmarkCounter{

    @Param("src/main/resources/text.txt")
    String inFile;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void firstSolution(Blackhole blackhole) throws IOException {
        int counter = 0;
        int readSymbol;
        int maxLength = 0;

        FileReader fileReader = new FileReader(inFile);
        while ((readSymbol = fileReader.read()) > 0) {
            counter++;
            if (readSymbol == '\n' || readSymbol == '\r') {
                if (maxLength < counter) {
                    maxLength = counter - 1;
                }
                counter = 0;
            }
        }
        blackhole.consume(maxLength);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void secondSolution(Blackhole blackhole) throws IOException {
        int maxLength = Files.lines(Paths.get(inFile)).map(String::length).max(Integer::compareTo).orElse(0);
        blackhole.consume(maxLength);
    }
}