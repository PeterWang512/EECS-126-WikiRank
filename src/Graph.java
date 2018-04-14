import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by physiconomic on 2018/4/14.
 */
public class Graph {
    public static int NUM_ITER = 10000;
    public ArrayList<Node> states = new ArrayList<>();

    public Graph() {

    }

    public void pageRank() {
        for (int i = 0; i < NUM_ITER; i++) {
            System.out.print(i + " ");
            if ((i+1) % 30 == 0)
                System.out.println();

            for (Node s: states)
                s.initUpdate();

            for (Node s : states)
                s.calculateRank();

            double diff = 0.0;
            for (Node s : states)
                diff += s.updateRank();

            if (diff <= 0.000001) {
                System.out.println();
                return;
            }
        }
    }

    public void sortRanks() {
        Collections.sort(states, (n1, n2) -> -(Double.compare(n1.rank, n2.rank)));
    }

    public void printRanks(int threshold) {
        int idx = 0;
        for (Node s : states) {
            System.out.println(s.name + ": " + s.rank);
            idx += 1;
            if (idx >= threshold) {
                break;
            }
        }
    }
}
