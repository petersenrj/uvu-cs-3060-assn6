/* Promise of Originality
 I promise that this source code file has, in it's entirety, been
 written by myself or a member of my group and by no other person or
 persons. If at any time an exact copy of this source code is found to
 be used by another person outside my group in this term, I understand
 that all members of my group and the members of the group that
 submitted the copy will receive a zero on this assignment.
 */


package clook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author petersenrr
 */
public class clook {
    
    private static ArrayList<Integer> array;
    private static int total = 0;
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        
        File file = null;
        if (0 < args.length)
        {
            file = new File(args[0]);
        }
        
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        clook.array = new ArrayList<>();
        String line;
        
        while((line = bufferReader.readLine()) !=null)
        {
            
            int temp = Integer.parseInt(line);
            clook.array.add(temp);
        }
        calculate();
        
        System.out.println("SSTF Total Seek: " + clook.total);
    }
    
    private static void calculate()
    {
        int temp = clook.array.get(0);
        Collections.sort(clook.array);
        int i = 0;
        int next = 0;
        int current = 0;
        
        while(clook.array.get(i) != null)
        {
            if(temp == clook.array.get(i))
            {
                next = i+1;
                current = i;
                break;
            }
            i++;
        }
        
        for(int j = 0; j < clook.array.size()-1; j++)
        {
            if(clook.array.get(next) > clook.array.get(current))
            {
                clook.total += (clook.array.get(next) - clook.array.get(current));
                current = next;
                if(next == (clook.array.size()-1))
                {
                    next = 0;
                }
                else
                {
                    next = next + 1;
                }
            }
            else
            {
                clook.total += (clook.array.get(current) - clook.array.get(next));
                current = next;
                if(next == (clook.array.size()-1))
                {
                    next = 0;
                }
                else
                {
                    next = next + 1;
                }
            }
        }
    }
}
