package alys;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import java.io.File;

/**
 *
 * @author Husni
 */
public class NaiveBayesDriver extends JFrame {
    
    
//    public static void main(String[] args)  throws FileNotFoundException {
//        JFrame frame = new NaiveBayesDriver();
//        frame.show();
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
//        int result = fileChooser.showOpenDialog(frame);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
//        }
//        File selectedFile = fileChooser.getSelectedFile();
//        DataReader dataReader = new DataReader();
//        //dataReader.read("src\\alys\\car.arff");
//        dataReader.read(selectedFile.getAbsolutePath());
//        ArrayList<ArrayList<String>> data = dataReader.getData();
//        
//        NaiveBayes a = new NaiveBayes();
//        a.init(data);
//        System.out.println("Full training set test");
//        a.classify(data);
//        System.out.println("");
//        System.out.println("10 Folds cross validation test");
//        a.folds(data);
//    }
}
