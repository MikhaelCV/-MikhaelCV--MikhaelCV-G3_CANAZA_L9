//Ejercicio 03 del Laboratorio 09. Esta implementación es genérica y mantiene las interfaces similares a GraphLink.


public class VertexObj<V, E> {
    protected V info;
    protected int position;

    public VertexObj(V info, int position) {
        this.info = info;
        this.position = position;
    }

    public V getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VertexObj) {
            VertexObj<V, E> other = (VertexObj<V, E>) obj;
            return this.info.equals(other.info);
        }
        return false;
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
public class EdgeObj<V, E> {
    protected E info;
    protected VertexObj<V, E> endVertex1;
    protected VertexObj<V, E> endVertex2;
    protected int position;

    public EdgeObj(VertexObj<V, E> vert1, VertexObj<V, E> vert2, E info, int position) {
        this.endVertex1 = vert1;
        this.endVertex2 = vert2;
        this.info = info;
        this.position = position;
    }

    @Override
    public String toString() {
        return "(" + endVertex1 + " -- " + endVertex2 + ")";
    }
}

import java.util.*;

public class GraphListEdge<V, E> {
    ArrayList<VertexObj<V, E>> secVertex;
    ArrayList<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ArrayList<>();
        this.secEdge = new ArrayList<>();
    }

    public boolean searchVertex(V value) {
        return secVertex.stream().anyMatch(v -> v.getInfo().equals(value));
    }

    public void insertVertex(V value) {
        if (!searchVertex(value)) {
            secVertex.add(new VertexObj<>(value, secVertex.size()));
        }
    }

    private VertexObj<V, E> getVertex(V value) {
        for (VertexObj<V, E> v : secVertex) {
            if (v.getInfo().equals(value)) return v;
        }
        return null;
    }

    public boolean searchEdge(V v1, V v2) {
        VertexObj<V, E> a = getVertex(v1);
        VertexObj<V, E> b = getVertex(v2);
        if (a == null || b == null) return false;

        for (EdgeObj<V, E> e : secEdge) {
            if ((e.endVertex1.equals(a) && e.endVertex2.equals(b)) ||
                (e.endVertex1.equals(b) && e.endVertex2.equals(a))) {
                return true;
            }
        }
        return false;
    }

    public void insertEdge(V v1, V v2) {
        VertexObj<V, E> a = getVertex(v1);
        VertexObj<V, E> b = getVertex(v2);

        if (a != null && b != null && !searchEdge(v1, v2)) {
            secEdge.add(new EdgeObj<>(a, b, null, secEdge.size()));
        }
    }

    public void bfs(V start) {
        Set<V> visited = new HashSet<>();
        Queue<VertexObj<V, E>> queue = new LinkedList<>();
        VertexObj<V, E> origin = getVertex(start);

        if (origin == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        queue.add(origin);
        visited.add(origin.getInfo());

        while (!queue.isEmpty()) {
            VertexObj<V, E> current = queue.poll();
            System.out.print(current.getInfo() + " ");

            for (EdgeObj<V, E> edge : secEdge) {
                VertexObj<V, E> neighbor = null;
                if (edge.endVertex1.equals(current)) neighbor = edge.endVertex2;
                else if (edge.endVertex2.equals(current)) neighbor = edge.endVertex1;

                if (neighbor != null && !visited.contains(neighbor.getInfo())) {
                    visited.add(neighbor.getInfo());
                    queue.add(neighbor);
                }
            }
        }

        System.out.println(); // Salto final
    }
}

///////////////////////////////////////////////////////////
//ejercicio 08


//a 
public void mostrarFormal() {
    System.out.println("🔹 Vértices:");
    for (VertexObj<V, E> v : secVertex) {
        System.out.print(v.getInfo() + " ");
    }

    System.out.println("\n🔹 Aristas:");
    Set<String> mostradas = new HashSet<>();
    for (EdgeObj<V, E> e : secEdge) {
        String v1 = e.endVertex1.getInfo().toString();
        String v2 = e.endVertex2.getInfo().toString();
        String par = v1 + "-" + v2;
        String parInv = v2 + "-" + v1;
        if (!mostradas.contains(parInv)) {
            System.out.println(par);
            mostradas.add(par);
        }
    }
}


//b 

public void mostrarListaAdyacencia() {
    System.out.println("🔸 Lista de Adyacencia:");
    for (VertexObj<V, E> v : secVertex) {
        System.out.print(v.getInfo() + " → ");
        for (EdgeObj<V, E> e : secEdge) {
            if (e.endVertex1.equals(v)) {
                System.out.print(e.endVertex2.getInfo() + " ");
            } else if (e.endVertex2.equals(v)) {
                System.out.print(e.endVertex1.getInfo() + " ");
            }
        }
        System.out.println();
    }
}

//c 

public void mostrarMatrizAdyacencia() {
    int n = secVertex.size();
    int[][] matriz = new int[n][n];

    // Llenar matriz (no dirigido)
    for (EdgeObj<V, E> e : secEdge) {
        int i = secVertex.indexOf(e.endVertex1);
        int j = secVertex.indexOf(e.endVertex2);
        matriz[i][j] = 1;
        matriz[j][i] = 1;
    }

    System.out.println("🔹 Matriz de Adyacencia:");
    System.out.print("   ");
    for (VertexObj<V, E> v : secVertex) {
        System.out.print(v.getInfo() + " ");
    }
    System.out.println();

    for (int i = 0; i < n; i++) {
        System.out.print(secVertex.get(i).getInfo() + ": ");
        for (int j = 0; j < n; j++) {
            System.out.print(matriz[i][j] + " ");
        }
        System.out.println();
    }
}


//d 


//e 



















