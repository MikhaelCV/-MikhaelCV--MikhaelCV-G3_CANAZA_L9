//metodo bfs anchura un vertoce

public void bfs(E start) {
    Set<E> visited = new HashSet<>();
    Queue<Vertex<E>> queue = new LinkedList<>();
    Vertex<E> origin = getVertex(start);

    if (origin == null) {
        System.out.println("El vértice no existe.");
        return;
    }

    queue.add(origin);
    visited.add(origin.data);

    while (!queue.isEmpty()) {
        Vertex<E> current = queue.poll();
        System.out.print(current.data + " ");

        Edge<E> edge = current.adjacency;
        while (edge != null) {
            E neighbor = edge.destination.data;
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                queue.add(edge.destination);
            }
            edge = edge.next;
        }
    }

    System.out.println(); // salto de línea final
}
