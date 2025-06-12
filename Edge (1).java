public class Edge<E> {
    public Vertex<E> destination;
    public Edge<E> next;
    public int weight;

    public Edge(Vertex<E> destination, int weight) {
        this.destination = destination;
        this.weight = weight;
        this.next = null;
    }
}
