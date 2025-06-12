// === Estructuras base ===
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

// Edge.java
public class Edge<E> {
    public Vertex<E> destination;
    public Edge<E> next;
    public int weight;

    public Edge(Vertex<E> destination) {
        this(destination, 0);
    }

    public Edge(Vertex<E> destination, int weight) {
        this.destination = destination;
        this.weight = weight;
        this.next = null;
    }
}

// === GraphLink.java ===
import java.util.*;

public class GraphLink<E> {
    private Vertex<E> head;

    public GraphLink() {
        head = null;
    }

    public void insertVertex(E value) {
        if (!searchVertex(value)) {
            Vertex<E> newVertex = new Vertex<>(value);
            newVertex.next = head;
            head = newVertex;
        }
    }

    public boolean searchVertex(E value) {
        return getVertex(value) != null;
    }

    private Vertex<E> getVertex(E value) {
        Vertex<E> current = head;
        while (current != null) {
            if (current.data.equals(value)) return current;
            current = current.next;
        }
        return null;
    }

    public boolean searchEdge(E v1, E v2) {
        Vertex<E> origin = getVertex(v1);
        if (origin == null) return false;
        Edge<E> edge = origin.adjacency;
        while (edge != null) {
            if (edge.destination.data.equals(v2)) return true;
            edge = edge.next;
        }
        return false;
    }

    public void insertEdge(E v1, E v2) {
        Vertex<E> origin = getVertex(v1);
        Vertex<E> destination = getVertex(v2);
        if (origin != null && destination != null && !searchEdge(v1, v2)) {
            Edge<E> newEdge = new Edge<>(destination);
            newEdge.next = origin.adjacency;
            origin.adjacency = newEdge;

            Edge<E> inverseEdge = new Edge<>(origin);
            inverseEdge.next = destination.adjacency;
            destination.adjacency = inverseEdge;
        }
    }

    public void insertEdgeWeight(E v1, E v2, int weight) {
        Vertex<E> origin = getVertex(v1);
        Vertex<E> destination = getVertex(v2);
        if (origin != null && destination != null && !searchEdge(v1, v2)) {
            origin.adjacency = new Edge<>(destination, weight);
            destination.adjacency = new Edge<>(origin, weight);
        }
    }

    public void removeEdge(E v1, E v2) {
        removeEdgeOneDirection(v1, v2);
        removeEdgeOneDirection(v2, v1);
    }

    private void removeEdgeOneDirection(E from, E to) {
        Vertex<E> v = getVertex(from);
        if (v != null) {
            Edge<E> current = v.adjacency, prev = null;
            while (current != null) {
                if (current.destination.data.equals(to)) {
                    if (prev == null) v.adjacency = current.next;
                    else prev.next = current.next;
                    break;
                }
                prev = current;
                current = current.next;
            }
        }
    }

    public void removeVertex(E value) {
        if (head == null) return;
        Vertex<E> current = head;
        while (current != null) {
            removeEdgeOneDirection(current.data, value);
            current = current.next;
        }
        if (head.data.equals(value)) {
            head = head.next;
            return;
        }
        Vertex<E> prev = head;
        current = head.next;
        while (current != null) {
            if (current.data.equals(value)) {
                prev.next = current.next;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Ejercicio 1 - BFS y BFS Path
    public void bfs(E start) {
        Set<E> visited = new HashSet<>();
        Queue<Vertex<E>> queue = new LinkedList<>();
        Vertex<E> origin = getVertex(start);
        if (origin == null) return;
        queue.add(origin);
        visited.add(origin.data);
        while (!queue.isEmpty()) {
            Vertex<E> current = queue.poll();
            System.out.print(current.data + " ");
            Edge<E> edge = current.adjacency;
            while (edge != null) {
                if (!visited.contains(edge.destination.data)) {
                    visited.add(edge.destination.data);
                    queue.add(edge.destination);
                }
                edge = edge.next;
            }
        }
        System.out.println();
    }

    public ArrayList<E> bfsPath(E start, E end) {
        Map<E, E> parent = new HashMap<>();
        Set<E> visited = new HashSet<>();
        Queue<Vertex<E>> queue = new LinkedList<>();
        Vertex<E> origin = getVertex(start);
        if (origin == null || !searchVertex(end)) return null;
        queue.add(origin);
        visited.add(start);
        while (!queue.isEmpty()) {
            Vertex<E> current = queue.poll();
            if (current.data.equals(end)) break;
            Edge<E> edge = current.adjacency;
            while (edge != null) {
                E neighbor = edge.destination.data;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current.data);
                    queue.add(edge.destination);
                }
                edge = edge.next;
            }
        }
        ArrayList<E> path = new ArrayList<>();
        E step = end;
        while (step != null && parent.containsKey(step)) {
            path.add(0, step);
            step = parent.get(step);
        }
        if (step != null && step.equals(start)) {
            path.add(0, start);
            return path;
        }
        return null;
    }

    // Métodos auxiliares usados en otros ejercicios
    public int contarVertices() {
        int count = 0;
        Vertex<E> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public int gradoEntrada(E v) {
        int count = 0;
        Vertex<E> current = head;
        while (current != null) {
            Edge<E> edge = current.adjacency;
            while (edge != null) {
                if (edge.destination.data.equals(v)) count++;
                edge = edge.next;
            }
            current = current.next;
        }
        return count;
    }

    public int gradoSalida(E v) {
        Vertex<E> vertex = getVertex(v);
        if (vertex == null) return -1;
        int count = 0;
        Edge<E> edge = vertex.adjacency;
        while (edge != null) {
            count++;
            edge = edge.next;
        }
        return count;
    }

    // (continúa en archivo siguiente: GraphLinkParte2.java)
}
