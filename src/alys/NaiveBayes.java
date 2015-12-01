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
    private int rowSize = 0;
    private int colSize = 0;
    private String[] classNames;
    private ArrayList<String> classRow;
    private int correctclassify;
    private int incorrectclassify;
    private int correctfold;
    private int incorrectfold;

    private int classifiedInstance = 0;
    
    public NaiveBayes() {}
    
    public int getrowSize(){
        return rowSize;
    }    
    
    public ArrayList<String> getclassRow(){
        return classRow;
    }
    
    public int getclassifiedInstance(){
        return classifiedInstance;
    }
    public int getcorrectfold(){
        return correctfold;
    }
    public int getincorrectfold(){
        return incorrectfold;
    }
    public int getcorrectclassify(){
        return correctclassify;
    }
    public int getincorrectclassify(){
        return incorrectclassify;
    }
    public void init(ArrayList<ArrayList<String>> data) {
        // Set data size
        rowSize = data.get(0).size();
        colSize = data.size();
        
        modelTable = new Table[colSize - 1];
        ArrayList<String> classRow = data.get(data.size() - 1);
        
        // Set classNames
        classNames = new HashSet<String>(Arrays.asList(classRow.toArray(new String[classRow.size()]))).toArray(new String[0]);
        
        // Hitung jumlah kemunculan kelas
        Map<String, Integer> classNameCount;
        classNameCount = countClassName(classRow);
        
        
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
                a.put(b.getRowKey(), b.getColumnKey(), b.getValue()/classNameCount.get(b.getColumnKey()));
            }
        }
    }
    
    private Map<String, Integer>  countClassName(ArrayList<String> classRow) {
        Map<String, Integer> classNameCount = new HashMap<>();
        for (int i = 0; i < classRow.size(); ++i) {
            if (classNameCount.containsKey(classRow.get(i))) {
                int count = classNameCount.get(classRow.get(i));
                classNameCount.put(classRow.get(i), count + 1);
            } else {
                classNameCount.put(classRow.get(i), 1);
            }
        }
        return classNameCount;
    }
    
    public void classify(ArrayList<ArrayList<String>> data) {
        //ArrayList<String> classRow = data.get(data.size() - 1);
        classRow = data.get(data.size() - 1);
        Map<String, Integer> classNameCount;
        classNameCount = countClassName(classRow);
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
        correctclassify = correct;
        incorrectclassify = incorrect;
        
//        System.out.print("Correctly classified: " + correct + "/" + rowSize + "  ");
//        System.out.println(String.valueOf((new Float(correct)/rowSize * 100) + "%"));
//        System.out.print("Incorrectly classified: " + incorrect + "/" + classRow.size() + "  ");
//        System.out.println(String.valueOf((new Float(incorrect)/rowSize * 100) + "%"));
    }
    
    public void folds(ArrayList<ArrayList<String>> data) {
        int foldSize = 10;
        int dividedSize = data.get(0).size()/foldSize;
        //ArrayList<String> classRow = data.get(data.size() - 1);
        classRow = data.get(data.size() - 1);

        // Break data into foldSize
        ArrayList<ArrayList<String>>[] dataPartials = new ArrayList[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            ArrayList<String> col = new ArrayList<>();
            ArrayList<ArrayList<String>> model = new ArrayList<>();;
            for (int j = 0; j < data.get(i).size(); ++j) {
                col.add(data.get(i).get(j));
                if ((j + 1) % dividedSize == 0 && j < dividedSize * foldSize) {
                    
                    model.add(new ArrayList<String>(col));
                    col.clear();
                }
            }
            if (!col.isEmpty()) {
                for (int j = 0; j < col.size(); ++j) {
                    model.get(j).add(col.get(j));
                }
            }
            dataPartials[i] = model;
        }
        
        int correct = 0;
        classifiedInstance = 0;
        for (int x = 0; x < foldSize; ++x) {
            // Membuat training set
            ArrayList<ArrayList<String>> trainingSet = new ArrayList<>();
            for (int i = 0; i < dataPartials.length; ++i) {
                ArrayList<String> col = new ArrayList<>();
                for (int j = 0; j < foldSize; j++) {
                    if (j != x) {
                        col.addAll(dataPartials[i].get(j));
                    }
                }
                trainingSet.add(col);
            }
            
            // Membuat test set
            ArrayList<ArrayList<String>> testSet = new ArrayList<>();
            for (int i = 0; i < dataPartials.length; ++i) {
                testSet.add(dataPartials[i].get(x));
            }
            
            // Model for Naive Bayes Algorithm
            Table<String, String, Double>[] modelTable = new Table[colSize - 1];
            
            // Initialize each Table in modelTable
            for (int i = 0; i < modelTable.length; i++) {
                modelTable[i] = HashBasedTable.create();
            }

            // Hitung kemunculan atribut
            for (int i = 0; i < trainingSet.size() - 1; ++i) {
                for (int j = 0; j < trainingSet.get(0).size(); ++j) {
                    if (modelTable[i].contains(trainingSet.get(i).get(j), classRow.get(j))) {
                        Double count = modelTable[i].get(trainingSet.get(i).get(j), classRow.get(j));
                        modelTable[i].put(trainingSet.get(i).get(j), classRow.get(j), count + 1);
                    } else {
                        modelTable[i].put(trainingSet.get(i).get(j), classRow.get(j), new Double(1));
                    }
                }
            }
            
            // Hitung jumlah kemunculan kelas
            Map<String, Integer> classNameCount;
            classNameCount = countClassName(trainingSet.get(trainingSet.size() - 1));
            
            // Hitung probabilitas kemunculan atribut
            for (Table<String, String, Double> a : modelTable) {
                for (Table.Cell<String, String, Double> b : a.cellSet()) {
                    a.put(b.getRowKey(), b.getColumnKey(), b.getValue() / classNameCount.get(b.getColumnKey()));
                }
            }
            
            for (int i = 0; i < testSet.get(0).size(); ++i) {
                double max = 0;
                int index = 0;
                for (int j = 0; j < classNames.length; ++j) {
                    double p = new Double(classNameCount.get(classNames[j])) / trainingSet.size();
                    for (int k = 0; k < colSize - 1; ++k) {
                        double _p;
                        if (modelTable[k].get(testSet.get(k).get(i), classNames[j]) != null) {
                            _p = modelTable[k].get(testSet.get(k).get(i), classNames[j]);
                        } else {
                            _p = 0;
                        }
                        p = p * _p;
                    }
                    if (p > max) {
                        max = p;
                        index = j;
                    }
                }
                if (classNames[index].equals(trainingSet.get(trainingSet.size() - 1).get(i))) {
                    correct++;
                }
                classifiedInstance++;
            }
        }
        int incorrect = classifiedInstance - correct;
        alys a = new alys();
        correctfold = correct;
        incorrectfold = incorrect;
        
//        System.out.print("Correctly classified: " + correct + "/" + classifiedInstance + "  ");
//        System.out.println(String.valueOf((new Float(correct)/classifiedInstance * 100) + "%"));
//        System.out.print("Incorrectly classified: " + incorrect + "/" + classifiedInstance + "  ");
//        System.out.println(String.valueOf((new Float(incorrect)/classifiedInstance * 100) + "%"));
    }
}
