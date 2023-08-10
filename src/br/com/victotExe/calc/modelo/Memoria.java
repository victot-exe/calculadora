package br.com.victotExe.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private static final Memoria instancia = new Memoria();
	
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
	private String textoAtual = "";
	
	//	singleTon(padrão que permite apenas uma instância da classe
	private Memoria() {//método construtor privado
	}
	
	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void AdcionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
		
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String valor) {
		
		if("AC".equals(valor)) {
			textoAtual = "";
		} else {
			textoAtual += valor;
		}
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}
}
