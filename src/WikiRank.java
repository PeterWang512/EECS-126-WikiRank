import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by physiconomic on 2018/4/14.
 */
public class WikiRank {

    public HashMap<String, ArrayList<String>> map;
    public HashSet<String> names;
    public Graph g;
    boolean retrieved = false;

    public WikiRank() {

    }

    public void retrieveData() {
        // Retrieve Data
        DB db = new DB(0);
        map = db.getMap();
        names = db.getSet();
    }

    public void writeToFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("./data/map.ser"))) {

            oos.writeObject(map);

            System.out.println("Map written to file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("./data/names.ser"))) {

            oos.writeObject(names);

            System.out.println("Names written to file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readFile() {
        try (ObjectInputStream ois
                              = new ObjectInputStream(new FileInputStream("./data/map.ser"))) {

            map = (HashMap<String, ArrayList<String>>) ois.readObject();
            System.out.println("Map read from file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (ObjectInputStream ois
                     = new ObjectInputStream(new FileInputStream("./data/names.ser"))) {

            names = (HashSet<String>) ois.readObject();
            System.out.println("Map read from file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void buildGraph(double damping) {
        g = new Graph();
        HashMap<String, Node> stringToNode = new HashMap<String, Node>();

        for (String name : names) {
            Node n = new Node(name, damping);
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
    }

    public void pageRank(double d) {
        // PageRank
        g.pageRank();
        g.sortRanks();
        try {
            String file = "damp" + d;
            g.printRanks(file, 200);
            g.cluster(file, 10, 100);
            g.toJson(file, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        WikiRank wr = new WikiRank();
        if (!wr.retrieved) {
            wr.retrieveData();
            wr.writeToFile();
        } else {
            wr.readFile();
        }

        double[] dampings = {0.8, 0.9};
        for (double d : dampings) {
            wr.buildGraph(d);
            wr.pageRank(d);
        }
        System.out.println("State size: " + wr.names.size());

    }
}
