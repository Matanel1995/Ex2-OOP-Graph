import Graphs.DWGraph;
import Graphs.GraphAlgo;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import GUI.*;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        ans = new DWGraph(json_file);
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DWGraph temp = new DWGraph(json_file);
        DirectedWeightedGraphAlgorithms ans = new GraphAlgo(temp);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        GraphGUI myGUI = new GraphGUI(json_file);
    }

    public static void main(String[] args) {
        String myJson = args[0];
        //String myJson = "C:\\Users\\Matanel\\IdeaProjects\\Ex2\\src\\jsonFiles\\g1.json";
        getGrapg(myJson);
        getGrapgAlgo(myJson);
        runGUI(myJson);
    }
}