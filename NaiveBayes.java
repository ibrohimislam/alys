/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alys;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Husni
 */
public class NaiveBayes {
    private Table<String, String, Double>[] modelTable;
    private Map<String, Integer> classNameCount;
    private int rowSize = 0;
    private int colSize = 0;
    private String[] classNames;
    
    public NaiveBayes() {}
    
    public void init(ArrayList<ArrayList<String>> data) {
        modelTable = new Table[data.size() -1];
        ArrayList<String> classRow = data.get(data.size() - 1);
        
        // Set data size
        rowSize = data.get(0).size();
        colSize = data.size();
        
        // Set classNames
        classNames = new HashSet<String>(Arrays.asList(classRow.toArray(new String[classRow.size()]))).toArray(new String[0]);
        
        // Hitung jumlah kemunculan kelas
        countClassName(classRow);
        
        // Initialize each Table in modelTable
        for (int i = 0; i < modelTable.length; i++) {
            modelTable[i] = HashBasedTable.create();
        }
        
        // Hitung kemunculan atribut
        for (int i = 0; i < colSize - 1; ++i) {
            for (int j = 0; j < rowSize; ++j) {
                if (modelTable[i].contains(data.get(i).get(j), classRow.get(j))) {
                    Double count = modelTable[i].get(data.get(i).get(j), classRow.get(j));
                    modelTable[i].put(data.get(i).get(j), classRow.get(j), count + 1);
                } else {
                    modelTable[i].put(data.get(i).get(j), classRow.get(j), new Double(1));
                }
            }    
        }
        
        // Hitung probabilitas kemunculan atribut
        for (Table<String, String, Double> a : modelTable) {
            for (Table.Cell<String, String, Double> b : a.cellSet()) {
                a.put(b.getRowKey(), b.getColumnKey(), new Double(b.getValue()/classNameCount.get(b.getColumnKey())));
            }
        }
    }
    
    private void countClassName(ArrayList<String> classRow) {
        classNameCount = new HashMap<>();
        for (int i = 0; i < classRow.size(); ++i) {
            if (classNameCount.containsKey(classRow.get(i))) {
                int count = classNameCount.get(classRow.get(i));
                classNameCount.put(classRow.get(i), count + 1);
            } else {
                classNameCount.put(classRow.get(i), 1);
            }
        }
    }
    
    public void classify(ArrayList<ArrayList<String>> data) {
        ArrayList<String> classRow = data.get(data.size() - 1);
        int count = 0;
        for (int i = 0; i < rowSize; ++i) {
            double max = 0;
            int index = 0;
            for (int j = 0; j < classNames.length; ++j) {
                double p = new Double(classNameCount.get(classNames[j]))/rowSize;
                for (int k = 0; k < colSize - 1; ++k) {
                    double x;
                    if (modelTable[k].get(data.get(k).get(i), classNames[j]) != null) {
                        x = modelTable[k].get(data.get(k).get(i), classNames[j]);   
                    } else {
                        x = 0;
                    }
                    p = p * x;
                    
                }
                if (p > max) {
                    max = p;
                    index = j;
                }
            }
            if (classNames[index].equals(classRow.get(i))) {
                count++;
            }
        }
        int correct = count;
        int incorrect = rowSize - count;
        
        System.out.print("Correctly classified: " + correct + "/" + rowSize + "  ");
        System.out.println(String.valueOf((new Float(correct)/rowSize * 100) + "%"));
        System.out.print("Incorrectly classified: " + incorrect + "/" + classRow.size() + "  ");
        System.out.println(String.valueOf((new Float(incorrect)/rowSize * 100) + "%"));

    }
}
