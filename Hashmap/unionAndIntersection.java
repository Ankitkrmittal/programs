package Hashmap;

import java.util.HashSet;

public class unionAndIntersection {
    public static void main(String[] args) {
        int [] arr1 = {7,3,9};
        int [] arr2 = {6,3,9,2,9,4};
        HashSet<Integer> set = new HashSet<>();
        for(int i=0;i<arr1.length;i++) {
            set.add(arr1[i]);
        }
        for(int i=0;i<arr2.length;i++) {
            set.add(arr2[i]);
        }
        System.out.println("union of set is :" + set.size());
        set.clear();
        for(int i=0;i<arr1.length;i++) {
            set.add(arr1[i]);
        }
        int count =0;
         for(int i=0;i<arr2.length;i++) {
            if(set.contains(arr2[i])) {
                count++;
                set.remove(arr2[i]);
            }
         }
         System.out.println("intersection of set is :" + count);
         
    }
}
