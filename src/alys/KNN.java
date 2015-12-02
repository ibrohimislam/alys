package alys;

import com.google.common.collect.Ordering;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Ibrohim Kholilul Islam
 */

public class KNN {

    private String[][] data;
    private int k;

    public KNN() {}

    public void init(ArrayList<ArrayList<String>> _data, Map<String, Integer> parameter) {
        data = new String[_data.size()][];

        for (int i = 0; i < _data.size(); i++) {
            ArrayList<String> row = _data.get(i);
            data[i] = row.toArray(new String[row.size()]);
        }
        
        Integer kValue = parameter.get("k");
        if (kValue != null) {
            k = kValue;
        }
    }

    public String classify(ArrayList<String> instance) {
        int nRow = data[0].length;
        int nColumn = data.length;
        int[] result = new int[nRow];

        for (int i = 0; i < nRow; i++) {
            result[i] = 0;
        }

        for (int column=0; column < nColumn-1; column++) {
            String instanceValue = instance.get(column);
            for (int row=0, nSample = nRow; row < nSample; row++) {
                if (data[column][row].equals(instanceValue))
                    result[row]++;
            }
        }

        Map<Integer, Integer> similarityOccurence = new HashMap<>();

        for (int row=0, nSample = nRow; row < nSample; row++) {
            Integer key = result[row];
            Integer n = similarityOccurence.get(key);
            
            if (n==null)
                similarityOccurence.put(key, 1);
            else
                similarityOccurence.put(key, n+1);
        }

        String classRow[] = data[data.length-1];

        List<Instance> list = new ArrayList<Instance>();
        for (int i = 0; i < nRow; i++){
            list.add(new Instance(result[i],classRow[i],i));
            //System.out.println(result[i] +" "+classRow[i] +" "+ classSimilarityOccurence.get(data[nColumn - 1][i]+result[i]));
        }
        
        list = Ordering.natural().greatestOf(list, k);

        Map<String, Integer> classOccurence = new HashMap<>();
        
        for (int i=0; i<k; i++) {
            //System.out.println("" + list.get(i).getSalt() + " " + list.get(i).getInstanceClass());
            String instanceClass = list.get(i).getInstanceClass();

            //if (similarityOccurence.get(list.get(i).getSimilarity()) > k-i){
                //System.out.println(similarityOccurence.get(list.get(i).getSimilarity()) + " " + (k-i));
            //} else {
                Integer n = classOccurence.get(instanceClass);
                
                if (n==null)
                    classOccurence.put(instanceClass, 1);
                else
                    classOccurence.put(instanceClass, n+1);
            //}
        }

        int max = 0;
        String classMaxOccurence = "";

        Iterator it = classOccurence.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            Integer val = (Integer) pair.getValue();
            // System.out.println(pair.getKey());
            // System.out.println(pair.getValue());
            if (max <= val) {
                max = val;
                classMaxOccurence = (String)pair.getKey();
            }
            it.remove();
        }

        return classMaxOccurence;
    }

    class Instance implements Comparable<Instance> {
        int similarity;
        String myClass;
        int index;

        public Instance(int similarity, String _myClass, int _index) {
            this.similarity = similarity;
            this.myClass = _myClass;
            this.index = _index;
        }

        @Override
        public int compareTo(Instance o) {
            return similarity < o.similarity ? 1 : similarity > o.similarity ? -1 : (index >= o.index ? 1 : -1);
        }

        public String getInstanceClass(){
            return myClass;       
        }

        public int getSimilarity(){
            return similarity;       
        }

        public int getSalt(){
            return index;       
        }
    }

}
