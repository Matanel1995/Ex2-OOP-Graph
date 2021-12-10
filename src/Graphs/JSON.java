package Graphs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JSON {
    JSONArray NodeJsonArr=new JSONArray();
    JSONArray EdgeJsonArr=new JSONArray();
    JSONObject graphObj;
    int nodeCount;
    int edgeCount;

    public JSON(String jsonFile) {//name AND location of the file
        String myJson = null;
        try {
            myJson = new Scanner(new File(jsonFile)).useDelimiter("\\Z").next();

            this.graphObj = new JSONObject(myJson);
            this.NodeJsonArr = graphObj.getJSONArray("Nodes");
            this.EdgeJsonArr = graphObj.getJSONArray("Edges");
            this.nodeCount = 0;
            this.edgeCount = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // NEED TO TEST!!!
    public JSON(DWGraph g) { // Constructor of JSON from graph object
        try {
            this.NodeJsonArr = new JSONArray(g.nodes.values());
            this.EdgeJsonArr = new JSONArray(g.edegs.values());
            String string_g = "{\"Edges\":" + this.EdgeJsonArr.toString() + ",\"Nodes\":" + this.NodeJsonArr.toString() + "}";
            this.graphObj = new JSONObject(string_g);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public JSONArray getNodeJsonArr() {
        return NodeJsonArr;
    }

    public JSONArray getEdgeJsonArr() {
        return EdgeJsonArr;
    }

    public JSONObject getGraphObj() {
        return graphObj;
    }

    public Node generateNode(int node_index) { //index of node
        JSONObject nodeObj = this.NodeJsonArr.getJSONObject(node_index);
        int id = nodeObj.getInt("id");
        String[] stGeo = nodeObj.getString("pos").split(",");
        GeoLocationData gld = new GeoLocationData(Double.parseDouble(stGeo[0]), Double.parseDouble(stGeo[1]), Double.parseDouble(stGeo[2]));
        ++nodeCount;
        return new Node(id, gld);
    }

    public Node generateNode(JSONObject nodeObj) { //index of node
        int id = nodeObj.getInt("id");
        String[] stGeo = nodeObj.getString("pos").split(",");
        GeoLocationData gld = new GeoLocationData(Double.parseDouble(stGeo[0]), Double.parseDouble(stGeo[1]), Double.parseDouble(stGeo[2]));
        ++nodeCount;
        return new Node(id, gld);
    }

    public Edge generateEdge(int edge_index) { //index of edge
        JSONObject edgeObj = this.EdgeJsonArr.getJSONObject(edge_index);
        int src = edgeObj.getInt("src");
        int dest = edgeObj.getInt("dest");
        double w = edgeObj.getDouble("w");
        ++edgeCount;
        return new Edge(src, dest, w);
    }

    public void import_all_nodes(DWGraph graph) {//
        for (Object o : this.NodeJsonArr) {
            graph.addNode(generateNode((JSONObject) o));
        }
    }

    public void import_all_edges(DWGraph graph) {
        for (int i = 0; i < this.EdgeJsonArr.length(); i++) {
            JSONObject edgeObj = EdgeJsonArr.getJSONObject(i);
            graph.connect(edgeObj.getInt("src"), edgeObj.getInt("dest"), edgeObj.getDouble("w"));
        }
    }

    public List<Node> all_nodes_to_list() {
        List<Node> ll = new ArrayList<>();
        for (Object o : this.NodeJsonArr) {
            ll.add(generateNode((JSONObject) o));
        }
        return ll;
    }

    public List<Edge> all_edges_to_list() {
        List<Edge> ll = new ArrayList<>();
        for (Object o : this.EdgeJsonArr) {
            JSONObject edgeObj = (JSONObject) o;
            ll.add(new Edge(edgeObj.getInt("src"), edgeObj.getInt("dest"), edgeObj.getDouble("w")));
        }
        return ll;
    }

    public void toJSONFile(String fileName) {//Enter file name+path
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(this.graphObj.toString());
            file.flush();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public boolean toJSONFileBool(String fileName) {//Enter file name+path
        try (FileWriter file = new FileWriter(fileName)) {
            String temp = this.graphObj.toString();
            file.write(this.graphObj.toString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.fillInStackTrace();
            return false;
        }
    }
}