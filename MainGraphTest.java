// === MainGraphTest.java ===
public class MainGraphTest {
    public static void main(String[] args) {
        System.out.println("--- Pruebas con GraphLink (No dirigido) ---");
        GraphLink<String> g1 = new GraphLink<>();
        g1.insertVertex("A");
        g1.insertVertex("B");
        g1.insertVertex("C");
        g1.insertVertex("D");
        g1.insertEdge("A", "B");
        g1.insertEdge("A", "C");
        g1.insertEdge("B", "D");

        System.out.println("BFS desde A:");
        g1.bfs("A");

        System.out.println("Camino de A a D:");
        System.out.println(g1.bfsPath("A", "D"));

        System.out.println("¿Es camino?: " + g1.esCamino());
        System.out.println("¿Es ciclo?: " + g1.esCiclo());
        System.out.println("¿Es completo?: " + g1.esCompleto());

        g1.mostrarFormal();
        g1.mostrarListaAdyacencia();
        g1.mostrarMatrizAdyacencia();

        System.out.println("--- Pruebas con GraphListEdge ---");
        GraphListEdge<String, Integer> g2 = new GraphListEdge<>();
        g2.insertVertex("X");
        g2.insertVertex("Y");
        g2.insertVertex("Z");
        g2.insertVertex("W");
        g2.insertEdge("X", "Y");
        g2.insertEdge("Y", "Z");
        g2.insertEdge("Z", "W");
        g2.insertEdge("W", "X");

        System.out.println("BFS desde X:");
        g2.bfs("X");

        g2.mostrarFormal();
        g2.mostrarListaAdyacencia();
        g2.mostrarMatrizAdyacencia();
    }
}
