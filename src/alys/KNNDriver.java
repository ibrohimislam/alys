import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import alys.*;

/**
 *
 * @author Ibrohim Kholilul Islam
 */
public class KNNDriver {
    
    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        dataReader.read("weather.arff");
        ArrayList<ArrayList<String>> data = dataReader.getData();
        
        KNN a = new KNN();
        Map<String, Integer> param = new HashMap<>();
        param.put("k", new Integer(2));
        a.init(data, param);

        System.out.println("Full training set test");
        
        for (int i=0; i<data.get(0).size(); i++) {
            ArrayList<String> instance = new ArrayList<String>();
            for (int j=0; j<data.size(); j++) {
                instance.add(data.get(j).get(i));
            }
            //System.out.println(">>" + instance.get(4));
            System.out.println(a.classify(instance));
        }
    }
}
