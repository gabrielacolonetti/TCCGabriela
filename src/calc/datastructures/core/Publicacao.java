package calc.datastructures.core;

import java.util.ArrayList;
import java.util.List;

public class Publicacao {
	private String ano;
	private String titulo;
	private List<Pessoa> autores = new ArrayList<Pessoa>();

	
	public Publicacao() {
		
		
	}
	public Publicacao(String a, String t, List<Pessoa> aut){
		this.ano = a;
		this.titulo = t;
		this.autores.addAll(aut);
		
	}


	
	public List<Pessoa> getAutores() {
		return autores;
	}



	public void setAutor(Pessoa autor) {
		System.out.println("autor: "+autor.getNome());
		this.autores.add(new Pessoa(autor.getNome(), autor.getId()));
	}



	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Override
	public boolean equals(Object obj) {
		Publicacao p = (Publicacao) obj;
		
		// TODO Auto-generated method stub
		
		return (this.titulo.equals(p.getTitulo()) && this.ano.equals(p.getAno()));
	}
}


