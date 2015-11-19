package alys;


import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 *
 * @author Husni
 */
public class NaiveBayesDriver {
    
    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        dataReader.read("src\\alys\\weather.txt");
        ArrayList<ArrayList<String>> data = dataReader.getData();
        
        NaiveBayes a = new NaiveBayes();
        a.init(data);
        a.classify(data);
    }
}
