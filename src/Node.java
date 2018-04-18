import java.util.ArrayList;

/**
 * Created by physiconomic on 2018/4/14.
 */
public class Node {

    public double damping = 0.85;

    String name;
    public ArrayList<Node> links = new ArrayList<>();
    public double weight;
    public double rank;
    public double newRank;
    public double dc;
    public int position = -1;
    public int cluster = -1;

    public Node(String name, double damping) {
        this.name = name;
        this.rank = 1.0;
        this.damping = damping;
        this.dc = (1.0 - damping);
    }

    public void addLink(Node node) {
        links.add(node);
    }

    public void setWeight() {
        int sz = links.size();
        if (sz == 0) {
            links.add(this);
            this.weight = 1.0;
        } else {
            this.weight = 1.0 / sz;
        }
    }

    public void initUpdate() {
        newRank = dc;
    }

    public void calculateRank() {
        for (Node link : links) {
            link.newRank += damping * rank * weight;
        }
    }

    public double updateRank() {
        double diff = newRank - rank;
        rank = newRank;

        return Math.abs(diff);
    }
}