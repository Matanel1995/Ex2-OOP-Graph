package Graphs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class NodeTest {
    final int WHITE =0 , GRAY = 1 , BLACK=2;
    GeoLocationData loc1 = new GeoLocationData(10,10,0);
    GeoLocationData loc2 = new GeoLocationData(10,20,0);
    GeoLocationData loc3 = new GeoLocationData(20,10,0);
    GeoLocationData loc4 = new GeoLocationData(10,10,10);
    Node node1 = new Node(1,loc1);
    @Test
    void SetAndGetLocation(){
        //Check if i can change location proparly.
        Assert.assertEquals(loc1,node1.getLocation());
        node1.setLocation(loc2);
        Assert.assertEquals(loc2,node1.getLocation());
        node1.setLocation(loc3);
        Assert.assertEquals(loc3,node1.getLocation());
        node1.setLocation(loc4);
        Assert.assertEquals(loc4,node1.getLocation());
    }
    @Test
    void setAndGetWiehgt(){
        //Check if the init value was set to Max integer as it should.
        Assertions.assertEquals(Integer.MAX_VALUE,node1.getWeight());
        //Check if changing location wont change the weight
        node1.setLocation(loc2);
        Assertions.assertEquals(Integer.MAX_VALUE,node1.getWeight());
        //Check if i can set the weight correcly.
        node1.setWeight(10);
        Assertions.assertEquals(10,node1.getWeight());
    }
    @Test
    void setAndGetTag(){
        //Check if the init value was set to White as is should
        Assertions.assertEquals(WHITE,node1.getTag());
        //Check if i can change the tag value corectly.
        node1.setTag(BLACK);
        Assertions.assertEquals(BLACK,node1.getTag());
        node1.setTag(GRAY);
        Assertions.assertEquals(GRAY,node1.getTag());
    }

    @Test void GetSetInfo(){
        Assertions.assertEquals("Key:1" , node1.getInfo());
        node1.setInfo("Key:4");
        Assertions.assertEquals(4,node1.getKey());
        Assertions.assertEquals("Key:4" , node1.getInfo());
    }
}
