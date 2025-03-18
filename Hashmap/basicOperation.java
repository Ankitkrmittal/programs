package Hashmap;

import java.util.HashMap;
import java.util.Set;

public class basicOperation {
    public static void main(String[] args) {
        HashMap<String,Integer> hm = new HashMap<>();
        //insert
        hm.put("India", 100);
        hm.put("China", 150);
        hm.put("US", 50);

        System.out.println(hm);
        //get
        int population = hm.get("India");
        System.out.println(population);
        System.out.println(hm.get("Indonesia"));

        //containsKey
        System.out.println(hm.containsKey("India"));//true
        System.out.println(hm.containsKey("Indonesia"));//false

        //Remove
        System.out.println(hm.remove("China"));
        System.out.println(hm);

        //size
        System.out.println(hm.size());

        //Is empty
        System.out.println(hm.isEmpty());


        //Iterate
        Set<String> keys = hm.keySet();
        System.out.println(keys);
        for (String k : keys) {
            
            System.out.println("key =" + k + ",value=" + hm.get(k));

        }


    }
}
