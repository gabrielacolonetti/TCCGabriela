package calc.datastructures.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Aux {
	
	
	public static Map<Tupla, Integer> listaRelacoes = new HashMap<Tupla, Integer>();

	public void criarPares(Pessoa p, Publicacao pub){
		for (Pessoa pessoa : pub.getAutores()) {
			if(listaRelacoes.size() == 0){
				listaRelacoes.put(new Tupla(pessoa, p), 1);
			}else{
				 for (Tupla par : listaRelacoes.keySet()) {
					if(par.existeRelacao(pessoa, p)){
						/*obter o peso da aresta para ser incrementado*/
						listaRelacoes.put(par, listaRelacoes.get(par)+1);				
						
					}else{
						listaRelacoes.put(new Tupla(pessoa, p), 1);
					}
					
				}
			}
		}
		
		 
	}
	public static void main(String[] args){
		Pessoa p1 = new Pessoa("A","1");
		Pessoa p2 = new Pessoa("B","2");
		ArrayList<Pessoa> a = new ArrayList<>();
		a.add(p1);
		//a.add(p2);
		Tupla t = new Tupla(p1, p2);
		Publicacao p = new Publicacao("Titulo1","2015",a);
		
		
		System.out.println(p.getAutores().get(0).getNome());
		/*1-Publicacao vazia e adicionar autor 1; -> Verificar condicao vazia
		 * 2- Publicacao com 1 autor: Adicionar um segundo autor;
		 * 3- Publicacao com dois autores: e adicionar mais um;
		 * 4- COlocar uma relacao no mapa para verificar existencia;
		 * * 
		 * */
		Aux aux = new Aux();
		aux.criarPares(p2, p);
		for (Tupla par1 : aux.listaRelacoes.keySet()) {
			System.out.println(par1.toString());
			
		}
		
		
		
	}

}
