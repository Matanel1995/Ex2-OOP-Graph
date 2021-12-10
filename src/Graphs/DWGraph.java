package Graphs;

import api.EdgeData;
import api.NodeData;

import java.util.*;

public class DWGraph implements api.DirectedWeightedGraph {
    HashMap<Integer, Node> nodes = new HashMap<Integer,Node>();
    HashMap<Integer , HashMap<Integer , Edge>> edegs = new HashMap<Integer,HashMap<Integer,Edge>>();
    Stack<Integer> nextKey = new Stack<>();
    double[] corners = new double[4];
    boolean iterFlag;
    int mc , edgesNumber ,nodeNumber;


    public DWGraph(){
        this.mc =0;
        edgesNumber=0;
        nodeNumber =0;
        this.iterFlag = false;
    }
    public DWGraph(String jsonFile) {
        this.iterFlag = false;
        edgesNumber = 0;
        this.mc = 0;
        this.corners[0] = Double.MAX_VALUE;
        this.corners[1] = 0;
        this.corners[2] = Double.MAX_VALUE;
        this.corners[3] = 0;
        // this.nodes.put(node.key,node);
        JSON json =new JSON(jsonFile);
        json.import_all_nodes(this);
        json.import_all_edges(this);
        this.mc =0;
    }

    @Override
    public NodeData getNode(int key) {
        if(this.nodes.containsKey(key))
            return nodes.get(key);
        else {
            return null;
        }
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        // add if exist
        return (edegs.get(src)).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        //check if i can make changes based on iterator
        if(this.iterFlag){
            throw new RuntimeException("Cant add new node after creating an iterator");
        }
        else {// no iterator was created until now
            //check if allready exist.
            if(!this.nodes.containsKey(n.getKey())) {
                nodes.put(n.getKey(), (Node) n);
                this.nodeNumber = this.nodeNumber+1;
                this.mc = this.mc + 1;
                if(n.getLocation().x() < this.corners[0])
                    this.corners[0] = n.getLocation().x();
                if(n.getLocation().x() > this.corners[1])
                    this.corners[1] =n.getLocation().x();
                if(n.getLocation().y() < this.corners[2])
                    this.corners[2] = n.getLocation().y();
                if(n.getLocation().y() > this.corners[3])
                    this.corners[3] =n.getLocation().y();
            }
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(this.iterFlag){
            throw new RuntimeException("Run time Exception");
        }
        else{
            if(src == dest) return;
            //check if there is no edge like the one i want to add
            if(!this.edegs.containsKey(src) || !this.edegs.get(src).containsKey(dest)){
                //check if there is src node and dst node in my graph before i add new edge
                if(this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
                    if (this.edegs.containsKey(src)) {
                        Edge tempEdge = new Edge(src, dest, w);
                        this.edegs.get(src).put(tempEdge.destination, tempEdge);
                        this.nodes.get(dest).incomingEdges.add(tempEdge);
                        this.mc = this.mc + 1;
                        this.edgesNumber = this.edgesNumber + 1;
                    } else {
                        Edge tempEdge = new Edge(src, dest, w);
                        HashMap<Integer, Edge> tempHash = new HashMap<Integer, Edge>();
                        tempHash.put(dest, tempEdge);
                        edegs.put(src, tempHash);
                        //add new edge to dest node incoming list
                        this.nodes.get(dest).incomingEdges.add(tempEdge);
                        this.mc = this.mc + 1;
                        this.edgesNumber = this.edgesNumber + 1;
                    }
                }
            }
            //allready egde from this SRC to this DST just need to change W
            else{
                    this.edegs.get(src).get(dest).weight = w;
            }
            }
        }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator it = nodes.values().iterator();
        this.iterFlag = true;
        return it;
        }

    @Override
    public Iterator<EdgeData> edgeIter() {
        ArrayList<EdgeData> edgeArrayList = new ArrayList<>();
        Set<Integer> tempSet = this.edegs.keySet();
        Iterator<Integer> tempIt = tempSet.iterator();
        for(Object i : tempSet) {
            int tmpSource = tempIt.next();
            if (this.edegs.get(tmpSource) != null) {
                Set<Integer> destSet = this.edegs.get(tmpSource).keySet();
                Iterator<Integer> iterDest = destSet.iterator();
                for (Object j : destSet) {
                    int tmpDest = iterDest.next();
                    edgeArrayList.add(this.edegs.get(tmpSource).get(tmpDest));
                }
            }
        }
        this.iterFlag = true;
        return edgeArrayList.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        if(this.edegs.containsKey(node_id)) {
            Iterator it = edegs.get(node_id).values().iterator();
            this.iterFlag = true;
            return it;
        }
        else{
            return null;
        }
    }

    @Override
    public NodeData removeNode(int key) {
        if (this.iterFlag) {
            throw new RuntimeException("Cant remove new node after creating an iterator");
            }
        else { //i can change the graph because no iterator was constructed
            // check if there is node with the given key
            if(this.nodes.containsKey(key)){
                LinkedList<Edge> tempList = this.nodes.get(key).incomingEdges;
                int size = tempList.size();
                for(int i=0; i< size;i++){
                    int tempSrc = tempList.get(0).getSrc();
                    removeEdge(tempSrc,key);
                }
                if(this.edegs.containsKey(key)) {
                    this.edgesNumber = this.edgesNumber - this.edegs.get(key).size();
                    this.edegs.remove(key);
                    this.mc = this.mc + 1;
                    this.nextKey.push(key);
                    this.nodeNumber = this.nodeNumber+1;
                }
            return this.nodes.remove(key);
            }
        }
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.iterFlag) {
            throw new RuntimeException("Run time Exception");
        } else { //i can change the graph because no iterator was constructed
            // check if there is edge with the given src and dest
            if(this.edegs.get(src) != null && this.edegs.get(src).get(dest) != null){
                this.mc =this.mc +1;
                this.edgesNumber = this.edgesNumber -1;
                this.nodes.get(dest).incomingEdges.remove(this.edegs.get(src).get(dest));
                return this.edegs.get(src).remove(dest);
            }
            else{
                return null;
            }
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgesNumber;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    public int nextKey(){
        if(this.nextKey.empty())
            return this.nodeNumber;
        else
            return this.nextKey.pop();
    }
    public double[] getBounds(){
        return this.corners;
    }
    public void setFlag(){
        this.iterFlag = false;
    }
}
