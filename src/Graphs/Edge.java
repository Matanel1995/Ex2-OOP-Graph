package Graphs;

public class Edge implements api.EdgeData {
    final int WHITE =0 , GRAY = 1 , BLACK=2;
    int source , destination;
    double weight;
    int tagInfo;
    public Edge(){
        this.destination = 0;
        this.source = 0;
        this.weight =0;
        this.tagInfo = WHITE;
    }

    public Edge(int src, int dst ,double w){
        this.weight = w;
        this.source = src;
        this.destination = dst;
        this.tagInfo = WHITE;
    }
    /** Copy Constructors */
    public Edge(Edge other){
        this.source = other.source;
        this.weight = other.weight;
        this.destination = other.destination;
        this.tagInfo = other.tagInfo;
    }
    @Override
    public int getSrc() {

        return this.source;
    }

    @Override
    public int getDest() {

        return this.destination;
    }

    @Override
    public double getWeight() {

        return this.weight;
    }

    @Override
    public String getInfo() {

        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {

        return this.tagInfo;
    }

    @Override
    public void setTag(int t) {
        this.tagInfo = t;
    }

    }
