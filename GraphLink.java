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

            // como es no dirigido, insertamos también la inversa
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

        // eliminar aristas entrantes
        Vertex<E> current = head;
        while (current != null) {
            removeEdgeOneDirection(current.data, value);
            current = current.next;
        }

        // eliminar vértice de la lista
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
    public void dfs(E start) {
        Set<E> visited = new HashSet<>();
        dfsRecursive(getVertex(start), visited);
    }

    private void dfsRecursive(Vertex<E> v, Set<E> visited) {
        if (v == null || visited.contains(v.data)) return;
        visited.add(v.data);
        System.out.print(v.data + " ");
        Edge<E> edge = v.adjacency;
        while (edge != null) {
            dfsRecursive(edge.destination, visited);
            edge = edge.next;
        }
    }

    public void bfs(E start) {
        Set<E> visited = new HashSet<>();
        Queue<Vertex<E>> queue = new LinkedList<>();
        Vertex<E> origin = getVertex(start);
        if (origin != null) {
            queue.add(origin);
            visited.add(origin.data);
            while (!queue.isEmpty()) {
                Vertex<E> current = queue.poll();
                System.out.print(current.data + " ");
                Edge<E> edge = current.adjacency;
                while (edge != null) {
                    if (!visited.contains(edge.destination.data)) {
                        queue.add(edge.destination);
                        visited.add(edge.destination.data);
                    }
                    edge = edge.next;
                }
            }
        }
    }

    public ArrayList<E> bfsPath(E start, E end) {
        Map<E, E> prev = new HashMap<>();
        Set<E> visited = new HashSet<>();
        Queue<Vertex<E>> queue = new LinkedList<>();
        Vertex<E> origin = getVertex(start);

        if (origin == null) return null;

        queue.add(origin);
        visited.add(origin.data);

        while (!queue.isEmpty()) {
            Vertex<E> current = queue.poll();
            if (current.data.equals(end)) break;
            Edge<E> edge = current.adjacency;
            while (edge != null) {
                E dest = edge.destination.data;
                if (!visited.contains(dest)) {
                    visited.add(dest);
                    prev.put(dest, current.data);
                    queue.add(edge.destination);
                }
                edge = edge.next;
            }
        }

        ArrayList<E> path = new ArrayList<>();
        E step = end;
        while (step != null && prev.containsKey(step)) {
            path.add(0, step);
            step = prev.get(step);
        }
        if (step != null && step.equals(start)) {
            path.add(0, start);
            return path;
        }
        return null;
    }

    public boolean isConexo() {
        if (head == null) return true;
        Set<E> visited = new HashSet<>();
        dfsRecursive(head, visited);

        int count = 0;
        Vertex<E> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return visited.size() == count;
    }

    public Stack<E> Dijkstra(E start, E end) {
        Map<E, Integer> dist = new HashMap<>();
        Map<E, E> prev = new HashMap<>();
        Set<E> visited = new HashSet<>();
        PriorityQueue<E> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        Vertex<E> current = head;
        while (current != null) {
            dist.put(current.data, Integer.MAX_VALUE);
            current = current.next;
        }

        dist.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            E u = pq.poll();
            if (visited.contains(u)) continue;
            visited.add(u);
            Vertex<E> vertexU = getVertex(u);
            if (vertexU != null) {
                Edge<E> edge = vertexU.adjacency;
                while (edge != null) {
                    E v = edge.destination.data;
                    int alt = dist.get(u) + edge.weight;
                    if (alt < dist.get(v)) {
                        dist.put(v, alt);
                        prev.put(v, u);
                        pq.add(v);
                    }
                    edge = edge.next;
                }
            }
        }

        Stack<E> path = new Stack<>();
        E step = end;
        while (step != null && prev.containsKey(step)) {
            path.push(step);
            step = prev.get(step);
        }
        if (step != null && step.equals(start)) {
            path.push(start);
            return path;
        }
        return null;
    }
}
///////////////////////////////////////////////////////////////////////////////////
//Esto cubre el Ejercicio 02 del Laboratorio 09.

public void insertEdgeWeight(E v1, E v2, int weight) {
    Vertex<E> origin = getVertex(v1);
    Vertex<E> destination = getVertex(v2);

    if (origin != null && destination != null && !searchEdge(v1, v2)) {
        // Inserta arista v1 -> v2
        Edge<E> edge1 = new Edge<>(destination, weight);
        edge1.next = origin.adjacency;
        origin.adjacency = edge1;

        // Inserta arista v2 -> v1 (no dirigido)
        Edge<E> edge2 = new Edge<>(origin, weight);
        edge2.next = destination.adjacency;
        destination.adjacency = edge2;
    }
}

public ArrayList<E> shortPath(E start, E end) {
    Map<E, Integer> distance = new HashMap<>();
    Map<E, E> previous = new HashMap<>();
    Set<E> visited = new HashSet<>();
    PriorityQueue<E> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

    // Inicializar distancias
    Vertex<E> temp = head;
    while (temp != null) {
        distance.put(temp.data, Integer.MAX_VALUE);
        temp = temp.next;
    }
    distance.put(start, 0);
    queue.add(start);

    while (!queue.isEmpty()) {
        E current = queue.poll();
        if (visited.contains(current)) continue;
        visited.add(current);

        Vertex<E> currentVertex = getVertex(current);
        if (currentVertex == null) continue;

        Edge<E> edge = currentVertex.adjacency;
        while (edge != null) {
            E neighbor = edge.destination.data;
            int alt = distance.get(current) + edge.weight;
            if (alt < distance.get(neighbor)) {
                distance.put(neighbor, alt);
                previous.put(neighbor, current);
                queue.add(neighbor);
            }
            edge = edge.next;
        }
    }

    // Reconstruir el camino
    ArrayList<E> path = new ArrayList<>();
    E step = end;

    while (step != null && previous.containsKey(step)) {
        path.add(0, step);
        step = previous.get(step);
    }
    if (step != null && step.equals(start)) {
        path.add(0, start);
        return path;
    }
    return null; // No hay camino
}


public boolean isConexo() {
    if (head == null) return true;

    Set<E> visited = new HashSet<>();
    dfsRecursive(head, visited);

    int totalVertices = 0;
    Vertex<E> temp = head;
    while (temp != null) {
        totalVertices++;
        temp = temp.next;
    }

    return visited.size() == totalVertices;
}


public Stack<E> Dijkstra(E start, E end) {
    Map<E, Integer> dist = new HashMap<>();
    Map<E, E> prev = new HashMap<>();
    Set<E> visited = new HashSet<>();
    PriorityQueue<E> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

    Vertex<E> temp = head;
    while (temp != null) {
        dist.put(temp.data, Integer.MAX_VALUE);
        temp = temp.next;
    }

    dist.put(start, 0);
    pq.add(start);

    while (!pq.isEmpty()) {
        E u = pq.poll();
        if (visited.contains(u)) continue;
        visited.add(u);

        Vertex<E> current = getVertex(u);
        if (current != null) {
            Edge<E> edge = current.adjacency;
            while (edge != null) {
                E v = edge.destination.data;
                int alt = dist.get(u) + edge.weight;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(v);
                }
                edge = edge.next;
            }
        }
    }

    // reconstruir camino
    Stack<E> path = new Stack<>();
    E step = end;
    while (step != null && prev.containsKey(step)) {
        path.push(step);
        step = prev.get(step);
    }
    if (step != null && step.equals(start)) {
        path.push(start);
        return path;
    }

    return null; // no hay camino
}










