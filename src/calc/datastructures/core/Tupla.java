package calc.datastructures.core;

public class Tupla {
	private Pessoa p1, p2;
	
	public Tupla(Pessoa p1, Pessoa p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean existeRelacao(Pessoa p1, Pessoa p2){
		if(p1.getId() == p2.getId()){
			//lancar excecao
		}
		if(this.p1.getId().equals(p1.getId()) && this.p2.getId().equals(p2.getId())){
			return true;
		}
		if(this.p1.getId().equals(p2.getId()) && this.p2.getId().equals(p1.getId())){
			return true;
		}
		return false;
	}
	

	
	public Pessoa getP1() {
		return p1;
	}

	public void setP1(Pessoa p1) {
		this.p1 = p1;
	}

	public Pessoa getP2() {
		return p2;
	}

	public void setP2(Pessoa p2) {
		this.p2 = p2;
	}

	@Override
	public String toString() {		
		return this.p1.getNome()+"\t"+this.p2.getNome();
	}
	
	/*
	public static void main(String[] args){
		Pessoa p1 = new Pessoa("A","1");
		Pessoa p2 = new Pessoa("B","2");
		
		Tupla t = new Tupla(p1, p2);
		System.out.println(t.existeRelacao(p2, p2));
		
	}*/

	
}

