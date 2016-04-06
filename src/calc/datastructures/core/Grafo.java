package calc.datastructures.core;

import java.util.HashMap;
import java.util.Map;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Grafo {

	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	
	public Grafo(){
		
		
	}
	
	public void criaVertice(Map<Tupla, Integer> autores){
		String p1;
		String p2;
		//varre mapa de autores e seus pesos
		for (Map.Entry<Tupla, Integer> relacao : autores.entrySet()) {
			p1 = relacao.getKey().getP1().getNome();
			p2 = relacao.getKey().getP2().getNome();
			grafo.addVertex(p1);
			grafo.addVertex(p2);
			DefaultWeightedEdge e = new DefaultWeightedEdge();
			grafo.setEdgeWeight(e, relacao.getValue());
			grafo.addEdge(p1, p2, e);

		}
	}
	
}
