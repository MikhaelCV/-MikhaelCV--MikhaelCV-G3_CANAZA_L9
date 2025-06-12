public class GraphLink<E> {
    private Vertex<E> head;

    public GraphLink() {
        head = null;
    }

    public boolean searchVertex(E value) {
        Vertex<E> temp = head;
        while (temp != null) {
            if (temp.data.equals(value)) return true;
            temp = temp.next;
        }
        return false;
    }

    public boolean searchEdge(E from, E to) {
        Vertex<E> v = getVertex(from);
        if (v == null) return false;
        Edge<E> e = v.adjacency;
        while (e != null) {
            if (e.destination.data.equals(to)) return true;
            e = e.next;
        }
        return false;
    }

    private Vertex<E> getVertex(E value) {
        Vertex<E> temp = head;
        while (temp != null) {
            if (temp.data.equals(value)) return temp;
            temp = temp.next;
        }
        return null;
    }

    // removeVertex, removeEdge y dfs() se agregan después
}
