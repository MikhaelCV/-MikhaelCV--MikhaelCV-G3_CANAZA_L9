public class TestGraphListEdge {
    public static void main(String[] args) {
        GraphListEdge<String, Integer> g = new GraphListEdge<>();

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");

        g.insertEdge("A", "B");
        g.insertEdge("A", "C");
        g.insertEdge("B", "D");
        g.insertEdge("C", "D");

        System.out.println("¿Existe vértice A?: " + g.searchVertex("A"));
        System.out.println("¿Existe arista entre A y D?: " + g.searchEdge("A", "D"));
        System.out.println("¿Existe arista entre A y B?: " + g.searchEdge("A", "B"));

        System.out.println("🔁 BFS desde A:");
        g.bfs("A");
    }
}
