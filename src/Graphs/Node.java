package Graphs;

import api.GeoLocation;
import org.json.JSONObject;

import java.util.LinkedList;

public class Node implements api.NodeData {
    final int WHITE =0 , GRAY = 1 , BLACK=2;
    int id;
    int key;
    double nodeWeight;
    GeoLocationData cordinates;
    int tagInfo , previusNodeId;
    LinkedList<Edge> incomingEdges = new LinkedList<Edge>();


    public Node(){
        this.cordinates = new GeoLocationData();
        this.key = 0;
        this.tagInfo =WHITE;
        this.nodeWeight = Double.MAX_VALUE;
        this.previusNodeId = -1;
    }
    public  Node(int key , GeoLocation L){
        this.cordinates = (GeoLocationData)L;
        this.key = key;
        this.tagInfo = WHITE;
        this.nodeWeight = Double.MAX_VALUE;
        this.previusNodeId = -1;
    }
    //copy
    public Node(Node other){
        this.key = other.getKey();
        this.cordinates = (GeoLocationData) other.getLocation();
        this.nodeWeight = other.getWeight();
        this.tagInfo = other.getTag();
    }
    @Override
    public int getKey() {

        return this.key;
    }

    @Override
    public GeoLocation getLocation() {

        return this.cordinates;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.cordinates = (GeoLocationData) p;
    }

    @Override
    public double getWeight() {

        return this.nodeWeight;
    }

    @Override
    public void setWeight(double w) {

        this.nodeWeight = w;
    }

    @Override
    public String getInfo() {
        return this.toString();
    }

    @Override
    public void setInfo(String s) {
        String tempString =s.split(":")[1];
        this.key = Integer.parseInt(tempString);
    }

    @Override
    public int getTag() {

        return this.tagInfo;
    }

    @Override
    public void setTag(int t) {

        this.tagInfo = t;
    }
    public String toString (){
        return "Key:" + this.key;
    }
}
