
package subsequencefinder;

import java.util.Random;
import java.util.Set;

public class SubsequenceFinder {

    public static void main(String[] args) {
        String v = randomString(10);
        String w = randomString(10);
        System.out.println("v: " + v);
        System.out.println("w: " + w);
        
        
        MaximalCommonSubsequence finder = new MaximalCommonSubsequence(v, w);
        
        String sub = finder.find();
        System.out.println("Maximal common subsequence: " + sub);
        
        Set<String> set = finder.findAll();
        System.out.println("All maximal common subsequences:");
        for(String string : set)
            System.out.println(string);

    }
    
    public static String randomString(int length) {
        Random random = new Random();
        String string = "";
        
        for(int i = 0; i < length; i++) {
            string += (char) (random.nextInt(26) + 97);
        }
        
        return string;
    }
}
