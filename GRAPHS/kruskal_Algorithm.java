package GRAPHS;

import java.util.*;

public class kruskal_Algorithm {
    HashMap<Integer,HashMap<Integer,Integer>> map;
    public kruskal_Algorithm(int v) {
        map = new HashMap<>();
        for(int i=1;i<=v;i++) {
            map.put(i,new HashMap<>());
        }
    }
    public void AddEdge(int v1, int v2, int cost) {
        map.get(v1).put(v2, cost);
        map.get(v2).put(v1,cost);
    }
    class EdgePair{
        int e1;
        int e2;
        int cost;
        public EdgePair(int e1, int e2, int cost) {
            this.e1= e1;
            this.e2 = e2;
            this.cost = cost;
        }
        public String toString() {
            return e1 + "-" + e2 + "@" + cost;
        }
    }
    public List<EdgePair> getAllEdges() {
        List<EdgePair> ll = new ArrayList<>();
        for(int e1 : map.keySet()) {
            for(int e2: map.get(e1).keySet()) {
                int cost = map.get(e1).get(e2);
                ll.add(new EdgePair(e1,e2,cost));
            }
        }
        return ll; 
    }
    public int kruskal() {
        List<EdgePair> ll = getAllEdges();
        Collections.sort(ll,new Comparator<EdgePair>(){

            @Override
            public int compare(EdgePair o1,EdgePair o2) {
                return o1.cost-o2.cost;
            }
        }); 
        int sum =0;
        DisJointSet dsu = new DisJointSet();
        for(int v: map.keySet()) {
            dsu.CreateSet(v);
        }
        for(EdgePair e : ll) {
            int re1 = dsu.find(e.e1);
            int re2= dsu.find(e.e2);
            if(re1 == re2) {
                //nothing
            }
            else {
                dsu.union(e.e1,e.e2);
                sum += e.cost;
                System.out.println(e);
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        kruskal_Algorithm ks  = new kruskal_Algorithm(n);
        int m = sc.nextInt();
        for(int i=0;i<m;i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            int cost = sc.nextInt();
            ks.AddEdge(v1, v2, cost);
        }
        System.out.println(ks.kruskal());
    }
}
