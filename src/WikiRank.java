import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by physiconomic on 2018/4/14.
 */
public class WikiRank {

    public static void main(String[] args) {
        DB db = new DB(0);
        HashMap<String, ArrayList<String>> map = db.getMap();
        HashSet<String> names = db.getSet();

        Graph g = new Graph();
        HashMap<String, Node> stringToNode = new HashMap<String, Node>();

        for (String name : names) {
            Node n = new Node(name);
            stringToNode.put(name, n);
            g.states.add(n);
        }

        for (Node n : g.states) {
            ArrayList<String> outgoing = map.get(n.name);
            if (outgoing != null) {
                for (String name : outgoing) {
                    n.addLink(stringToNode.get(name));
                }
            }
            n.setWeight();
        }

        g.pageRank();
        g.sortRanks();

        g.printRanks(200);
        g.cluster(100,1000);
    }
}
