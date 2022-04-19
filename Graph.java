import java.util.*;

class Vertx {
    int key;

    public Vertx(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertx vertx = (Vertx) o;

        return key == vertx.key;
    }

    @Override
    public String toString() {
        return "Key=" + key;
    }

    @Override
    public int hashCode() {
        return key;
    }
}

class Edge {
    Vertx v;
    int weight;

    public Edge(Vertx v, int weight) {
        this.v = v;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return v + "->weight=" + weight;
    }
}

public class Graph {
    Map<Vertx, List<Edge>> g;

    public Graph() {
        g = new HashMap<>();
    }

    public void addVert(Vertx v) {
        if (!g.containsKey(v)) {
            g.put(v, new ArrayList<>());
        }
    }

    public void addEdge(Vertx src, Vertx v, int weight) {
        g.get(src).add(new Edge(v, weight));
        g.get(v).add(new Edge(src, weight));
    }

    public int size() {
        return g.size();
    }

    public int getVertexK(Vertx v) {
        return v.key;
    }

    public List<Edge> getAdj(Vertx src) {
        return g.get(src);
    }

    public List<Vertx> getAdjV(Vertx src) {
        List<Vertx> list = new ArrayList<>();
        for (Edge edge : getAdj(src)) {
            list.add(edge.v);
        }
        return list;
    }

    public void BFS(Vertx v) {
        boolean[] visited = new boolean[size() + 1];
        List<Vertx> queue = new ArrayList<>();
        Arrays.fill(visited, false);
        visited[getVertexK(v)] = true;
        queue.add(v);
        while (!queue.isEmpty()) {
            v = queue.remove(0);
            System.out.println(v + " ");
            for (Vertx vertx : getAdjV(v)) {
                if (!visited[getVertexK(vertx)]) {
                    visited[getVertexK(vertx)] = true;
                    queue.add(vertx);
                }
            }
        }
    }

    public void DFS(Vertx v) {
        boolean[] visited = new boolean[size() + 1];
        DFSUtil(v, visited);
    }

    public void DFSUtil(Vertx v, boolean[] visited) {
        if (!visited[getVertexK(v)]) {
            visited[getVertexK(v)] = true;
            System.out.println(v + " ");
            for (Vertx vertx : getAdjV(v)) {
                DFSUtil(vertx, visited);
            }
        }
    }

    public List<Vertx> getVert() {
        List<Vertx> vert = new ArrayList<>();
        for (Map.Entry<Vertx, List<Edge>> list : g.entrySet()) {
            vert.add(list.getKey());
        }
        return vert;
    }
    public List<Vertx> printPath(Vertx v, Vertx v2, int cost){
        boolean[] visited = new boolean[size()+1];
        List<Vertx> arr= new ArrayList<>();
        return printRecurs2(v,v2,visited,arr,cost);
    }
    public void printRecurs(Vertx v, Vertx v2,boolean[] visited,List<Vertx> arr){
        arr.add(v);
        if(v.equals(v2)){
            System.out.println(arr);
            visited[getVertexK(v)]=false;
            arr.remove(v);
            return;
        }
        visited[getVertexK(v)]=true;
        for(Vertx aux: getAdjV(v)){
            if(!visited[getVertexK(aux)]){
                printRecurs(aux,v2,visited,arr);
            }
        }
        visited[getVertexK(v)]=false;
        arr.remove(v);
    }
    public List<Vertx> printRecurs2(Vertx v, Vertx v2, boolean[] visited, List<Vertx> arr, int cost){
        arr.add(v);
        if(v.equals(v2)){
            System.out.println(arr);
            if(costTotal(arr)==cost){
                return arr;
            }
            visited[getVertexK(v)]=false;
            arr.remove(v);

        }
        visited[getVertexK(v)]=true;
        for(Vertx aux: getAdjV(v)){
            if(!visited[getVertexK(aux)]){
                printRecurs(aux,v2,visited,arr);
            }
        }
        visited[getVertexK(v)]=false;
        arr.remove(v);
        return null;
    }
    public int costTotal(List<Vertx> arr){
        return 2;
    }
    public Map<Vertx, Integer> dijkstraAlg(Vertx start) {
        Map<Vertx, Integer> totalCost = new HashMap<>();
        List<Vertx> queue = new ArrayList<>();
        //boolean[] visited = new boolean[size()+1];
        totalCost.put(start, 0);
        queue.add(start);
        //visited[getVertexK(start)]=true;
        for (Vertx vert : getVert()) {
            if (!vert.equals(start)) {
                totalCost.put(vert, Integer.MAX_VALUE);
            }
        }
        while (queue.size() != 0) {
            start = queue.remove(0);
            for (Edge e : getAdj(start)) {
                Vertx v = e.v;
                int weight = e.weight;
                int total = totalCost.get(start) + weight;
                //System.out.println(start+"-"+e.v+"-"+total);
                if (total < totalCost.get(v)) {
                    //System.out.println("CORRECTED"+start+"-"+e.v+"-"+total);
                    totalCost.put(v, total);
                    queue.add(v);
                }
            }
        }
        return totalCost;
    }
}
