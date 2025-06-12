GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertVertex("D");
g.insertEdge("A", "B");
g.insertEdge("A", "C");
g.insertEdge("B", "D");
g.insertEdge("C", "D");

System.out.println("BFS desde A:");
g.bfs("A");

System.out.println("Camino de A a D:");
System.out.println(g.bfsPath("A", "D"));

GraphLink<Integer> g = new GraphLink<>();

// Insertamos los vértices
g.insertVertex(1);
g.insertVertex(2);
g.insertVertex(3);
g.insertVertex(4);
g.insertVertex(5);

// Insertamos las aristas (no dirigido)
g.insertEdge(1, 2);
g.insertEdge(1, 3);
g.insertEdge(2, 4);
g.insertEdge(3, 5);

// Grafo visual:
/// 1 - 2
/// |    \
/// 3     4
///  \
///    5

System.out.println("🔁 BFS desde 1:");
g.bfs(1);  // Debería imprimir: 1 3 2 5 4 (según orden de inserción)

System.out.println("📍 Camino de 1 a 4:");
ArrayList<Integer> camino = g.bfsPath(1, 4);
System.out.println(camino);  // Debería imprimir algo como: [1, 2, 4]

GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertVertex("D");
g.insertEdgeWeight("A", "B", 1);
g.insertEdgeWeight("A", "C", 5);
g.insertEdgeWeight("B", "D", 3);
g.insertEdgeWeight("C", "D", 1);

System.out.println("¿Grafo conexo?: " + g.isConexo());

System.out.println("Camino más corto de A a D (ArrayList):");
System.out.println(g.shortPath("A", "D"));

System.out.println("Camino más corto de A a D (Stack):");
System.out.println(g.Dijkstra("A", "D"));


//prueba del ejer 5

GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertVertex("D");

g.insertEdge("A", "B");
g.insertEdge("B", "C");
g.insertEdge("C", "D");
// no se conecta D con A → esCamino

System.out.println("Grado de B: " + g.gradoNodo("B"));
System.out.println("¿Es camino?: " + g.esCamino());
System.out.println("¿Es ciclo?: " + g.esCiclo());
System.out.println("¿Es rueda?: " + g.esRueda());
System.out.println("¿Es completo?: " + g.esCompleto());

// test del ejercicio 6

GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertEdge("A", "B");
g.insertEdge("A", "C");
g.insertEdge("B", "C");

g.mostrarFormal();
g.mostrarListaAdyacencia();
g.mostrarMatrizAdyacencia();

//ejemplo del ejer 07
GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");

g.insertEdge("A", "B");
g.insertEdge("B", "C");

System.out.println("¿Es camino dirigido?: " + g.esCaminoDirigido());
System.out.println("¿Es ciclo dirigido?: " + g.esCicloDirigido());
System.out.println("¿Es rueda dirigida?: " + g.esRuedaDirigida());
System.out.println("¿Es completo dirigido?: " + g.esCompletoDirigido());

//ejercicio 08
GraphListEdge<String, Integer> g = new GraphListEdge<>();

g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertVertex("D");

g.insertEdge("A", "B");
g.insertEdge("A", "C");
g.insertEdge("B", "D");

g.mostrarFormal();
g.mostrarListaAdyacencia();
g.mostrarMatrizAdyacencia();

//ejermplo ejer 09

GraphLink<String> g = new GraphLink<>();
g.insertVertex("A");
g.insertVertex("B");
g.insertVertex("C");
g.insertEdge("A", "B");
g.insertEdge("B", "C");

System.out.println("¿Es conexo?: " + g.esConexo());
System.out.println("¿Es plano?: " + g.esPlano());
System.out.println("¿Es auto-complementario?: " + g.esAutoComplementario());

// Para probar isomorfismo
GraphLink<String> h = new GraphLink<>();
h.insertVertex("X");
h.insertVertex("Y");
h.insertVertex("Z");
h.insertEdge("X", "Y");
h.insertEdge("Y", "Z");

System.out.println("¿Es isomorfo con h?: " + g.esIsomorfo(h));






