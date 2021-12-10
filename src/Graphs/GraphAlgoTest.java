package Graphs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GraphAlgoTest {
    String g1 = "C:\\Users\\Matanel\\IdeaProjects\\OOP=Ex3\\src\\jsonFiles\\G1.json";
    DWGraph MyGraph= new DWGraph(g1);
    GraphAlgo myGraph = new GraphAlgo(MyGraph);

    @Test
    void TestTranspose(){
        Boolean flag = myGraph.isConnected();
        Assertions.assertTrue(flag);
    }

    @Test
    void testShortest(){
        double answer = this.myGraph.shortestPathDist(1,10);
        Assertions.assertEquals(2,answer);
    }

    @Test
    void  center(){
        Assertions.assertEquals(8,myGraph.center().getKey());
        String g2 = "C:\\Users\\Matanel\\IdeaProjects\\OOP=Ex3\\src\\jsonFiles\\G2.json";
        MyGraph = new DWGraph(g2);
        this.myGraph.init(MyGraph);
        Assertions.assertEquals(0,myGraph.center().getKey());
        String g3 = "C:\\Users\\Matanel\\IdeaProjects\\OOP=Ex3\\src\\jsonFiles\\G3.json";
        MyGraph= new DWGraph(g3);
        this.myGraph.init(MyGraph);
        Assertions.assertEquals(40,myGraph.center().getKey());
    }
    }
