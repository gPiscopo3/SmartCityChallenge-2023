package rec.planner.streamprocessor;

import java.io.IOException;

public class provaMain {
    public static void main(String args[]) throws IOException {
        Consumer genLoadData = new ConsumerGenData("172.31.3.218:31200", "groupA", "earliest");
        genLoadData.consume("load_data");

    }
}
