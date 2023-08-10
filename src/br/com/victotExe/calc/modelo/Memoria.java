package br.com.victotExe.calc.modelo;

public class Memoria {

	private static final Memoria instancia = new Memoria();
	
	private String textoAtual = "";
	
	//	singleTon(padrão que permite apenas uma instância da classe
	private Memoria() {//método construtor privado
	}
	
	public static Memoria getInstancia() {
		return instancia;
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	
	
}
