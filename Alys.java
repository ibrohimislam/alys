package alys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Husni
 */
public class Alys {
    
    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        dataReader.read("C:\\Users\\husnimun\\Documents\\NetBeansProjects\\alys\\src\\alys\\data.txt");
        
        ArrayList<ArrayList<String>> data = dataReader.getData();
        
        for (ArrayList<String> row : data) {
            for (String element : row) {
                System.out.print(element);
                System.out.print(',');
            }
            System.out.println("");
        }
        
    }
}
