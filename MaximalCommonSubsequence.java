
package subsequencefinder;

import java.util.HashSet;
import java.util.Set;

public class MaximalCommonSubsequence {
    
    private String v;
    private String w;
    private int[][] t;  // A table where t[i][j] is length of maximal common
                        // subsequence of v.substring(0,i) and w.substring(0,j).
    
    public MaximalCommonSubsequence(String v, String w) {
        this.v = v;
        this.w = w;
        t = new int[v.length() + 1][w.length() + 1]; 
    }
    
    // Returns a maximal common subusequence of strings v and w.
    public String find() {
        createTable();
        return createSubsequence();
    }
    
    // Returns the set of all maximal common subsequences of v and w.
    public Set<String> findAll() {
        if( v.length() == 0 || w.length() == 0 )
            return new HashSet<String>();
        
        createTable();
        
        /** We successively build set of maximal common subsequences of v.substring(0,i) and w.substring(0,j).
         * We do this by looping through the table just created, but it is only necessary to store latest column
         * of sets of subsequences.
         */
        Set<String>[] column = createFirstColumn();
        
        for( int j = 2; j <= w.length(); j++ )
            column = createNextColumn(column, j);
        
        return column[v.length()];
    }
    
    private Set<String>[] createFirstColumn() {
        Set<String>[] column = ( Set<String>[] ) new Set[ v.length() + 1 ];
        
        for( int i = 0; i <= v.length(); i++ ) {
            Set<String> set = new HashSet<>();
            if(t[i][1] == 1)
                set.add( "" + w.charAt(0) );
            column[i] = set;
        }        
        return column;
    }
    
    private Set<String>[] createNextColumn(Set<String>[] column, int j) {
        Set<String>[] nextColumn = ( Set<String>[] ) new Set[ v.length() + 1 ];
        int last = v.length();
        
        for( int i = v.length(); i >= 0; i-- ) {
            Set<String> set = new HashSet<>();
            if( i-1 < last )
                last = findLastAppearance(w.charAt(j - 1), i);
            
            if(last == -1 || t[i][j-1] >= 2 + t[last][j-1])   // In this case strings coming from t[last][j-1] will be smaller
                set = column[i];                              // than those from t[i][j-1].
            else {
                if( column[last].isEmpty() )
                    set.add( "" + w.charAt(j - 1) );
                
                for( String s : column[last] )
                    set.add( s + w.charAt(j - 1) );
                
                if( t[i][j-1] == 1 + t[last][j-1] )
                    set.addAll(column[i]);        
            }
            
            nextColumn[i] = set;
        }
        
        return nextColumn;
    }
    
    
    // Returns a maximal common subsequence using table.
    private String createSubsequence() {
        StringBuilder sub = new StringBuilder();
        int i = v.length();
        
        for( int j = w.length(); j > 0; j-- ) {
            if(t[i][j] > t[i][j-1]) {
                sub.insert(0, w.charAt(j - 1));
                i = findLastAppearance(w.charAt(j - 1), i);
            }
        }
        
        return sub.toString();
    }
    
    // Create the table t.
    private void createTable() {
        for( int j = 1; j <= w.length(); j++ ) {
            int last = v.length();    // Store location of last appearance of w.charAt(j - 1) in current substring of v.
            
            for(int i = v.length(); i >0; i--) {    // Loop through substrings of v.
                if(i-1 < last)
                    last = findLastAppearance(w.charAt(j - 1), i);
                if(last == -1 || t[last][j-1] < t[i][j-1])    // No alteration necesarry if substring of v does not contain w.charAt(j-1),
                    t[i][j] = t[i][j-1];                      // or if common subsequences of of v.substring(0,i) and w.substring(0,j-1) are strictly 
                else                                          // longer than those of v.substring(0,last) and w.substring(0,j-1).
                    t[i][j] = t[last][j-1]  + 1;       
            }
        }
    }
    
    
    // Returns last appearance of character c before position n in String v. Returns -1 if no appearance.
    private int findLastAppearance(char c, int n) {
        int appearance = -1;
        int i = n - 1;
        
        while(appearance == -1 && i > -1) {
            if(c == v.charAt(i))
                appearance = i;
            i--;
        }
        
        return appearance;
    }
    
    //Prints current state of table t.
    public void printTable() {
        for(int i = 0; i <= v.length(); i++) {
            if(i == 0)
                System.out.print("  ");
            else
                System.out.print(v.charAt(i - 1) + " ");
            
            for(int j = 1; j <= w.length(); j++) {
                if(i == 0)
                    System.out.print(w.charAt(j - 1) + " ");
                else
                    System.out.print(t[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
