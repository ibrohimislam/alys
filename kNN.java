package alys;

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

public class kNN {

    private String data[][];
    private int k;

    public kNN() {}

    public void init(String _data[][], Map<String, Integer> parameter) {
        data = _data.clone();
        
        Integer kValue = parameter.get("k");
        if (kValue != null) {
            k = kValue;
        }
    }

    public String classify(String instance[]) {
        int nRow = data[0].length;
        int[] result = new int[nRow];

        for (int i = 0; i < nRow; i++) {
            result[i] = 0;
        }

        for (int column=0, nColumn = data.length; column < nColumn - 1; column++) {
            String instanceValue = instance[column];
            for (int row=0, nSample = nRow; row < nSample; row++) {
                if (data[column][row].equals(instanceValue))
                    result[column]++;
            }
        }

        String classRow[] = data[data.length-1];

        List<Instance> list = new ArrayList<Instance>();
        for (int i = 0; i < nRow; i++)
            list.add(new Instance(result[i],classRow[i]));
        Collections.sort(list);


        Map<String, Integer> classOccurence = new HashMap<>();
        
        for (int i=0; i<k; i++) {
            String instanceClass = list.get(i).getInstanceClass();
            Integer n = classOccurence.get(instanceClass);
            
            if (n==null)
                classOccurence.put(instanceClass, 1);
            else
                classOccurence.put(instanceClass, n+1);
        }

        int max = 0;
        String classMaxOccurence = "";

        Iterator it = classOccurence.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Integer val = (Integer) pair.getValue();
            if (max < val) {
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

        public Instance(int similarity, String _myClass) {
            this.similarity = similarity;
            this.myClass = _myClass;
        }

        @Override
        public int compareTo(Instance o) {
            return similarity < o.similarity ? -1 : similarity > o.similarity ? 1 : 0;
        }

        public String getInstanceClass(){
            return myClass;       
        }
    }

}
