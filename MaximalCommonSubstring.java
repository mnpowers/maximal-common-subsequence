package subsequencefinder;

import java.util.HashSet;
import java.util.Set;

public class MaximalCommonSubstring {

    private String v;
    private String w;

    public MaximalCommonSubstring(String v, String w) {
        this.v = v;
        this.w = w;
    }
    /**
     * Returns a maximal common substring (i.e., consecutive subsequence) of v and w. The idea is that we could
     * create a table like the printMatchTable() below, and common substrings of v and w would correspond to strings of  
     * 1's on diagonals. However, we do not actually need to store the table.
     */
    public String find() {
        if (v.length() == 0 || w.length() == 0) {
            return "";
        }

        int start = -1;    // Starting index in v of maximal common substring.
        int length = 0;    // Length of maximal common substring.

        for (int k = 0; k < v.length() + w.length() - 1; k++) {   // Loop through diagonals of table, from top-rightmost diagonal to bottom-leftmost.
            int diagonalStart = Math.max(k + 1 - w.length(), 0);  // First row of diagonal.
            int diagonalEnd = Math.min(k, v.length() - 1);        // Last row of diagonal.
            int currentStart = -1;                                // Starting index of current common substring.
            int currentLength = 0;                                // Length of current common substring.

            for (int i = diagonalStart; i <= diagonalEnd; i++) {
                boolean match = (v.charAt(i) == w.charAt(i + w.length() - 1 - k)); // Check whether position (i,j) in table records a match.

                if (currentStart == -1 && match) {    // Start recording common substring.
                    currentStart = i;
                    currentLength = 1;
                } else if (currentStart != -1 && match) // Continue recording common substring.
                {
                    currentLength++;
                } else if (currentStart != -1 && !match) {  // Stop recording common substring, and check if it is new candidate for maximal. 
                    if (currentLength > length) {
                        start = currentStart;
                        length = currentLength;
                    }
                    currentStart = -1;
                    currentLength = 0;
                }
            }
            if (currentLength > length) {
                start = currentStart;
                length = currentLength;
            }
        }
        if (start == -1) {
            return "";
        }
        return v.substring(start, start + length);
    }
    
    /**
     * Returns the set of all maximal common substrings of v and w.
     * The algorithm is very similar to that of find().
     */
    public Set<String> findAll() {
        Set<String> maximals = new HashSet<>();    // Store strings of maximal length.
        maximals.add("");  // Empty substring is common.
        int length = 0;    // Length of maximal common substring.

        for (int k = 0; k < v.length() + w.length() - 1; k++) {   // Loop through diagonals of table, from top-rightmost diagonal to bottom-leftmost.
            int diagonalStart = Math.max(k + 1 - w.length(), 0);  // First row of diagonal.
            int diagonalEnd = Math.min(k, v.length() - 1);        // Last row of diagonal.
            int currentStart = -1;                                // Starting index of current common substring.
            int currentLength = 0;                                // Length of current common substring.

            for (int i = diagonalStart; i <= diagonalEnd; i++) {
                boolean match = (v.charAt(i) == w.charAt(i + w.length() - 1 - k)); // Check whether place (i,j) in table records a match.

                if (currentStart == -1 && match) {    // Start recording common substring.
                    currentStart = i;
                    currentLength = 1;
                } else if (currentStart != -1 && match) // Continue recording common substring.
                {
                    currentLength++;
                } else if (currentStart != -1 && !match) {  // Stop recording common substring, and check if it is new candidate for maximal. 
                    if (currentLength > length) {
                        maximals.clear();
                        length = currentLength;
                    }
                    if (currentLength >= length) {
                        maximals.add(v.substring(currentStart, currentStart + currentLength));
                    }
                    currentStart = -1;
                    currentLength = 0;
                }
            }
            if (currentLength > length) {
                maximals.clear();
                length = currentLength;
            }
            if (currentLength >= length && currentStart != -1) {
                maximals.add(v.substring(currentStart, currentStart + currentLength));
            }
        }
        return maximals;
    }

    /**
     * Prints table whose (i,j) entry is 1 or 0 according as whether or not 
     * v.charAt(i) matches w.charAt(j).
     */
    public void printMatchesTable() {
        for(int i = -1; i < v.length(); i++) {
            if(i == -1)
                System.out.print("  ");
            else
                System.out.print(v.charAt(i) + " ");
            
            for(int j = 0; j < w.length(); j++) {
                if(i == -1)
                    System.out.print(w.charAt(j) + " ");
                else {
                    int x = (( v.charAt(i) == w.charAt(j) ) ? 1 : 0);
                    System.out.print(x + " ");
                }
            }
            System.out.println("");
        }
    }
}
