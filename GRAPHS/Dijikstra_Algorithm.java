package GRAPHS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;



public class Dijikstra_Algorithm {
    HashMap<Integer,HashMap<Integer,Integer>> map;
    public Dijikstra_Algorithm(int v) {
        map = new HashMap<>();
        for(int i=1;i<=v;i++) {
            map.put(i,new HashMap<>());
        }
    }
    public void AddEdge(int v1,int v2, int cost) {
        map.get(v1).put(v2,cost);
        map.get(v2).put(v1,cost);

    }
    class DijikstraPair {
        int vtx;
        String path;
        int cost;
        public DijikstraPair(int vtx,String path,int cost) {
            this.vtx = vtx;
            this.path = path;
            this.cost = cost;
        }
        public String toString() {
            return this.vtx + "-" + this.path+" @ "+ this.cost;
        }
    }
    public void Dijikstra(int src) {
        PriorityQueue<DijikstraPair> pq = new PriorityQueue<>(new Comparator<DijikstraPair>() {
            @Override
            public int compare(DijikstraPair o1,DijikstraPair o2) {
                return o1.cost - o2.cost;
            }
        });
        HashSet<Integer> visited = new HashSet<>();
        pq.add(new DijikstraPair(src, ""+src, 0));
        while(!pq.isEmpty()) {
           DijikstraPair rp = pq.poll();
           if(visited.contains(rp.vtx)) {
            continue;

        }
        visited.add(rp.vtx);
        System.out.println(rp);
        for(int nbrs:map.get(rp.vtx).keySet()) {
            if(!visited.contains(nbrs)) {
                int cost = map.get(rp.vtx).get(nbrs);
                pq.add(new DijikstraPair(nbrs, rp.path+nbrs, rp.cost + cost));
            }
        }
        }

    }
    public static void main(String[] args) {
        Dijikstra_Algorithm dj = new Dijikstra_Algorithm(7);
        dj.AddEdge(1, 4, 6);
        dj.AddEdge(1, 2, 10);
        dj.AddEdge(2, 3, 7);
        dj.AddEdge(3, 4, 5);
        dj.AddEdge(4, 5, 1);
        dj.AddEdge(5, 6, 4);
        dj.AddEdge(6, 7, 3);
        dj.Dijikstra(1);
    }
}
