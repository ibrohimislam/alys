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
        dataReader.read("src\\alys\\weather.arff");
        ArrayList<ArrayList<String>> data = dataReader.getData();
        
        NaiveBayes a = new NaiveBayes();
        a.init(data);
        System.out.println("Full training set test");
        a.classify(data);
        System.out.println("");
        System.out.println("10 Folds cross validation test");
        a.folds(data);
    }
}
