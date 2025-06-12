// metodo bfsPath(v, z): Devuelve camino entre dos vértices


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

    // reconstruir camino
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

    return null; // no hay camino
}
