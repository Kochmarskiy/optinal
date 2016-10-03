package unique;

import java.util.HashSet;
import java.util.Set;

public class UniqueString {
    public static  String getUniqueString(String line){
        Set<Character> characters = new HashSet<>();
        for(int i=0;i<line.length();i++)
            if(!characters.contains(line.charAt(i)))
                characters.add(line.charAt(i));
        int amountOfDifferentSymbols = characters.size();
        char[] elements = new char[amountOfDifferentSymbols];
        for(int i=0;i<=line.length()-amountOfDifferentSymbols;i++)
        {
            label :for(int j=i;j<amountOfDifferentSymbols+i;j++)
            {
                elements[j-i]=line.charAt(j);
                char c =  elements[j-i];
                for(int k=j-i-1;k>=0;k--)
                    if(elements[k]==c) break label;
                if((j-i+1)==amountOfDifferentSymbols)
                    return new String(elements);

            }
        }
        return null;
    }
}

