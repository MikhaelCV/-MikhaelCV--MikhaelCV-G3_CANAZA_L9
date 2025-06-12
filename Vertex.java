// Vertex.java
public class Vertex<E> {
    public E data;
    public Vertex<E> next;
    public Edge<E> adjacency;

    public Vertex(E data) {
        this.data = data;
        this.next = null;
        this.adjacency = null;
    }
}
