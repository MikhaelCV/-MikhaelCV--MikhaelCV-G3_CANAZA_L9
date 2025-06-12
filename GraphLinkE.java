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
// para el ejercicio 5
//a
public int gradoNodo(E v) {
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
//b
public boolean esCamino() {
    int countGrado1 = 0;
    int countGrado2 = 0;

    Vertex<E> current = head;
    while (current != null) {
        int grado = gradoNodo(current.data);
        if (grado == 1) countGrado1++;
        else if (grado == 2) countGrado2++;
        else return false;
        current = current.next;
    }

    return countGrado1 == 2 && countGrado2 >= 1;
}

//c
public boolean esCiclo() {
    Vertex<E> current = head;
    while (current != null) {
        if (gradoNodo(current.data) != 2) return false;
        current = current.next;
    }
    return true;
}

//d
public boolean esRueda() {
    int total = contarVertices();
    if (total < 4) return false;

    int gradoNMenos1 = 0;
    int grado3 = 0;

    Vertex<E> current = head;
    while (current != null) {
        int grado = gradoNodo(current.data);
        if (grado == total - 1) gradoNMenos1++;
        else if (grado == 3) grado3++;
        else return false;
        current = current.next;
    }

    return gradoNMenos1 == 1 && grado3 == total - 1;
}

//e
public boolean esCompleto() {
    int total = contarVertices();
    Vertex<E> current = head;
    while (current != null) {
        if (gradoNodo(current.data) != total - 1) return false;
        current = current.next;
    }
    return true;
}

public int contarVertices() {
    int count = 0;
    Vertex<E> current = head;
    while (current != null) {
        count++;
        current = current.next;
    }
    return count;
}

//////////////////////////////////////////////////////////////////////////
//ejercicio 6

// a 
public void mostrarFormal() {
    System.out.println("🔹 Vértices:");
    Vertex<E> current = head;
    while (current != null) {
        System.out.print(current.data + " ");
        current = current.next;
    }
    System.out.println("\n🔹 Aristas:");
    Set<String> mostradas = new HashSet<>();
    current = head;
    while (current != null) {
        Edge<E> edge = current.adjacency;
        while (edge != null) {
            String par = current.data + "-" + edge.destination.data;
            String parInvertido = edge.destination.data + "-" + current.data;
            if (!mostradas.contains(parInvertido)) {
                System.out.println(par);
                mostradas.add(par);
            }
            edge = edge.next;
        }
        current = current.next;
    }
}


//b 
public void mostrarListaAdyacencia() {
    System.out.println("🔸 Lista de Adyacencia:");
    Vertex<E> current = head;
    while (current != null) {
        System.out.print(current.data + " → ");
        Edge<E> edge = current.adjacency;
        while (edge != null) {
            System.out.print(edge.destination.data + " ");
            edge = edge.next;
        }
        System.out.println();
        current = current.next;
    }
}


//c 
public void mostrarMatrizAdyacencia() {
    List<E> vertices = new ArrayList<>();
    Vertex<E> current = head;
    while (current != null) {
        vertices.add(current.data);
        current = current.next;
    }

    int n = vertices.size();
    int[][] matriz = new int[n][n];

    // llenar matriz
    for (int i = 0; i < n; i++) {
        Vertex<E> v = getVertex(vertices.get(i));
        Edge<E> edge = v.adjacency;
        while (edge != null) {
            int j = vertices.indexOf(edge.destination.data);
            matriz[i][j] = 1;
            matriz[j][i] = 1; // no dirigido
            edge = edge.next;
        }
    }

    System.out.println("🔹 Matriz de Adyacencia:");
    System.out.print("   ");
    for (E e : vertices) System.out.print(e + " ");
    System.out.println();

    for (int i = 0; i < n; i++) {
        System.out.print(vertices.get(i) + ": ");
        for (int j = 0; j < n; j++) {
            System.out.print(matriz[i][j] + " ");
        }
        System.out.println();
    }
}

//////////////////////////////////////////////////////////////
//ejercicio 07

//a 
public int gradoEntrada(E v) {
    int count = 0;
    Vertex<E> current = head;
    while (current != null) {
        Edge<E> edge = current.adjacency;
        while (edge != null) {
            if (edge.destination.data.equals(v)) {
                count++;
            }
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


//b 
public boolean esCaminoDirigido() {
    int inicio = 0, fin = 0, intermedios = 0;
    Vertex<E> current = head;

    while (current != null) {
        int in = gradoEntrada(current.data);
        int out = gradoSalida(current.data);

        if (in == 0 && out == 1) inicio++;
        else if (in == 1 && out == 0) fin++;
        else if (in == 1 && out == 1) intermedios++;
        else return false;

        current = current.next;
    }

    return inicio == 1 && fin == 1;
}


//c 
public boolean esCicloDirigido() {
    Vertex<E> current = head;
    while (current != null) {
        if (gradoEntrada(current.data) != 1 || gradoSalida(current.data) != 1)
            return false;
        current = current.next;
    }
    return true;
}


//d 
public boolean esRuedaDirigida() {
    int total = contarVertices();
    if (total < 4) return false;

    int centro = 0, periferia = 0;

    Vertex<E> current = head;
    while (current != null) {
        int in = gradoEntrada(current.data);
        int out = gradoSalida(current.data);

        if (out == total - 1 && in == 0) centro++;
        else if (in == 2 && out == 1) periferia++;
        else return false;

        current = current.next;
    }

    return centro == 1 && periferia == total - 1;
}


//e
public boolean esCompletoDirigido() {
    int total = contarVertices();

    Vertex<E> current = head;
    while (current != null) {
        if (gradoSalida(current.data) != total - 1 || gradoEntrada(current.data) != total - 1)
            return false;
        current = current.next;
    }

    return true;
}

/////////////////////////////////////////
// ejercicio 09


//a 
public boolean esIsomorfo(GraphLink<E> otro) {
    if (this.contarVertices() != otro.contarVertices()) return false;
    if (this.contarAristas() != otro.contarAristas()) return false;

    // Comparar grados de entrada y salida
    List<Integer> gradosEntradaA = this.gradosEntradaOrdenados();
    List<Integer> gradosEntradaB = otro.gradosEntradaOrdenados();
    List<Integer> gradosSalidaA = this.gradosSalidaOrdenados();
    List<Integer> gradosSalidaB = otro.gradosSalidaOrdenados();

    return gradosEntradaA.equals(gradosEntradaB) && gradosSalidaA.equals(gradosSalidaB);
}

private int contarAristas() {
    int total = 0;
    Vertex<E> current = head;
    while (current != null) {
        Edge<E> edge = current.adjacency;
        while (edge != null) {
            total++;
            edge = edge.next;
        }
        current = current.next;
    }
    return total;
}

private List<Integer> gradosEntradaOrdenados() {
    List<Integer> lista = new ArrayList<>();
    Vertex<E> current = head;
    while (current != null) {
        lista.add(gradoEntrada(current.data));
        current = current.next;
    }
    Collections.sort(lista);
    return lista;
}

private List<Integer> gradosSalidaOrdenados() {
    List<Integer> lista = new ArrayList<>();
    Vertex<E> current = head;
    while (current != null) {
        lista.add(gradoSalida(current.data));
        current = current.next;
    }
    Collections.sort(lista);
    return lista;
}



//b 
public boolean esPlano() {
    int V = contarVertices();
    int A = contarAristas();
    return A <= 3 * V - 6;
}


//c 
public boolean esConexo() {
    List<E> vertices = new ArrayList<>();
    Vertex<E> current = head;
    while (current != null) {
        vertices.add(current.data);
        current = current.next;
    }

    for (E v : vertices) {
        Set<E> visitados = bfsVisitados(v);
        if (visitados.size() != vertices.size()) return false;
    }

    return true;
}

private Set<E> bfsVisitados(E start) {
    Set<E> visited = new HashSet<>();
    Queue<Vertex<E>> queue = new LinkedList<>();
    Vertex<E> origin = getVertex(start);
    if (origin == null) return visited;

    queue.add(origin);
    visited.add(origin.data);

    while (!queue.isEmpty()) {
        Vertex<E> current = queue.poll();
        Edge<E> edge = current.adjacency;
        while (edge != null) {
            E dest = edge.destination.data;
            if (!visited.contains(dest)) {
                visited.add(dest);
                queue.add(edge.destination);
            }
            edge = edge.next;
        }
    }

    return visited;
}



//d 

public boolean esAutoComplementario() {
    GraphLink<E> complemento = grafoComplementario();
    return this.esIsomorfo(complemento);
}

private GraphLink<E> grafoComplementario() {
    GraphLink<E> comp = new GraphLink<>();

    // Copiar vértices
    Vertex<E> current = head;
    while (current != null) {
        comp.insertVertex(current.data);
        current = current.next;
    }

    // Para cada par de vértices (u, v), si no hay arista en this, insertarla en comp
    List<E> vertices = new ArrayList<>();
    current = head;
    while (current != null) {
        vertices.add(current.data);
        current = current.next;
    }

    for (E u : vertices) {
        for (E v : vertices) {
            if (!u.equals(v) && !this.searchEdge(u, v)) {
                comp.insertEdge(u, v); // o insertEdgeWeight con peso 1
            }
        }
    }

    return comp;
}


//e 






























