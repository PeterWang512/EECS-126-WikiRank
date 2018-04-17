import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.*;

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

    //cluster the top n entries into k clusters
    public void cluster(int k, int n){
        double c = 0.05;
        int[][] freq = new int[k][n];
        List<Node> centroids = new ArrayList<>();
        List<HashSet<Node>> clusters = new ArrayList<>();
        //create the centroids
        for(int i = 0; i < k; i++){
            centroids.add(states.get(i));
        }
        for(int i = 0; i < k; i++){
            HashSet<Node> hs = new HashSet<>();
            hs.add(centroids.get(i));
            clusters.add(hs);
        }
        for(int i = 0; i < n; i++){
            states.get(i).position = i;
        }

        double threshold = states.get(n).rank;

        //for each centroid, perform a random walk, and keep track of the
        // frequency of hitting each node
        for(Node cent: centroids){
            Node curr_state = cent;
            for(int i = 0; i < NUM_ITER; i++){
                Node next_state = null;
                if(Math.random() < c){
                    next_state = cent;
                }
                int repeats = 0;
                while(next_state == null){
                    List<Node> l = curr_state.links;

                    Node proposed_next_state =
                      l.get((int) (Math.random() * l.size()));
                    if(proposed_next_state.rank > threshold){
                        next_state = proposed_next_state;
                    }
                    repeats++;
                    //going to assume we're stuck in an infinite loop
                    if(repeats > 100){
                        next_state = cent;
                    }
                }
                freq[cent.position][next_state.position]++;
                curr_state = next_state;
            }
        }
        for(int i = 0; i < n; i++){
            int max_index = 0;
            for(int j = 0; j < k; j++){
                if(freq[j][i] > freq[max_index][i]){
                    max_index = j;
                }
            }
            if(freq[max_index][i] > 0){
                clusters.get(max_index).add(states.get(i));
            }
        }
        int idx = 0;
        for(HashSet<Node> cluster: clusters){
            System.out.println(idx);
            for(Node s: cluster){
                System.out.println(s.name + ": " + s.rank);
            }
            idx++;
            System.out.println();
        }
    }

}
