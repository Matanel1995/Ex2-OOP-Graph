package Graphs;

import api.EdgeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GraphTest {
    GeoLocationData geo1 = new GeoLocationData(1,1,1);
    GeoLocationData geo2 = new GeoLocationData(2,2,2);
    GeoLocationData geo3 = new GeoLocationData(3,3,3);
    GeoLocationData geo4 = new GeoLocationData(4,4,4);
    GeoLocationData geo5 = new GeoLocationData(5,5,5);
    Node node1= new Node(1,geo1);
    Node node2= new Node(2,geo2);
    Node node3= new Node(3,geo3);
    Node node4= new Node(4,geo4);
    Node node5= new Node(5,geo5);
    String g1 = "C:\\Users\\Matanel\\IdeaProjects\\OOP=Ex3\\src\\jsonFiles\\g1.json";
    DWGraph MyGraph= new DWGraph(g1);
    @Test
    void addNodesAndEdge() {
        Assertions.assertEquals(0,MyGraph.getMC());
        MyGraph.addNode(node1); //+1
        MyGraph.addNode(node2); //+1
        Assertions.assertEquals(2,MyGraph.getMC());
        MyGraph.addNode(node3); //+1
        MyGraph.addNode(node4); //+1
        Assertions.assertEquals(4,MyGraph.getMC());
        // Add the same node again should not add becouse allready exist
        MyGraph.addNode(node2); //does not count node with key =2 allready exist
        MyGraph.removeNode(node2.getKey()); //+1
        MyGraph.removeNode(node1.getKey()); //+1
        MyGraph.removeNode(node3.getKey()); //+1
        Assertions.assertEquals(7,MyGraph.getMC());
        MyGraph.connect(1,2,5); //does not count cant connect edge to no existing node
        MyGraph.connect(2,3,5); //does not count cant connect edge to no existing node
        Assertions.assertEquals(7,MyGraph.getMC());
        MyGraph.connect(3,4,5); //does not count cant connect edge to no existing node
        MyGraph.connect(4,5,5); //does not count cant connect edge to no existing node
        Assertions.assertEquals(7,MyGraph.getMC());
        MyGraph.removeEdge(1,2); //does not count cant remove edge that does not exist
        MyGraph.removeEdge(2,3); //does not count cant remove edge that does not exist
        MyGraph.removeEdge(3,4); //does not count cant remove edge that does not exist
        Assertions.assertEquals(7,MyGraph.getMC());
        MyGraph.addNode(node1); //+1
        MyGraph.addNode(node2); //+1
        MyGraph.addNode(node3); //+1
        MyGraph.connect(1,2,5); //+1
        MyGraph.connect(2,3,5); //+1
        Assertions.assertEquals(12,MyGraph.getMC());
        MyGraph.connect(3,4,5); //+1
        MyGraph.connect(4,5,5); // should not be counted not node with id 5
        Assertions.assertEquals(13,MyGraph.getMC());
        MyGraph.removeEdge(1,2); //+1
        MyGraph.removeEdge(2,3); //+1
        MyGraph.removeEdge(3,4); //+1
        Assertions.assertEquals(16,MyGraph.getMC());
    }

    @Test
    void checkExeptions(){
        MyGraph.addNode(node2);
        MyGraph.addNode(node3);
        MyGraph.addNode(node4);
        try{
            Iterator tempItr = MyGraph.nodeIter();
            MyGraph.addNode(node5);
            fail();
        }
        catch (RuntimeException ex){
            assertEquals("Cant add new node after creating an iterator" , ex.getMessage());
        }

        try {
            MyGraph.removeNode(node4.getKey());
            fail();
        }
        catch (RuntimeException ex){
            assertEquals("Cant remove new node after creating an iterator" , ex.getMessage());
        }
    }

    @Test
    void testIters(){
        MyGraph.addNode(node1); //+1
        MyGraph.addNode(node2); //+1
        MyGraph.addNode(node3); //+1
        MyGraph.addNode(node4); //+1
        MyGraph.addNode(node5); //+1
        Iterator tempIter = MyGraph.nodeIter();
        int counter =0;
        while (tempIter.hasNext()){
            counter++;
            tempIter.next();
        }
        Assertions.assertEquals(5,counter);
        tempIter = MyGraph.edgeIter(node1.getKey());
        counter =0;
        while (tempIter!= null &&tempIter.hasNext()){
            counter++;
            tempIter.next();
        }
        Assertions.assertEquals(0,counter);
    }

    @Test
    void testIncomingEdges(){
        MyGraph.addNode(node1);
        MyGraph.addNode(node2);
        MyGraph.addNode(node3);
        MyGraph.addNode(node4);
        MyGraph.addNode(node5);
        MyGraph.connect(1,2,6);
        MyGraph.connect(3,2,6);
        Assertions.assertEquals(MyGraph.nodes.get(2).incomingEdges.size(),2);
        MyGraph.connect(4,2,6);
        MyGraph.connect(5,2,6);
        Assertions.assertEquals(MyGraph.nodes.get(2).incomingEdges.size(),4);
        MyGraph.removeEdge(4,2);
        Assertions.assertEquals(MyGraph.nodes.get(2).incomingEdges.size(),3);
    }

    @Test
    void removeNode(){
        Assertions.assertEquals(6 , MyGraph.edgeSize());
        MyGraph.removeNode(1);
        Assertions.assertEquals(2,MyGraph.edgeSize());
        MyGraph.removeNode(3);
        Assertions.assertEquals(0,MyGraph.edgeSize());
    }

    @Test void testiterators(){
        MyGraph.addNode(node1);
        MyGraph.addNode(node2);
        MyGraph.addNode(node3);
        MyGraph.addNode(node4);
        MyGraph.addNode(node5);
        MyGraph.connect(1,2,6);
        MyGraph.connect(3,2,6);
        MyGraph.connect(4,2,6);
        MyGraph.connect(5,2,6);
        MyGraph.connect(2,1,6);
        MyGraph.connect(2,3,6);
        MyGraph.connect(2,4,6);
        MyGraph.connect(2,5,6);
        Iterator<EdgeData> testIt =MyGraph.edgeIter();
        int counter =0 ;
        while (testIt.hasNext()){
            Edge next =(Edge) testIt.next();
            counter++;
        }
        Assertions.assertEquals(10,counter);
    }

    @Test
    void jsonGraphTest(){
        Assertions.assertTrue(MyGraph.nodes.containsKey(0));
        Assertions.assertTrue(MyGraph.nodes.containsKey(1));
        Assertions.assertTrue(MyGraph.nodes.containsKey(2));
        Assertions.assertTrue(MyGraph.nodes.containsKey(3));
        Assertions.assertFalse(MyGraph.nodes.containsKey(4));
        Assertions.assertTrue(MyGraph.edegs.get(0).containsKey(1));
        Assertions.assertTrue(MyGraph.edegs.get(1).containsKey(0));
        Assertions.assertFalse(MyGraph.edegs.get(0).containsKey(2));

    }
}