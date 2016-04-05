/* Promise of Originality
 I promise that this source code file has, in it's entirety, been
 written by myself or a member of my group and by no other person or
 persons. If at any time an exact copy of this source code is found to
 be used by another person outside my group in this term, I understand
 that all members of my group and the members of the group that
 submitted the copy will receive a zero on this assignment.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ssft
{

    private static ArrayList<Integer> array;
    private static int total = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException{
        File file = null;
        if (0 < args.length)
        {
            file = new File(args[0]);
        }

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        array = new ArrayList<>();
        String line;

        while((line = bufferReader.readLine()) !=null)
        {

            int temp = Integer.parseInt(line);
            array.add(temp);
        }

        int current = 0;

        while(ssft.array.size() >= 2)
        {
            current = compare(current);
        }
        System.out.println("SSTF Total Seek: " + total);
    }

    private static int compare(int current)
    {

        int currentIndex = current;

        int currentValue;
        int next = currentIndex + 1;
        if(currentIndex == array.size()-1)
        {
            next = 0;
        }

        int count = 0;
        int newIndex = 1;
        int lowestTemp = -1;
        if(next < array.size())
        {
            lowestTemp = Math.abs(ssft.array.get(currentIndex) - ssft.array.get(next));
            newIndex = next;
        }
        while(count < array.size()-1)
        {
            if(next < array.size())
            {
                if(Math.abs(ssft.array.get(currentIndex) - ssft.array.get(next)) <= lowestTemp)
                {
                    lowestTemp = Math.abs(ssft.array.get(currentIndex) - ssft.array.get(next));
                    newIndex = next;
                }

            }
            if(next == ssft.array.size()-1)
            {
                next = 0;
            }
            else
            {
                next = next + 1;
            }
            count++;
        }

        total += lowestTemp;
        //System.out.println("newIndex = " + ssft.array.get(newIndex)
        //  + " removed = " + ssft.array.get(currentIndex) + " sum = " + total);
        currentValue = ssft.array.get(newIndex);
        array.remove(currentIndex);

        for(int i = 0; i < array.size(); i++)
        {
            if(ssft.array.get(i) == currentValue)
            {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }
}
