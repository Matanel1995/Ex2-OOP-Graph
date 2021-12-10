package Graphs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EdgeTest {
    final int WHITE =0 , GRAY = 1 , BLACK=2;
    Edge edge1= new Edge(1,10,300);
    @Test
    void GetSrc(){
        Assertions.assertEquals(1,edge1.getSrc());
    }
    @Test
    void GetDst(){
        Assertions.assertEquals(10,edge1.getDest());
    }
    @Test
    void GetWeight(){
        Assertions.assertEquals(300,edge1.getWeight());
    }
    @Test
    void GetAndSetTag(){
        //Check the init if its White
        Assertions.assertEquals(WHITE,edge1.getTag());
        //Check if i can change the tag value
        edge1.setTag(GRAY);
        Assertions.assertEquals(GRAY,edge1.getTag());
        edge1.setTag(BLACK);
        Assertions.assertEquals(BLACK,edge1.getTag());
    }
}