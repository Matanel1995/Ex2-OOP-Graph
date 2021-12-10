package Graphs;

import api.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    final int WHITE = 0, GRAY = 1, BLACK = 2;
    DWGraph myGraph;

    public GraphAlgo(DWGraph g) {
        init(g);
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.myGraph = (DWGraph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.myGraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph copy_graph = new DWGraph();
        Iterator<NodeData> nodeIt = this.myGraph.nodeIter();
        while (nodeIt.hasNext()) {
            Node temp = (Node) nodeIt.next();
            NodeData temp_node = new Node(temp);
            copy_graph.addNode(temp_node);
        }
        Iterator<EdgeData> edgeIt = this.myGraph.edgeIter();
        while (edgeIt.hasNext()) {
            Edge temp = (Edge) edgeIt.next();
            EdgeData temp_edge = new Edge(temp);
            copy_graph.connect(temp_edge.getSrc(), temp_edge.getDest(), temp_edge.getWeight());
        }
        return copy_graph;
    }

    @Override
    public boolean isConnected() {
        this.setWhite();
        if (DFS(this.myGraph) == 1) {
            DWGraph tempGraph = GraphTraspose(this.myGraph);
            if (DFS(tempGraph) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.myGraph.nodeSize() == 0 || this.myGraph.getNode(src) == null || this.myGraph.getNode(dest) == null)
            return -1;
        if (src == dest)
            return 0;
        List<NodeData> path = this.shortestPath(src, dest);
        if (path == null)
            return -1;
        if (path.get(0) == this.myGraph.getNode(src))
            return this.myGraph.getNode(dest).getWeight();
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        Comparator<NodeData> lessWeight = new Comparator<>() {
            @Override
            public int compare(NodeData node1, NodeData node2) {
                return Double.compare(node1.getWeight(), node2.getWeight());
            }
        };
        Iterator<NodeData> resetnodes = this.myGraph.nodeIter();
        while (resetnodes.hasNext()) {
            Node r = (Node) resetnodes.next();
            r.setWeight(Double.MAX_VALUE);
            r.setTag(0);
        }
        PriorityQueue<Node> q = new PriorityQueue<>(lessWeight);
        HashMap<Integer, Integer> parents = new HashMap();
        Node tempnode = (Node) this.myGraph.getNode(src);
        q.add(tempnode);
        tempnode.nodeWeight = 0;
        while (!q.isEmpty()) {
            Node curr = q.poll();
            if (curr.getTag() == 0) {
                curr.setTag(1);
                if (curr.getKey() == dest) break;
                Iterator<NodeData> it = this.myGraph.nodeIter();
                while (it.hasNext()) {
                    Node n = (Node) it.next();
                    if (n.getTag() == 0) {
                        Edge temp = (Edge) this.myGraph.getEdge(curr.getKey(), n.getKey());
                        if (temp != null && temp.getWeight() + curr.nodeWeight < n.nodeWeight) {
                            n.setWeight((temp.getWeight() + curr.nodeWeight));
                            if (parents.containsKey(n.getKey())) {
                                parents.remove(n.getKey());
                            }
                            parents.put(n.getKey(), curr.getKey());
                            if (!q.contains(n))
                                q.add(n);
                        }
                    }
                }
            }
        }
        List<Node> path = new ArrayList<>();
        if (this.myGraph.getNode(dest).getWeight() == Double.MAX_VALUE)
            return null;
        int assemble = dest;
        while (assemble != src) {
            Node par = (Node) this.myGraph.getNode(assemble);
            path.add(par);
            assemble = parents.get(assemble);
        }
        path.add((Node) this.myGraph.getNode(src));
        List<NodeData> reversedpath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedpath.add(path.get(i));
        }
        return reversedpath;
    }

    @Override
    public NodeData center() {
        if (!this.isConnected() || this.myGraph.nodeSize() == 0) {
            return null;
        } else {
            Node center = new Node();
            double cost;
            double maxForEach;
            double minOfAll = Double.POSITIVE_INFINITY;
            Iterator<NodeData> srcIt = this.myGraph.nodeIter();
            while (srcIt.hasNext()) {
                maxForEach = Double.NEGATIVE_INFINITY;
                Node tempSrc = (Node) srcIt.next();
                Iterator<NodeData> dstIt = this.myGraph.nodeIter();
                while (dstIt.hasNext()) {
                    Node tempDst = (Node) dstIt.next();
                    if (tempSrc == tempDst)
                        continue;
                    cost = shortestPathDist(tempSrc.getKey(), tempDst.getKey());
                    if (cost > maxForEach) {
                        maxForEach = cost;
                    }
                }
                if (maxForEach < minOfAll) {
                    minOfAll = maxForEach;
                    center = tempSrc;
                }
            }
            return center;
        }
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        double min = Double.POSITIVE_INFINITY;
        List<NodeData> returnedPath = new ArrayList<>();
        Iterator<NodeData> srcIt = cities.listIterator();
        while (srcIt.hasNext()) {
            NodeData curr = srcIt.next();
            Comparator<NodeData> lessWeight = new Comparator<>() {
                @Override
                public int compare(NodeData node1, NodeData node2) {
                    return Double.compare(shortestPathDist(curr.getKey(), node1.getKey()), shortestPathDist(curr.getKey(), node2.getKey()));
                }
            };
            PriorityQueue<NodeData> pQueue = new PriorityQueue<>(lessWeight);
            Iterator<NodeData> dstIt = cities.listIterator();
            while (dstIt.hasNext()) {
                NodeData tmp = dstIt.next();
                if (curr.getKey() != tmp.getKey())
                    pQueue.add(tmp);
            }
            double sum = 0;
            List<NodeData> tempPath = new ArrayList<>();
            NodeData tempCurr = curr;
            tempPath.add(curr);
            while (!pQueue.isEmpty()) {
                sum = sum + shortestPathDist(tempCurr.getKey(), pQueue.peek().getKey());
                tempCurr = pQueue.peek();
                tempPath.add(pQueue.poll());
            }
            if (sum < min) {
                min = sum;
                returnedPath = tempPath;
            }
        }
        return returnedPath;
    }

    @Override
    public boolean save(String file) {
        JSONObject saveJsonObject = new JSONObject();
        JSONArray jsonEdgeArr = new JSONArray();
        saveJsonObject.put("Edges", jsonEdgeArr);
        JSONArray jsonNodeArr = new JSONArray();
        saveJsonObject.put("Nodes", jsonNodeArr);
        Iterator<NodeData> nodeIt = this.myGraph.nodeIter();
        while (nodeIt.hasNext()) {
            Iterator<EdgeData> edgeIt = this.myGraph.edgeIter(nodeIt.next().getKey());
            while (edgeIt.hasNext()) {
                EdgeData temp = edgeIt.next();
                JSONObject jsonEdgeObject = new JSONObject();
                jsonEdgeObject.put("src", temp.getSrc());
                jsonEdgeObject.put("w", temp.getWeight());
                jsonEdgeObject.put("dest", temp.getDest());
                jsonEdgeArr.put(jsonEdgeObject);
            }
        }
        Iterator<NodeData> NodeIter = this.myGraph.nodeIter();
        while (NodeIter.hasNext()) {
            NodeData temp = NodeIter.next();
            JSONObject jsonNodeObject = new JSONObject();
            jsonNodeObject.put("pos", temp.getLocation().x() + "," + temp.getLocation().y() + "," + temp.getLocation().z());
            jsonNodeObject.put("id", temp.getKey());
            jsonNodeArr.put(jsonNodeObject);
        }
        try (FileWriter f = new FileWriter(file)) {
            f.write(saveJsonObject.toString());
            f.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        JSON j = new JSON(file);
        return true;
    }

    private DWGraph GraphTraspose(DWGraph g) {
        DWGraph transposeGraph = new DWGraph();
        Iterator<NodeData> NodeIter = g.nodeIter();
        while (NodeIter.hasNext()) {
            Node temp = (Node) NodeIter.next();
            temp.setTag(WHITE);
            transposeGraph.addNode(temp);
        }
        Iterator<EdgeData> EdgeIter = g.edgeIter();
        while (EdgeIter.hasNext()) {
            Edge temp = (Edge) EdgeIter.next();
            transposeGraph.connect(temp.getDest(), temp.getSrc(), temp.getWeight());
        }
        return transposeGraph;
    }

    private void DFSRec(DWGraph g, Node n) {
        g.getNode(n.getKey()).setTag(GRAY);
        Iterator<EdgeData> it = g.edgeIter(n.getKey());
        while (it != null && it.hasNext()) {
            Node nTemp = (Node) g.getNode(it.next().getDest());
            if (nTemp.getTag() == WHITE) {
                DFSRec(g, nTemp);
            }
        }
    }

    private int DFS(DWGraph g) {
        int counter = 0;
        Iterator<NodeData> it = g.nodeIter();
        while (it != null && it.hasNext()) {
            Node nTemp = (Node) it.next();
            if (nTemp.getTag() == WHITE) {
                DFSRec(g, nTemp);
                counter++;
            }
        }
        return counter;
    }

    private void setWhite() {
        Iterator<NodeData> nodeIt = this.myGraph.nodeIter();
        while (nodeIt.hasNext()) {
            Node temp = (Node) nodeIt.next();
            temp.setTag(0);
        }
    }
}

