/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Husni
 */
public class DataReader {
    
    private ArrayList<ArrayList<String>> data;
    public DataReader() {
        data = new ArrayList<>();
    }
    
    public DataReader(String filename) throws FileNotFoundException {
        read(filename);
    }
    
    public void read(String filename) throws FileNotFoundException {
        String delimiter = ",";
        Scanner sc = new Scanner(new File(filename));
        while(!sc.nextLine().contains("@data")) {}
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            
            ArrayList<String> row = new ArrayList<>(Arrays.asList(line.split(delimiter)));
            if (data.isEmpty()) {
                for (String element : row) {
                    data.add(new ArrayList<>(Arrays.asList(element)));
                }
            } else {
                for (int i = 0; i < row.size(); ++i) {
                    data.get(i).add(row.get(i));
                }
            }
        }
    }
    
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
}
